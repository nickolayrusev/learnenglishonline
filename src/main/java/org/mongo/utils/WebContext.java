package org.mongo.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {

	private static ThreadLocal<WebContext> tlv = new ThreadLocal<WebContext>();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext servletContext;
	
	protected WebContext() {}
	
	private WebContext(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) {
		this.request = request;
		this.response = response;
		this.servletContext = servletContext;
	}
	
	public static WebContext getInstance() {
		return tlv.get();
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}


	public HttpServletResponse getResponse() {
		return response;
	}


	public static void create(HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) {
		WebContext wc = new WebContext(request, response, servletContext);
		tlv.set(wc);
	}
	
	public static void clear() {
		tlv.set(null);
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
}