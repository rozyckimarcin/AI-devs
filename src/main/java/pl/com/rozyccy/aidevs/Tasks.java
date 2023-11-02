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
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static pl.com.rozyccy.aidevs.TokenExtractor.getValueForToken;

public class Tasks {
  private static final Logger logger = LogManager.getLogger(Tasks.class);

  protected CloseableHttpClient httpClient;

  private static final String AI_DEVS_URL = "https://zadania.aidevs.pl/";

  public Tasks() {
    this.httpClient = HttpClients.createDefault();
  }

  public String getTokenForTask(String taskName, String apiKey) throws IOException {
    logger.info("Get AI-devs token for task name " + taskName);
    HttpPost httpPostTask = createHttpPostRequest(taskName, "token", Map.of("apikey", apiKey));
    String responseStr = getResponseFromHttpClient(httpPostTask);
    return getValueForToken(responseStr, "token");
  }

  public String getTask(String token) throws IOException {
    logger.info("Get AI-devs task");
    HttpGet httpGetTask = new HttpGet(getUri("task", token));
    return getResponseFromHttpClient(httpGetTask);
  }

  public int postAnswer(String token, String answer) throws IOException {
    logger.info("Post AI-devs answer " + answer);
    HttpPost httpPostTask = createHttpPostRequest(token, "answer", Map.of("answer", answer));
    return getResponseCodeFromHttpClient(httpPostTask);
  }

  public int postAnswer2(String token, String answer) throws IOException {
    logger.info("Post AI-devs answer " + answer);
    HttpPost httpPostTask = createHttpPostRequest(token, "answer", answer);
    return getResponseCodeFromHttpClient(httpPostTask);
  }

  public int postAnswer(String token, List<Integer> answer) throws IOException {
    logger.info("Post AI-devs answer " + answer);
    HttpPost httpPostTask =
        createHttpPostRequest(token, "answer", Map.of("answer", String.valueOf(answer)));
    return getResponseCodeFromHttpClient(httpPostTask);
  }

  private HttpPost createHttpPostRequest(String token, String page, Map<String, String> map)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    HttpPost httpPostTask = new HttpPost(getUri(page, token));
    logger.info("JSON Answer object: " + objectMapper.writeValueAsString(map));
    httpPostTask.setEntity(
        new StringEntity(objectMapper.writeValueAsString(map), ContentType.APPLICATION_JSON));
    return httpPostTask;
  }

  private HttpPost createHttpPostRequest(String token, String page, String answer) {
    HttpPost httpPostTask = new HttpPost(getUri(page, token));
    logger.info("JSON Answer object: " + answer);
    httpPostTask.setEntity(new StringEntity(answer, ContentType.APPLICATION_JSON));
    return httpPostTask;
  }

  private String getUri(String page, String token) {
    return AI_DEVS_URL + page + "/" + token;
  }

  private String getResponseFromHttpClient(HttpRequestBase httpRequest) throws IOException {
    HttpResponse response = httpClient.execute(httpRequest);
    String responseStr = EntityUtils.toString(response.getEntity());
    logger.debug(
        "Response code: {}\nResponse body: {}",
        response.getStatusLine().getStatusCode(),
        responseStr);
    return responseStr;
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
