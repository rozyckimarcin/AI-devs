package pl.com.rozyccy.aidevs.openai.datamodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatCompletionTest {

  private static final String CONTENT = "Bardzo ważny i istotny kontekst.";
  private static final String INPUT_JSON =
      "{\n"
          + "  \"id\": \"chatcmpl-8GY1tHxBd2QryeNafFVpoMcF65YZN\",\n"
          + "  \"object\": \"chat.completion\",\n"
          + "  \"created\": 1698954749,\n"
          + "  \"model\": \"gpt-3.5-turbo-0613\",\n"
          + "  \"choices\": [\n"
          + "    {\n"
          + "      \"index\": 0,\n"
          + "      \"message\": {\n"
          + "        \"role\": \"assistant\",\n"
          + "        \"content\": \""
          + CONTENT
          + "\"\n"
          + "      },\n"
          + "      \"finish_reason\": \"stop\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"usage\": {\n"
          + "    \"prompt_tokens\": 22,\n"
          + "    \"completion_tokens\": 633,\n"
          + "    \"total_tokens\": 655\n"
          + "  }\n"
          + "}";

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void mapToJson() {
    assertDoesNotThrow(() -> objectMapper.readValue(INPUT_JSON, ChatCompletion.class));
  }

  @Test
  void getContent() throws JsonProcessingException {
    ChatCompletion chatCompletion = objectMapper.readValue(INPUT_JSON, ChatCompletion.class);

    assertEquals(CONTENT, chatCompletion.choices().get(0).message().content());
  }
}
