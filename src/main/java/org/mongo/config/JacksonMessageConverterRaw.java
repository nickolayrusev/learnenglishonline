package org.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@Profile("prod")
public class JacksonMessageConverterRaw implements MessegeConverter {
	@Bean
	@Override
	public MappingJackson2HttpMessageConverter jsonConverter() {
		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJacksonHttpMessageConverter.setPrefixJson(false);
		mappingJacksonHttpMessageConverter.setPrettyPrint(false);
		return mappingJacksonHttpMessageConverter;
	}


}
