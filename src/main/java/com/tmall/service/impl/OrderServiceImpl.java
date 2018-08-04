package com.tmall.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tmall.mapper.OrderMapper;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderExample;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import com.tmall.service.UserService;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderMapper omapper;
	@Autowired
	OrderItemService oiService;
	@Autowired
	UserService uService;
	@Override
	public void add(Order o) {
		omapper.insert(o);
	}

	@Override
	public void delete(int id) {
		omapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Order o) {
		omapper.updateByPrimaryKeySelective(o);
	}

	@Override
	public Order get(int id) {
		Order order = omapper.selectByPrimaryKey(id);
		oiService.fill(order);
		setUser(order);
		return order;
	}

	@Override
	public List<Order> list() {
		OrderExample example = new OrderExample();
		example.setOrderByClause("id desc");
		List<Order> os = omapper.selectByExample(example);
		Logger.getLogger(this.getClass()).debug("-------os size-----:"+os.size());
		oiService.fill(os);
		setUser(os);
		return os;
	}
	
	private void setUser(Order o) {
		User u = uService.get(o.getUid());
		o.setUser(u);
	}
	private void setUser(List<Order> os) {
		for (Order order : os) {
			setUser(order);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public float add(Order c, List<OrderItem> ois) {
		float total = 0;
		add(c);
		for (OrderItem orderItem : ois) {
			orderItem.setOid(c.getId());
			oiService.update(orderItem);
			total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
		}
		return total;
	}

	@Override
	public List<Order> list(int uid, String excludedStatus) {
		OrderExample example = new OrderExample();
		example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
		example.setOrderByClause("id desc");
		List<Order> os = omapper.selectByExample(example);
		return os;
	}
}
