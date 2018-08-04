package com.tmall.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
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
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.Review;
import com.tmall.pojo.User;
import com.tmall.service.CategoryService;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
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
	@Autowired
	OrderService oService;
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
		session.setAttribute("ois", oiItems);
		model.addAttribute("total", total);
		return "fore/buy";
	}
	@RequestMapping("foreaddCart")
	@ResponseBody
	public String addCart(int pid,int num,HttpSession session) {
		Product p = productService.get(pid);
		User user = (User) session.getAttribute("user");
		boolean found = false;
		List<OrderItem> ois = oiService.listByUser(user.getId());
		for (OrderItem orderItem : ois) {
			if (orderItem.getProduct().getId().intValue()==p.getId().intValue()) {
				orderItem.setNumber(orderItem.getNumber()+num);
				oiService.update(orderItem);
				found = true;
				break;
			}
		}
		if(!found) {
			OrderItem oiItem = new OrderItem();
			oiItem.setNumber(num);
			oiItem.setPid(pid);
			oiItem.setUid(user.getId());
			oiService.add(oiItem);
		}
		return "success";
	}
	@RequestMapping("forecart")
	public String cart(HttpSession session,Model model) {
		User user = (User) session.getAttribute("user");
		List<OrderItem> ois = oiService.listByUser(user.getId());
		model.addAttribute("ois", ois);
		return "fore/cart";
	}
	@RequestMapping("forechangeOrderItem")
	@ResponseBody
	public String changeOrderItem(HttpSession session,int pid,int num) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "fail";
		}
		List<OrderItem> ois = oiService.listByUser(user.getId());
		for (OrderItem orderItem : ois) {
			if (orderItem.getProduct().getId().intValue() == pid) {
				orderItem.setNumber(num);
				oiService.update(orderItem);
				break;
			}
		}
		return "success";
	}
	@RequestMapping("foredeleteOrderItem")
	@ResponseBody
	public String deleteOrderItem(HttpSession session,int oiid) {
		User user = (User) session.getAttribute("user");
		if (user==null) {
			return "fail";
		}
		oiService.delete(oiid);
		return "success";
	}
	@RequestMapping("forecreateOrder")
	public String createOrder(Model model,HttpSession session,Order order) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:loginPage";
		}
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + RandomUtils.nextInt(10000);
		order.setOrderCode(orderCode);
		order.setCreateDate(new Date());
		order.setUid(user.getId());
		order.setStatus(oService.waitPay);
		List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
		float total = oService.add(order, ois);
		return "redirect:forealipay?oid="+order.getId()+"&total="+total;
	}
	@RequestMapping("forepayed")
	public String payed(Model model,int oid,float total) {
		Order order = oService.get(oid);
		order.setStatus(OrderService.waitDelivery);
		order.setPayDate(new Date());
		oService.update(order);
		model.addAttribute("o", order);
		return "fore/payed";
	}
	@RequestMapping("forebought")
	public String bought(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Order> os = oService.list(user.getId(), OrderService.delete);
		oiService.fill(os);
		model.addAttribute("os", os);
		return "fore/bought";
	}
	@RequestMapping("foreconfirmPay")
	public String confirmPay(Model model,int oid) {
		Order order = oService.get(oid);
		oiService.fill(order);
		model.addAttribute("o",order);
		return "fore/confirmPay";
	}
	@RequestMapping("foreorderConfirmed")
	public String orderConfirmed(Model model,int oid) {
		Order order = oService.get(oid);
		order.setStatus(OrderService.waitReview);
		order.setConfirmDate(new Date());
		oService.update(order);
		return "fore/orderConfirmed";
	}
	@RequestMapping("foredeleteOrder")
	@ResponseBody
	public String deleteOrder(int oid) {
		Order order = oService.get(oid);
		order.setStatus(OrderService.delete);
		oService.update(order);
		return "success";
	}
	@RequestMapping("forereview")
	public String review(Model model,int oid) {
		Order order = oService.get(oid);
		oiService.fill(order);
		Product product  = order.getOrderItems().get(0).getProduct();
		List<Review> reviews = rService.list(product.getId());
		productService.setSaleAndReviewNumber(product);
		model.addAttribute("o", order);
		model.addAttribute("p", product);
		model.addAttribute("reviews", reviews);
		return "fore/review";
	}
	@RequestMapping("foredoreview")
	public String doreview(HttpSession session,@RequestParam("oid") int oid,@RequestParam("pid") int pid,@RequestParam("content") String content) {
		Order order = oService.get(oid);
		order.setStatus(OrderService.finish);
		oService.update(order);
		
		Product product = productService.get(pid);
		content = HtmlUtils.htmlEscape(content);
		
		User user = (User) session.getAttribute("user");
		Review review = new Review();
		review.setContent(content);
		review.setCreateDate(new Date());
		review.setPid(pid);
		review.setUid(user.getId());
		rService.add(review);
		return "redirect:forereview?oid="+oid+"&showonly=true";
	}
}
