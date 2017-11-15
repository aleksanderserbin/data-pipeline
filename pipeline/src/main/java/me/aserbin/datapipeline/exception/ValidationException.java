package me.aserbin.datapipeline.exception;

/**
 * Runtime exception to be thrown on {@link me.aserbin.datapipeline.core.Validation}
 * failure.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Throwable t) {
        super(t);
    }
}
