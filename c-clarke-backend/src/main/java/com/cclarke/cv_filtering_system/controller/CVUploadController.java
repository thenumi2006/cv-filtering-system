package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cv")
public class CVUploadController {

    @Autowired
    private CVService cvService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCV(@RequestParam("file") MultipartFile file) {
        try {
            String extractedText = cvService.saveAndExtractText(file);

            return ResponseEntity.ok("Text Extracted Successfully: " + extractedText.substring(0, 100) + "...");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}