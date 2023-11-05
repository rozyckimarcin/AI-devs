package pl.com.rozyccy.aidevs.datamodel;

import java.util.List;

public record EmbeddingAnswer(List<Double> answer) implements Answer {}
