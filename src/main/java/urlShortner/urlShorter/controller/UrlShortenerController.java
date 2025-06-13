package urlShortner.urlShorter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import urlShortner.urlShorter.service.UrlShortenerService;

import java.util.List;

@RestController
@RequestMapping("/url-shortener")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        if (longUrl == null || longUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("URL cannot be null or empty");
        }

        try {
            String shortCode = urlShortenerService.shortenUrl(longUrl);
            return ResponseEntity.ok(shortCode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to shorten URL: " + e.getMessage());
        }
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getLongUrl(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));
        
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY); // 301 redirect
        return redirectView;
    }





}