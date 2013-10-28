package org.mongo.domain;

import java.util.List;

public class Word extends BaseMongoObject {
	private String englishValue;
	private String type;
	private List<String> bulgarianValues;
	
	public String getEnglishValue() {
		return englishValue;
	}
	public void setEnglishValue(String englishValue) {
		this.englishValue = englishValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getBulgarianValues() {
		return bulgarianValues;
	}
	public void setBulgarianValues(List<String> bulgarianValues) {
		this.bulgarianValues = bulgarianValues;
	}
	
	
}
