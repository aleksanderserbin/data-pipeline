package me.aserbin.datapipeline.middleware;

import me.aserbin.datapipeline.core.PipelineData;
import me.aserbin.datapipeline.core.PipelineJob;

import java.util.function.Function;

/**
 * Custom pipeline processor similiar to map function. Used to change data or to
 * map the data into another object space.
 * Takes a {@link Function}, which is applied to data, retrieved from previous
 * pipeline steps and returns the result of the function, wrapped into {@link PipelineData}
 *
 * @see PipelineJob
 * @see PipelineData
 * @see me.aserbin.datapipeline.core.Pipeline
 * @see Function
 */
public class Mapper<I, O> implements PipelineJob<I, O> {

    private Function<I, O> mapper;

    public Mapper(Function<I, O> mapFunction) {
        this.mapper = mapFunction;
    }

    @Override
    public PipelineData<O> run(PipelineData<I> input) {
        return new PipelineData<O>(this.mapper.apply(input.getData()));
    }
}
