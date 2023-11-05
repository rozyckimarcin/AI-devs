import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.tasks.*;

import java.io.IOException;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    executeTask(new TaskC01L01(), args);
    executeTask(new TaskC01L04T01(), args);
    executeTask(new TaskC01L04T02(), args);
    executeTask(new TaskC01L05(), args);
    executeTask(new TaskC02L02(), args);
  }

  private static void executeTask(Task task, String... parameters) throws IOException {
    logger.info("Start task {}", task.getClass());
    int responseCode = task.executeTask(parameters);
    logger.info("Result for task one {}", responseCode);
  }
}
