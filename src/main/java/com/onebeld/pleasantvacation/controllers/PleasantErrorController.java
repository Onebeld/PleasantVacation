package com.onebeld.pleasantvacation.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling HTTP errors.
 * Used to redirect users to the appropriate error pages,
 * based on the HTTP request status code.
 */
@Controller
public class PleasantErrorController implements ErrorController {
    /**
     * Handles the error page based on the HTTP request status code.
     *
     * @param request A {@link HttpServletRequest} object representing an HTTP request
     * @return Name of the displayed error page
     */
    @GetMapping("/error")
    String handleError(HttpServletRequest request) {
        String errorPage = "error";

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value())
                errorPage = "error/404";
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
                errorPage = "error/500";
            else if (statusCode == HttpStatus.FORBIDDEN.value())
                errorPage = "error/403";
        }

        return errorPage;
    }
}
