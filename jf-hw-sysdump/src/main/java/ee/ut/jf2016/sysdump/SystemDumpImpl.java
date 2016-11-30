package ee.ut.jf2016.sysdump;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.ut.jf2016.xmlModels.SystemDump;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.MessageLoggers;
import org.zeroturnaround.exec.ProcessExecutor;

import javax.xml.bind.JAXB;

public class SystemDumpImpl implements ee.ut.jf2016.sysdump.SystemDump, Info {
    public SystemDumpImpl() {
        this.getSystemEnvironment();
        this.getSystemProperties();
        this.getSystemVersion();
    }

    private static final Logger log = LoggerFactory.getLogger(SystemDumpImpl.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public Map<String, String> systemEnvironment;
    public Map<String, String> systemProperties;
    public String systemVersion;
    @Override
    public Map<String, String> getSystemEnvironment() {
        log.info("getSystemEnvironment is called");
        systemEnvironment =  new TreeMap<>(System.getenv());
        return systemEnvironment;
    }

    @Override
    public Map<String, String> getSystemProperties() {
        log.info("getSystemProperties is called");
        TreeMap propertyMap = new TreeMap();
        Properties properties = System.getProperties();
        properties.stringPropertyNames().stream().forEach(p -> propertyMap.put(p, properties.getProperty(p)));
        systemProperties = propertyMap;
        return  systemProperties;
    }

    @Override
    public String getSystemVersion(){
        log.info("getSystemVersion is called");
        String version ;
        String[] commands;
        if(SystemUtils.IS_OS_WINDOWS) {
            commands = new String[]{"cmd", "/c", "ver"};
        } else {
            commands = new String[]{"uname", "-a"};
        }
        try {
            version = (new ProcessExecutor(commands)).readOutput(true).setMessageLogger(MessageLoggers.TRACE).execute().getOutput().getUTF8().trim();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
        systemVersion = version;
        return version;
    }

    /**
     * returns the current object since it already implements Info thereby encapsulated
     * @return
     * @throws Exception
     */
    @Override
    public Info newInfo() throws Exception {
        return this;
    }

    @Override
    public void writeXml(Info src, Path dest) throws Exception {
        log.info("writeXml is called");
        PrintWriter out = arrangeTheFileRequirements(dest);
        SystemDump xmlModel = new SystemDump(src);
        JAXB.marshal(xmlModel, out);
        out.close();
    }

    @Override
    public void writeJson(Info src, Path dest) throws Exception {
        PrintWriter out = arrangeTheFileRequirements(dest);
        out.write(gson.toJson(src));
        out.close();
    }

    /**
     * carries out the common tasks before creating a file
     * making sure that such a directory exists, o/w creates it
     * @param dest
     * @return
     * @throws Exception
     */
    public PrintWriter arrangeTheFileRequirements(Path dest) throws Exception {
        if(dest.toFile().isDirectory()) throw new Exception("dest cannot be a directory");
        if(!dest.getParent().toFile().exists()) Files.createDirectories(dest.getParent());
        return new PrintWriter(dest.toString());
    }

}
