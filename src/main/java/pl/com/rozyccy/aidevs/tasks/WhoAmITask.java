package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.StringAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.ChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestChatCompletion;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class WhoAmITask extends Task {

    public WhoAmITask(String openAIKey) {
        super(openAIKey);
    }

    public StringAnswer getAnswer(String input, String question) throws IOException, InterruptedException, URISyntaxException {
        String startInput = "O kogo chodzi? Gdy nie będziesz na 100% pewny o kogo może chodzić zwróć: 'Nie wiem'. Jeśli wiesz zwróć tylko imię i nazwisko. Lista podpowiedzi: ";
        HttpResponse response =
                executeRequestToOpenAIAPI(
                        "chat/completions",
                        new RequestChatCompletion(
                                OpenAIModelEnum.GPT_3_5_TURBO.getModelName(),
                                List.of(
                                        new RequestMessage("system", startInput + input),
                                        new RequestMessage("user", question))));

        ChatCompletion responseStr =
                new ObjectMapper()
                        .readValue(EntityUtils.toString(response.getEntity()), ChatCompletion.class);
        logger.info("Response from OpenAI API: {}", responseStr);

        return new StringAnswer(responseStr.choices().getFirst().message().content());
    }
}
