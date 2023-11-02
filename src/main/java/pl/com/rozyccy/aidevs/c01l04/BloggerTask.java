package pl.com.rozyccy.aidevs.c01l04;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.OpenAIConnector;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.BlogTaskAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.ChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.RequestChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.RequestMessage;

public class BloggerTask {
  private static final Logger logger = LogManager.getLogger(BloggerTask.class);

  OpenAIConnector openAIConnector;

  public BloggerTask(String openAIKey) {
    this.openAIConnector = new OpenAIConnector(openAIKey);
  }

  public BlogTaskAnswer getAnswer(List<String> inputs) {
    Map<String, String> map = new LinkedHashMap<>();

    for (String key : inputs) {
      map.put(key, "");
    }

    inputs.parallelStream()
        .forEach(
            input -> {
              try {
                HttpResponse response =
                    openAIConnector.connectAndGetAnswer(
                        "chat/completions",
                        new RequestChatCompletion(
                            OpenAIModelEnum.GPT_3_5_TURBO.getModelName(),
                            List.of(new RequestMessage("user", input))));

                ChatCompletion responseStr =
                    new ObjectMapper()
                        .readValue(
                            EntityUtils.toString(response.getEntity()), ChatCompletion.class);
                logger.info("Response from OpenAI API:" + responseStr);
                map.put(input, responseStr.choices().get(0).message().content());
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            });

    return new BlogTaskAnswer(map.values().stream().toList());
  }
}
