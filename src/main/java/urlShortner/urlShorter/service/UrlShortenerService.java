package urlShortner.urlShorter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlShortner.urlShorter.entity.UrlMapping;
import urlShortner.urlShorter.repository.UrlMappingRepository;

import java.util.Optional;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    // Method to shorten a URL
    public String shortenUrl(String longUrl){
        String shortCode = generateShortCode(longUrl);
        UrlMapping urlMapping = new UrlMapping(longUrl, shortCode);
        urlMappingRepository.save(urlMapping);
        return shortCode;
    }

    public Optional<String> getLongUrl(String shortCode){
        return urlMappingRepository.findByShortUrl(shortCode)
                .map(UrlMapping::getOriginalUrl);
    }

    private String generateShortCode(String longUrl) {
        // Simple hash-based short code generation
        return Integer.toHexString(longUrl.hashCode());
    }
}
