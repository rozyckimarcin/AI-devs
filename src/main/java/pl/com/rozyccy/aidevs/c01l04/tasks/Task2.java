package pl.com.rozyccy.aidevs.c01l04.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import pl.com.rozyccy.aidevs.Tasks;
import pl.com.rozyccy.aidevs.c01l04.ModerationTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.ModerationTaskAnswer;

import java.io.IOException;

public class Task2 extends Task {
  @Override
  public int executeTask(String... parameters) throws IOException {
    String openAIKey = parameters[1];
    Tasks tasks = new Tasks();
    ModerationTask moderationTask = new ModerationTask(openAIKey);
    AIDevsTaskResponse token = tasks.getTokenForTask("moderation", parameters[0]);
    AIDevsTaskResponse taskApiResponse = tasks.getTask(token.token());
    logger.info("Your task is: {}", taskApiResponse.msg());
    ModerationTaskAnswer answer = moderationTask.getAnswer(taskApiResponse.input());
    int responseCode =
        tasks.postAnswer(token.token(), new ObjectMapper().writeValueAsString(answer));
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
    return responseCode;
  }
}
