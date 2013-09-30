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
 * ʱ�䣬��Դ��title���ı�
 */
public class HtmlParserTool {
	// ��ȡһ����վ�ϵ�����,filter ������������
	public static Set<String> extracLinks(String url,LinkFilter filter) {

		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			// �õ����о������˵ı�ǩ
			NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> ��ǩ
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
	 * ��ȡ�ض�ҳ�����ĳ���ַ�����ͷ��url
	 * @param pattenUrl Ҫ��ȡ��URL��ͷ
	 * @param incomeUrl �����URL
	 * return Set<String> ����Ҫ���url����
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
	 * ��ȡ�涨��ǩ�����Ե�����
	 * ʹ��ʾ��:
	 * HtmlParseUtil.getContentByTagNameAndAttribute(sourse, "div", "class", "hello");
	 * ���ȡ����div�ڵ㣬���Ҹýڵ��������class="hello"
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
		         //���ò���Ҫ�õ�ҳ����������������Ϣ  
		         sb.setLinks(false);  
		         //���ý�����Ͽո�������ո������  
		         sb.setReplaceNonBreakingSpaces(true);  
		         //���ý�һ���пո���һ����һ�ո�������  
		         sb.setCollapse(true);  
		         //����Ҫ������URL  
		         sb.setURL(url);  
		         //���ؽ��������ҳ���ı���Ϣ  
		         return sb.getStrings();  
      }
	  
	  public static StringBuffer delTag(String tag,StringBuffer content)//ȥ���ض���ǩ����   
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
