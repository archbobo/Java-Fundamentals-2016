package ee.ut.jf2016.xmlModels;

import ee.ut.jf2016.sysdump.Info;

import java.util.Map;

/**
 * Created by Erdem on 9/28/2016.
 */
public class SystemDump {
    public ListHolder systemEnvironment = new ListHolder();
    public  ListHolder systemProperties = new ListHolder();
    public String systemVersion;
    public SystemDump(Info src) {
        src.getSystemEnvironment().forEach((K,V) -> this.systemEnvironment.entry.add(new MapEntry(K,V)));
        src.getSystemProperties().forEach((K,V) -> this.systemProperties.entry.add(new MapEntry(K,V)));
        this.systemVersion = src.getSystemVersion();
    }
}
