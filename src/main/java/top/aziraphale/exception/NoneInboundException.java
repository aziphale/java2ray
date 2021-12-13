package top.aziraphale.exception;

/**
 * no inbound config set
 * @author Sheffery
 * @date 2021/12/13 7:48 PM
 */
public class NoneInboundException extends RayBaseException {

    @Override
    public String getMessage() {
        return "there is no inbound configuration";
    }
}
