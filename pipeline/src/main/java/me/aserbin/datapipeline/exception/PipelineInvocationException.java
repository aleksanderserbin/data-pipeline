package me.aserbin.datapipeline.exception;

/**
 * Exception to be thrown when one of the {@link me.aserbin.datapipeline.core.Pipeline}
 * jobs fails.
 *
 * @see me.aserbin.datapipeline.core.Pipeline
 */
public class PipelineInvocationException extends RuntimeException {
    public PipelineInvocationException(Exception e) {
        super(e);
    }
    public PipelineInvocationException(String message) {
        super(message);
    }
}
