package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;
import java.net.URISyntaxException;
import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.tasks.PeopleTask;

public class ExerciseC03L05 extends Exercise {
  @Override
  public int executeTask(String... parameters)
      throws IOException, InterruptedException, URISyntaxException {
    String openAIKey = parameters[1];
    AIDevsApiExecutor aiDevsApiExecutor = new AIDevsApiExecutor();
    AIDevsTaskResponse token = aiDevsApiExecutor.getTokenForTask("people", parameters[0]);
    ExerciseC03L05TaskResponse taskApiResponse =
        aiDevsApiExecutor.getTask(token.token(), ExerciseC03L05TaskResponse.class);
    logger.info("Your task is: {}", taskApiResponse.msg());

    PeopleTask peopleTask = new PeopleTask(openAIKey);
    StringAnswer answer = peopleTask.getAnswer(taskApiResponse.question());

    int responseCode = aiDevsApiExecutor.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
