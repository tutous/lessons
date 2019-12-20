package de.tutous.spring.boot.common.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class SessionSupportContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		SessionSupportContext.set(httpServletRequest.getSession().getId());
		doBeforeFilter(httpServletRequest);
		chain.doFilter(request, response);
		doAfterFilter(httpServletResponse);
	}

	public void doBeforeFilter(HttpServletRequest httpServletRequest) {};

	public void doAfterFilter(HttpServletResponse httpServletResponse) {};

	@Override
	public void destroy() {
	}

}
