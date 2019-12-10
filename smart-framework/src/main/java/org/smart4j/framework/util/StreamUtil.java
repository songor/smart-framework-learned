package org.smart4j.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 常用的流操作
 */
public final class StreamUtil {

    /**
     * 从输入流中获取字符串
     */
    public static String convert(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Convert Stream to String failure", e);
        }
        return sb.toString();
    }

}
