package me.aserbin.datapipeline.core;

import me.aserbin.datapipeline.exception.PipelineInvocationException;
import me.aserbin.datapipeline.middleware.FunctionalProducer;
import me.aserbin.datapipeline.middleware.Mapper;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Provides integration tests for {@link Pipeline}.
 */
public class PipelineTest {

    private static final String INIT_STRING = "test";
    private static final String INIT_NUMBER_STRING = "123";
    private static final String MODIFIED_STRING = "tESt";

    @Test
    public void runEmptyTest() throws Exception {
        PipelineBuilder.startFrom(String.class)
                .build().run("test");
    }

    @Test
    public void runTest() throws Exception {
        String result = PipelineBuilder.startFrom(String.class)
                .map(String::toUpperCase)
                .then(new Mapper<>(s -> s.replace('T', 't')))
                .build()
                .run(INIT_STRING);
        Assert.assertEquals(MODIFIED_STRING, result);
    }

    @Test(expected = PipelineInvocationException.class)
    public void runWithExceptionTest() {
        PipelineBuilder.startFrom(String.class)
                .then(new FunctionalProducer<>(s -> {throw new RuntimeException(s);}))
                .build()
                .run(INIT_STRING);
    }

    @Test
    public void runWithExceptionIgnoredTest() {
        String result = PipelineBuilder.startFrom(String.class)
                .map(String::toUpperCase)
                .then(new Mapper<>(s -> s.replace('T', 't')))
                .then(new FunctionalProducer<>(s -> {throw new RuntimeException(s);}), JobFailActions.IGNORE)
                .build()
                .run(INIT_STRING);
        Assert.assertEquals(MODIFIED_STRING, result);
    }

    @Test
    public void forkWithExceptionTest() {
        Pipeline child  = PipelineBuilder.startFrom(Integer.class)
                .then(new FunctionalProducer<>(i -> {
                    throw new RuntimeException(i.toString());
                }))
                .build();

        Pipeline parent = PipelineBuilder.startFrom(String.class)
                .map(Integer::getInteger)
                .fork(child)
                .build();

        parent.run(INIT_NUMBER_STRING);
    }


}