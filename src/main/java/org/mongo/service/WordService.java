package org.mongo.service;

import java.util.List;

import org.mongo.domain.Word;
import org.mongo.repository.WordRepository;
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
	
	public List<Word> getAll(int size,String filter){
		Query query = new Query();
		if(filter!=null){
			query = new Query().addCriteria(Criteria.where("englishValue").regex(filter,"i")).limit(size);
			List<Word> wordList = mongoOperations.find(query, Word.class);
			return wordList;
		}
		
		long allWordsCount = wordRepository.count() ;
		System.out.println("allWordsCount is: "+allWordsCount);
		if(allWordsCount < size){
			query = new Query();
		}else{
			long count = allWordsCount - size;
			int skip =  (int) Math.round( (Math.random() * (count)) ) ;
			System.out.println("starting from: " + skip);
			query = new Query().limit(-1).skip(skip).limit(size);
			List<Word> wordList = mongoOperations.find(query, Word.class);
			return wordList;
		}
		
		List<Word> wordList = mongoOperations.find(query, Word.class);
		return wordList;
		
	}
	
	public Word getById(String id) {
		Word findOne = wordRepository.findOne(id);
		return findOne;
	}
	
	public Word delete(String id){
		Word findOne = wordRepository.findOne(id);
		wordRepository.delete(findOne);
		return findOne;
		
	}
}
