package org.mongo.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

@Configuration
@Profile("prod")
@PropertySource({"classpath:persistence.properties"})
@EnableMongoRepositories("org.mongo.repository")
public class MongoProd implements MongoConfig {
	
	@Autowired
	Environment env;
	
	@Override
	public FactoryBean<Mongo> factoryBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MongoDbFactory mongoDbFactory() throws MongoException,
			UnknownHostException {
		MongoDbFactory factory = new SimpleMongoDbFactory(new MongoURI(env.getProperty("mongo_uri.heroku")));
		return factory;
	}

	@Override
	@Bean
	public MongoOperations mongoTemplate() throws MongoException, UnknownHostException {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
	
	@Override
	@Bean
	public GridFsOperations gridFsTemplate() throws MongoException, UnknownHostException {
		MappingMongoConverter converter = 
				new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
		return new GridFsTemplate(mongoDbFactory(), converter);
	}

}
