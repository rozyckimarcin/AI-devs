package pl.com.rozyccy.aidevs.openai.datamodel.request;

public record WhisperInput(String model, String path) implements OpenAIRequest {}
