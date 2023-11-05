package pl.com.rozyccy.aidevs.tasks;

import java.io.IOException;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.c01l04.BloggerTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.BlogTaskAnswer;

public class TaskC01L04T02 extends Task {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    Tasks tasks = new Tasks();
    BloggerTask bloggerTask = new BloggerTask(openAIKey);
    AIDevsTaskResponse token = tasks.getTokenForTask("blogger", parameters[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    BlogTaskAnswer answer = bloggerTask.getAnswer(taskApiResponse.blog());
    int responseCode = tasks.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
