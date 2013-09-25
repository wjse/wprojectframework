package com.wprojectframework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @class MD5Util.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * MD5工具类
 */
public class MD5Util {
	
	private static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

	/**
	 * 私有构造
	 */
	private MD5Util() {
	}

	/**
	 * 加密字符串
	 * 
	 * @param s
	 * @return MD5 String
	 */
	public static final String getMd5Str(String s) {
		if(null == s){
			return "";
		}
		byte[] strTemp = s.getBytes();
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = HEX_DIGITS[(byte0 >>> 4 & 0xF)];
				str[(k++)] = HEX_DIGITS[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
