package top.aziraphale.encrypt.delegate;

/**
 * @author Sheffery
 * @date 2021/12/19 5:33 PM
 */
@FunctionalInterface
public interface Hash {

    byte[] hash(byte[] data);
}
