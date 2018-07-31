package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.mapper.ReviewMapper;
import com.tmall.pojo.Review;
import com.tmall.service.ReviewService;
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	@Override
	public void add(Review c) {
	}

	@Override
	public void delete(int id) {
	}

	@Override
	public void update(Review c) {
	}

	@Override
	public Review get(int id) {
		return null;
	}

	@Override
	public List<Review> list(int pid) {
		return null;
	}

	@Override
	public int getCount(int pid) {
		return 0;
	}

}
