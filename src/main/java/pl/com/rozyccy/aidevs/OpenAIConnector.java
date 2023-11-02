package pl.com.rozyccy.aidevs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.openai.datamodel.OpenAIRequest;

public class OpenAIConnector {
  private static final Logger logger = LogManager.getLogger(OpenAIConnector.class);
  private static final String URL = "https://api.openai.com/v1/";
  private final String openAIKey;
  protected CloseableHttpClient httpClient;

  public OpenAIConnector(String openAIKey) {
    this.openAIKey = openAIKey;
    this.httpClient = HttpClients.createDefault();
  }

  public HttpResponse connectAndGetAnswer(String taskName, OpenAIRequest request) throws IOException {
    logger.info("Connecting to OpenAI API to get an answer to question: " + request);

    HttpPost httpPost = new HttpPost(URL + taskName);
    httpPost.addHeader("Authorization", "Bearer " + openAIKey);
    httpPost.addHeader("Content-Type", "application/json");
    httpPost.setEntity(new StringEntity(request.getJson(), StandardCharsets.UTF_8));
    return httpClient.execute(httpPost);
  }
}
