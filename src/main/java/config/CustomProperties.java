package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Configuration
@ConfigurationProperties(prefix ="com.ocal.medhead")
@Data public class CustomProperties {	
	private String apiUrl;

	String getApiUrl() {
		return this.apiUrl;
	}
	void setApiUrl(String s) {
		this.apiUrl = s;
	}
}
