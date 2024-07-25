package io.github.hylexus.jt.utils;

/**
 * @Author dujunliang
 * @Date 2024/7/25 16:14
 * @Description
 */
public class Jdk8Adapter {

    public static String stringRepeat(String str, int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

}
