package org.mongo.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongo.config.JacksonMessageConverterPretty;
import org.mongo.config.JacksonMessageConverterRaw;
import org.mongo.config.RootConfig;
import org.mongo.config.WebConfig;
import org.mongo.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class,RootConfig.class,JacksonMessageConverterPretty.class,JacksonMessageConverterRaw.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class ServiceTest {
	@Autowired
	WordService wordService;
	
	private static Logger logger = LoggerFactory.getLogger(ServiceTest.class);
	
	@Test
	public void getAllTags() throws Exception {
		List<String> allTags = wordService.getAllTags();
		assertNotNull(allTags);
		logger.info("all tags: " + allTags);
	}
}
