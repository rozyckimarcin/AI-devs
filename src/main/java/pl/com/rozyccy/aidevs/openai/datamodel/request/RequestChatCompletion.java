package pl.com.rozyccy.aidevs.openai.datamodel.request;

import java.util.List;

public record RequestChatCompletion(String model, List<RequestMessage> messages)
    implements OpenAIRequest {}
