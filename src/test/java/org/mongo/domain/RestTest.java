package org.mongo.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.mongo.service.WordService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class,RootConfig.class,JacksonMessageConverterPretty.class,JacksonMessageConverterRaw.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("prod")
public  class RestTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private WordService wordService;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac).addFilters(new WebContextFilter()).build();
	}
	
	@Test
	public void testDeleteEmptyWords() throws Exception {
		wordService.deleteEmptyWords();
	}
	
	@Test
	public void mockPerformWordPost() throws Exception{
		Word word = new Word();
		word.setBulgarianValues(Arrays.asList("теменужка"));
		word.setEnglishValue("violet");
		word.setTags(Arrays.asList("Flower","Plant","noun"));
		
		String value = new ObjectMapper().writeValueAsString(word);
		logger.info("request body:"+value);
		MvcResult andReturn = this.mvc.perform(post("/words")
	            .content(value).contentType(MediaType.APPLICATION_JSON)).andReturn(); // <-- sets the request content
		logger.info(andReturn.getResponse().getContentAsString());
	}
	@Test
	public void testPutWord() throws Exception {
		Word word = new Word();
		word.setBulgarianValues(Arrays.asList("теменужка"));
		word.setEnglishValue("violet");
		word.setTags(Arrays.asList("Flower","Plant","noun"));
		
		String value = new ObjectMapper().writeValueAsString(word);
		logger.info("request body:"+value);
		MvcResult andReturn = this.mvc.perform(put("/words/21")
	            .content(value).contentType(MediaType.APPLICATION_JSON)).andReturn(); // <-- sets the request content
		logger.info(andReturn.getResponse().getContentAsString());
	
	}

	@Test
	public void mockGetWordById() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/words/526e8ae8e4b048ded05411eb").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	
	@Test
	public void mockGetWords() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/words?size=10").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	
	@Test
	public void mockGetWordsWithFilter() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/words?size=1&filter=cu").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	
	@Test
	public void mockGetWordsByTags() throws Exception {
		MvcResult andReturn = this.mvc
			.perform(get("/words?tags=Flower,noun").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	
	@Test
	public void mockPutWord() throws Exception {
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("englishValue", "dd");
//		node.putArray("bulgarianValues").add("тестваме").add("отново").add("ай малко");
//		node.putArray("tags").add("noun").add("object");
		this.mvc.perform(put("/words/5270fb35e4b032a4f60ed426")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(node))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		logger.info(this.mvc.perform(get("/words/5270fb35e4b032a4f60ed426")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
		
	}

	@Test
	public void mockPutWordArray() throws Exception {
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("tags", new ObjectMapper().writeValueAsString( new String[] {"noun", "drislio"}));
		this.mvc.perform(put("/words/5270fb35e4b032a4f60ed426")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(node))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		logger.info(this.mvc.perform(get("/words/5270fb35e4b032a4f60ed426")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
		
	}
	
	
	@Test
	public void testRandomGeneration() throws Exception {
		for(int i=0;i<100;i++){
			logger.info("number is: " + (int) Math.round( Math.random() * 7)) ;
	
		}
	}
	
}
