package com.ie;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
/*
 *http://www.cnblogs.com/answeryi/archive/2009/11/03/1595509.html 
 *http://allenj2ee.iteye.com/blog/222455
 *http://www.ibm.com/developerworks/cn/opensource/os-cn-crawler/
 */
public class HtmlParserTool {
	// ��ȡһ����վ�ϵ�����,filter ������������
	public static Set<String> extracLinks(String url,LinkFilter filter) {

		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			// ���� <frame >��ǩ�� filter��������ȡ frame ��ǩ��� src ��������ʾ������
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};
			// OrFilter �����ù��� <a> ��ǩ���� <frame> ��ǩ
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			// �õ����о������˵ı�ǩ
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> ��ǩ
				{
					LinkTag link = (LinkTag) tag;//a href="http://www.china.com.cn" target="_blank"
					System.out.println(tag.getText());
					String linkUrl = link.getLink();// url
					if(filter.accept(linkUrl))
						links.add(linkUrl);
				} else// <frame> ��ǩ
				{
		        // ��ȡ frame �� src ���Ե������� <frame src="test.html"/>
					String frame = tag.getText();
					System.out.println(frame);
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if (end == -1)
						end = frame.indexOf(">");
					String frameUrl = frame.substring(5, end - 1);
					if(filter.accept(frameUrl))
						links.add(frameUrl);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
	
	public static Set<String> getLinkStartWithPatten()
	{
		Set<String> links = HtmlParserTool.extracLinks("http://news.sina.com.cn/",new LinkFilter()
		{
			//��ȡ�� http://www.twt.edu.cn ��ͷ������
			public boolean accept(String url) {
				if(url.startsWith("http://news.sina.com.cn/c/"))
					return true;
				else
					return false;
			}
			
		});
		return links;
	}
	//���Ե� main ����
	public static void main(String[]args)
	{
        Set<String> links = HtmlParserTool.extracLinks("http://news.sina.com.cn/",new LinkFilter()
		{
			//��ȡ�� http://www.twt.edu.cn ��ͷ������
			public boolean accept(String url) {
				if(url.startsWith("http://news.sina.com.cn/"))
					return true;
				else
					return false;
			}
			
		});
		//for(String link : links)
			//System.out.println(link);
	}
}
