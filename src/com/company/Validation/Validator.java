package com.company.Validation;

public class Validator {

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static int parsePositiveInt(String s) {
        int x = Integer.parseInt(s);
        if (x <= 0) throw new IllegalArgumentException();
        return x;
    }
}
