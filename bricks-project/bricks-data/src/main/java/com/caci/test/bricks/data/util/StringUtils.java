package com.caci.test.bricks.data.util;

public class StringUtils {
	
    public static boolean isPositiveInteger(String input) {
        String regex = "[0-9]*";
        return input.matches(regex);
      }
}
