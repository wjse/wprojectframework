package com.wprojectframework.dao;

import org.oproject.framework.orm.ibatis.bytecode.codegenerator.annotations.DynamicIbatisDAO;
import com.wprojectframework.model.User;

@DynamicIbatisDAO(value="UserDAO")
public interface UserDAO extends IGenericDAO<User>{

}
