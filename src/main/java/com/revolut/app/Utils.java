package com.revolut.app;

import com.google.gson.Gson;
import spark.ResponseTransformer;

import java.util.UUID;

public class Utils {

    public static long generateUid() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return Utils::toJson;
    }
}
