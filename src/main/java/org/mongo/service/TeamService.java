package org.mongo.service;

import org.mongo.domain.Word;
import org.mongo.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
	@Autowired
	WordRepository wordRepository;

	
	public Word saveOrUpdate(Word word){
		return wordRepository.save(word);
	}

}
