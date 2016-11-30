package org.zeroturnaround.jf.hw.remote;

import java.util.HashMap;
import java.util.Map;

import org.zeroturnaround.jf.hw.Plugin;

public class RemotePluginManager {

  public static String[] findAllPlugins() {
    return findAllPluginInfos().keySet().toArray(new String[] {});
  }

  private static Map<String, String> findAllPluginInfos() {
    return new HashMap() {
      {
        put("NomNomNomPlugin", "https://raw.github.com/zeroturnaround/jf-hw-classloaders/master/plugins-remote/NomNomNomPlugin/README.properties");
        put("ChickenPlugin", "https://raw.github.com/zeroturnaround/jf-hw-classloaders/master/plugins-remote/ChickenPlugin/README.properties");
        put("HeadAndShouldersPlugin", "https://raw.github.com/zeroturnaround/jf-hw-classloaders/master/plugins-remote/HeadAndShouldersPlugin/README.properties");
      }
    };
  }

  public static Plugin getPluginInstance(String pluginName) {
    Map<String, String> allPlugins = findAllPluginInfos();
    String remoteAddress = allPlugins.get(pluginName);
    RemotePluginLoader cl = new RemotePluginLoader(pluginName);
    Class clazz ;
    try {
      clazz = cl.loadClass(remoteAddress);
    }
    catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      return (Plugin) clazz.newInstance();
    }
    catch (InstantiationException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }

  }
}
