package com.example.demo.controller;

import com.example.demo.dto.URLInputDTO;
import com.example.demo.entityclass.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;


    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody URLInputDTO urlDTO) {
        Url url;
        Map<String, String> response = new HashMap<>();
        if (urlDTO.customUrl == null) {
            url = urlService.shortenUrl(urlDTO.getLongUrl());
        } else {
            url = urlService.shortenCustomUrl(urlDTO.getLongUrl(), urlDTO.getCustomUrl());
        }
        if (url == null) {
            response.put("error", "Failed to shorten URL. Please try again with a different custom URL or long URL.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.put("shortenedUrl", url.getShortUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectUrl(@PathVariable String shortUrl) {
        return urlService.getOriginalUrl(shortUrl)
                .map(url -> new RedirectView(url.getOriginalUrl()))
                .orElse(null);
    }

    @GetMapping("/home")
    public String homePage() {
        return "homePage";
    }

    @GetMapping("/getPostLogin")
    public String getLogin() {
        return "postlogin";
    }
}
