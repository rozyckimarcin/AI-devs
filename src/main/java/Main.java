import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.exercises.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
    executeTask(new ExerciseC01L01(), args);
    executeTask(new ExerciseC01L04T01(), args);
    executeTask(new ExerciseC01L04T02(), args);
    executeTask(new ExerciseC01L05(), args);
    executeTask(new ExerciseC02L02(), args);
    executeTask(new ExerciseC02L03(), args);
    executeTask(new ExerciseC02L04(), args);
    executeTask(new ExerciseC02L05(), args);
    executeTask(new ExerciseC03L01(), args);
    executeTask(new ExerciseC03L02(), args);
  }

  private static void executeTask(Exercise exercise, String... parameters) throws IOException, InterruptedException, URISyntaxException {
    logger.info("Start exercise {}", exercise.getClass());
    int responseCode = exercise.executeTask(parameters);
    logger.info("Result for exercise {} {}", exercise.getClass(), responseCode);
  }
}
