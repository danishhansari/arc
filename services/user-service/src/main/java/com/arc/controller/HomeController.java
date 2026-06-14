package com.arc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import payload.response.ApiResponse;

@RestController
public class HomeController {


    @GetMapping
    public ApiResponse getResonse() {
        ApiResponse response = new ApiResponse();
        response.setMessage("This will work eventually");
        return response;
    }
}
