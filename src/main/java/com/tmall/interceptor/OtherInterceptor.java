package com.tmall.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.pojo.Category;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;
import com.tmall.service.CategoryService;
import com.tmall.service.OrderItemService;

public class OtherInterceptor implements HandlerInterceptor {
	@Autowired
	CategoryService cService;
	@Autowired
	OrderItemService oiService;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelView)
			throws Exception {
		HttpSession session = request.getSession();
		String contextPath = session.getServletContext().getContextPath();
		request.getSession().setAttribute("contextPath", contextPath);
		List<Category> cs = cService.list();
		request.getSession().setAttribute("cs_search", cs);
		
		User user = (User) session.getAttribute("user");
		int cartTotalItemNumber = 0;
		if (null != user) {
			List<OrderItem> ois = oiService.listByUser(user.getId());
			for (OrderItem orderItem : ois) {
				cartTotalItemNumber += orderItem.getNumber().intValue();
			}
		}
		request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

}
