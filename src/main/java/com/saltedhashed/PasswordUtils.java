package com.saltedhashed;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PasswordUtils {

    private static char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()".toCharArray();

    public static String getRandomPassword() {
        Random random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5 + random.nextInt(15); i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
