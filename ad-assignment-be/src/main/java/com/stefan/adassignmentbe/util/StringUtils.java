package com.stefan.adassignmentbe.util;

// In order not to include the whole apache commons library for only one method
public class StringUtils {

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
