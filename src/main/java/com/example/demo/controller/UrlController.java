package com.example.demo.controller;

import com.example.demo.dto.URLInputDTO;
import com.example.demo.entityclass.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody URLInputDTO urlDTO, Model model) {
        Url url = urlService.shortenUrl(urlDTO.getLongUrl());
        System.out.println(url.getShortUrl());
        System.out.println("entered controller");
        model.addAttribute("shortenedUrl", url.getShortUrl());
        return "postlogin";
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
