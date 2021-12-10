package top.aziraphale.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * UUID generator
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UUIDUtil {

    /**
     * @return XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
     */
    public static String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
