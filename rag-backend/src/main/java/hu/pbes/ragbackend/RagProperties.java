package hu.pbes.ragbackend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rag")
public class RagProperties {
    private String ollamaUrl;
    private String ollamaModel;
    private String documentPath;
}
