package com.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.util.ImageUtil;
import com.tmall.util.UploadedImageFile;

@Controller
@RequestMapping("")
public class ProductImageController {
	@Autowired
	ProductImageService piService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@RequestMapping("admin_productImage_list")
	public String list(Model model,int pid) {
		Product p = productService.get(pid);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		List<ProductImage> pisSingle = piService.list(pid, piService.type_single);
		List<ProductImage> pisDetail = piService.list(pid, piService.type_detail);
		model.addAttribute("p", p);
		model.addAttribute("pisSingle", pisSingle);
		model.addAttribute("pisDetail", pisDetail);
		return "admin/listProductImage";
	}
	
	@RequestMapping("admin_productImage_add")
	public String add(HttpSession session,ProductImage pi,UploadedImageFile imageFile) {
		piService.add(pi);
		String filename = pi.getId() + ".jpg";
		String imageFolder = null;
		String imageFolder_small = null;
		String imageFolder_middle = null;
		if (piService.type_single.equals(pi.getType())) {
			imageFolder = session.getServletContext().getRealPath("img/productSingle");
			imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
		}else {
			imageFolder = session.getServletContext().getRealPath("img/productDetail");
		}
		File file = new File(imageFolder, filename);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			imageFile.getImage().transferTo(file);
			BufferedImage img = ImageUtil.change2jpg(file);
			ImageIO.write(img, "jpg", file);
			if (piService.type_single.equals(pi.getType())) {
				File f_small = new File(imageFolder_small, filename);
				File f_middle = new File(imageFolder_middle, filename);
				ImageUtil.resizeImage(file, 56, 56, f_small);
				ImageUtil.resizeImage(file, 217, 190, f_middle);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:admin_productImage_list?pid="+pi.getPid();
	}
	@RequestMapping("admin_productImage_delete")
	public String delete(int piid,HttpSession session) {
		ProductImage pi = piService.get(piid);
		String filename = pi.getId() + ".jpg";
		String imageFolder = null;
		String imageFolder_small = null;
		String imageFolder_middle = null;
		if (ProductImageService.type_single.equals(pi.getType())) {
			imageFolder = session.getServletContext().getRealPath("img/productSingle");
			imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
			File file = new File(imageFolder, filename);
			File f_small = new File(imageFolder_small, filename);
			File f_middle = new File(imageFolder_middle, filename);
			file.delete();
			f_small.delete();
			f_middle.delete();
		}else {
			imageFolder = session.getServletContext().getRealPath("img/productDetail");
			File file = new File(imageFolder, filename);
			file.delete();
		}
		piService.delete(piid);
		return "redirect:admin_productImage_list?pid="+pi.getPid();
	}
}
