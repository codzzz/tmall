package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.ProductMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductExample;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper mapper;
	@Autowired
	CategoryService categoryService;
	@Override
	public void add(Product product) {
		mapper.insert(product);
	}

	@Override
	public void delete(int pid) {
		mapper.deleteByPrimaryKey(pid);
	}

	@Override
	public void update(Product product) {
		mapper.updateByPrimaryKeySelective(product);
	}

	@Override
	public Product get(int pid) {
		return mapper.selectByPrimaryKey(pid);
	}

	@Override
	public List<Product> list(int cid) {
		ProductExample example = new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		List<Product> result = mapper.selectByExample(example);
		setCategory(result);
		return result;
	}
	public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }
}
