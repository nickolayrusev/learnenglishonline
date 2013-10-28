package org.mongo.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public interface MongoConfig {
	
	FactoryBean<Mongo> factoryBean();

	MongoDbFactory mongoDbFactory() throws MongoException, UnknownHostException;

	MongoOperations mongoTemplate() throws MongoException, UnknownHostException;
	
	GridFsOperations gridFsTemplate() throws MongoException, UnknownHostException;
	
}
