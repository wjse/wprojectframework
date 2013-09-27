package com.wprojectframework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> list = new ArrayList<String>();
	private Map<String,Object> map = new HashMap<String, Object>();
	private User son;
	private int tid;
	private String tname;
	private String[] array;
	/**
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}
	/**
	 * @return the map
	 */
	public Map<String, Object> getMap() {
		return map;
	}
	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	/**
	 * @return the son
	 */
	public User getSon() {
		return son;
	}
	/**
	 * @param son the son to set
	 */
	public void setSon(User son) {
		this.son = son;
	}
	/**
	 * @return the tid
	 */
	public int getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}
	/**
	 * @return the tname
	 */
	public String getTname() {
		return tname;
	}
	/**
	 * @param tname the tname to set
	 */
	public void setTname(String tname) {
		this.tname = tname;
	}
	/**
	 * @return the array
	 */
	public String[] getArray() {
		return array;
	}
	/**
	 * @param array the array to set
	 */
	public void setArray(String[] array) {
		this.array = array;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestEntity [list=");
		builder.append(list);
		builder.append(", map=");
		builder.append(map);
		builder.append(", son=");
		builder.append(son);
		builder.append(", tid=");
		builder.append(tid);
		builder.append(", tname=");
		builder.append(tname);
		builder.append(", array=");
		builder.append(Arrays.toString(array));
		builder.append("]");
		return builder.toString();
	}
	
}
