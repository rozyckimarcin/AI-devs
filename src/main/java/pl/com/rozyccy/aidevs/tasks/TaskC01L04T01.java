package pl.com.rozyccy.aidevs.tasks;

import java.io.IOException;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.c01l04.ModerationTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.ModerationTaskAnswer;

public class TaskC01L04T01 extends Task {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    Tasks tasks = new Tasks();
    ModerationTask moderationTask = new ModerationTask(openAIKey);
    AIDevsTaskResponse token = tasks.getTokenForTask("moderation", parameters[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    ModerationTaskAnswer answer = moderationTask.getAnswer(taskApiResponse.input());
    int responseCode = tasks.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
