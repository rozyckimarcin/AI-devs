package pl.com.rozyccy.aidevs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Additional token extractor class. */
public class TokenExtractor {

  public static boolean getFlaggedFromResult(String jsonString) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(jsonString);

    // Extract the "flagged" field
    return rootNode
            .path("results")
            .get(0)
            .path("flagged")
            .asBoolean();
  }

}
