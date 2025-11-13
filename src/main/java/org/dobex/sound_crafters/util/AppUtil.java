package org.dobex.sound_crafters.util;

import java.security.SecureRandom;

public class AppUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static String generateCode() {
        Integer randomNumber = AppUtil.SECURE_RANDOM.nextInt(1_000_000);
        return String.format("%06d", randomNumber);
    }
}
