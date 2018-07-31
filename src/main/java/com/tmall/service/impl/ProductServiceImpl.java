package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.ProductMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductExample;
import com.tmall.pojo.ProductImage;
import com.tmall.service.CategoryService;
import com.tmall.service.OrderItemService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.service.ReviewService;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper mapper;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductImageService piService;
	@Autowired
	OrderItemService oiService;
	@Autowired
	ReviewService rService;
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

	@Override
	public void fill(List<Category> cs) {
		for (Category category : cs) {
			fill(category);
		}
	}

	@Override
	public void fill(Category c) {
		List<Product> ps = list(c.getId());
		c.setProducts(ps);
	}

	@Override
	public void fillByRow(List<Category> cs) {
		int productNumberEachRow = 8;
		for (Category category : cs) {
			List<Product> products = list(category.getId());
			List<List<Product>> productByRow = new ArrayList<>();
			for (int i = 0; i < products.size(); i+=productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = size > products.size()?products.size():size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productByRow.add(productsOfEachRow);
			}
			category.setProductsByRow(productByRow);
		}
	}

	@Override
	public void setSaleAndReviewNumber(Product p) {
		int saleNumber = oiService.getSaleCount(p.getId());
		p.setSaleCount(saleNumber);
		int reviewNumber = rService.getCount(p.getId());
		p.setReviewCount(reviewNumber);
	}

	@Override
	public void setSaleAndReviewNumber(List<Product> ps) {
		for (Product product : ps) {
			setSaleAndReviewNumber(product);
		}
	}

	@Override
	public List<Product> search(String keyword) {
		ProductExample example = new ProductExample();
		example.createCriteria().andNameLike("%"+keyword+"%");
		example.setOrderByClause("id desc");
		List<Product> ps = mapper.selectByExample(example);
		setCategory(ps);
		setImage(ps);
		return ps;
	}
}
