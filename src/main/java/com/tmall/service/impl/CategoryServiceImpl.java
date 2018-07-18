package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.CategoryMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.CategoryExample;
import com.tmall.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryMapper categorymapper;
	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		CategoryExample example = new CategoryExample();
		example.setOrderByClause("id desc");
		return categorymapper.selectByExample(example);
	}
	@Override
	public void add(Category category) {
		categorymapper.insert(category);
	}
	@Override
	public void delete(int id) {
		categorymapper.deleteByPrimaryKey(id);
	}
	@Override
	public Category get(int id) {
		return categorymapper.selectByPrimaryKey(id);
	}
	@Override
	public void update(Category category) {
		categorymapper.updateByPrimaryKeySelective(category);
	}
}
