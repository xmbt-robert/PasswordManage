package com.fx.passform;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by fan.xu on 2014/11/4.
 */
public class BootLoader {
    public static void main(final String[] args) throws Exception {
        // check that the lib folder exists
        File libRoot = new File(LIB_FOLDER);
        if (!libRoot.exists()) {
            throw new Exception("No 'lib' folder exists!");
        }
        // read all *.jar files in the lib folder to array
        File[] libs = libRoot.listFiles(new FileFilter() {
            public boolean accept(File dir) {
                String name = dir.getName().toLowerCase();
                return name.endsWith("jar") || name.endsWith("zip");
            }
        });

        URL[] urls = new URL[libs.length];
        // fill the urls array with URLs to library files found in libRoot
        for (int i = 0; i < libs.length; i++) {
            urls[i] = new URL("file", null, libs[i].getAbsolutePath());
        }
        // create a new classloader and use it to load our app.
        classLoader = new URLClassLoader(urls,
                Thread.currentThread().
                        getContextClassLoader()
        );
        // get the main method in our application to bring up the app.
        final Method mtd = classLoader.loadClass(APP_MAIN_CLASS).getMethod("main",
                new Class[]{String[].class});
        // Using thread to launch the main 'loop' so that the current Main method
        // can return while the app is starting
        new Thread(new Runnable() {
            public void run() {
                try {
                    mtd.invoke(null, new Object[]{args});
                } // forward the args
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AppMain").start();
        // Give the app some time to start before returning from main.
        // This doesn't delay the starting in any way
        Thread.sleep(1000);
    }

   private static final String LIB_FOLDER = "./WebRoot/WEB-INF/lib";
//    private static final String LIB_FOLDER = "D:\\WebRoot\\WEB-INF\\lib";
    private static final String APP_MAIN_CLASS = "com.fx.passform.PassFormServer";
    private static ClassLoader classLoader;
}
