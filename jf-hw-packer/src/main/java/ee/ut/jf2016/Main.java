package ee.ut.jf2016;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import ee.ut.jf2016.packer.Packer;
import ee.ut.jf2016.packer.PackerImpl;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 3) {
            String command = args[0].toLowerCase(Locale.ENGLISH);
            Path p1 = Paths.get(args[1]);
            Path p2 = Paths.get(args[2]);
            Packer packer = new PackerImpl();
            switch (command) {
            case "pack":
                packer.pack(p1, p2);
                return;
            case "unpack":
                packer.unpack(p1, p2);
                return;
            }
        }
        System.out.println("Usage: java -jar jf2016-executable.jar ...");
        System.out.println("  pack <inputDir> <outputArchive>");
        System.out.println("  unpack <inputArchive> <outputDir>");
    }

}
