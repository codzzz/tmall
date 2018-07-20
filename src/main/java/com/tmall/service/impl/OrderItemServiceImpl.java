package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.OrderItemMapper;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.OrderItemExample;
import com.tmall.pojo.Product;
import com.tmall.service.OrderItemService;
import com.tmall.service.ProductService;
@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	OrderItemMapper oimapper;
	@Autowired
	ProductService pService;
	
	@Override
	public void add(OrderItem oi) {
		oimapper.insert(oi);
	}

	@Override
	public void delete(int id) {
		oimapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(OrderItem oi) {
		oimapper.updateByPrimaryKeySelective(oi);
	}

	@Override
	public OrderItem get(int id) {
		OrderItem orderItem = oimapper.selectByPrimaryKey(id);
		setProduct(orderItem);
		return orderItem;
	}

	@Override
	public List<OrderItem> list() {
		OrderItemExample example = new OrderItemExample();
		example.setOrderByClause("id desc");
		List<OrderItem> ois = oimapper.selectByExample(example);
		setProduct(ois);
		return ois;
	}

	@Override
	public void fill(List<Order> os) {
		for (Order order : os) {
			fill(order);
		}
	}

	@Override
	public void fill(Order o) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andOidEqualTo(o.getId());
		example.setOrderByClause("id desc");
		List<OrderItem> ois = oimapper.selectByExample(example);
		setProduct(ois);
		float total = 0;
		int totalNumber = 0;
		for (OrderItem orderItem : ois) {
			total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
			totalNumber += orderItem.getNumber();
		}
		o.setTotal(total);
		o.setTotalNumber(totalNumber);
		o.setOrderItems(ois);
	}
	private void setProduct(OrderItem oi) {
		Product p = pService.get(oi.getPid());
		oi.setProduct(p);
	}
	private void setProduct(List<OrderItem> ois) {
		for (OrderItem orderItem : ois) {
			setProduct(orderItem);
		}
	}
}
