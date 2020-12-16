package com.liouxb.boot.es.controller;

import com.liouxb.boot.es.domain.TestEs;
import com.liouxb.boot.es.repository.TestEsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liouwb
 */
@RestController
@RequestMapping("test_es")
public class TestEsController {
    @Autowired
    private TestEsRepository testEsRepository;

    @GetMapping("test")
    public String test() {
        return "hello world";
    }

    @GetMapping("test_es/{id}")
    public TestEs testEs(@PathVariable String id) {
        return testEsRepository.getOne(1);
    }
}
