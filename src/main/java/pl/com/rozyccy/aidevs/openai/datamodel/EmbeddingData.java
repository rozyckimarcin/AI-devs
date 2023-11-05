package pl.com.rozyccy.aidevs.openai.datamodel;

import java.util.List;

public record EmbeddingData(List<Double> embedding, int index, String object) {}
