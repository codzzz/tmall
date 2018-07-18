package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.ProductImageMapper;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.ProductImageExample;
import com.tmall.service.ProductImageService;
@Service
public class ProductImageImpl implements ProductImageService {

	@Autowired
	ProductImageMapper ProImmapper;
	@Override
	public void add(ProductImage image) {
		ProImmapper.insert(image);
	}

	@Override
	public void delete(int piid) {
		ProImmapper.deleteByPrimaryKey(piid);
	}

	@Override
	public void update(ProductImage image) {
		ProImmapper.updateByPrimaryKeySelective(image);
	}

	@Override
	public ProductImage get(int piid) {
		return ProImmapper.selectByPrimaryKey(piid);
	}

	@Override
	public List<ProductImage> list(int pid, String type) {
		ProductImageExample example = new ProductImageExample();
		example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
		example.setOrderByClause("id desc");
		return ProImmapper.selectByExample(example);
	}

}
