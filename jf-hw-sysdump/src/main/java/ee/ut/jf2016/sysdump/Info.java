package ee.ut.jf2016.sysdump;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * State of a system dump.
 */
public interface Info {

  /**
   * @return sorted map of all environment variables.
   */
  Map<String, String> getSystemEnvironment();

  /**
   * @return sorted map of all system properties.
   */
  Map<String, String> getSystemProperties();

  /**
   * @return output of a system-dependent command line tool giving some system info.
   *  On Windows: <code>cmd /c ver</code>
   *  On others: <code>uname -a</code>
   */
  String getSystemVersion();

}
