package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.service.impl.FileStorageServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    private final FileStorageServiceImpl fileStorageServiceImpl;

    public MainController(FileStorageServiceImpl fileStorageServiceImpl) {
        this.fileStorageServiceImpl = fileStorageServiceImpl;
    }

    @RequestMapping
    public String home() {
        return "index";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/thanks_for_buying")
    public String thanksForBuying() {
        return "thanks_for_buying";
    }

    @GetMapping("/images/tours/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileStorageServiceImpl.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
