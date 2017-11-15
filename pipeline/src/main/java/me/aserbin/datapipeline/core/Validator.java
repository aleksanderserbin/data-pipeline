package me.aserbin.datapipeline.core;

/**
 * Provides interface for more complex validators than
 * {@link Validation} do. Extends {@link PipelineJob}.
 *
 * Allows to write complicate logical clauses and executed at {@link Pipeline}.
 *
 * @see Pipeline
 * @see PipelineJob
 */
public interface Validator<T> extends PipelineJob<T, T> {

    void validate(T input);

    @Override
    default PipelineData<T> run(PipelineData<T> input) {
        validate(input.getData());
        return input;
    }
}
