package org.mongo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;


@Configuration
@EnableAspectJAutoProxy
@PropertySource({"classpath:persistence.properties","classpath:social.properties"})
@ComponentScan(basePackages={"org.mongo.service","org.mongo.domain","org.mongo.worker"})
public class RootConfig {
	
	
}
