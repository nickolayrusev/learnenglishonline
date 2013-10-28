package org.mongo.service;

import java.util.List;

import org.mongo.domain.Word;
import org.mongo.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
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
	
	public List<Word> getAll(int size){
		Query query = new Query().limit(size);
		List<Word> wordList = mongoOperations.find(query, Word.class);
		return wordList;
	}

}
