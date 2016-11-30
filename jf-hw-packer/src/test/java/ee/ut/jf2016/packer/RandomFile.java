package ee.ut.jf2016.packer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RandomFile {

  private static final Logger log = LoggerFactory.getLogger(RandomFile.class);

  static Path create(Path file) throws IOException {
    Random random = new Random();
    byte[] buffer = new byte[4096];
    long limit = 100L * FileUtils.ONE_MB;
    int size = 0;
    log.debug("Generating random file at {}", file);
    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(file))) {
      while (size < limit) {
        random.nextBytes(buffer);
        out.write(buffer);
        size += buffer.length;
      }
    }
    log.debug("Wrote {} bytes to {}", size, file);
    return file;
  }

}
