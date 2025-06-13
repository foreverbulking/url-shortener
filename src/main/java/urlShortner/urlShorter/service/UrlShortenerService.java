package urlShortner.urlShorter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlShortner.urlShorter.entity.UrlMapping;
import urlShortner.urlShorter.repository.UrlMappingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    // Method to shorten a URL
    public String shortenUrl(String longUrl){
        if (longUrl == null || longUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        
        // Basic URL validation
        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "https://" + longUrl;
        }
        
        try {
            // Check if URL already exists
            List<UrlMapping> existingMappings = urlMappingRepository.findAllByOriginalUrl(longUrl);
            if (!existingMappings.isEmpty()) {
                return existingMappings.get(0).getShortUrl();
            }
            
            // Generate unique short code
            String shortCode = generateUniqueShortCode(longUrl);
            UrlMapping urlMapping = new UrlMapping(longUrl, shortCode);
            urlMappingRepository.save(urlMapping);
            return shortCode;
        } catch (Exception e) {
            throw new RuntimeException("Failed to shorten URL: " + e.getMessage(), e);
        }
    }

    public Optional<String> getLongUrl(String shortCode){
        if (shortCode == null || shortCode.trim().isEmpty()) {
            return Optional.empty();
        }
        
        try {
            // Handle multiple results by taking the first one
            List<UrlMapping> mappings = urlMappingRepository.findAllByShortUrl(shortCode);
            if (mappings.isEmpty()) {
                return Optional.empty();
            }
            // Return the first (or most recent) mapping
            return Optional.of(mappings.get(0).getOriginalUrl());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve URL: " + e.getMessage(), e);
        }
    }

    public List<UrlInfoResponse> getAllUrls() {
        try {
            return urlMappingRepository.findAll().stream()
                    .map(mapping -> new UrlInfoResponse(mapping.getShortUrl(), mapping.getOriginalUrl()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all URLs: " + e.getMessage(), e);
        }
    }

    public long getUrlCount() {
        try {
            return urlMappingRepository.count();
        } catch (Exception e) {
            throw new RuntimeException("Failed to count URLs: " + e.getMessage(), e);
        }
    }

    private String generateUniqueShortCode(String longUrl) {
        String baseCode = Integer.toHexString(Math.abs(longUrl.hashCode()));
        String shortCode = baseCode;
        int attempts = 0;
        
        // Ensure uniqueness by adding random suffix if needed
        while (!urlMappingRepository.findAllByShortUrl(shortCode).isEmpty() && attempts < 10) {
            shortCode = baseCode + new Random().nextInt(1000);
            attempts++;
        }
        
        return shortCode;
    }

    // DTO class for the service layer
    public static class UrlInfoResponse {
        private String shortCode;
        private String originalUrl;

        public UrlInfoResponse(String shortCode, String originalUrl) {
            this.shortCode = shortCode;
            this.originalUrl = originalUrl;
        }

        public String getShortCode() {
            return shortCode;
        }

        public void setShortCode(String shortCode) {
            this.shortCode = shortCode;
        }

        public String getOriginalUrl() {
            return originalUrl;
        }

        public void setOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
        }
    }
}
