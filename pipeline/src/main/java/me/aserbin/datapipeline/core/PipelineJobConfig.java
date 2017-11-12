package me.aserbin.datapipeline.core;

/**
 * Encapsulates {@link PipelineJob} invocation config.
 *
 * @see PipelineJob
 * @see JobFailActions
 */
public class PipelineJobConfig {
    private PipelineJob job;
    private JobFailActions onFail;

    public PipelineJobConfig(PipelineJob job, JobFailActions onFail) {
        this.job = job;
        this.onFail = onFail;
    }

    public PipelineJobConfig() {
    }

    public PipelineJob getJob() {
        return job;
    }

    public void setJob(PipelineJob job) {
        this.job = job;
    }

    public JobFailActions getOnFail() {
        return onFail;
    }

    public void setOnFail(JobFailActions onFail) {
        this.onFail = onFail;
    }
}
