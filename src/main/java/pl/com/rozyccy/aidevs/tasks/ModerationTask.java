package pl.com.rozyccy.aidevs.tasks;

import static pl.com.rozyccy.aidevs.TokenExtractor.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.datamodel.ModerationTaskAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestModerationAPI;

public class ModerationTask extends Task {

  public ModerationTask(String openAIKey) {
    super(openAIKey);
  }

  public ModerationTaskAnswer getAnswer(List<String> inputs) {
    List<Integer> list = new ArrayList<>();

    inputs.forEach(
        input -> {
          try {
            HttpResponse response =
                executeRequestToOpenAIAPI("moderations", new RequestModerationAPI(input));
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
