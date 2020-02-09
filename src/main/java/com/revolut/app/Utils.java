package com.revolut.app;

import java.util.UUID;

public class Utils {

    public static long generateUid() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
