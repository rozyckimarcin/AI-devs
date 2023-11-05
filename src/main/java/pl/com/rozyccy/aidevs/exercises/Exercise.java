package pl.com.rozyccy.aidevs.exercises;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public abstract class Exercise {
  protected static final Logger logger = LogManager.getLogger(Exercise.class);

  public abstract int executeTask(String... parameters) throws IOException;

  public void checkResponseCode(int responseCode) {
    if (responseCode == HttpStatus.SC_OK) {
      logger.info("!!! SUCCESS !!!");
    }
  }
}
