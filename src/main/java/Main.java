import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.exercises.*;

import java.io.IOException;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    executeTask(new ExerciseC01L01(), args);
    executeTask(new ExerciseC01L04T01(), args);
    executeTask(new ExerciseC01L04T02(), args);
    executeTask(new ExerciseC01L05(), args);
    executeTask(new ExerciseC02L02(), args);
    executeTask(new ExerciseC02L03(), args);
  }

  private static void executeTask(Exercise exercise, String... parameters) throws IOException {
    logger.info("Start exercise {}", exercise.getClass());
    int responseCode = exercise.executeTask(parameters);
    logger.info("Result for exercise {} {}", exercise.getClass(), responseCode);
  }
}
