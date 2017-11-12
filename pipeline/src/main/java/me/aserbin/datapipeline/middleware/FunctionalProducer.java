package me.aserbin.datapipeline.middleware;

import me.aserbin.datapipeline.core.Producer;

import java.util.function.Consumer;

/**
 * Pipeline producer interface, which takes single argument function {@link Consumer}
 * and applies it on its turn. The argument is data, which came from the previous
 * pipeline step.
 *
 * @see Producer
 * @see Consumer
 */
public class FunctionalProducer<I> implements Producer<I> {

    private Consumer<I> producerFunction;

    public FunctionalProducer(Consumer<I> producerFunction) {
        this.producerFunction = producerFunction;
    }

    @Override
    public void produce(I input) {
        this.producerFunction.accept(input);
    }
}
