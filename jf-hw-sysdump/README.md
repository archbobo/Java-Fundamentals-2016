Java Fundamentals - I/O 2 Homework
===========

Description
----------

Your task is to write a Java program which outputs system info as XML and JSON files. Implement the following interface

```
public interface SystemDump {

  /**
   * @return current system dump info.
   */
  Info newInfo() throws Exception;

  /**
   * Write given data into a file as XML.
   */
  void writeXml(Info src, Path dest) throws Exception;

  /**
   * Write given data into a file as JSON.
   */
  void writeJson(Info src, Path dest) throws Exception;

}

public interface Info {

  /**
   * @return sorted map of all environment variables.
   */
  Map<String, String> getSystemEnvironment();

  /**
   * @return sorted map of all system properties.
   */
  Map<String, String> getSystemProperties();

  /**
   * @return output of a system-dependent command line tool giving some system info.
   *  On Windows: <code>cmd /c ver</code>
   *  On others: <code>uname -a</code>
   */
  String getSystemVersion();

}
```

The project has a set of unit tests. To get the maximum points all those tests must pass. Existing tests cannot be altered.

Requirements
----------

1. Both Maps have to be sorted A-Z by keys.
2. Execute a proper command line tool depending on the OS and gather its output.
3. Use exsiting XML and JSON APIs (don't write a String directly).
4. Buffer data for better performance.
5. Close all resources properly.

Example JSON
------------

```
{  
   "systemEnvironment":{  
      "HOME":"/Users/mati",
      "MAVEN_OPTS":"-Xmx1024M"
   },
   "systemProperties":{  
      "java.specification.name":"Java Platform API Specification",
      "path.separator":":"
   },
   "systemVersion":"Darwin Stalker.local 15.6.0 Darwin Kernel Version 15.6.0: Mon Aug 29 20:21:34 PDT 2016; root:xnu-3248.60.11~1/RELEASE_X86_64 x86_64\\n"
}
```

Example XML
-----------

```
<systemDump>
  <systemEnvironment>
    <entry key="HOME">/Users/mati</entry>
    <entry key="MAVEN_OPTS">-Xmx1024M</entry>
  </systemEnvironment>
  <systemProperties>
    <entry key="java.specification.name">Java Platform API Specification</entry>
    <entry key="path.separator">:</entry>
  </systemProperties>
  <systemVersion>
    Darwin Stalker.local 15.6.0 Darwin Kernel Version 15.6.0: Mon Aug 29 20:21:34 PDT 2016; root:xnu-3248.60.11~1/RELEASE_X86_64 x86_64\n
  </systemVersion>
</systemDump>
```
