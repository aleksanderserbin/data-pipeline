package me.aserbin.datapipeline.core;

import me.aserbin.datapipeline.exception.PipelineInvocationException;

import java.util.List;
import java.util.Map;

/**
 *  Pipeline is the central entity of data-pipeline project.
 *  It takes number of consumers, processors and producers to
 *  perform the necessary data operations.
 *
 *  {@link #run Run} method launches the given jobs and controlls
 *  pipeline invocation. It throws {@link PipelineInvocationException} on every exception,
 *  which is not handled by {@link PipelineJob}.
 *
 *  @see PipelineJob
 *  @see JobFailActions
 */
public class Pipeline<I, O> {

    private List<PipelineJobConfig> jobs;


    /**
     * Access is restricted to protected, as Pipeline instances
     * should only be created with {@link PipelineBuilder}, as it ensures
     * type compatibility at compile time.
     * @param jobs
     */
    protected Pipeline(List<PipelineJobConfig> jobs) {
        this.jobs = jobs;
    }

    /**
     * Runs the pipeline according to jobs passed via constructior.
     * Throws {@link PipelineInvocationException} if jobs {@link JobFailActions}
     * set to {@link JobFailActions#STOP STOP} and underlying {@link PipelineJob}
     * throws an exception or if {@link JobFailActions#IGNORE IGNORE} used with
     * not-{@link Producer} job.
     *
     * @param input
     * @return processed data
     */
    public O run(I input) {
        PipelineData result = new PipelineData(input);
        for (PipelineJobConfig jobConfig : jobs) {
            PipelineJob job = jobConfig.getJob();
            System.out.println("Running job: " + job.getClass().getName());
            try {
                result = job.run(result);
            } catch (Exception e) {
                switch (jobConfig.getOnFail()) {
                    case IGNORE:
                        if ( job instanceof Producer) {
                            continue;
                        } else {
                            throw new PipelineInvocationException("IGNORE strategy is only suitable for Producers");
                        }
                    case STOP:
                        throw new PipelineInvocationException(e);
                }
            }
        }
        return (O) result.getData();
    }


}
