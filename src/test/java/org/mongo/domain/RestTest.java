package org.mongo.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongo.config.JacksonMessageConverterPretty;
import org.mongo.config.JacksonMessageConverterRaw;
import org.mongo.config.RootConfig;
import org.mongo.config.WebConfig;
import org.mongo.interceptor.WebContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class,RootConfig.class,JacksonMessageConverterPretty.class,JacksonMessageConverterRaw.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public  class RestTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac).addFilters(new WebContextFilter()).build();
	}

	
	@Test
	public void mockPerformWordPost() throws Exception{
		Word word = new Word();
		word.setBulgarianValues(Arrays.asList("котка"));
		word.setEnglishValue("cat");
		word.setType("noun");
		
		String value = new ObjectMapper().writeValueAsString(word);
		logger.info("request body:"+value);
		MvcResult andReturn = this.mvc.perform(post("/words")
	            .content(value).contentType(MediaType.APPLICATION_JSON)).andReturn(); // <-- sets the request content
		logger.info(andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetWords() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/words?size=1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	
}
