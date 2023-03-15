package at.co.account.utils;

import java.util.Collection;
import java.util.Map;

public interface Empty {
    static boolean isEmpty(Object o) {
        return o == null;
    }
    static boolean isNotEmpty(Object o) {
        return o != null;
    }
    static boolean isEmpty(String o) {
        return o == null || o.equals("");
    }
    static boolean isNotEmpty(String o) {
        return o != null && !o.equals("");
    }
    static boolean isEmpty(Collection o) {
        return o == null || o.isEmpty();
    }
    static boolean isNotEmpty(Collection o) {
        return o != null && !o.isEmpty();
    }
    static boolean isEmpty(Map o) {
        return o == null || o.isEmpty();
    }
    static boolean isNotEmpty(Map o) {
        return o != null && !o.isEmpty();
    }
    static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
    static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }
    static boolean ifNotNull(final Object o) {
        return o != null;
    }
}