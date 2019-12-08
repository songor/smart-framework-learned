package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作工具类
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("className: " + className + ", isInitialized: " + isInitialized);
            throw new RuntimeException("Load class failure", e);
        }
        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            LOGGER.error("packageName: " + packageName);
            throw new IllegalArgumentException("Parameter should not be empty");
        }
        Set<Class<?>> classSet = new HashSet<>();
        Enumeration<URL> urls;
        try {
            urls = getClassLoader().getResources(packageName.replace(".", "/"));
        } catch (IOException e) {
            LOGGER.error("packageName: " + packageName);
            throw new RuntimeException("Get resources failure", e);
        }
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String packagePath = url.getPath();
                addClass(classSet, packagePath, packageName);
            }
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                String fullName = packageName + "." + className;
                classSet.add(loadClass(fullName, false));
            } else {
                String subPackagePath = packagePath + "/" + file.getName();
                String subPackageName = packageName + "." + file.getName();
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

}
