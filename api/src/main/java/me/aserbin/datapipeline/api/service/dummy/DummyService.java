package me.aserbin.datapipeline.api.service.dummy;

import me.aserbin.datapipeline.model.Dummy;

import java.util.List;

/**
 * Interface for service to work with dummy entity.
 */
public interface DummyService {

    void onPost(Dummy dummy);

    void onPostBatch(List<Dummy> dummies);

}
