package ee.ut.jf2016.packer;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoggingPacker implements Packer {

  private static final Logger log = LoggerFactory.getLogger(LoggingPacker.class);

  private final Packer target;

  LoggingPacker(Packer target) {
    this.target = target;
  }

  @Override
  public void pack(Path inputDir, Path outputArchive) throws IOException {
    try {
      target.pack(inputDir, outputArchive);
    }
    catch (Exception e) {
      log.trace("pack() failed", e);
      throw e;
    }
  }

  @Override
  public void unpack(Path inputArchive, Path outputDir) throws IOException {
    try {
      target.unpack(inputArchive, outputDir);
    }
    catch (Exception e) {
      log.trace("unpack() failed", e);
      throw e;
    }
  }

}
