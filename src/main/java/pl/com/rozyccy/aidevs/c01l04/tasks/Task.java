package pl.com.rozyccy.aidevs.c01l04.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public abstract class Task {
  protected static final Logger logger = LogManager.getLogger(Task.class);

  public abstract int executeTask(String... parameters) throws IOException;
}
