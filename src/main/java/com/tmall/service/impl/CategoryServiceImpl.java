package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.CategoryMapper;
import com.tmall.pojo.Category;
import com.tmall.service.CategoryService;
import com.tmall.util.Page;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryMapper categorymapper;
	@Override
	public List<Category> list(Page page) {
		// TODO Auto-generated method stub
		return categorymapper.list(page);
	}
	public int total() {
		return categorymapper.total();
	}
	@Override
	public void add(Category category) {
		categorymapper.add(category);
	}
	@Override
	public void delete(int id) {
		categorymapper.delete(id);
	}
	@Override
	public Category get(int id) {
		return categorymapper.get(id);
	}
	@Override
	public void update(Category category) {
		categorymapper.update(category);
	}
}
