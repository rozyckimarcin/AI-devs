package pl.com.rozyccy.aidevs.openai.datamodel;

public record Usage(int prompt_tokens, int completion_tokens, int total_tokens) {}
