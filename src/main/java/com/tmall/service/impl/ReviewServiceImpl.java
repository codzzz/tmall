package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.ReviewMapper;
import com.tmall.pojo.Review;
import com.tmall.pojo.ReviewExample;
import com.tmall.pojo.User;
import com.tmall.service.ReviewService;
import com.tmall.service.UserService;
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	@Autowired
	UserService uService;
	@Override
	public void add(Review c) {
		reviewMapper.insert(c);
	}

	@Override
	public void delete(int id) {
		reviewMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Review c) {
		reviewMapper.updateByPrimaryKeySelective(c);
	}

	@Override
	public Review get(int id) {
		return reviewMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Review> list(int pid) {
		ReviewExample example = new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("id desc");
		List<Review> reviews = reviewMapper.selectByExample(example);
		setUser(reviews);
		return reviews;
	}

	@Override
	public int getCount(int pid) {
		return list(pid).size();
	}
	
	private void setUser(Review review) {
		User user = uService.get(review.getUid());
		review.setUser(user);
	}
	private void setUser(List<Review> reviews) {
		for (Review review : reviews) {
			setUser(review);
		}
	}
}
