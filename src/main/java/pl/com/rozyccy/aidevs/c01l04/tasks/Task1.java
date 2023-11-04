package pl.com.rozyccy.aidevs.c01l04.tasks;

import java.io.IOException;
import org.apache.http.HttpStatus;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;

public class Task1 extends Task {

  @Override
  public int executeTask(String... parameters) throws IOException {
    Tasks tasks = new Tasks();
    AIDevsTaskResponse tokenResponse = tasks.getTokenForTask("helloapi", parameters[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(tokenResponse.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    int responseCode =
        tasks.postAnswer(
            tokenResponse.token(), "{\"answer\":\"" + taskApiResponse.cookie() + "\"}");
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
    return responseCode;
  }
}
