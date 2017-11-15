package me.aserbin.datapipeline.core;

import me.aserbin.datapipeline.exception.ValidationException;

import java.util.function.Predicate;

/**
 * Specific case of {@link PipelineJob} with semantics of validation.
 * Designed for simple pipeline validations. Accepts predicate and
 * error message to return on predicate failure.
 *
 * @see Pipeline
 * @see PipelineJob
 */
public class Validation<T> implements PipelineJob<T, T> {

    private Predicate<T> condition;
    private String errorMessage;

    public Validation(Predicate<T> condition, String errorMessage) {
        this.condition = condition;
        this.errorMessage = errorMessage;
    }

    @Override
    public PipelineData<T> run(PipelineData<T> input) {
        if (!condition.test(input.getData())) {
            throw new ValidationException(errorMessage);
        }
        return input;
    }
}
