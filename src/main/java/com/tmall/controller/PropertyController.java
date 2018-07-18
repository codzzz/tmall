package com.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Property;
import com.tmall.service.CategoryService;
import com.tmall.service.PropertyService;
import com.tmall.util.Page;

@Controller
@RequestMapping("")
public class PropertyController {
	@Autowired
	PropertyService proservice;
	@Autowired
	CategoryService catservice;
	
	
	@RequestMapping("admin_property_list")
	public String list(Model model,int cid,Page page) {
		Category c = catservice.get(cid);
		List<Property> list = proservice.list(cid);
		PageHelper.offsetPage(page.getStart(), page.getCount());
		int total = (int) new PageInfo<>(list).getTotal();
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		model.addAttribute("ps", list);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		return "admin/listProperty";
	}
	
	@RequestMapping("admin_property_add")
	public String add(Model model,Property p) {
		proservice.add(p);
		return "redirect:admin_property_list?cid="+p.getCid();
	}
	
	@RequestMapping("admin_property_delete")
	public String delete(Model model,int pid) {
		Property property = proservice.get(pid);
		proservice.delete(pid);
		return "redirect:admin_property_list?cid="+property.getCid();
	}
	
	@RequestMapping("admin_property_edit")
	public String edit(Model model,int pid) {
		Property property = proservice.get(pid);
		Category c = catservice.get(property.getCid());
		property.setCategory(c);
		model.addAttribute("p", property);
		return "admin/editProperty";
	}
	
	@RequestMapping("admin_property_update")
	public String update(Model model,Property p) {
		proservice.update(p);
		return "redirect:admin_property_list?cid="+p.getCid();
	}
}
