package pl.com.rozyccy.aidevs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;

public class Tasks {
  private static final Logger logger = LogManager.getLogger(Tasks.class);

  protected CloseableHttpClient httpClient;
  private final ObjectMapper objectMapper;

  private static final String AI_DEVS_URL = "https://zadania.aidevs.pl/";

  public Tasks() {
    this.httpClient = HttpClients.createDefault();
    this.objectMapper = new ObjectMapper();
  }

  public AIDevsTaskResponse getTokenForTask(String taskName, String apiKey) throws IOException {
    logger.info("Get AI-devs token for task name " + taskName);
    HttpPost httpPostTask = createHttpPostTokenRequest(taskName, Map.of("apikey", apiKey));
    return getResponseFromHttpClient(httpPostTask);
  }

  public AIDevsTaskResponse getTask(String token) throws IOException {
    logger.info("Get AI-devs task");
    HttpGet httpGetTask = new HttpGet(getUri("task", token));
    return getResponseFromHttpClient(httpGetTask);
  }

  public int postAnswer(String token, String answer) throws IOException {
    logger.info("Post AI-devs answer " + answer);
    HttpPost httpPostTask = createHttpPostRequest(token, answer);
    return getResponseCodeFromHttpClient(httpPostTask);
  }

  private HttpPost createHttpPostTokenRequest(String token, Map<String, String> map)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    HttpPost httpPostTask = new HttpPost(getUri("token", token));
    logger.info("JSON Answer object: " + objectMapper.writeValueAsString(map));
    httpPostTask.setEntity(
        new StringEntity(objectMapper.writeValueAsString(map), ContentType.APPLICATION_JSON));
    return httpPostTask;
  }

  private HttpPost createHttpPostRequest(String token, String answer) {
    HttpPost httpPostTask = new HttpPost(getUri("answer", token));
    logger.info("JSON Answer object: " + answer);
    httpPostTask.setEntity(new StringEntity(answer, ContentType.APPLICATION_JSON));
    return httpPostTask;
  }

  private String getUri(String page, String token) {
    return AI_DEVS_URL + page + "/" + token;
  }

  private AIDevsTaskResponse getResponseFromHttpClient(HttpRequestBase httpRequest) throws IOException {
    HttpResponse response = httpClient.execute(httpRequest);
    AIDevsTaskResponse token = objectMapper.readValue(EntityUtils.toString(response.getEntity()), AIDevsTaskResponse.class);
    logger.debug(
        "Response code: {}\nResponse body: {}",
        response.getStatusLine().getStatusCode(),
        token);
    return token;
  }

  private int getResponseCodeFromHttpClient(HttpRequestBase httpRequest) throws IOException {
    HttpResponse response = httpClient.execute(httpRequest);
    logger.debug(
        "Response code: {}\nResponse body: {}",
        response.getStatusLine().getStatusCode(),
        EntityUtils.toString(response.getEntity()));
    return response.getStatusLine().getStatusCode();
  }
}
