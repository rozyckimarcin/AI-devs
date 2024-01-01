package pl.com.rozyccy.aidevs.exercises;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.tasks.WhisperTask;

import java.io.IOException;

public class ExerciseC02L04 extends Exercise {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
    AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("whisper", parameters[0]);
    AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());

    WhisperTask whisperTask = new WhisperTask(openAIKey);
    StringAnswer answer = whisperTask.getAnswer(taskApiResponse.msg());

    logger.info("Input task answer is: " + answer);

    int responseCode = AIDevsApiExecutor.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}
