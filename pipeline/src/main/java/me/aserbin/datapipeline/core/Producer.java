package me.aserbin.datapipeline.core;

/**
 *  Particular case of {@link PipelineJob}, which causes side effects, such as
 *  file write or database persist. Having made its job, Producer returns
 *  the same data as it got to the pipeline. Producers can be made optional with
 *  {@link JobFailActions}, while constructing the pipeline: producer failure
 *  will not affect the whole pipeline on failure.
 *
 *  Producer interface requires {@link #produce produce} to be implemented and provides
 *  default behaviour for {@link #run(PipelineData) run} method, inherited from
 *  {@link PipelineJob}.
 *
 *  @see PipelineJob
 *  @see JobFailActions
 *  @see PipelineData
 *  @see Pipeline
 */
public interface Producer<I> extends PipelineJob<I, I> {

    void produce(I input);

    @Override
    default PipelineData<I> run(PipelineData<I> input) {
        produce(input.getData());
        return input;
    }
}
