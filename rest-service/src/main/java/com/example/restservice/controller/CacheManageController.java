package com.example.restservice.controller;

import com.example.restservice.service.CacheManageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book/cache")
@RequiredArgsConstructor
public class CacheManageController {

    private final CacheManageService cacheManageService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Invalidate cache")
    protected void resetCache() {
        cacheManageService.clearCache();
    }
}
