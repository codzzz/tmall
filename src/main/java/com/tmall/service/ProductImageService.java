package com.tmall.service;

import java.util.List;

import com.tmall.pojo.ProductImage;

public interface ProductImageService {
	public String type_single = "type_single";
	public String type_detail = "type_detail";
	
	void add(ProductImage image);
	void delete(int piid);
	void update(ProductImage image);
	ProductImage get(int piid);
	List<ProductImage> list(int pid,String type);
}
