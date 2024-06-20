package com.onebeld.pleasantvacation.controllers;

import com.onebeld.pleasantvacation.services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for handling requests to the main pages of the site and outputting image files.
 * The class manages the routes associated with the home page, the About page,
 * “Thank you for purchasing” page, and serving image files for tours.
 * The {@link FileStorageService} service is used to serve the files.
 */
@Controller
@RequestMapping("/")
public class MainController {
    private final FileStorageService fileStorageService;

    public MainController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Returns the name of the view that will be displayed for the home page.
     *
     * @return The name of the view that will be displayed for the home page
     */
    @RequestMapping
    String getHomePage() {
        return "index";
    }

    /**
     * Returns the name of the view to be displayed on the About page.
     *
     * @return The name of the view that will be displayed for the About page
     */
    @RequestMapping("/about")
    String getAboutPage() {
        return "about";
    }

    /**
     * Returns the name of the view that will be displayed on the “Thank you for your purchase” page.
     *
     * @return The name of the view that will be displayed on the “Thank you for your purchase” page
     */
    @RequestMapping("/thanks_for_buying")
    String getThanksForBuyingPage() {
        return "thanks_for_buying";
    }

    /**
     * Outputs a file from the “images/tours” directory by the specified name.
     *
     * @param filename Filename for maintenance
     * @return A {@link ResponseEntity} containing the file resource if found, or a 404 Not Found response if the file is not found
     */
    @GetMapping("/images/tours/{filename:.+}")
    @ResponseBody
    ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileStorageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
