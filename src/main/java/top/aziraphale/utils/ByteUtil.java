package top.aziraphale.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteUtil {

    public static final int BIT_MASK_ALL_ONE = 0xFF;

    /**
     * convert long into byte arr
     * Big Endian
     * @param num
     * @return
     */
    public static byte[] longToByte(long num) {
        byte[] arr = new byte[8];
        arr[7] = (byte) ((num)       & BIT_MASK_ALL_ONE);
        arr[6] = (byte) ((num >>  8) & BIT_MASK_ALL_ONE);
        arr[5] = (byte) ((num >> 16) & BIT_MASK_ALL_ONE);
        arr[4] = (byte) ((num >> 24) & BIT_MASK_ALL_ONE);
        arr[3] = (byte) ((num >> 32) & BIT_MASK_ALL_ONE);
        arr[2] = (byte) ((num >> 40) & BIT_MASK_ALL_ONE);
        arr[1] = (byte) ((num >> 48) & BIT_MASK_ALL_ONE);
        arr[0] = (byte) ((num >> 56) & BIT_MASK_ALL_ONE);
        return arr;
    }

    /**
     * convert uuid into byte arr
     * @param uuid will replace character "-" with ""
     * @return
     */
    public static byte[] uuidToByte(String uuid) {
        if (uuid == null) {
            return null;
        }
        uuid = uuid.replace("-", "");
        char[] chars = uuid.toCharArray();
        // result byte array length
        int length = chars.length / 2;
        byte[] bytes = new byte[length];
        for (int offset = 0; offset < chars.length; offset += 2) {
            bytes[offset >> 1] = (byte) ((convertHex(chars[offset]) << 4) | convertHex(chars[offset + 1]));
        }
        return bytes;
    }

    public static void printByteArr(byte... arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("null");
        } else {
            for (byte sub : arr) {
                for (int offset = 7; offset >= 0; offset--) {
                    System.out.print((sub >> offset) & 1);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static int convertHex(char one) {
        switch (one) {
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'a':
            case 'A':
                return 10;
            case 'b':
            case 'B':
                return 11;
            case 'c':
            case 'C':
                return 12;
            case 'd':
            case 'D':
                return 13;
            case 'e':
            case 'E':
                return 14;
            case 'f':
            case 'F':
                return 15;
            default:
                throw new RuntimeException("invalid hex symbol");
        }
    }
}
