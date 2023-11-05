package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.ChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestMessage;

public class InputTask extends Task {

  public InputTask(String openAIKey) {
    super(openAIKey);
  }

  public StringAnswer getAnswer(List<String> inputs, String question) throws IOException {

    var inputAsAMap = extractNameDescriptionPairs(inputs);
    var nameFromQuestion = extractCapitalizedWord(question);

    logger.info(
        "Information I have for name {} are: {}",
        nameFromQuestion,
        inputAsAMap.get(nameFromQuestion));

    HttpResponse response =
        executeRequestToOpenAIAPI(
            "chat/completions",
            new RequestChatCompletion(
                OpenAIModelEnum.GPT_3_5_TURBO.getModelName(),
                List.of(
                    new RequestMessage("system", inputAsAMap.get(nameFromQuestion)),
                    new RequestMessage("user", question))));

    ChatCompletion responseStr =
        new ObjectMapper()
            .readValue(EntityUtils.toString(response.getEntity()), ChatCompletion.class);
    logger.info("Response from OpenAI API:" + responseStr);

    return new StringAnswer(responseStr.choices().get(0).message().content());
  }

  public static Map<String, String> extractNameDescriptionPairs(List<String> inputs) {
    Map<String, String> resultMap = new HashMap<>();

    inputs.forEach(
        input -> {
          String[] sentences = input.split("(?=[A-Z])");

          for (String sentence : sentences) {
            Pattern pattern = Pattern.compile("^(\\w+) (.+)$");
            Matcher matcher = pattern.matcher(sentence);

            if (matcher.find()) {
              String name = matcher.group(1);
              String description = matcher.group(2);
              resultMap.put(name, name + " " + description);
            }
          }
        });
    return resultMap;
  }

  public static String extractCapitalizedWord(String input) {
    Pattern pattern = Pattern.compile("\\b[A-Z][a-z]+\\b");
    Matcher matcher = pattern.matcher(input);

    if (matcher.find()) {
      return matcher.group();
    }

    return null;
  }
}
