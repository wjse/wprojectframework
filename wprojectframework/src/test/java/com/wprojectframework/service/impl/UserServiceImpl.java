package com.wprojectframework.service.impl;

import java.util.List;
import java.util.Map;

import org.oproject.framework.orm.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wprojectframework.dao.UserDAO;
import com.wprojectframework.model.User;
import com.wprojectframework.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService{
	
	@Autowired@Qualifier("UserDAO")
	UserDAO dao;

	@Override
	public User getUser(int id) {
		return dao.get(id);
	}

	@Override
	public List<User> getUsers(Map<String, Object> map) {
		return dao.queryForList(map);
	}

	@Override
	public void saveUser(User user) {
		dao.save(user);
	}

	@Override
	public void updateUser(User user) {
		dao.update(user);
	}

	@Override
	public void deleteUser(int id) {
		dao.delete(id);
	}

	@Override
	public PageResult<User> getUserPage(Map<String, Object> map, int pageNum,int pageSize) {
		return dao.queryForPageResult(map, pageNum, pageSize);
	}

}
