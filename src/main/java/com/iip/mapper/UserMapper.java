package com.iip.mapper;

import com.iip.domain.User;

public interface UserMapper {
	public User findByName(String name);
	public User findById(Integer id);
}
