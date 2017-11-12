package me.aserbin.datapipeline.core;

/**
 * Container for {@link PipelineJob} result or any data, which is handled
 * by {@link Pipeline}.
 *
 * @see Pipeline
 * @see PipelineJob
 */
public class PipelineData<T> {
    private T data;

    public PipelineData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
