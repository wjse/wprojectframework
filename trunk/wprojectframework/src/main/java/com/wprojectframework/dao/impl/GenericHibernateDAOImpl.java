package com.wprojectframework.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.oproject.framework.orm.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import com.wprojectframework.dao.GenericDAO;
import com.wprojectframework.util.ClassTypeUtil;

/**
 * 
 * <pre>
 * 通用DAO接口Hibernate抽象实现
 * 分页查询的条件conditionForPage由子类实现
 * </pre>
 * @param <T> Entity
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     GenericDAO
 * @see     ClassTypeUtil
 * @see     SessionFactory
 * @see     Criteria
 * @since   JDK1.6
 */
public abstract class GenericHibernateDAOImpl<T extends Object> implements GenericDAO<T> {

	/**
	 * 泛型类模板
	 */
	private Class<T> clz;
	
	/**
	 * sessionFactory
	 */
	@Autowired@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * session
	 */
	private Session session;
	
	/**
	 * @return the criteria
	 */
	public Criteria getCriteria() {
		return getSession().createCriteria(clz);
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * getSession
	 * @return
	 */
	protected Session getSession() {
		if(null == session || !session.isOpen()){
			session = sessionFactory.openSession();
		}
		return session;
	}
	
	/**
	 * 构造
	 */
	public GenericHibernateDAOImpl(){
		clz = ClassTypeUtil.getGenericSuperclass(getClass(),0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable pk) {
		return (T) getSession().get(clz, pk);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#load(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Serializable pk) {
		return (T) getSession().load(clz, pk);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#select(java.lang.Object)
	 */
	@Override
	@Deprecated
	public T select(Object obj) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#read(java.util.Map)
	 */
	@Override
	@Deprecated
	public Map<String, Object> read(Map<String, Object> map) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#save(java.lang.Object)
	 */
	@Override
	public void save(T t) {
		getSession().save(t);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#insert(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(Map<String, Object> map) {
		T entity = (T) map.get("entity");
		save(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#add(java.lang.Object)
	 */
	@Override
	public Object add(Object obj) {
		return getSession().save(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		getSession().merge(t);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#update(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Map<String, Object> map) {
		T t = (T) map.get("entity");
		update(t);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable pk) {
		del(get(pk));
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#del(java.lang.Object)
	 */
	@Override
	public void del(T t) {
		getSession().delete(t);
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#remove(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void remove(Map<String, Object> map) {
		T t = (T) map.get("entity");
		del(t);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#batchSave(java.util.Collection)
	 */
	@Override
	public void batchSave(Collection<T> col) {
		Iterator<T> it = getCollectionIterator(col);
		while(it.hasNext()){
			save(it.next());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#batchUpdate(java.util.Collection)
	 */
	@Override
	public void batchUpdate(Collection<T> col) {
		Iterator<T> it = getCollectionIterator(col);
		while(it.hasNext()){
			update(it.next());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#batchDelete(java.util.Collection)
	 */
	@Override
	public void batchDelete(Collection<T> col) {
		Iterator<T> it = getCollectionIterator(col);
		while(it.hasNext()){
			del(it.next());
		}
	}
	
	private Iterator<T> getCollectionIterator(Collection<T> col){
		Assert.notNull(col,"the collection is null");
		return col.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#queryForList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryForList(Map<String, Object> map) {
		Criteria criteria = conditionForPage(map);
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#queryForList(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryForList(Object obj) {
		return getSession().createQuery("from " +obj.getClass()).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#queryForPageResult(int, int, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<T> queryForPageResult(Map<String, Object> map,int pageNum, int pageSize) {
		Criteria criteria = conditionForPage(map);
		int count = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setFirstResult((pageNum-1)*pageSize);
		criteria.setMaxResults(pageSize);
		List<T> list = criteria.list();
		PageResult<T> page = new PageResult<T>(count, pageSize, pageNum);
		page.setResultList(list);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.dao.IGenericDAO#queryForPageResult(int, int, java.lang.Object)
	 */
	@Override
	@Deprecated
	public PageResult<T> queryForPageResult(Object obj,int pageNum, int pageSize) {
		return null;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public abstract Criteria conditionForPage(Map<String,Object> map);

}
