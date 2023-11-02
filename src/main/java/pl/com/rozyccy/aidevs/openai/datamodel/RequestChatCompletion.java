package pl.com.rozyccy.aidevs.openai.datamodel;

import java.util.List;

public record RequestChatCompletion(String model, List<RequestMessage> messages)
    implements OpenAIRequest {}
