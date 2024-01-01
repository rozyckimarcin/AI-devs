package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.WhisperText;
import pl.com.rozyccy.aidevs.openai.datamodel.request.WhisperInput;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhisperTask extends Task {
    public WhisperTask(String openAIKey) {
        super(openAIKey);
    }

    public StringAnswer getAnswer(String taskMessage) throws IOException {
        String mp3Url = extractUrl(taskMessage);
        logger.info("URL to MP3 file is: " + mp3Url);

        String localPath = getLocalPath("data", mp3Url);
        downloadMp3(mp3Url, localPath);
        logger.info("File downloaded to: " + localPath);

        HttpResponse response =
                executeRequestToOpenAIAPI(
                        "audio/transcriptions",
                        new WhisperInput(
                                OpenAIModelEnum.WHISPER_1.getModelName(), localPath));

        WhisperText responseStr =
                new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), WhisperText.class);
        logger.info("Response from OpenAI API:" + responseStr);

        return new StringAnswer(responseStr.text());
    }

    private String extractUrl(String message) {
        String regex = "\\b(https?|ftp|file):\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|]";
        String url = "";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            url = matcher.group();
        }
        return url;
    }

    public static void downloadMp3(String mp3Url, String savePath) throws IOException {
        URL url = new URL(mp3Url);
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public static String getLocalPath(String directory, String url) {
        // Split the URL by forward slash '/'
        String[] parts = url.split("/");

        // Get the last part of the array (after the last '/')
        return ".\\" + directory + "\\" + parts[parts.length - 1];
    }
}
