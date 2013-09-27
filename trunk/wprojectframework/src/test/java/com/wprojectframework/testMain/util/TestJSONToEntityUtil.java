package com.wprojectframework.testMain.util;

import java.util.Date;

import net.sf.json.JSONObject;
import com.wprojectframework.model.TestEntity;
import com.wprojectframework.model.User;
import com.wprojectframework.util.JSONToEntityUtil;

public class TestJSONToEntityUtil {
	
	public static void main(String[] args) {
		User user = new User();
		user.setId(1);
		user.setUsername("wujia");
		TestEntity entity = new TestEntity();
		entity.getList().add("a");
		entity.getList().add("b");
		entity.getList().add("c");
		entity.getMap().put("1", new TestEntity());
		entity.getMap().put("2", new User(2,"hello"));
		entity.getMap().put("3", new Date());
		entity.getMap().put("4", Integer.valueOf("4"));
		entity.getMap().put("5", Short.valueOf("5"));
		entity.getMap().put("6", Float.valueOf("6"));
		entity.getMap().put("7", Byte.valueOf("7"));
		entity.getMap().put("8", Long.valueOf("8"));
		entity.getMap().put("9", Double.valueOf("9"));
		entity.getMap().put("10", Boolean.valueOf("true"));
		entity.setSon(user);
		entity.setTid(1);
		entity.setTname("TestEntity");
		entity.setArray(new String[]{"sdas","sadad","asfafa"});
		JSONObject json = JSONObject.fromObject(entity);
//		System.out.println(json.toBean(json));
		TestEntity newEntity = JSONToEntityUtil.getEntity(TestEntity.class,json);
		System.out.println(newEntity);
		System.out.println(newEntity.getMap().get("7").getClass());
//		System.out.println(newEntity);
//		System.out.println(newEntity.getMap().get("3"));
	}
}

