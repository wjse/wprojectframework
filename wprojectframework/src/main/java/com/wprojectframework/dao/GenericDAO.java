package com.wprojectframework.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.oproject.framework.orm.PageResult;

/**
 * 
 * @class IGenericDAO.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo 
 * 公用ibatis,hibernate DAO接口定义
 * @param <T> Entity
 */
public interface GenericDAO<T> {

	/* 查询单条记录 */
	/**
	 * 根据主键查询实体
	 * Hibernate是根据Session.get(Class,pk)进行查询的
	 * @param Serializable pk
	 * @return T
	 */
	T get(Serializable pk);

	/**
	 * 根据主键加载实体
	 * Hibernate是根据Session.load(Class,pk)进行查询的
	 * @param Serializable pk
	 * @return T
	 */
	T load(Serializable pk);

	/**
	 * 根据Object查询实体
	 * Hibernate未实现该行为
	 * @param Object obj
	 * @return Object
	 */
	T select(Object obj);

	/**
	 * 根据Map查询Map
	 * Hibernate未实现该行为
	 * @param Map map
	 * @return Map
	 */
	Map<String, Object> read(Map<String, Object> map);

	/* 新增 */
	/**
	 * 新增实体
	 * Hibernate是根据Session.save(T)进行新增的
	 * @param T t
	 */
	void save(T t);

	/**
	 * 新增Map类型实体
	 * Hibernate:
	 * 1- T t = map.get("entity");
	 * 2-Session.save(t)
	 * @param Map map
	 */
	void insert(Map<String, Object> map);

	/**
	 * 新增Object类型实体
	 * Hibernate是根据Session.save(obj)进行新增的
	 * @param Object obj
	 */
	Object add(Object obj);

	/* 修改 */
	/**
	 * 修改实体
	 * Hibernate是根据Session.update(t)进行修改
	 * @param T t
	 */
	void update(T t);

	/**
	 * 根据Map修改
	 * Hibernate:
	 * 1- T t = map.get("entity");
	 * 2-Session.update(t)
	 * @param Map map
	 */
	void update(Map<String, Object> map);

	/* 删除 */
	/**
	 * 根据主键删除
	 * Hibernate是根据Session.delete(Session.get(pk))进行删除
	 * @param Serializable pk
	 */
	void delete(Serializable pk);

	/**
	 * 根据实体删除
	 * Hibernate是根据Session.delete(t)进行删除
	 * @param T t
	 */
	void del(T t);

	/**
	 * 根据Map删除
	 * Hibernate:
	 * 1- T t = map.get("entity");
	 * 2-Session.delete(t)
	 * @param Map map
	 */
	void remove(Map<String, Object> map);

	/* 批量 */
	/**
	 * 批量新增
	 * Hibernate最好由子类进行JDBC重写
	 * @param Collection col
	 */
	void batchSave(Collection<T> col);

	/**
	 * 批量修改
	 * Hibernate最好由子类进行JDBC重写
	 * @param Collection col
	 */
	void batchUpdate(Collection<T> col);

	/**
	 * 批量删除
	 * Hibernate最好由子类进行JDBC重写
	 * @param Collection col
	 */
	void batchDelete(Collection<T> col);

	/* 查询结果集 */
	/**
	 * 根据Map查询结果集
	 * Hibernate根据Criteria查询
	 * @param Map map
	 * @return List
	 */
	List<T> queryForList(Map<String, Object> map);

	/**
	 * 根据Object查询结果集
	 * Hibernate根据obj类模板查询，无条件 
	 * @param Object obj
	 * @return List
	 */
	List<T> queryForList(Object obj);

	/* 查询分页 */
	/**
	 * 根据Map查询分页
	 * Hibernate根据Criteria查询
	 * @param int pageNum
	 * @param int pageSize
	 * @param Map map
	 * @return PageResult
	 */
	PageResult<T> queryForPageResult(Map<String, Object> map,int pageNum, int pageSize);

	/**
	 * 根据Object查询分页
	 * Hibernate未实现该行为
	 * @param int pageNum
	 * @param int pageSize
	 * @param Object obj
	 * @return PageResult
	 */
	PageResult<T> queryForPageResult(Object obj,int pageNum, int pageSize);
}
