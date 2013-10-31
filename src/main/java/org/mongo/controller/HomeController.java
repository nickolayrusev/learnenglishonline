package org.mongo.controller;

import java.util.Collections;
import java.util.List;

import org.mongo.domain.Word;
import org.mongo.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	WordService wordService;
	
	
	@RequestMapping(value = "/words", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Word> post(@RequestBody Word word) {
		wordService.saveOrUpdate(word);
		return new ResponseEntity<Word>(word,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/words/{id}", method = RequestMethod.GET)
	public ResponseEntity<Word> get(@PathVariable("id") String id) {
		Word word = wordService.getById(id);
		return new ResponseEntity<Word>(word,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/words", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<List<Word>> get(
			@RequestParam(required = false, value = "size", defaultValue = "10") Integer size,
			@RequestParam(required = false, value = "filter") String filter) {
		List<Word> all = wordService.getAll(size,filter);
		Collections.shuffle(all);
		return new ResponseEntity<List<Word>>(all, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/words/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ResponseEntity<Word> delete(@PathVariable("id") String id) {
		Word deletedWord = wordService.delete(id);
		return new ResponseEntity<Word>(deletedWord, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public @ResponseBody List<String> getTags() {
		List<String> allTags = wordService.getAllTags();
		return allTags;
	}

	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public @ResponseBody List<String> ad() {
		List<String> allTags = wordService.getAllTags();
		return allTags;
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody
	String handleIOException(IllegalArgumentException ex) {
		logger.info("handleIOException - Catching: "
				+ ex.getClass().getSimpleName());
		return ex.getLocalizedMessage();
	}
	
}
