package unl.edu.ec.papershop.exception;

public class EncryptorException extends Exception {

    private static final long serialVersionUID = 1L;

    public EncryptorException() {
        super();
    }

    public EncryptorException(String message) {
        super(message);
    }

    public EncryptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptorException(Throwable cause) {
        super(cause);
    }
}
