package com.genai.Fitlogic.controller;

import com.genai.Fitlogic.service.FitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fitness")
public class FitnessQuery {

    @Autowired
    FitService fitService;

    @GetMapping("/ask")
    public String getAnswer(@RequestParam String query){
        System.out.println("Inside Controller");
        return fitService.getAnswerRAG(query);
    }

    @PostMapping("/uploadReport")
    public ResponseEntity<String> uploadReport(@RequestParam("file")MultipartFile multipartFile){
        fitService.ingestReport(multipartFile);
        return ResponseEntity.ok("Uploaded Successfully");

    }
}
