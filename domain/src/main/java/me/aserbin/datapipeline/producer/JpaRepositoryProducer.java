package me.aserbin.datapipeline.producer;

import me.aserbin.datapipeline.core.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * Generalized abstraction over {@link JpaRepository} to provide compatibility
 * with {@link me.aserbin.datapipeline.core.Pipeline}.
 * @param <V> Data type
 * @param <K> Key type
 *
 * @see Producer
 * @see me.aserbin.datapipeline.core.Pipeline
 * @see JpaRepository
 */
public class JpaRepositoryProducer<K extends Serializable, V> implements Producer<V> {

    private JpaRepository<V, K> repository;

    public JpaRepositoryProducer(JpaRepository<V, K> repository) {
        this.repository = repository;
    }

    public void produce(V input) {
        this.repository.save(input);
    }

}
