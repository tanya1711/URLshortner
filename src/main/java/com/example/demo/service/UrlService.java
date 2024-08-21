package com.example.demo.service;

import com.example.demo.entityclass.Url;
import com.example.demo.mongorepository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url shortenUrl(String originalUrl) {
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlRepository.existsByShortUrl(shortUrl));
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl("hello"+shortUrl);
        return urlRepository.save(url);
    }

    public Url shortenCustomUrl(String originalUrl, String customUrl) {
         if(urlRepository.existsByShortUrl(customUrl));
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(customUrl);
        return urlRepository.save(url);
    }

    private String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public Optional<Url> getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }
}
