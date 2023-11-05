package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.BlogTaskAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.ChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestMessage;

public class BloggerTask extends Task {

  public BloggerTask(String openAIKey) {
    super(openAIKey);
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
                    executeRequestToOpenAIAPI(
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
