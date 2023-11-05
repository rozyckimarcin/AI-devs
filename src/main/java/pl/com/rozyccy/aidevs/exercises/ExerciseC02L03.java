package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;
import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.tasks.EmbeddingTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.EmbeddingAnswer;

public class ExerciseC02L03 extends Exercise {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
    AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("embedding", parameters[0]);
    AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());

    EmbeddingTask embeddingTask = new EmbeddingTask(openAIKey);
    EmbeddingAnswer answer = embeddingTask.getAnswer("Hawaiian pizza");

    logger.info("Input task answer is: " + answer);

    int responseCode = AIDevsApiExecutor.postAnswer(token.token(), answer);
    checkResponseCode(responseCode);
    return responseCode;
  }
}