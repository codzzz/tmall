package com.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.User;
import com.tmall.service.UserService;
import com.tmall.util.Page;

@Controller
@RequestMapping("")
public class UserController {
	@Autowired
	UserService userService;
	@RequestMapping("admin_user_list")
	public String list(Model model,Page page) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<User> users = userService.list();
		int total = (int) new PageInfo<>(users).getTotal();
		page.setTotal(total);
		model.addAttribute("us", users);
		return "admin/listUser";
	}
}
