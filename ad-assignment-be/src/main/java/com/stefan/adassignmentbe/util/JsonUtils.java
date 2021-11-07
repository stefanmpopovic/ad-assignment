package com.stefan.adassignmentbe.util;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static <T> T parseJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        var arrayClass = Array.newInstance(clazz, 0).getClass();
        var array = (T[]) new Gson().fromJson(json, arrayClass);

        return Arrays.asList(array);
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
}
