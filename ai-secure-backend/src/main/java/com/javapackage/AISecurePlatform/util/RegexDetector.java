package com.javapackage.AISecurePlatform.util;

import java.util.regex.Pattern;

public class RegexDetector {

    public static boolean containsEmail(String text) {
        return Pattern.compile("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+").matcher(text).find();
    }

    public static boolean containsPhone(String text) {
        return Pattern.compile("\\b\\d{10}\\b").matcher(text).find();
    }

    public static boolean containsApiKey(String text) {
        return Pattern.compile("sk-[A-Za-z0-9]+").matcher(text).find();
    }

    public static boolean containsPassword(String text) {
        return Pattern.compile("(?i)password\\s*[:=]\\s*\\S+").matcher(text).find();
    }

    public static boolean containsError(String text) {
        return text.toLowerCase().contains("exception") || text.toLowerCase().contains("error");
    }
}
