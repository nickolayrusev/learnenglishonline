package org.mongo.aspects;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.mongo.config.PropertyFilterMixIn;
import org.mongo.utils.CommonUtils;
import org.mongo.utils.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
@Aspect
public class JsonFilterAspect {

	private static final Logger logger = LoggerFactory.getLogger(JsonFilterAspect.class);
	
	@Around("execution( public * org.mongo.controller..*.*(..)) && @annotation(annotation)")
	public Object logBefore(final ProceedingJoinPoint joinPoint,final RequestMapping annotation) throws Throwable {
		logger.info("******");
		logger.info("annotation value: " + annotation.value());
		logger.info("logBefore() is running!");
		logger.info("hijacked : " + joinPoint.getSignature().getName());
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		logger.info("sig name : " + signature);
		logger.info("return type: " + signature.getReturnType());
		
		logger.info("******");
		
	        
		//check if method is GET
		if(!Arrays.asList(annotation.method()).contains(RequestMethod.GET)){
			return joinPoint.proceed();
		}
		if(!Arrays.asList(signature.getParameterNames()).contains("fields")){
			return joinPoint.proceed();
		}
		if(joinPoint.getArgs()[0]==null){
			return joinPoint.proceed();
		}
		Object proceed = joinPoint.proceed();
		ResponseEntity<?> entity = (ResponseEntity<?>)proceed;
		logger.info("body is: " + entity.getBody());
		//tup moment
		String fields = joinPoint.getArgs()[0]!=null ? (String)joinPoint.getArgs()[0]  : "";
		String[] splittedFields = fields.split(",");
		if(splittedFields.length == 1 ){
			try{
				if(List.class.isAssignableFrom(entity.getBody().getClass())){
					logger.info("is assignable");
					Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
					ParameterizedType responseEntityType = (ParameterizedType) method.getGenericReturnType();
					ParameterizedType listType = (ParameterizedType) responseEntityType.getActualTypeArguments()[0];
					Type actualType = listType.getActualTypeArguments()[0];
					Class<?> actualClass = (Class<?>) actualType;
					
					logger.info("responseEntityType type: "+responseEntityType);
					logger.info("list type: "+listType);
					logger.info("actualType type: "+actualType);
					logger.info("return type in generic: " + actualType + " class is : " + actualClass);
//					actualClass.getDeclaredField(splittedFields[0]);
					
					List<String> allFields = CommonUtils.getAllFields(actualClass);
					if(allFields.contains(splittedFields[0])){
						joinPoint.proceed();
					}else{
						return null;
					}
				}else{
					logger.info("not assignable");
					List<String> allFields = CommonUtils.getAllFields(entity.getBody().getClass());
					if(allFields.contains(splittedFields[0])){
						joinPoint.proceed();
					}else{
						return null;
					}
//					entity.getBody().getClass().getDeclaredField(splittedFields[0]);
				}
			}
			catch(NoSuchFieldException exc){
				logger.error("no such field",exc);
				return null;
			}
			
		}
		FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept(splittedFields));
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Object.class, PropertyFilterMixIn.class);
		ObjectWriter writer = mapper.writer(filters);
		try {
			HttpServletResponse response = WebContext.getInstance().getResponse();
			//TODO set all response headers from entity object headers !
			HttpHeaders headers = entity.getHeaders();
			for(Map.Entry<String, List<String>> entry : headers.entrySet()){
				logger.info("key: " + entry.getKey() + "value: " + entry.getValue());
				List<String> values = entry.getValue();
				for (String string : values) {
					response.addHeader(entry.getKey(), string);
				}
			}
			response.setStatus(entity.getStatusCode().value());
			writer.writeValue(response.getOutputStream(), entity.getBody());
		} catch(Exception ex) {
			logger.error("ex", ex);
		}
		return null;
		
		
	}
}
