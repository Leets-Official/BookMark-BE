package leets.bookmark.global.config;

import leets.bookmark.global.auth.jwt.service.AesEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AesEncryptorConfig {
    @Value("${security.aes.key}")
    private String secretKey;

    @Bean
    public AesEncryptor aesEncryptor() {
        return new AesEncryptor(secretKey);
    }
}
