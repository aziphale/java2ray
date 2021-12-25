package top.aziraphale.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sheffery
 * @date 2021/12/19 3:19 PM
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteUtil {

    public static final int BIT_MASK_ALL_ONE = 0xFF;

    /**
     * convert byte arr into ASCII string
     * @param bytes
     * @return
     */
    public static String toString(byte... bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        char[] chars = new char[bytes.length];
        for (int index = 0; index < bytes.length; index++) {
            chars[index] = (char) bytes[index];
        }
        return new String(chars);
    }

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

    /**
     * combine byte arrays together
     */
    public static byte[] copy(byte[] ori, int repeat) {
        if (ori == null || ori.length == 0 || repeat == 0) {
            return new byte[0];
        }
        byte[] array = new byte[ori.length * repeat];
        for (int i = 0; i < array.length; i++) {
            array[i] = ori[i % repeat];
        }
        return array;
    }

    /**
     * combine byte arrays together
     */
    public static byte[] concat(byte[] prev, byte[] next) {
        int split = (prev == null ? 0 : prev.length);
        int totalLength = split + (next == null ? 0 : next.length);
        byte[] array = new byte[totalLength];
        if (prev != null) {
            System.arraycopy(prev, 0, array, 0, prev.length);
        }
        if (next != null) {
            System.arraycopy(next, 0, array, split, next.length);
        }
        return array;
    }

    /**
     * return head bytes in byte array
     * @param src
     * @param length
     * @return
     */
    public static byte[] head(byte[] src, int length) {
        if (src == null) {
            return new byte[length];
        }
        byte[] array = new byte[length];
        if (src.length <= length) {
            System.arraycopy(src, 0, array, 0, src.length);
        } else {
            System.arraycopy(src, 0, array, 0, array.length);
        }
        return array;
    }

    /**
     * return last bytes in byte array
     * @param src
     * @param length
     * @return
     */
    public static byte[] tail(byte[] src, int length) {
        if (src == null) {
            return new byte[length];
        }
        byte[] array = new byte[length];
        if (src.length <= length) {
            System.arraycopy(src, 0, array, length - src.length, src.length);
        } else {
            System.arraycopy(src, src.length - length, array, 0, length);
        }
        return array;
    }

    /**
     * print every bit in byte array
     * only for develop
     * @param array
     */
    public static void printByteArr(byte... array) {
        if (array == null || array.length == 0) {
            System.out.println("null");
        } else {
            int posLength = Integer.toString(array.length - 1).length();
            for (int i = 0; i < array.length; i++) {
                byte sub = array[i];
                System.out.printf("[%" + posLength + "d]", i + 1);
                for (int offset = 7; offset >= 0; offset--) {
                    System.out.print((sub >> offset) & 1);
                }
                System.out.print(" ");
                if ((i + 1) % 8 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            List<String> list = new ArrayList<>();
            for (byte sub : array) {
                list.add("(byte) " + String.valueOf(sub));
            }
            // quick declaim byte array
            System.out.println("byte[] array = new byte[]{" + String.join(", ", list) + "};");
            System.out.println();
        }
    }

    private static int convertHex(char one) {
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
