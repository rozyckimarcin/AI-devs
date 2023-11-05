package pl.com.rozyccy.aidevs.openai.datamodel.request;

public record RequestEmbeddingInput(String model, String input) implements OpenAIRequest {}
