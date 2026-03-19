package com.cclarke.cv_filtering_system.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CVService {
    private final String UPLOAD_DIR = "uploads/";

    public String saveAndExtractText(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(System.currentTimeMillis() + "_" + file.getOriginalFilename());

        // Faster way to copy files
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        try (PDDocument document = PDDocument.load(new File(filePath.toString()))) {
            // SPEED BOOSTER: Only extract text from the first 3 pages
            if (document.getNumberOfPages() > 3) {
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setStartPage(1);
                stripper.setEndPage(3);
                return stripper.getText(document);
            }

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}