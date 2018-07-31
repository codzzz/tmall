package com.tmall.comparator;

import java.util.Comparator;

import com.tmall.pojo.Product;

public class ProductSaleCountComparator implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o2.getSaleCount()-o1.getSaleCount();
	}

}
