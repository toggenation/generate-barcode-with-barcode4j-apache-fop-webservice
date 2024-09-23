package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DocumentGeneratorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentGeneratorService documentGeneratorService;

    @GetMapping("/document")
    public void getDocument(HttpServletRequest request, HttpServletResponse response) {
        documentGeneratorService.generateDocument(request, response);
    }

}
