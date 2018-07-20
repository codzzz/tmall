package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.ProductMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductExample;
import com.tmall.pojo.ProductImage;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper mapper;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductImageService piService;
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
		Product product = mapper.selectByPrimaryKey(pid);
		 Category c = categoryService.get(product.getCid());
		 product.setCategory(c);
		 setImage(product);
		return product;
	}

	@Override
	public List<Product> list(int cid) {
		ProductExample example = new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		List<Product> result = mapper.selectByExample(example);
		setCategory(result);
		setImage(result);
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
    public void setImage(List<Product> ps) {
    		for (Product product : ps) {
				setImage(product);
			}
    }
    public void setImage(Product p) {
    		ProductImage productImage = piService.list(p.getId(), "type_single").get(0);
    		p.setpImage(productImage);
    }
}
