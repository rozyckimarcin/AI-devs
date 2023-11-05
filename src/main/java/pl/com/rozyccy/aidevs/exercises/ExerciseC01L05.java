package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;
import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

public class ExerciseC01L05 extends Exercise {
  @Override
  public int executeTask(String... parameters) throws IOException {
    AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
    AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("liar", parameters[0]);
    AIDevsTaskResponse response =
        AIDevsApiExecutor.postQuestion(token.token(), "task", " What is capital of Poland?");
    logger.info("Response code is: {}", response);
    int responseCode =
        AIDevsApiExecutor.postAnswer(
            token.token(), checkIfAnswerIsOnTopic(response.answer(), "Warsaw", "Poland"));
    checkResponseCode(responseCode);
    return responseCode;
  }

  private StringAnswer checkIfAnswerIsOnTopic(String answer, String... checkWords) {
    for (String word : checkWords) {
      if (answer.contains(word)) {
        return new StringAnswer("YES");
      }
    }
    return new StringAnswer("NO");
  }
}
