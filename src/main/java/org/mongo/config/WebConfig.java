package org.mongo.config;

import java.util.List;

import org.mongo.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.mongo"})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	MessegeConverter converter;
	
	@Bean
	public InternalResourceViewResolver resolver(){
		InternalResourceViewResolver res = new InternalResourceViewResolver();
		res.setSuffix(".jsp");
		res.setPrefix("/WEB-INF/views/");
		return res;
		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		//addresources from external to project folder
//		registry.addResourceHandler("/res/**").addResourceLocations("file:/home/nickolay/Pictures/");
//		registry.addResourceHandler("/res/**").addResourceLocations("file:C:\\Users//rusev\\Desktop\\teams\\");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
	@Override
 	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
 		converters.add(converter.jsonConverter());
 		
 		//Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();
 		XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
 		xstreamMarshaller.setAutodetectAnnotations(true);
 		MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter(xstreamMarshaller, xstreamMarshaller);
  		converters.add(marshallingHttpMessageConverter);
 	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	

}
