package ee.ut.jf2016.packer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackerImpl implements Packer {
    final int BUFFER_SIZE = 4096;
    final int UNCORRUPTED_BYTE = 42;
    private static final Logger log = LoggerFactory.getLogger(PackerImpl.class);
    @Override
    public void pack(Path inputDir, Path outputArchive) throws IOException {
        log.info("Packing {} into {}", inputDir, outputArchive);
        if (!Files.isDirectory(inputDir)) throw new IOException(); // for the non directory input
        List<Path> allFiles =
                Files.walk(inputDir)
                        .filter(Files::isRegularFile) // only get the regular files
                        .collect(Collectors.toList());
        DataInputStream in;
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(outputArchive, new OpenOption[0])));
        out.writeByte(UNCORRUPTED_BYTE);
        for (Path inputFile: allFiles) {
            out.writeUTF(inputDir.relativize(inputFile).toString().replace("\\","/")); // relative path of the current file to the input Directory
            out.writeLong(Files.size(inputFile));
            in = new DataInputStream(new BufferedInputStream(Files.newInputStream(inputFile, new OpenOption[0])));
            byte[] buffer = new byte[BUFFER_SIZE]; // read all of them with blocks of 4096 byte, avoiding memory space overflow
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length); // write all of them with blocks of 4096 byte, avoiding memory space overflow
            }
            in.close();
        }
        out.close();

    }

    @Override
    public void unpack(Path inputArchive, Path outputDir) throws IOException {
        log.info("Unpacking {} into {}", inputArchive, outputDir);
        if(Files.size(inputArchive)== 0) throw new IOException(); // if an empty archive file then we throw up
        DataInputStream  in = new DataInputStream(new BufferedInputStream(Files.newInputStream(inputArchive, new OpenOption[0])));
        byte firstByte = in.readByte();
        if(firstByte != UNCORRUPTED_BYTE) { //check if it is corrupted
            in.close();
            throw new IOException();
        }
        Files.createDirectories(outputDir); // firstly create such a file for the output
        DataOutputStream out;
        while(in.available() > 0){
            Path relativePath = Paths.get(in.readUTF());
            Path realPath = outputDir.resolve(relativePath);
            Files.createDirectories(realPath.getParent());
            out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(realPath, new OpenOption[0])));
            long sizeOfBytes = in.readLong();
            byte[] buffer = new byte[BUFFER_SIZE];
            long times =  (sizeOfBytes/ BUFFER_SIZE); // fileSize is this many times of the buffer size
            long remainder = (sizeOfBytes % BUFFER_SIZE);// and some residual bytes
            for (int i = 0; i < times; i++) { //therefore we read it times times with the size of buffer
                in.read(buffer);
                out.write(buffer, 0, BUFFER_SIZE);
            }
            buffer = new byte[(int) remainder];// and the remainder part to read exactly consume required amount of byte
            in.read(buffer);
            out.write(buffer);
            out.close();
        }
        in.close();
    }

}
