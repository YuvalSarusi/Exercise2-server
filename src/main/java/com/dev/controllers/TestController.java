package com.dev.controllers;

import com.dev.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
public class TestController {


    @Autowired
    private Persist persist;

    @PostConstruct
    private void init () {

    }



}
