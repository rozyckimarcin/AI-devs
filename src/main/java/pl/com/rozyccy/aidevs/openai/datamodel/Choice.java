package pl.com.rozyccy.aidevs.openai.datamodel;

public record Choice(int index, Message message, String logprobs, String finish_reason) {}
