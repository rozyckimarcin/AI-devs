package pl.com.rozyccy.aidevs.tasks;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.OpenAIConnector;
import pl.com.rozyccy.aidevs.openai.datamodel.request.OpenAIRequest;

public abstract class Task {
  protected static final Logger logger = LogManager.getLogger(Task.class);
  protected OpenAIConnector openAIConnector;

  public Task(String openAIKey) {
    this.openAIConnector = new OpenAIConnector(openAIKey);
  }

  protected HttpResponse executeRequestToOpenAIAPI(String taskName, OpenAIRequest request)
      throws IOException {
    return openAIConnector.connectAndGetAnswer(taskName, request);
  }
}
