package pl.com.rozyccy.aidevs.c01l04;

import static pl.com.rozyccy.aidevs.TokenExtractor.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.OpenAIConnector;
import pl.com.rozyccy.aidevs.datamodel.ModerationTaskAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.RequestModerationAPI;

public class ModerationTask {
  private static final Logger logger = LogManager.getLogger(ModerationTask.class);

  private final String openAIKey;

  public ModerationTask(String openAIKey) {
    this.openAIKey = openAIKey;
  }

  public ModerationTaskAnswer getAnswer(List<String> inputs) {
    List<Integer> list = new ArrayList<>();
    OpenAIConnector openAIConnector = new OpenAIConnector(openAIKey);

    inputs.forEach(
        input -> {
          try {
            HttpResponse response =
                openAIConnector.connectAndGetAnswer("moderations", new RequestModerationAPI(input));
            String responseStr = EntityUtils.toString(response.getEntity());
            logger.info("Response from OpenAI API:" + responseStr);
            list.add(getFlaggedFromResult(responseStr) ? 1 : 0);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    return new ModerationTaskAnswer(list);
  }
}
