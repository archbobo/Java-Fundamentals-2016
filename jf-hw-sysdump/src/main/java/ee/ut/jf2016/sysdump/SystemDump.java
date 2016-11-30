package ee.ut.jf2016.sysdump;

import java.nio.file.Path;

public interface SystemDump {

  /**
   * @return current system dump info.
   */
  Info newInfo() throws Exception;

  /**
   * Write given data into a file as XML.
   */
  void writeXml(Info src, Path dest) throws Exception;

  /**
   * Write given data into a file as JSON.
   */
  void writeJson(Info src, Path dest) throws Exception;

}
