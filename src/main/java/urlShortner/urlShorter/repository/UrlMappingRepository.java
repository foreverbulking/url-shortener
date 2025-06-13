package urlShortner.urlShorter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urlShortner.urlShorter.entity.UrlMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortUrl(String shortUrl);
    List<UrlMapping> findAllByShortUrl(String shortUrl);
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
    List<UrlMapping> findAllByOriginalUrl(String originalUrl);
}
