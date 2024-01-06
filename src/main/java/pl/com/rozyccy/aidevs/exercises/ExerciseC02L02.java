package pl.com.rozyccy.aidevs.exercises;

import java.io.IOException;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.tasks.InputTask;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

public class ExerciseC02L02 extends Exercise {
    @Override
    public int executeTask(String... parameters) throws IOException {
        String openAIKey = parameters[1];
        AIDevsApiExecutor AIDevsApiExecutor = new AIDevsApiExecutor();
        AIDevsTaskResponse token = AIDevsApiExecutor.getTokenForTask("inprompt", parameters[0]);
        // TODO Can be created separate response class
        AIDevsTaskResponse taskApiResponse = AIDevsApiExecutor.getTask(token.token(), AIDevsTaskResponse.class);
        logger.info("Your task is: {}", taskApiResponse.msg());

        InputTask inputTask = new InputTask(openAIKey);

        StringAnswer answer = inputTask.getAnswer(taskApiResponse.input(), taskApiResponse.question());
        logger.info("Input task answer is: " + answer);

        int responseCode = AIDevsApiExecutor.postAnswer(token.token(), answer);
        checkResponseCode(responseCode);
        return responseCode;
    }
}
