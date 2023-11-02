package pl.com.rozyccy.aidevs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/** Additional token extractor class. */
public class TokenExtractor {
  private static final Logger logger = LogManager.getLogger(TokenExtractor.class);

  /**
   * Method extracting value for json string object for specific key.
   *
   * @param jsonString json string object where should be key with its value
   * @param key key that will be searched in json object
   * @return value assigned to key in json object
   */
  public static String getValueForToken(String jsonString, String key) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);

      // Assuming the JSON structure contains a key field
      JsonNode tokenNode = jsonNode.get(key);

      if (tokenNode != null) {
        return tokenNode.asText();
      } else {
        // Token not found in JSON
        return null;
      }
    } catch (Exception e) {
      logger.error("Error in try to extract key value from json object.", e);
      return null;
    }
  }

  public static List<String> getListForToken(String jsonString, String key) throws JsonProcessingException {
    List<String> list = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(jsonString);
    JsonNode inputArray = rootNode.get(key);

    if (inputArray.isArray()) {
      for (JsonNode item : inputArray) {
        list.add(item.asText());
      }
    }
    return list;
  }

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
