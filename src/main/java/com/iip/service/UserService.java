package com.iip.service;

import com.iip.domain.User;

public interface UserService {

	public User findByName(String name);
	
	public User findById(Integer id);
}
