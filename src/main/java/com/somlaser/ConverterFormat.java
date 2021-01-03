package com.somlaser;

import java.util.List;

public enum ConverterFormat {
    LOWER_CASE,
    UPPER_CASE;

    public static List<String> getLowerCaseArgValues() {
        return List.of("l", "lower", "lower_case");
    }
}
