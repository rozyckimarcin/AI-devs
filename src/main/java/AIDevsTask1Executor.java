import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.Tasks;

import java.io.IOException;

import static pl.com.rozyccy.aidevs.TokenExtractor.getValueForToken;

public class AIDevsTask1Executor {

  private static final Logger logger = LogManager.getLogger(AIDevsTask1Executor.class);

  /**
   * Main method to resolve AI-devs tasks
   *
   * @param args first program argument should be api-key
   * @throws IOException if there is an exception
   */
  public static void main(String[] args) throws IOException {
    Tasks tasks = new Tasks();
    String token = tasks.getTokenForTask("helloapi", args[0]);
    String taskApiResponse = tasks.getTask(token);
    logger.info("Your task is: {}", getValueForToken(taskApiResponse, "msg"));
    String answer = getValueForToken(taskApiResponse, "cookie");
    int responseCode = tasks.postAnswer(token, answer);
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
  }
}
