package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;
import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.tasks.ModerationTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.ModerationTaskAnswer;

public class ExerciseC01L04T01 extends Exercise {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
    ModerationTask moderationTask = new ModerationTask(openAIKey);
    AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("moderation", parameters[0]);
    AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    ModerationTaskAnswer answer = moderationTask.getAnswer(taskApiResponse.input());
    int responseCode = AIDevsApiExecutor.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
