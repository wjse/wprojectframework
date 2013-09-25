package com.wprojectframework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

/**
 * 
 * @class FtpUtil.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 文件上传工具类
 * 集成apache FTPClient,
 * FTP连接属性由spring IOC注入
 */
public class FtpUtil{
	
	/**
	 * apache FTPClient
	 */
	private FTPClient client;
	
	/**
	 * FTP路径
	 */
	private String host;
	
	/**
	 * FTP端口号
	 */
	private int port;
	
	/**
	 * 服务器登录名
	 */
	private String username;
	
	/**
	 * 服务器密码
	 */
	private String password;
	
	/**
	 * 上传目录
	 */
	private String dir;
	
	/**
	 * 文件流大小
	 */
	private int size;
	
	/**
	 * logger
	 */
	private static final Logger log = Logger.getLogger(FtpUtil.class);
	

	/**
	 * @return the client
	 */
	public FTPClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(FTPClient client) {
		this.client = client;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 上传文件
	 * @param imgBytes
	 * @param imgName
	 * @return
	 */
	public String upload(byte[] imgBytes,String imgName){
		//TODO
		OutputStream out = null;
		ByteArrayInputStream bis = null;
		try {
			initClient();
			client.deleteFile(imgName);//如果服务器上有同名的文件先删除
			out = client.storeUniqueFileStream(imgName);//获取自定义输出流
			bis = new ByteArrayInputStream(imgBytes);
			int c = 0;
			byte[] b = new byte[size];
			while((c = bis.read(b)) != -1){
				out.write(b,0,c);
			}
			//FTP图片相对路径
			String path = dir+File.separator+imgName;
			if(log.isDebugEnabled()){
				log.debug("file uploaded success on "+host+":"+port+File.separator+path);
			}
			return path;
		} catch (IOException e) {
			log.error(e.toString());
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				if(bis != null){
					bis.close();
				}
				if(out != null){
					out.close();
				}
				client.disconnect();
			} catch (IOException e) {
				log.error(e.toString());
				throw new RuntimeException(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 初始化连接
	 * @throws IOException
	 */
	private void initClient() throws IOException{
		client = new FTPClient();
		client.connect(host,port);
		client.login(username, password);
		client.changeWorkingDirectory(dir);
		//FTPClient默认文件类型为ASCII,图片文件需要设置文件类型为二进制
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		if(log.isDebugEnabled()){
			log.debug("connect the FTP server successful in : "+host+":"+port);
		}
	}
}
