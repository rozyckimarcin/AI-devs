package pl.com.rozyccy.aidevs.tasks;

import java.io.IOException;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.c01l04.InputTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

public class TaskC02L02 extends Task {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    Tasks tasks = new Tasks();
    AIDevsTaskResponse token = tasks.getTokenForTask("inprompt", parameters[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());

    InputTask inputTask = new InputTask(openAIKey);

    StringAnswer answer = inputTask.getAnswer(taskApiResponse.input(), taskApiResponse.question());
    logger.info("Input task answer is: " + answer);

    int responseCode = tasks.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
