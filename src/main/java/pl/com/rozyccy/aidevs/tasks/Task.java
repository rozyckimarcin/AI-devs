package pl.com.rozyccy.aidevs.tasks;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public abstract class Task {
  protected static final Logger logger = LogManager.getLogger(Task.class);

  public abstract int executeTask(String... parameters) throws IOException;

  public void checkResponseCode(int responseCode) {
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
  }
}
