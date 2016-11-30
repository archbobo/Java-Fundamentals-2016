package org.zeroturnaround.jf.hw.remote;


import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

public class RemotePluginLoader extends ClassLoader {

    String pluginName;
    public RemotePluginLoader(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public Class<?> loadClass(String readMeUrl) throws ClassNotFoundException {
        Class clazz;
        try {
            clazz = getParent().loadClass(readMeUrl);
            return clazz;
        }
        catch (ClassNotFoundException e) {}
        byte[] classByteArray = null;
        Properties properties = null;
        try{
            InputStream response = setupConnection(readMeUrl);
            properties = new Properties();
            properties.load(response);
            String classUrl = readMeUrl.replaceAll("README\\.properties", this.pluginName+".png");
            response = setupConnection(classUrl);
            byte[] imageBytes = IOUtils.readFully(response, -1, true);
            int startIndex = findStartIndex(imageBytes);
            classByteArray = Arrays.copyOfRange(imageBytes,startIndex,imageBytes.length);
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return super.defineClass(properties.getProperty("class"),classByteArray,0,classByteArray.length);
    }

    private int findStartIndex(byte[] imageBytes){
        for (int i = 0; i <imageBytes.length ; i++) {
            if (isCAFEBABE(imageBytes, i)) return i;
        }
        return -1;
    }
    private InputStream setupConnection(String urlAdd) throws IOException {
        URL url = new URL(urlAdd);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }
    private boolean isCAFEBABE(byte[] arr, int index){
        return index <= arr.length-4 && arr[index] == -54 && arr[index+1] == -2 && arr[index+2]== -70 && arr[index+3]==-66;
    }
}
