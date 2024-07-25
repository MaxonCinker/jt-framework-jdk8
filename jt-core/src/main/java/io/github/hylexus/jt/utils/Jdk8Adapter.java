package io.github.hylexus.jt.utils;

import java.util.*;
import java.util.stream.Collectors;

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

    public static <T> boolean optionalIsEmpty(Optional<T> optional) {
        return !optional.isPresent();
    }

    public static NoSuchElementException optionalOrElseThrow() {
        return new NoSuchElementException("No value present");
    }

    public static <E> Set<E> setOf(E... e) {
        return Arrays.stream(e).collect(Collectors.toSet());
    }

    public static <E> List<E> listOf(E... e) {
        return Arrays.stream(e).collect(Collectors.toList());
    }

    public static Map<String, Object> mapOf(Object... o) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < o.length; i += 2) {
            map.put(o[i].toString(), o[i + 1]);
        }
        return map;
    }

}
