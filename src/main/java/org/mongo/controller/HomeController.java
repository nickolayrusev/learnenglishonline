package org.mongo.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.mongo.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	MongoOperations mongoOperations;
	
	@Autowired
	TeamService teamService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		logger.info("Welcome home! the client locale is yordanova remote galina"+ locale.toString());
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		Thread currentThread = Thread.currentThread();
		logger.info("thread name"+ currentThread.getName());
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	@RequestMapping(value = "/working", method = RequestMethod.GET)
	public @ResponseBody String get() {
		return "working";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody
	String handleIOException(IllegalArgumentException ex) {
		logger.info("handleIOException - Catching: "
				+ ex.getClass().getSimpleName());
		return ex.getLocalizedMessage();
	}
	
}
