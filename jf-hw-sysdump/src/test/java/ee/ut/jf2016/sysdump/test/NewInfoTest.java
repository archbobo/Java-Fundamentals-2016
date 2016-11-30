package ee.ut.jf2016.sysdump.test;

import org.junit.BeforeClass;

import ee.ut.jf2016.sysdump.Info;
import ee.ut.jf2016.sysdump.SystemDumpImpl;

public class NewInfoTest extends BaseTest {

  private static Info data;

  @BeforeClass
  public static void init() throws Exception {
    data = new SystemDumpImpl().newInfo();
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
