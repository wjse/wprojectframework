package com.wprojectframework.testMain.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.wprojectframework.cache.CacheTemplate;
import com.wprojectframework.core.BeanHandler;

public class TestCache {
	
	CacheTemplate cacheTemplate;
	
	@Before
	public void SetUp(){
		cacheTemplate = BeanHandler.getBean("cacheTemplage");
	}
	
	@Test
	public void testPut(){
		cacheTemplate.put("key", "value");
	}
	
	@Test
	public void testGet(){
		Object value = cacheTemplate.get("key");
		Assert.assertNotNull(value);
		Assert.assertEquals(value, "value");
	}
	
	@Test
	public void testRemove(){
		cacheTemplate.remove("key");
		Assert.assertNotSame(cacheTemplate.get("key"), "value");
	}
}
