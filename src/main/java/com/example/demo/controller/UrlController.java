package com.example.demo.controller;

import com.example.demo.dto.URLInputDTO;
import com.example.demo.entityclass.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Url url = urlService.shortenUrl(urlDTO.getLongUrl());
        System.out.println(url.getShortUrl());
        System.out.println("entered controller");

        // Create a map to store the shortened URL and return it as JSON
        Map<String, String> response = new HashMap<>();
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
        System.out.println("giving response");
        return "postlogin";
    }
}
