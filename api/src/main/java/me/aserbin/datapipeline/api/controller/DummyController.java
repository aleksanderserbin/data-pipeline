package me.aserbin.datapipeline.api.controller;

import me.aserbin.datapipeline.model.Dummy;
import me.aserbin.datapipeline.api.service.dummy.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/v1/api")
public class DummyController {

    private DummyService dummyService;

    @Autowired
    public DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @RequestMapping(value = {"/dummy/", "/dummy"}, method = RequestMethod.POST)
    public ResponseEntity postDummyJson(@RequestBody Dummy dummy) {
        dummyService.onPost(dummy);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"/dummy/batch/", "/dummy/batch"}, method = RequestMethod.POST)
    public ResponseEntity postBatchDummyJson(@RequestBody List<Dummy> dummies) {
        dummyService.onPostBatch(dummies);
        return ResponseEntity.ok().build();
    }

    
}
