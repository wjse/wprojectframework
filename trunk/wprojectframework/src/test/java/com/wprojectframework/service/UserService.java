package com.wprojectframework.service;

import java.util.List;
import java.util.Map;
import org.oproject.framework.orm.PageResult;
import com.wprojectframework.model.User;

public interface UserService {
	User getUser(int id);
	List<User> getUsers(Map<String,Object> map);
	PageResult<User> getUserPage(Map<String,Object> map,int pageNum,int pageSize);
	void saveUser(User user);
	void updateUser(User user);
	void deleteUser(int id);
}
