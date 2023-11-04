package pl.com.rozyccy.aidevs.openai.datamodel;

public record Error(int code, String message, String param, String type) {}
