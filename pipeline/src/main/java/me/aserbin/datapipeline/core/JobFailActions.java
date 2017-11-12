package me.aserbin.datapipeline.core;

/**
 * Set of strategies to apply when one of pipeline's
 * jobs fails. Available options are {@link #STOP STOP} and {@link #IGNORE IGNORE}.
 *
 * When {@link #STOP STOP} option is used, pipeline will throw the
 * {@link me.aserbin.datapipeline.exception.PipelineInvocationException} on every Exception,
 * which is not handled by {@link PipelineJob}.
 *
 * {@link #IGNORE IGNORE} option is only applicable to {@link Producer}, as they do not transform
 * the given data, but only making side effects with it. If IGNORE is used with
 * {@link PipelineJob}, which does not implement {@link Producer}, the
 * {@link me.aserbin.datapipeline.exception.PipelineInvocationException} will be thrown.
 *
 * These startegies can be specified while constructing the pipeline with {@link PipelineBuilder}.
 *
 * @see PipelineJob
 * @see PipelineBuilder
 * @see me.aserbin.datapipeline.exception.PipelineInvocationException
 */
public enum JobFailActions {

    STOP,
    IGNORE
}
