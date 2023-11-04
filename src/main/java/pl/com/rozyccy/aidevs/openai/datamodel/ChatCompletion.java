package pl.com.rozyccy.aidevs.openai.datamodel;

import java.util.List;

public record ChatCompletion(
    String id,
    String object,
    long created,
    String model,
    List<Choice> choices,
    Usage usage,
    Error error) {}
