package cn.hl.admin.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoredUrlsConfig {
    private List<String> urls;
}
