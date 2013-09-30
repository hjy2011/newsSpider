package ClawerTool;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
/*
 * 时间，来源，title，文本
 */
public class HtmlParserTool {
	// 获取一个网站上的链接,filter 用来过滤链接
	public static Set<String> extracLinks(String url,LinkFilter filter) {

		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			// 得到所有经过过滤的标签
			NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> 标签
				{
					LinkTag link = (LinkTag) tag;//a href="http://www.china.com.cn" target="_blank"
					String linkUrl = link.getLink();// url
					if(filter.accept(linkUrl))
						links.add(linkUrl);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
	/*
	 * 获取特定页面的以某个字符串开头的url
	 * @param pattenUrl 要获取的URL开头
	 * @param incomeUrl 传入的URL
	 * return Set<String> 符合要求的url集合
	 */
	public static Set<String> getLinkStartWithPatten(final String pattenUrl,String incomeUrl)
	{
		Set<String> links = HtmlParserTool.extracLinks(incomeUrl,new LinkFilter()
		{
			public boolean accept(String url) {
				if(url.startsWith(pattenUrl))
					return true;
				else
					return false;
			}
			
		});
		return links;
	}
	
	
	/**
	 * 获取规定标签及属性的内容
	 * 使用示例:
	 * HtmlParseUtil.getContentByTagNameAndAttribute(sourse, "div", "class", "hello");
	 * 会获取所有div节点，并且该节点具有属性class="hello"
	 * 
	 * @param sourse
	 * @param tagName
	 * @param attribute
	 * @param attributeValue
	 * @return
	 */
	public static List<String> getContentByTagNameAndAttribute(String sourse, String tagName, String attribute, String attributeValue)
	{
		List<String> list = new ArrayList<String>();
		Parser parser = null;
		
		NodeFilter tagNameFilter = new TagNameFilter(tagName);
		NodeFilter classNameFilter = new HasAttributeFilter(attribute, attributeValue);
		NodeFilter and = new AndFilter(tagNameFilter, classNameFilter);
		try {
			parser = new Parser(sourse);
			parser.setEncoding("gb2312");
			NodeList nodeList = parser.extractAllNodesThatMatch(and);
			for(int i=0; i<nodeList.size(); ++i)
			{
				String text = nodeList.elementAt(i).toPlainTextString();
				list.add(text);
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public static List<String> getHtmlByTagNameAndAttribute(String sourse, String tagName, String attribute, String attributeValue)
	{
		List<String> list = new ArrayList<String>();
		Parser parser = null;
		
		NodeFilter tagNameFilter = new TagNameFilter(tagName);
		NodeFilter classNameFilter = new HasAttributeFilter(attribute, attributeValue);
		NodeFilter and = new AndFilter(tagNameFilter, classNameFilter);
		try {
			parser = new Parser(sourse);
			parser.setEncoding("gb2312");
			NodeList nodeList = parser.extractAllNodesThatMatch(and);
			for(int i=0; i<nodeList.size(); ++i)
			{
				String text = nodeList.elementAt(i).toHtml();
				list.add(text);
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	 
	 public static  String getTitle(String url)
	 {
		 String tmp=null;
		 Parser parser;
		try {
			parser = new Parser(url);
			TagNameFilter filter = new TagNameFilter("title");
			NodeList nl =parser.extractAllNodesThatMatch(filter);
			for(int i =0;i <nl.size();i++){
			    tmp = nl.elementAt(i).toPlainTextString();
			    System.out.println(tmp);
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return tmp;
	 }
	 public static  String getParagraph(String url)
	 {
		 String tmp=null;
		 Parser parser;
		try {
			parser = new Parser(url);
			NodeFilter filter = new NodeClassFilter(ParagraphTag.class);
			NodeList nl =parser.extractAllNodesThatMatch(filter);
			for(int i =0;i <nl.size();i++){
			    tmp += nl.elementAt(i).toPlainTextString();
			    System.out.println(tmp);
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return tmp;
	 }
	  public static String getText(String url){ 
		  
		         StringBean sb = new StringBean();  
		         //sb.getClass().  
		         //设置不需要得到页面所包含的链接信息  
		         sb.setLinks(false);  
		         //设置将不间断空格由正规空格所替代  
		         sb.setReplaceNonBreakingSpaces(true);  
		         //设置将一序列空格由一个单一空格所代替  
		         sb.setCollapse(true);  
		         //传入要解析的URL  
		         sb.setURL(url);  
		         //返回解析后的网页纯文本信息  
		         return sb.getStrings();  
      }
	  
	  public static StringBuffer delTag(String tag,StringBuffer content)//去除特定标签内容   
	    {  
	        String beginTag="<"+tag;  
	        String endTag="</"+tag+">";  
	        int pos1=0;  
	        int pos2=0;  
	        while((pos2=content.indexOf(beginTag,0))!=-1)  
	        {  
	                pos1=content.indexOf(endTag,pos2)+endTag.length()-1;  
	                if(pos1>pos2)  
	                {  
	                    content=content.delete(pos2, pos1);  
	                }  
	                else  
	                {  
	                    pos1=content.lastIndexOf("</");  
	                    if(pos1>pos2)  
	                    {  
	                        content=content.delete(pos2, pos1);  
	                        content=content.append(tag+"></body></html");  
	                    }  
	                    else  
	                    {  
	                        content=content.delete(pos2, content.length());  
	                        content=content.append("</body></html");  
	                    }  
	                }  
	        }  
	        return content;  
	     }  

}
