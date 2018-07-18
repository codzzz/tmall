package com.tmall.service;

import java.util.List;

import com.tmall.pojo.Product;

public interface ProductService {
	void add(Product product);
	void delete(int pid);
	void update(Product product);
	Product get(int pid);
	List<Product> list(int cid);
}
