import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;

import java.io.IOException;

public class AIDevsTask1Executor {

  private static final Logger logger = LogManager.getLogger(AIDevsTask1Executor.class);

  /**
   * Main method to resolve AI-devs tasks
   *
   * @param args first program argument should be api-key
   * @throws IOException if there is an exception
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      logger.error("Please add api-key as an program argument");
      return;
    }
    Tasks tasks = new Tasks();
    AIDevsTaskResponse tokenResponse = tasks.getTokenForTask("helloapi", args[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(tokenResponse.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    int responseCode = tasks.postAnswer(tokenResponse.token(), taskApiResponse.cookie());
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
  }
}
