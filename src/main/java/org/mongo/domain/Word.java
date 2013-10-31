package org.mongo.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection="word")
public class Word extends BaseMongoObject {
	private String englishValue;
	private List<String> bulgarianValues;
	private List<String> tags;
	
	public String getEnglishValue() {
		return englishValue;
	}
	public void setEnglishValue(String englishValue) {
		this.englishValue = englishValue;
	}
	public List<String> getBulgarianValues() {
		return bulgarianValues;
	}
	public void setBulgarianValues(List<String> bulgarianValues) {
		this.bulgarianValues = bulgarianValues;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}
