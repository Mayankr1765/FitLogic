package com.genai.Fitlogic.controller;

import com.genai.Fitlogic.service.FitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fitness")
public class FitnessQuery {

    @Autowired
    FitService fitService;

    @GetMapping("/ask")
    public String getAnswer(@RequestParam String query){
        System.out.println("Inside Controller");
        return fitService.getAnswer(query);
    }
}
