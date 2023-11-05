package pl.com.rozyccy.aidevs;

public enum OpenAIModelEnum {
  GPT_3_5_TURBO("gpt-3.5-turbo"),
  TEXT_EMBEDDING_ADA_002("text-embedding-ada-002");

  private final String modelName;

  OpenAIModelEnum(String modelName) {
    this.modelName = modelName;
  }

  public String getModelName() {
    return modelName;
  }
}
