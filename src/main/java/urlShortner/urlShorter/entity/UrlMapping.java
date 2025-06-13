package urlShortner.urlShorter.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "url_mapping")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_mapping_seq")
    @SequenceGenerator(name = "url_mapping_seq", sequenceName = "url_mapping_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;
    
    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrl;

    public  UrlMapping(){

    }

    public UrlMapping(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public String toString() {
        return "UrlMapping{" +
                "originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }

}
