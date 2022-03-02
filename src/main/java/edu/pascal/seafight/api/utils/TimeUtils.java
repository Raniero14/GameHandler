package edu.pascal.seafight.api.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static int multiplyByTickDelay(int x, TimeUnit unit) {
        return (int) (unit.toSeconds(x) * 20);
    }

    public static int toNormalTime(int x) {
        return x / 50;
    }

}

