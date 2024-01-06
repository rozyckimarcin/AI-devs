package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.tasks.BloggerTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.BlogTaskAnswer;

public class ExerciseC01L04T02 extends Exercise {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
    BloggerTask bloggerTask = new BloggerTask(openAIKey);
    AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("blogger", parameters[0]);
    // TODO Can be created separate response class
    AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(token.token(), AIDevsTaskResponse.class);
    logger.info("Your task is: {}", taskApiResponse.msg());
    BlogTaskAnswer answer = bloggerTask.getAnswer(taskApiResponse.blog());
    int responseCode = AIDevsApiExecutor.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
