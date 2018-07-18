package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.PropertyMapper;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyExample;
import com.tmall.service.PropertyService;
@Service
public class PropertyServiceImpl implements PropertyService {
	@Autowired
	PropertyMapper mapper;
	@Override
	public void add(Property p) {
		mapper.insert(p);
	}

	@Override
	public void delete(int id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Property p) {
		mapper.updateByPrimaryKeySelective(p);
	}

	@Override
	public Property get(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Property> list(int cid) {
		PropertyExample example = new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return mapper.selectByExample(example);
	}

}
