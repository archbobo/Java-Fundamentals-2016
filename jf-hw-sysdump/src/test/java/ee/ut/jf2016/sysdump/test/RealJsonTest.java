package ee.ut.jf2016.sysdump.test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.google.gson.Gson;
import ee.ut.jf2016.sysdump.Info;
import ee.ut.jf2016.sysdump.SystemDump;
import ee.ut.jf2016.sysdump.SystemDumpImpl;

public class RealJsonTest extends BaseTest {

  private static Path file;

  private static InfoImpl data;

  @BeforeClass
  public static void init() throws Exception {
    file = Files.createTempFile("sysdump", ".json");
    SystemDump dump = new SystemDumpImpl();
    dump.writeJson(RealInfoFactory.getInfo(), file);
    try (Reader reader = Files.newBufferedReader(file)) {
      data = new Gson().fromJson(reader, InfoImpl.class);
    }
  }

  @AfterClass
  public static void destroy() throws IOException {
    Files.deleteIfExists(file);
  }

  @Override
  protected Info getExpected() {
    return RealInfoFactory.getInfo();
  }

  @Override
  protected Info getActual() {
    return data;
  }

}
