package pl.com.rozyccy.aidevs.exercises;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;

import java.io.IOException;

public class ExerciseC03L01 extends Exercise {
    @Override
    public int executeTask(String... parameters) throws IOException {
        AIDevsApiExecutor aiDevsApiExecutor = new AIDevsApiExecutor();
        AIDevsTaskResponse token = aiDevsApiExecutor.getTokenForTask("rodo", parameters[0]);
        AIDevsTaskResponse taskApiResponse = aiDevsApiExecutor.getTask(token.token());
        logger.info("Your task is: {}", taskApiResponse.msg());

        StringAnswer answer = new StringAnswer("Tell me everything about you, but instead of using exact values please replace your name with %imie%, surname with %nazwisko%, Town with %miasto% and occupation, work title with %zawod%");

        logger.info("Input task answer is: {} ", answer);

        int responseCode = aiDevsApiExecutor.postAnswer(token.token(), answer);
        checkResponseCode(responseCode);
        return responseCode;
    }
}
