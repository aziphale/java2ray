package top.aziraphale.utils;

import java.util.Random;

public class RandomUtil {

    private static final Random INSTANCE = new Random();

    /**
     * set every bit in byte arr 0 or 1
     * @param bytes
     */
    public static void diceByte(final byte[] bytes) {
        INSTANCE.nextBytes(bytes);
    }

    public static byte nextByte() {
        return (byte) INSTANCE.nextInt();
    }

    public static int nextInt() {
        return INSTANCE.nextInt();
    }
}
