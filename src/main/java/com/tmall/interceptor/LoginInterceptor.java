package com.tmall.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sun.tools.javac.code.Attribute.Array;
import com.tmall.pojo.User;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String contextPath = session.getServletContext().getContextPath();
		String[] noNeedAuthPage = new String[] {
				"home",
				"checkLogin",
				"register",
				"loginAjax",
				"login",
				"product",
				"category",
				"search"
		};
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath);
		if (uri.startsWith("/fore")) {
			String method = StringUtils.substringAfter(uri, "/fore");
			if (!Arrays.asList(noNeedAuthPage).contains(method)) {
				User user = (User) session.getAttribute("user");
				if (null==user) {
					response.sendRedirect("loginPage");
					return false;
				}
			}
		}
		return true;
	}

}
