package pl.com.rozyccy.aidevs.tasks;

import java.io.IOException;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

public class Task1 extends Task {

  @Override
  public int executeTask(String... parameters) throws IOException {
    Tasks tasks = new Tasks();
    AIDevsTaskResponse tokenResponse = tasks.getTokenForTask("helloapi", parameters[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(tokenResponse.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    int responseCode =
        tasks.postAnswer(tokenResponse.token(), new StringAnswer(taskApiResponse.cookie()));
    checkResponseCode(responseCode);
    return responseCode;
  }
}
