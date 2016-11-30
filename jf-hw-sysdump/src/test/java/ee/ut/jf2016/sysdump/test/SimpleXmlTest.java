package ee.ut.jf2016.sysdump.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ee.ut.jf2016.sysdump.Info;
import ee.ut.jf2016.sysdump.SystemDump;
import ee.ut.jf2016.sysdump.SystemDumpImpl;

public class SimpleXmlTest extends BaseTest {

  private static Path file;

  private static Info data;

  @BeforeClass
  public static void init() throws Exception {
    file = Files.createTempFile("sysdump", ".xml");
    SystemDump dump = new SystemDumpImpl();
    dump.writeXml(SimpleInfoFactory.getInfo(), file);
    data = XmlParser.parse(file);
  }

  @AfterClass
  public static void destroy() throws IOException {
    Files.deleteIfExists(file);
  }

  @Override
  protected Info getExpected() {
    return SimpleInfoFactory.getInfo();
  }

  @Override
  protected Info getActual() {
    return data;
  }

}
