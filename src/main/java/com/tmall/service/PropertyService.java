package com.tmall.service;

import java.util.List;

import com.tmall.pojo.Property;

public interface PropertyService {
	void add(Property p);
	void delete(int id);
	void update(Property p);
	Property get(int id);
	List<Property> list(int cid);
}
