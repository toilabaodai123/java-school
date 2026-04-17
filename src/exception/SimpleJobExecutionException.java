package exception;

import org.quartz.JobExecutionException;

public class SimpleJobExecutionException extends JobExecutionException {
    public SimpleJobExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
