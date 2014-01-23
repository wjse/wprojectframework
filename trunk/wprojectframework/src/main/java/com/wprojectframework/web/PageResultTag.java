package com.wprojectframework.web;

import java.io.IOException;
import java.text.MessageFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.oproject.framework.orm.PageResult;

/**
 * 
 * <pre>自定义分页标签</pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     TagSupport
 * @since   JDK1.6
 */
public class PageResultTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 默认css样式
	 */
	private String styleClass = "page";
	
	/**
	 * 分页对象
	 */
	private PageResult<Object> pageResult;
	
	/**
	 * 提交路径
	 */
	private String urlFormat;
	
	/**
	 * html
	 */
	private StringBuffer sb = new StringBuffer(300);
	
	/**
	 * logger
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @return the pageResult
	 */
	public PageResult<Object> getPageResult() {
		return pageResult;
	}

	/**
	 * @param pageResult the pageResult to set
	 */
	public void setPageResult(PageResult<Object> pageResult) {
		this.pageResult = pageResult;
	}

	/**
	 * @return the urlFormat
	 */
	public String getUrlFormat() {
		return urlFormat;
	}

	/**
	 * @param urlFormat the urlFormat to set
	 */
	public void setUrlFormat(String urlFormat) {
		this.urlFormat = urlFormat;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		if(null == pageResult){
			pageResult = PageResult.EMPTY_PAGE;
		}
		buildPage();
		try{
			pageContext.getOut().print(sb.toString());
		}catch (IOException e){
			logger.error(e);
		}
		return EVAL_PAGE;
	}
	
	/**
	 * 构建页面
	 */
	private void buildPage(){
		sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
		sb.append(styleClass);
		sb.append("\"><tr><td><span>");
		
		//首页
		home();
				
		// 上一页按钮
		previous();
		
		/*中间页*/ 
		middle();
		
		// 下一页按钮
		next();
		
		//尾页
		last();
		
		//总条数
		totalCount();
	}
	
	/**
	 * 首页
	 */
	private void home(){
		sb.append("<a href=\"");
		sb.append(MessageFormat.format(urlFormat, this.transform(String.valueOf(1)), new Object[]{}));
		sb.append("\">首页</a>");
		sb.append(printA(1));
	}
	
	/**
	 * 上一页
	 */
	private void previous(){
		sb.append("<a href=\"");
		if(pageResult.getIsFirstPage()){
			sb.append("javascript:void(0);");
		}else{
			sb.append(MessageFormat.format(urlFormat, this.transform(String.valueOf(pageResult.getPreviousPageNo())), new Object[]{}));
		}
		sb.append("\">上一页</a>");
	}
	
	/**
	 * 中间页
	 */
	private void middle(){
		int printCount = 10;
		for(int i = (pageResult.getCurrentPageNo() - 4 > 2?pageResult.getCurrentPageNo() - 4 : 2);
			i < pageResult.getCurrentPageNo() && printCount > 5; i++){
			sb.append(printA(i));
			printCount --;
		}
		// 
		for(int i = (pageResult.getCurrentPageNo() > 2?pageResult.getCurrentPageNo():2); 
			i < pageResult.getTotalPageCount() && printCount > 0; i++){
			sb.append(printA(i));
			printCount --;
		}
		//
		if(pageResult.getTotalPageCount() > 1){
			sb.append(printA(pageResult.getTotalPageCount()));
		}
	}
	
	/**
	 * 下一页
	 */
	private void next(){
		sb.append("<a href=\"");
		if(pageResult.getIsLastPage()){
			sb.append("javascript:void(0);");
		}else{
			sb.append(MessageFormat.format(urlFormat, this.transform(String.valueOf(pageResult.getNextPageNo())), new Object[]{}));
		}
		sb.append("\">下一页</a>");
	}
	
	/**
	 * 尾页
	 */
	private void last(){
		sb.append("<a href=\"");
		sb.append(MessageFormat.format(urlFormat, this.transform(String.valueOf(pageResult.getTotalPageCount())), new Object[]{}));
		sb.append("\">尾页</a>");
	}
	
	/**
	 * 总条数
	 */
	private void totalCount(){
		sb.append("<a>总条数:");
		sb.append(pageResult.getTotalSize());
		sb.append("</a></span>");
		sb.append("</td></tr>");
		sb.append("</table>");
	}
	
	/**
	 * 打印页面标签
	 * @param pageNO
	 * @return
	 */
	private String printA(int pageNO){
		StringBuilder sb = new StringBuilder(300);
		sb.append("<a ");
		if(pageNO == pageResult.getCurrentPageNo()){
			sb.append("class=\"c\" ");
		}
		sb.append("href=\"");
		sb.append(MessageFormat.format(urlFormat, this.transform(String.valueOf(pageNO)), new Object[]{}));
		sb.append("\">");
		sb.append(pageNO);
		sb.append("</a>");
		return sb.toString();
	}
	
	/**
	 * 去除逗号
	 * 因为数值在多位数情况下会被默认添加逗号区分位数如1,000
	 * 需要将1,000转换成1000
	 * @param str
	 * @return
	 */
	private StringBuffer transform(String str) 
	{
		StringBuffer sb = null;
		if(null != str)
		{
			String[] strs = str.split("[,]");
			if(null != strs)
			{
				sb = new StringBuffer();
				for (int i = 0; i < strs.length; i++) 
				{
					sb.append(strs[i]);
				}
			}
		}
		return sb;
	}

}
