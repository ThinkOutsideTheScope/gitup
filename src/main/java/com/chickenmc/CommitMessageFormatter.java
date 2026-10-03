package com.chickenmc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommitMessageFormatter {
    private static final Map<String, Supplier<String>> FORMATTERS = new HashMap<>();

    static {
        FORMATTERS.put("%d", () -> String.valueOf(LocalDate.now().getDayOfMonth()));
        FORMATTERS.put("%i", () -> String.valueOf(LocalDate.now().getMonthValue()));
        FORMATTERS.put("%y", () -> String.valueOf(LocalDate.now().getYear()));
        FORMATTERS.put("%w", () -> String.valueOf(LocalDate.now().getDayOfWeek()));
        FORMATTERS.put("%s", () -> String.valueOf(LocalTime.now().getSecond()));
        FORMATTERS.put("%m", () -> String.valueOf(LocalTime.now().getMinute()));
        FORMATTERS.put("%h", () -> String.valueOf(LocalTime.now().getHour()));
    }

    public static String format(String input) {
        if (input == null) return null;
        Pattern pattern = Pattern.compile("%\\w");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll(matchResult -> {
            String token = matchResult.group();
            return (FORMATTERS.containsKey(token) ? FORMATTERS.get(token).get() : token);
        });
    }
}
