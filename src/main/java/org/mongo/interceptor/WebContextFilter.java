package org.mongo.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mongo.utils.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component("webContextFilter")
public class WebContextFilter extends GenericFilterBean {
	private static Logger logger = LoggerFactory.getLogger(WebContextFilter.class);
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		logger.info("doing filter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		ServletContext servletContext = this.getServletContext();
		
		WebContext.create(request, response, servletContext);
		chain.doFilter(request, response);
		WebContext.clear();
	
	}

}