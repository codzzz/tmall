package com.tmall.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.github.pagehelper.PageHelper;
import com.tmall.comparator.ProductAllComparator;
import com.tmall.comparator.ProductDateComparator;
import com.tmall.comparator.ProductPriceComparator;
import com.tmall.comparator.ProductReviewComparator;
import com.tmall.comparator.ProductSaleCountComparator;
import com.tmall.pojo.Category;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.Review;
import com.tmall.pojo.User;
import com.tmall.service.CategoryService;
import com.tmall.service.OrderItemService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyValueService;
import com.tmall.service.ReviewService;
import com.tmall.service.UserService;

@Controller
@RequestMapping("")
public class ForeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	ProductImageService piService;
	@Autowired
	ReviewService rService;
	@Autowired
	PropertyValueService pvService;
	@Autowired
	OrderItemService oiService;
	@RequestMapping("forehome")
	public String home(Model model) {
		List<Category> cs = categoryService.list();
		productService.fill(cs);
		productService.fillByRow(cs);
		model.addAttribute("cs", cs);
		return "fore/home";
	}
	@RequestMapping("foreregister")
	public String register(Model model,User user) {
		String name = user.getName();
		name = HtmlUtils.htmlEscape(name);
		user.setName(name);
		if (userService.isExist(name)) {
			model.addAttribute("msg", "用户名已经使用");
			model.addAttribute("user", null);
			return "fore/register";
		}else {
			userService.add(user);
			return "redirect:registerSuccessPage";
		}
	}
	@RequestMapping("forelogin")
	public String login(Model model,@RequestParam("name") String name,@RequestParam("password") String password,HttpSession session) {
		name = HtmlUtils.htmlEscape(name);
		User user = userService.get(name, password);
		if (user == null) {
			 model.addAttribute("msg", "账号密码错误");
			 return "fore/login";
		}
		session.setAttribute("user", user);
		return "redirect:forehome";
	}
	@RequestMapping("forelogout")
	public String logout(Model model,HttpSession session) {
		session.removeAttribute("user");
		return "redirect:forehome";
	}
	@RequestMapping("foreproduct")
	public  String product(Model model,int pid) {
		Product product = productService.get(pid);
		List<ProductImage> productSingleImage = piService.list(pid, ProductImageService.type_single);
		List<ProductImage> productDetailImage = piService.list(pid, ProductImageService.type_detail);
		product.setProductSingleImages(productSingleImage);
		product.setProductDetailImages(productDetailImage);
		List<Review> rs = rService.list(pid);
		productService.setSaleAndReviewNumber(product);
		List<PropertyValue> pvs = pvService.list(pid);
		model.addAttribute("p", product);
		model.addAttribute("reviews", rs);
		model.addAttribute("pvs", pvs);
		return "fore/product";
	}
	@RequestMapping("forecheckLogin")
	@ResponseBody
	public String checkLogin(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "fail";
		}
		return "success";
	}
	@RequestMapping("foreloginAjax")
	@ResponseBody
	public String loginAjax(@RequestParam("name") String name,@RequestParam("password") String password,HttpSession session) {
		name = HtmlUtils.htmlEscape(name);
		User user = userService.get(name, password);
		if (user == null) {
			return "fail";
		}
		session.setAttribute("user", user);
		return "success";
	}
	
	@RequestMapping("forecategory")
	public String category(int cid,String sort,Model model) {
		Category c = categoryService.get(cid);
		productService.fill(c);
		productService.setSaleAndReviewNumber(c.getProducts());
		if (null != sort) {
			switch (sort) {
			case "review":
				Collections.sort(c.getProducts(), new ProductReviewComparator());
				break;
			case "date":
				Collections.sort(c.getProducts(), new ProductDateComparator());
				break;
			case "saleCount":
				Collections.sort(c.getProducts(), new ProductSaleCountComparator());
				break;
			case "price":
				Collections.sort(c.getProducts(), new ProductPriceComparator());
				break;
			case "all":
				Collections.sort(c.getProducts(), new ProductAllComparator());
				break;
			}
		}
		model.addAttribute("c", c);
		return "fore/category";
	}
	@RequestMapping("foresearch")
	public String search(String keyword,Model model) {
		PageHelper.offsetPage(0, 20);
		List<Product> ps = productService.search(keyword);
		productService.setSaleAndReviewNumber(ps);
		model.addAttribute("ps", ps);
		return "fore/searchResult";
	}
	@RequestMapping("forebuyone")
	public String buyone(int pid,int num,HttpSession session) {
		Product p = productService.get(pid);
		int oiid = 0;
		User user = (User) session.getAttribute("user");
		boolean found = false;
		List<OrderItem> ois = oiService.listByUser(user.getId());
		for (OrderItem orderItem : ois) {
			if (orderItem.getProduct().getId().intValue()==p.getId().intValue()) {
				orderItem.setNumber(orderItem.getNumber()+num);
				oiService.update(orderItem);
				found = true;
				oiid = orderItem.getId();
				break;
			}
		}
		if(!found) {
			OrderItem oiItem = new OrderItem();
			oiItem.setNumber(num);
			oiItem.setPid(pid);
			oiItem.setUid(user.getId());
			oiService.add(oiItem);
			oiid = oiItem.getId();
		}
		return "redirect:forebuy?oiid="+oiid;
	}
	@RequestMapping("forebuy")
	public String buy(String[] oiid,Model model,HttpSession session) {
		List<OrderItem> oiItems = new ArrayList<>();
		float total = 0;
		for (int i = 0; i < oiid.length; i++) {
			int id = Integer.parseInt(oiid[i]);
			OrderItem oi = oiService.get(id);
			oiItems.add(oi);
			total += oi.getNumber()*oi.getProduct().getPromotePrice();
		}
		model.addAttribute("ois",oiItems);
		model.addAttribute("total", total);
		return "fore/buy";
	}
}
