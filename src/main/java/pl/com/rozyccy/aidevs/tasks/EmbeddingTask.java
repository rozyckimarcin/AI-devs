package pl.com.rozyccy.aidevs.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.com.rozyccy.aidevs.OpenAIModelEnum;
import pl.com.rozyccy.aidevs.datamodel.EmbeddingAnswer;
import pl.com.rozyccy.aidevs.openai.datamodel.*;
import pl.com.rozyccy.aidevs.openai.datamodel.request.RequestEmbeddingInput;

public class EmbeddingTask extends Task {

  public EmbeddingTask(String openAIKey) {
    super(openAIKey);
  }

  public EmbeddingAnswer getAnswer(String sentence) throws IOException {
    HttpResponse response =
        executeRequestToOpenAIAPI(
            "embeddings",
            new RequestEmbeddingInput(
                OpenAIModelEnum.TEXT_EMBEDDING_ADA_002.getModelName(), sentence));

    Embedding responseStr =
        new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), Embedding.class);
    logger.info("Response from OpenAI API:" + responseStr);

    return new EmbeddingAnswer(responseStr.data().getFirst().embedding());
  }
}
