package com.github.huifer.ews.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Myfilter extends HttpServlet implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//todo
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		// 添加参数，允许任意domain访问
		resp.setHeader("Access-Control-Allow-Origin", "*");
		// 这个allow-headers要配为*，这样才能允许所有的请求头 ---
		resp.setHeader("Access-Control-Allow-Headers", "*");
		resp.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		resp.setHeader("Access-Control-Allow-Credentials", "true");

	}
}