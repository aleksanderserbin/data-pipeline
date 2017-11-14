package me.aserbin.datapipeline.core;

import me.aserbin.datapipeline.middleware.Mapper;
import org.junit.Assert;

import java.util.function.Function;

/**
 * Provides test for {@link PipelineBuilder}
 * Uses {@link String} as a type for all tests.
 * Only tests builder functions, not pipeline, therefore
 * {@link PipelineBuilder#fork}, {@link PipelineBuilder#map } are out of the test.
 */
public class PipelineBuilderTest {

    private PipelineBuilder<String, String> getDefaultBuilder() {
        return PipelineBuilder.startFrom(String.class);
    }

    @org.junit.Test
    public void startFrom() throws Exception {
        Assert.assertNotNull(PipelineBuilder.startFrom(String.class));
    }

    @org.junit.Test
    public void thenWithEmptyPipelineTest() throws Exception {
        PipelineBuilder<String, String> pb = getDefaultBuilder();
        pb.then(new Mapper<>(String::toLowerCase));
        Assert.assertEquals(pb.getSize(), new Integer(1));
    }

    @org.junit.Test
    public void thenWithFilledPipelineTest() throws Exception {
        PipelineBuilder<String, String> pb = getDefaultBuilder();
        pb.then(new Mapper<>(String::toLowerCase))
          .then(new Mapper<>(String::toLowerCase));
        Assert.assertEquals(pb.getSize(), new Integer(2));
    }

    @org.junit.Test
    public void thenTypesTest() throws Exception {
        PipelineBuilder<String, String> pb = getDefaultBuilder();
        pb.then(new Mapper<>(Integer::getInteger))
            .then(new Mapper<>(i -> i * 0.1));
        Assert.assertEquals(pb.getSize(), new Integer(2));
    }

}