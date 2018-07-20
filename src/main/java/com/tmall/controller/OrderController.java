package com.tmall.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Order;
import com.tmall.service.OrderService;
import com.tmall.util.Page;

@Controller
@RequestMapping("")
public class OrderController {
	@Autowired
	OrderService oService;
	
	@RequestMapping("admin_order_list")
	public String list(Model model,Page page) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Order> os = oService.list();
		Logger.getLogger(this.getClass()).debug("os size:"+os.size());
		int total = (int) new PageInfo<>(os).getTotal();
		page.setTotal(total);
		model.addAttribute("os", os);
		model.addAttribute("page", page);
		return "admin/listOrder";
	}
	
	
	@RequestMapping("admin_order_delivery")
	public String delivery(Order order) {
		order.setDeliveryDate(new Date());
		order.setStatus(OrderService.waitConfirm);
		oService.update(order);
		return "redirect:admin_order_list";
	}
	@RequestMapping("admin_order_cancel")
	public String cancel(Order order) {
		order.setDeliveryDate(new Date());
		order.setStatus(OrderService.waitDelivery);
		oService.update(order);
		return "redirect:admin_order_list";
	}
}
