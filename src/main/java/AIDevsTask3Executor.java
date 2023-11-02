import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.c01l04.BloggerTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.BlogTaskAnswer;

public class AIDevsTask3Executor {

  private static final Logger logger = LogManager.getLogger(AIDevsTask3Executor.class);

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
    BloggerTask bloggerTask = new BloggerTask(openAIKey);
    AIDevsTaskResponse token = tasks.getTokenForTask("blogger", args[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    BlogTaskAnswer answer = bloggerTask.getAnswer(taskApiResponse.blog());
    int responseCode =
        tasks.postAnswer(token.token(), new ObjectMapper().writeValueAsString(answer));
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
  }
}
