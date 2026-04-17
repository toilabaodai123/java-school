package exception;

public class SimpleException extends Exception {
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
