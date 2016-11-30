package ee.ut.jf2016.packer;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingRule implements TestRule {

  private static final Logger log = LoggerFactory.getLogger(LoggingRule.class);

  @Override
  public Statement apply(Statement statement, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        log.info("Started {}", description);
        try {
          statement.evaluate();
        }
        finally {
          log.info("Finished {}\n", description);
        }
      }
    };
  }

}
