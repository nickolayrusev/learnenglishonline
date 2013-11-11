package org.mongo.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
	private CommonUtils(){}
	public static List<String> getAllFields(Class<?> type) {
        List<String> fields = new ArrayList<String>();
        for (Field field : type.getDeclaredFields()) {
			fields.add(field.getName());
		}
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
        	for(Field f : c.getDeclaredFields()){
        		fields.add(f.getName());
        	}
        }
        return fields;
    }
	
	public static void copyProperties(Object dest, Object orig) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> claz = dest.getClass();
		Field[] declaredFields = claz.getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			Object value = field.get(orig);
			if(value != null) {
				field.set(dest, value);
			}
		}		
		
	}
}
