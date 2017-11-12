package me.aserbin.datapipeline.core;

import me.aserbin.datapipeline.middleware.FunctionalProducer;
import me.aserbin.datapipeline.middleware.Mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *  API to build {@link Pipeline} with respect to the types.
 *
 *  @see Pipeline
 */
public class PipelineBuilder<T> {

    private List<PipelineJobConfig> jobs;

    private PipelineBuilder(List<PipelineJobConfig> jobs) {
        this.jobs = jobs;
    }

    private PipelineBuilder() {
        this.jobs = new ArrayList<>();
    }

    /**
     * Sets the type of the objects passed into the pipeline. Used
     * instead of constructor.
     *
     * @param data Class type of wanted data
     * @return {@link PipelineBuilder} typed to data param
     */
    public static <T> PipelineBuilder<T> startFrom(Class<T> data) {
        return new PipelineBuilder<T>();
    }

    /**
     * Adds job to the pipeline. Does not take into consideration {@link JobFailActions} and sets default to
     * {@link JobFailActions#STOP}.
     * @param job {@link PipelineJob} to execute
     * @param <O> Output type of the job
     * @return {@link PipelineBuilder} typed to O
     */
    public <O> PipelineBuilder<O> then(PipelineJob<T, O> job) {
        return then(job, JobFailActions.STOP);
    }

    /**
     * Adds job to the pipeline with the defined {@link JobFailActions}.
     * @param job {@link PipelineJob} to execute
     * @param onFail Behavioral strategy on fail
     * @param <O> Output type of the job
     * @return {@link PipelineBuilder} typed to O
     */
    public <O> PipelineBuilder<O> then(PipelineJob<T, O> job, JobFailActions onFail) {
        this.jobs.add(new PipelineJobConfig(job, onFail));
        return new PipelineBuilder<>(this.jobs);
    }

    /**
     * Creates fork of the current pipeline and initializes it
     * with the data on the current step. Forked pipeline is run
     * on another thread.
     *
     * TODO: Add callback to fork to control result
     *
     * Parent pipeline does not wait until child pipeline finishes.
     * @param pipeline {@link Pipeline} to fork
     * @return the same {@link PipelineBuilder}
     */
    public PipelineBuilder<T> fork(Pipeline<T, ?> pipeline) {
        this.then(new FunctionalProducer<T>(data -> {
            new Thread(() -> pipeline.run(data)).start();
        }));
        return this;
    }

    /**
     * Adds map job to the current pipeline. Actually is a shortcut to
     * adding {@link Mapper} processor manually.
     * @param mapFunction function to apply
     * @return {@link PipelineBuilder} typed to O
     */
    public <O> PipelineBuilder<O> map(Function<T, O> mapFunction) {
        return this.then(new Mapper<>(mapFunction));
    }

    /**
     * Creates the {@link Pipeline} instance with all the jobs  to be run.
     * @return {@link Pipeline}
     */
    public Pipeline<? super Object, T> build() {
        return new Pipeline<>(this.jobs);
    }

    /**
     * @return number of jobs added to pipeline.
     */
    public Integer getSize() {
        return this.jobs.size();
    }


}
