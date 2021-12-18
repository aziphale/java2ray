package top.aziraphale.exception;

/**
 * @author Sheffery
 * @date 2021/12/18 1:53 PM
 */
public class FieldSoLongException extends RayBaseException {
    public FieldSoLongException(String field, int maxLength) {
        super(field + " is too long, the max length is " + maxLength);
    }
}
