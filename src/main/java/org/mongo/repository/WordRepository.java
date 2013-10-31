package org.mongo.repository;

import org.mongo.domain.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepository extends MongoRepository<Word,String> {
}
