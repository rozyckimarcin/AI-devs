package pl.com.rozyccy.aidevs;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class OpenAIConnector {
  private static final Logger logger = LogManager.getLogger(OpenAIConnector.class);

  private final String openAIKey;
  private final OpenAIModelEnum model;

  protected CloseableHttpClient httpClient;

  public OpenAIConnector(String openAIKey, OpenAIModelEnum model) {
    this.openAIKey = openAIKey;
    this.model = model;
    this.httpClient = HttpClients.createDefault();
  }

  public HttpResponse connectAndGetAnswer(String message) throws IOException {
    logger.info("Connecting to OpenAI API to get an answer to question: " + message);

    HttpPost httpPost = new HttpPost("https://api.openai.com/v1/moderations");
    httpPost.addHeader("Authorization", "Bearer " + openAIKey);
    httpPost.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
    httpPost.setEntity(createMessage(message));
    return httpClient.execute(httpPost);
  }

  private StringEntity createMessage(String message) {
    return new StringEntity("{\"input\":\"" + message + "\"}", StandardCharsets.UTF_8);
  }

  private StringEntity createMessage2(String message) throws UnsupportedEncodingException {
    return new StringEntity(
        "{\n"
            + "   \"messages\": "
            + message
            + ",\n"
            + "   \"model\": \""
            + model.getModelName()
            + "\"\n"
            + "}");
  }
}
