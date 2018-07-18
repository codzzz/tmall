package com.tmall.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
import com.tmall.util.Page;

@Controller
@RequestMapping("")
public class ProductController {
	
	@Autowired
	ProductService proService;
	@Autowired
	CategoryService catService;
	
	@RequestMapping("admin_product_list")
	public String list(Model model,int cid,Page page) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Product> list = proService.list(cid);
		int total = (int) new PageInfo<>(list).getTotal();
		page.setTotal(total);
		page.setParam("&cid="+cid);
		model.addAttribute("ps", list);
		Category c = catService.get(cid);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		return "admin/listProduct";
	}
	@RequestMapping("admin_product_add")
	public String add(Model model,Product p) {
		p.setCreateDate(new Date());
		proService.add(p);
		return "redirect:admin_product_list?cid="+p.getCid();
	}
	@RequestMapping("admin_product_delete")
	public String add(Model model,int pid) {
		Product p = proService.get(pid);
		proService.delete(pid);
		return "redirect:admin_product_list?cid="+p.getCid();
	}	
	@RequestMapping("admin_product_edit")
	public String edit(Model model,int pid) {
		Product product = proService.get(pid);
		Category category = catService.get(product.getCid());
		product.setCategory(category);
		model.addAttribute("p", product);
		return "admin/editProduct";
	}
	@RequestMapping("admin_product_update")
	public String update(Model model,Product p) {
		proService.update(p);
		return "redirect:admin_product_list?cid="+p.getCid();
	}
}
