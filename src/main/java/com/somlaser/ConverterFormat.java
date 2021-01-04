package com.somlaser;

import java.util.Arrays;
import java.util.List;

public enum ConverterFormat {
    LOWER_CASE,
    UPPER_CASE;

    public static List<String> getLowerCaseArgValues() {
        return Arrays.asList("l", "lower", "lower_case");
    }
}
