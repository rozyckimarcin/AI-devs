package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

public class ExerciseC01L01 extends Exercise {

  @Override
  public int executeTask(String... parameters) throws IOException {
    AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
    AIDevsTaskResponse tokenResponse = AIDevsApiExecutor.getTokenForTask("helloapi", parameters[0]);
    AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(tokenResponse.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    int responseCode =
        AIDevsApiExecutor.postAnswer(tokenResponse.token(), new StringAnswer(taskApiResponse.cookie()));
    checkResponseCode(responseCode);
    return responseCode;
  }
}
