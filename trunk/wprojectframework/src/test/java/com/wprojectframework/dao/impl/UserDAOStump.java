package com.wprojectframework.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.oproject.framework.orm.PageResult;
import org.springframework.stereotype.Component;
import com.wprojectframework.dao.UserDAO;
import com.wprojectframework.model.User;

@Component("UserDAO")
public class UserDAOStump implements UserDAO{

	@Override
	public User get(Serializable pk) {
		return new User((Integer)pk,"getUser"+pk);
	}

	@Override
	public User load(Serializable pk) {
		return null;
	}

	@Override
	public User select(Object obj) {
		return null;
	}

	@Override
	public Map<String, Object> read(Map<String, Object> map) {
		return null;
	}

	@Override
	public void save(User t) {
		System.out.println("Save..."+t);
	}

	@Override
	public void insert(Map<String, Object> map) {
		
	}

	@Override
	public void add(Object obj) {
		
	}

	@Override
	public void update(User t) {
		System.out.println("Update..."+t);
	}

	@Override
	public void update(Map<String, Object> map) {
		
	}

	@Override
	public void delete(Serializable pk) {
		System.out.println("Delete..."+pk);
	}

	@Override
	public void del(User t) {
		
	}

	@Override
	public void remove(Map<String, Object> map) {
		
	}

	@Override
	public void batchSave(Collection<User> col) {
		
	}

	@Override
	public void batchUpdate(Collection<User> col) {
		
	}

	@Override
	public void batchDelete(Collection<User> col) {
		
	}

	@Override
	public List<User> queryForList(Map<String, Object> map) {
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 20; i++) {
			list.add(new User(i+1,"User"+i));
		}
		return list;
	}

	@Override
	public List<User> queryForList(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult<User> queryForPageResult(Map<String, Object> map,int pageNum, int pageSize) {
		return new PageResult<User>(map.size(), pageSize, pageNum);
	}

	@Override
	public PageResult<User> queryForPageResult(Object obj, int pageNum,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}


}
