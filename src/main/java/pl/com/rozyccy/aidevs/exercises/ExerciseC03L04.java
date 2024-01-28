package pl.com.rozyccy.aidevs.exercises;

import io.qdrant.client.grpc.Points;
import pl.com.rozyccy.aidevs.AIDevsApiExecutor;
import pl.com.rozyccy.aidevs.database.qdrant.QdrantSearcher;
import pl.com.rozyccy.aidevs.datamodel.AIDevsTaskResponse;
import pl.com.rozyccy.aidevs.datamodel.EmbeddingAnswer;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.tasks.EmbeddingTask;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExerciseC03L04 extends Exercise {
    @Override
    public int executeTask(String... parameters) throws IOException, InterruptedException, URISyntaxException {
        String openAIKey = parameters[1];
        AIDevsApiExecutor aiDevsApiExecutor = new AIDevsApiExecutor();
        AIDevsTaskResponse token = aiDevsApiExecutor.getTokenForTask("search", parameters[0]);
        ExerciseC03L04TaskResponse taskApiResponse = aiDevsApiExecutor.getTask(token.token(), ExerciseC03L04TaskResponse.class);
        logger.info("Your task is: {}", taskApiResponse.msg());

        /* Code to read in file, prepare embeddings and push them to qdrant DB
        JsonDataReader jsonDataReader = new JsonDataReader();
        List<UnknownFacts> unknownFactsList = jsonDataReader.readInData(new File(".//data//archiwum.json"));

        List<Point> pointsList = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            UnknownFacts unknownFacts = unknownFactsList.get(i);
            EmbeddingTask embeddingTask = new EmbeddingTask(openAIKey);
            EmbeddingAnswer embeddingAnswer = embeddingTask.getAnswer(unknownFacts.info());

            logger.info("Embedding answer is : {}", embeddingAnswer);
            pointsList.add(new Point(unknownFacts, embeddingAnswer.answer()));
        }

        QdrantInserter2 qdrantInserter = new QdrantInserter2();
        try {
            qdrantInserter.insertData(pointsList);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        */


        EmbeddingTask embeddingTask = new EmbeddingTask(openAIKey);
        EmbeddingAnswer embeddingQuestion = embeddingTask.getAnswer(taskApiResponse.question());

        QdrantSearcher qdrantSearcher = new QdrantSearcher();
        List<Points.ScoredPoint> scoredPoints = null;
        try {
            scoredPoints = qdrantSearcher.search(embeddingQuestion.answer());
        } catch (ExecutionException e) {
            logger.error("Error while connecting to qdrant database.", e);
        }

        logger.info("find scored points: {}", scoredPoints);

        String payload = scoredPoints.getFirst().getPayloadMap().get("unknownFact").getStringValue();
        logger.info("Received payload: {}", payload);
        String searchedUrl = extractUrl(payload);
        logger.info("Searched url: {}", searchedUrl);


        StringAnswer answer = new StringAnswer(searchedUrl);
        logger.info("Final answer is: {} ", answer);

        int responseCode = aiDevsApiExecutor.postAnswer(token.token(), answer);
        checkResponseCode(responseCode);
        return responseCode;
    }

    public static String extractUrl(String inputString) {
        String pattern = "url=([^,]+)";
        Pattern urlPattern = Pattern.compile(pattern);
        Matcher matcher = urlPattern.matcher(inputString);

        if (matcher.find()) {
            // Group 1 contains the URL
            return matcher.group(1);
        } else {
            // Return null if no URL is found
            return null;
        }
    }
}
