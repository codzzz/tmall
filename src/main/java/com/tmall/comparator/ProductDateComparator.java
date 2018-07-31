package com.tmall.comparator;

import java.util.Comparator;

import com.tmall.pojo.Product;

public class ProductDateComparator implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getCreateDate().compareTo(o2.getCreateDate());
	}

}
