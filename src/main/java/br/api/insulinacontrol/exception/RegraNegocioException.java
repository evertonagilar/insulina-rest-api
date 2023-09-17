package exception;

public class RegraNegocioException extends Exception {

    public RegraNegocioException(String format, Object ...args){
        super(String.format(format, args));
    }

    public RegraNegocioException(String message) {
        super(message);
    }
}
