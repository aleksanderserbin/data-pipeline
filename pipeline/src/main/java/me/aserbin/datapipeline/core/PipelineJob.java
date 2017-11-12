package me.aserbin.datapipeline.core;


/**
 * Highest level abstraction of {@link Pipeline} member.
 * Encapsulates any action with the data. Pipeline job manages
 * {@link PipelineData} objects, which can be treated as a monadic
 * container.
 *
 * Provides {@link #run run} method to be implemented.
 *
 * @see Pipeline
 * @see Producer
 */
public interface PipelineJob<I, O> {

    PipelineData<O> run(PipelineData<I> input);

}
