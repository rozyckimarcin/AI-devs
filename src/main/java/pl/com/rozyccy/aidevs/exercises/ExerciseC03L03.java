package pl.com.rozyccy.aidevs.exercises;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.tasks.WhoAmITask;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExerciseC03L03 extends Exercise {
    @Override
    public int executeTask(String... parameters) throws IOException, InterruptedException, URISyntaxException {
        String openAIKey = parameters[1];
        AIDevsApiExecutor aiDevsApiExecutor = new AIDevsApiExecutor();
        AIDevsTaskResponse token = aiDevsApiExecutor.getTokenForTask("whoami", parameters[0]);
        StringBuilder hintsList = new StringBuilder();
        StringAnswer answer;
        int i = 1;
        do {
            ExerciseC03L03TaskResponse taskApiResponse = aiDevsApiExecutor.getTask(token.token(), ExerciseC03L03TaskResponse.class);
            logger.info("Your task is: {}", taskApiResponse.msg());
            hintsList.append("\n");
            hintsList.append(i++);
            hintsList.append(" ");
            hintsList.append(taskApiResponse.hint());

            WhoAmITask whoAmITask = new WhoAmITask(openAIKey);
            answer = whoAmITask.getAnswer(hintsList.toString(), "Czy wiesz na 100% o kim może być mowa? Podaj imię i nazwisko.");
            logger.info("Input task answer is: {} ", answer);
            // AI Devs api has active rate limiting for one IP (max 4 request in 10 seconds)
            Thread.sleep(2000);
        } while (answer.answer().contains("Nie"));

        logger.info("Final answer is: {} ", answer);

        int responseCode = aiDevsApiExecutor.postAnswer(token.token(), answer);
        checkResponseCode(responseCode);
        return responseCode;
    }
}
