package com.tmall.service;

import java.util.List;

import com.tmall.pojo.Order;

public interface OrderService {
	String waitPay = "waitPay";
	String waitDelivery = "waitDelivery";
	String waitConfirm = "waitConfirm";
	String waitReview = "waitReview";
	String finish = "finish";
	String delete = "delete";
	void add(Order o);
	void delete(int id);
	void update(Order o);
	Order get(int id);
	List<Order> list();
}
