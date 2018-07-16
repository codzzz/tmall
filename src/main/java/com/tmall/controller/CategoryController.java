package com.tmall.controller;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tmall.pojo.Category;
import com.tmall.service.CategoryService;
import com.tmall.util.ImageUtil;
import com.tmall.util.Page;
import com.tmall.util.UploadedImageFile;

@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired 
	CategoryService categoryService;
	@RequestMapping("admin_category_list")
	public String list(Model model,Page page) {
		List<Category> cs = categoryService.list(page);
		int total = categoryService.total();
		page.setTotal(total);
		model.addAttribute("cs", cs);
		model.addAttribute("page", page);
		return "admin/listCategory";
	}
	@RequestMapping("admin_category_add")
	public String add(Category category,HttpSession session,UploadedImageFile imageFile) throws IOException {
		categoryService.add(category);
		File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder, category.getId()+".jpg");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		imageFile.getImage().transferTo(file);
		BufferedImage image = ImageUtil.change2jpg(file);
		ImageIO.write(image, "jpg", file);
		return "redirect:/admin_category_list";
	}
	@RequestMapping("admin_category_delete")
	public String delete(int id,HttpSession session) {
		categoryService.delete(id);
		File file = new File(new File(session.getServletContext().getRealPath("img/category")), id+".jpg");
		file.delete();
		return "redirect:/admin_category_list";
	}
	@RequestMapping("admin_category_edit")
	public String edit(Model model,int id) {
		Category category = categoryService.get(id);
		model.addAttribute("c", category);
		return "admin/editCategory";
	}
	@RequestMapping("admin_category_update")
	public String update(Category category,HttpSession session,UploadedImageFile imageFile) throws IOException{
		categoryService.update(category);
		MultipartFile uploadFile = imageFile.getImage();
		if (null != uploadFile && !uploadFile.isEmpty()) {
			File file = new File(session.getServletContext().getRealPath("img/category"), category.getId()+".jpg");
			uploadFile.transferTo(file);
			BufferedImage image = ImageUtil.change2jpg(file);
			ImageIO.write(image, "jpg", file);
		}
		return "redirect:/admin_category_list";
	}
}
