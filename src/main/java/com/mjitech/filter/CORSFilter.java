package com.mjitech.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader("Access-Control-Allow-Origin", "http://www.mjitech.com");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.addHeader("Access-Control-Expose-Headers", "Info");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing

	}

}
