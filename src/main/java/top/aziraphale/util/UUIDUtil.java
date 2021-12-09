package top.aziraphale.util;

import java.util.UUID;

/**
 *
 */
public final class UUIDUtil {

    public static String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public byte[] toByte(String uuid) {
        if (uuid == null) {
            return new byte[0];
        }
        return uuid.getBytes();
    }

    private UUIDUtil() {
    }
}
