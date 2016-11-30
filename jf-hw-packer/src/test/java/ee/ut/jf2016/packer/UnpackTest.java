package ee.ut.jf2016.packer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class UnpackTest {

  @Rule
  public LoggingRule loggingRule = new LoggingRule();

  private final Packer packer = new LoggingPacker(new PackerImpl());

  private Path tempDir;

  private Path inputDir;

  private Path inputArchive;

  private Path outputDir;

  @Before
  public void init() throws IOException {
    tempDir = Files.createTempDirectory("jf-hw-packer");
    inputDir = Files.createDirectory(tempDir.resolve("input"));
    inputArchive = tempDir.resolve("archive");
    outputDir = tempDir.resolve("output");
  }

  @After
  public void destroy() throws IOException {
    FileUtils.forceDelete(tempDir.toFile());
  }

  // Failures

  @Test(expected = Exception.class)
  public void testNullArchive() throws IOException {
    packer.unpack(null, outputDir);
  }

  @Test(expected = Exception.class)
  public void testNullDir() throws IOException {
    packer.unpack(inputArchive, null);
  }

  @Test(expected = Exception.class)
  public void testMissingArchive() throws IOException {
    packer.unpack(tempDir.resolve("dir"), outputDir);
  }

  @Test(expected = Exception.class)
  public void testDirInsteadOfFile() throws IOException {
    packer.unpack(Files.createDirectory(inputArchive), outputDir);
  }

  @Test(expected = Exception.class)
  public void testFileInsteadOfDir() throws IOException {
    packer.unpack(inputArchive, Files.createFile(tempDir.resolve("foo")));
  }

  @Test(expected = Exception.class)
  public void testEmptyInput() throws IOException {
    packer.unpack(Files.createFile(inputArchive), outputDir);
  }

  @Test(expected = Exception.class)
  public void testInvalidArchive() throws IOException {
    packer.unpack(Files.write(inputArchive, new byte[] { 23 }), outputDir);
  }

  // Success

  @Test
  public void testEmptyArchive() throws IOException {
    unpackAndVerify();
  }

  @Test
  public void testSingleFile() throws IOException {
    Files.write(inputDir.resolve("foo"), "bar".getBytes(StandardCharsets.UTF_8));
    unpackAndVerify();
  }

  @Test
  public void testSingleFileInSubDir() throws IOException {
    Files.write(Files.createDirectory(inputDir.resolve("sub")).resolve("foo"), "123".getBytes(StandardCharsets.UTF_8));
    unpackAndVerify();
  }

  @Test
  public void testTwoFiles() throws IOException {
    Files.write(inputDir.resolve("foo"), "bar".getBytes(StandardCharsets.UTF_8));
    Files.write(Files.createDirectory(inputDir.resolve("sub")).resolve("hello"), "abcdef".getBytes(StandardCharsets.UTF_8));
    unpackAndVerify();
  }

  @Test
  public void testBigFile() throws IOException {
    RandomFile.create(inputDir.resolve("random"));
    unpackAndVerify();
  }

  private void unpackAndVerify() throws IOException {
    PackerTestUtil.unpackAndVerify(inputDir, tempDir, (inputArchive1, outputDir1) -> packer.unpack(inputArchive1, outputDir1));
  }

}
