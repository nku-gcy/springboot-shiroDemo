package com.iip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iip.domain.User;
import com.iip.mapper.UserMapper;
import com.iip.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	//注入Mapper接口
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findByName(String name) {
		return userMapper.findByName(name);
	}
	
	@Override
	public User findById(Integer id) {
		return userMapper.findById(id);
	}

	
}
