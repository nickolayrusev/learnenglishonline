package org.mongo.service;

import java.util.List;

import org.mongo.domain.Word;
import org.mongo.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class WordService {
	@Autowired
	WordRepository wordRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public Word saveOrUpdate(Word word){
		return wordRepository.save(word);
	}
	
	private static final Logger logger = LoggerFactory.getLogger(WordService.class);
	
	public List<Word> getAll (int size, String filter, List<String> tags){
		Query query = new Query();

		logger.info("size " + size);
		logger.info("filter " + filter);
		logger.info("tags " + tags);
		
		if(filter != null) {
			logger.info("filter by filter...");
			query.addCriteria(Criteria.where("englishValue").regex(filter,"i"));
		}
		if(tags != null && !tags.isEmpty()){
			logger.info("filter by tags...");
			query.addCriteria(Criteria.where("tags").in(tags));
		}
		long count = mongoOperations.count(query, Word.class);
		logger.info("count is: " + count);
		
		if(size < count) {
			logger.info("randomize");
			long skip = Math.round(Math.random() * (count - size));
			query.limit(-1).skip((int) skip).limit(size);
		} 
		List<Word> find = mongoOperations.find(query, Word.class);
		logger.info("returning... " + find );
		return find;
	}

	
	public Word getById(String id) {
		Word findOne = wordRepository.findOne(id);
		return findOne;
	}
	
	public List<String> getAllTags(){
		List<String> distinct = mongoOperations.getCollection("word").distinct("tags");
		return distinct;
	}
	
	public Word delete(String id){
		Word findOne = wordRepository.findOne(id);
		wordRepository.delete(findOne);
		return findOne;
		
	}
	public long deleteEmptyWords(){
		long counter = 0;
		Query query = new Query();
		List<Word> find = mongoOperations.find(query, Word.class);
		for (Word word : find) {
			if(word.getEnglishValue() == null || word.getEnglishValue().isEmpty()){
				delete(String.valueOf(word.getId()));
				counter ++;
			}
		}
		return counter;
	}
}
