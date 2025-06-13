package urlShortner.urlShorter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urlShortner.urlShorter.entity.UrlMapping;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortUrl(String shortUrl);

}
