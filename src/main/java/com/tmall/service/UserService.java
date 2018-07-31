package com.tmall.service;

import java.util.List;

import com.tmall.pojo.User;

public interface UserService {
	void add(User user);
	void delete(int id);
	void update(User user);
	User get(int id);
	List<User> list();
	Boolean isExist(String name);
	User get(String name,String password);
}
