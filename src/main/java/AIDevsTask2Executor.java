import static pl.com.rozyccy.aidevs.TokenExtractor.getListForToken;
import static pl.com.rozyccy.aidevs.TokenExtractor.getValueForToken;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.c01l04.ModerationTask;

public class AIDevsTask2Executor {

  private static final Logger logger = LogManager.getLogger(AIDevsTask2Executor.class);

  /**
   * Main method to resolve AI-devs tasks
   *
   * @param args first program argument should be api-key
   * @throws IOException if there is an exception
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      logger.error("Please add api-key as an program argument");
      return;
    }
    String openAIKey = args[1];
    Tasks tasks = new Tasks();
    ModerationTask moderationTask = new ModerationTask(openAIKey);
    String token = tasks.getTokenForTask("moderation", args[0]);
    String taskApiResponse = tasks.getTask(token);
    logger.info("Your task is: {}", getValueForToken(taskApiResponse, "msg"));
    List<Integer> answer = moderationTask.getAnswer(getListForToken(taskApiResponse, "input"));
    int responseCode = tasks.postAnswer2(token, parseToJson(answer));
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
  }

  private static String parseToJson(List<Integer> answer) {
    StringBuilder stringBuffer = new StringBuilder("{\"answer\":[");
    for (int i = 0; i < answer.size() - 1; i++) {
      stringBuffer.append("\"").append(answer.get(i)).append("\",");
    }
    stringBuffer.append("\"").append(answer.getLast()).append("\"]}");
    logger.info("Effect: " + stringBuffer);
    return stringBuffer.toString();
  }
}
