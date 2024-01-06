package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.ChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ScraperTask extends Task {

    public static final int MAX_RETRIES = 10;

    public ScraperTask(String openAIKey) {
        super(openAIKey);
    }

    public StringAnswer getAnswer(String input, String question) throws IOException, InterruptedException, URISyntaxException {
        String pageContent = downloadFileWithRetry(input);
        logger.info("Content from input page: {}", pageContent);

        HttpResponse response =
                executeRequestToOpenAIAPI(
                        "chat/completions",
                        new RequestChatCompletion(
                                OpenAIModelEnum.GPT_3_5_TURBO.getModelName(),
                                List.of(
                                        new RequestMessage("system", pageContent),
                                        new RequestMessage("user", question))));

        ChatCompletion responseStr =
                new ObjectMapper()
                        .readValue(EntityUtils.toString(response.getEntity()), ChatCompletion.class);
        logger.info("Response from OpenAI API:" + responseStr);

        return new StringAnswer(responseStr.choices().getFirst().message().content());
    }

    private static String downloadFileWithRetry(String fileUrl) throws IOException, InterruptedException, URISyntaxException {
        for (int retryCount = 1; retryCount <= MAX_RETRIES; retryCount++) {
            try {
                return downloadFileAsString(fileUrl);
            } catch (IOException e) {
                if (retryCount == MAX_RETRIES) {
                    // If it's the last retry attempt, rethrow the exception
                    throw e;
                } else {
                    // Log the exception and retry after a delay
                    logger.info("Retry # {}:{}", retryCount, e.getMessage());
                    Thread.sleep(1000); // Sleep for 1 second before retrying (adjust as needed)
                }
            }
        }
        throw new IOException("Max retries reached");
    }

    private static String downloadFileAsString(String fileUrl) throws IOException, URISyntaxException {
        URI uri = new URI(fileUrl);
        URL url = uri.toURL();
        URLConnection connection = url.openConnection();

        // Set the User-Agent header to simulate a request from a web browser
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.4567.89 Safari/537.36");

        // Open a BufferedReader to read the content of the file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;

            // Read each line from the file and append it to the StringBuilder
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Return the downloaded content as a String
            return content.toString();
        }
    }
}
