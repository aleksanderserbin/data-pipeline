package me.aserbin.datapipeline.repositories;

import me.aserbin.datapipeline.model.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository component for {@link Dummy} entity.
 */
@Repository
public interface DummyRepository extends JpaRepository<Dummy, Integer> {

}
