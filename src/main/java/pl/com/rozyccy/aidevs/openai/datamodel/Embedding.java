package pl.com.rozyccy.aidevs.openai.datamodel;

import java.util.List;

public record Embedding(List<EmbeddingData> data, String model, String object, Usage usage) {}
