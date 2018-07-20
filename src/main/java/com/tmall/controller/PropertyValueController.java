package com.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmall.pojo.Product;
import com.tmall.pojo.PropertyValue;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyValueService;

@Controller
@RequestMapping("")
public class PropertyValueController {
	@Autowired
	ProductService pService;
	@Autowired
	PropertyValueService pvService;
	
	@RequestMapping("admin_propertyValue_edit")
	public String edit(Model model,int pid) {
		Product p = pService.get(pid);
		pvService.init(p);
		List<PropertyValue> pvs = pvService.list(pid);
		System.out.println("pvs size:"+pvs.size());
		model.addAttribute("p", p);
		model.addAttribute("pvs", pvs);
		return "admin/editPropertyValue";
	}
	@RequestMapping("admin_propertyValue_update")
	@ResponseBody
	public String update(PropertyValue pv) {
		pvService.update(pv);
		return "success";
	}
}
