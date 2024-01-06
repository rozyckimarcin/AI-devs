package pl.com.rozyccy.aidevs.exercises;

import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.tasks.ScraperTask;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExerciseC03L02 extends Exercise {
    @Override
    public int executeTask(String... parameters) throws IOException, InterruptedException, URISyntaxException {
        String openAIKey = parameters[1];
        AIDevsApiExecutor aiDevsApiExecutor = new AIDevsApiExecutor();
        AIDevsTaskResponse token = aiDevsApiExecutor.getTokenForTask("scraper", parameters[0]);
        ExerciseC03L02TaskResponse taskApiResponse = aiDevsApiExecutor.getTask(token.token(), ExerciseC03L02TaskResponse.class);
        logger.info("Your task is: {}", taskApiResponse.msg());

        ScraperTask scraperTask = new ScraperTask(openAIKey);
        StringAnswer answer = scraperTask.getAnswer(taskApiResponse.input(), taskApiResponse.question());

        logger.info("Input task answer is: {} ", answer);

        int responseCode = aiDevsApiExecutor.postAnswer(token.token(), answer);
        checkResponseCode(responseCode);
        return responseCode;
    }
}
