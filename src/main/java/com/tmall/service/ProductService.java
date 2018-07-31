package com.tmall.service;

import java.util.List;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;

public interface ProductService {
	void add(Product product);
	void delete(int pid);
	void update(Product product);
	Product get(int pid);
	List<Product> list(int cid);
	
	void fill(List<Category> cs);
	
	void fill(Category c);
	
	void fillByRow(List<Category> cs);
	
	void setSaleAndReviewNumber(Product p);
	void setSaleAndReviewNumber(List<Product> ps);
	
	List<Product> search(String keyword);
}
