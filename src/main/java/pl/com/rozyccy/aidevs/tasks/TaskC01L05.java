package pl.com.rozyccy.aidevs.tasks;

import java.io.IOException;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

public class TaskC01L05 extends Task {
  @Override
  public int executeTask(String... parameters) throws IOException {
    Tasks tasks = new Tasks();
    AIDevsTaskResponse token = tasks.getTokenForTask("liar", parameters[0]);
    AIDevsTaskResponse response =
        tasks.postQuestion(token.token(), "task", " What is capital of Poland?");
    logger.info("Response code is: {}", response);
    int responseCode =
        tasks.postAnswer(
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
