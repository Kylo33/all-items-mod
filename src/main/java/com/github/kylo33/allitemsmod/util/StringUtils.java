package com.github.kylo33.allitemsmod.util;

public class StringUtils {
    public static String unformat(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < input.length()) {
            if (input.charAt(i) == '§')
                i += 2;
            else
                result.append(input.charAt(i++));
        }
        return result.toString();
    }
}
