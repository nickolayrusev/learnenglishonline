package org.mongo.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
	public void mockGetCountry() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/country/England").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetAllTeams() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/teams?fields=id,crest1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetTeamsByCountryAndLevel() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/championship/England/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	
	@Test
	public void mockGetPlayerById() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/players/513de959497027027fe6f4b0?fields=firstName").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetPlayersByTeamId() throws Exception{
		MvcResult andReturn = this.mvc.perform(get("/players?team=513712ecc6dec2b764c61ccd&fields=firstName").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetTweetById() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/tweet/123").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetPlayersByTeamName() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/team/player/manchester-city").accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockGetAllPlayers() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/player").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info("response as string:"+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void mockPerformPersonPost() throws Exception{
		/*Person p = new Person();
		p.setName("nrusev");
		p.setAge(12);
		String value = new ObjectMapper().writeValueAsString(p);
		logger.info("request body:"+value);
		MvcResult andReturn = this.mvc.perform(post("/person/add")
	            .content(value).contentType(MediaType.APPLICATION_JSON)).andReturn(); // <-- sets the request content
		logger.info(andReturn.getResponse().getContentAsString());*/
	}
	@Test
	public void mockGetePersonXml() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/person/ivan/13").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		System.out.println("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	
	@Test
	public void mockGetImage() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/image").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info(("response as string:"
				+ andReturn.getResponse().getContentAsString()));
	}
	
	@Test
	public void mockGetPersonModelXml() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/personmodel/ivo/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		System.out.println("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void testString() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/testString").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
	}
	@Test
	public void testObjectSerialization() throws Exception {
		MvcResult andReturn = this.mvc
				.perform(get("/testObjectSerializationList?fields=text").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		logger.info("response as string:"
				+ andReturn.getResponse().getContentAsString());
		logger.info("headers: "+andReturn.getResponse().getHeaderNames());
	}
}
