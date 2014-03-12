package com.hid.pdf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hid.pdf.domain.SessionUser;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		SessionUser user = (SessionUser) session.getAttribute("session-user");

		if (user == null && (req.getRequestURI().contains("administrator"))) {
			RequestDispatcher rd = req.getRequestDispatcher("/pages/login.jsf");
			rd.forward(request, response);
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

}
