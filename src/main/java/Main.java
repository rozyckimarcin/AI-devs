import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.com.rozyccy.aidevs.c01l04.tasks.Task;
import pl.com.rozyccy.aidevs.c01l04.tasks.Task1;
import pl.com.rozyccy.aidevs.c01l04.tasks.Task2;
import pl.com.rozyccy.aidevs.c01l04.tasks.Task3;

import java.io.IOException;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
//    executeTask(new Task1(), args);
//    executeTask(new Task2(), args);
//    executeTask(new Task3(), args);
  }

  private static void executeTask(Task task, String... parameters) throws IOException {
    logger.info("Start task {}", task.getClass());
    int responseCode = task.executeTask(parameters);
    logger.info("Result for task one {}", responseCode);
  }
}
