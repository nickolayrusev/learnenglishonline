package org.mongo.config;

import org.springframework.http.converter.HttpMessageConverter;

public interface MessegeConverter {
	HttpMessageConverter<?> jsonConverter();
}
