package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.zip.CRC32;

/**
 * @author Sheffery
 * @date 2021/12/19 12:48 PM
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CRC {

    public static int CHECK_SUM(byte[] bytes) {
        CRC32 check = new CRC32();
        check.update(bytes);
        return (int) check.getValue();
    }
}
