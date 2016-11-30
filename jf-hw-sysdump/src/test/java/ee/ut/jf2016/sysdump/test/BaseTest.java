package ee.ut.jf2016.sysdump.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import ee.ut.jf2016.sysdump.Info;

 abstract class BaseTest {

  protected abstract Info getExpected();

  protected abstract Info getActual();

  @Test
  public void testSystemEnvironment() {
    Assert.assertEquals(getExpected().getSystemEnvironment(), getActual().getSystemEnvironment());
  }

  @Test
  public void testSystemProperties() {
    Assert.assertEquals(getExpected().getSystemProperties(), getActual().getSystemProperties());
  }

  @Test
  public void testSystemEnvironmentOrder() {
    Assert.assertEquals(new ArrayList<>(getExpected().getSystemEnvironment().entrySet()), new ArrayList<>(getActual().getSystemEnvironment().entrySet()));
  }

  @Test
  public void testSystemPropertiesOrder() {
    Assert.assertEquals(new ArrayList<>(getExpected().getSystemProperties().entrySet()), new ArrayList<>(getActual().getSystemProperties().entrySet()));
  }

  @Test
  public void testSystemInfo() {
    Assert.assertEquals(getExpected().getSystemVersion().trim(), getActual().getSystemVersion().trim());
  }

}
