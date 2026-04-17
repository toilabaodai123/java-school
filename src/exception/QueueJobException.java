package exception;

public class QueueJobException extends Exception {
    public QueueJobException(String message) {
        super(message);
    }

    public QueueJobException(String message, Throwable cause) {
        super(message, cause);
    }
}
