package SinaNewsClawer;

import java.util.Set;
import java.util.HashSet;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
public class TestHtmlParser {
	public static Set<String> FilteLink(String url,LinkFilter fiter)//fiter是自己定义的过滤连接的接口
	{
		Set<String> linkSet = new HashSet<String>();
		try {
			Parser HtmlParser = new Parser(url);
			HtmlParser.setEncoding("gb2312");
			NodeList list = HtmlParser.parse(null);
			for(int i = 0;i<list.size();i++)
			{
				System.out.println(list.elementAt(i).getChildren());
			}
			//自定义链接过滤器，取下frame链接
			StringFilter Framefilter = new StringFilter(){
				public boolean accept(Node node){
					//System.out.println(node.getText());
					if(node.getText().startsWith("iframe src="))
					{ 
					   
					   return true;
					}
					return false;
				}
			};
			
			OrFilter aAndFrameFilter = new OrFilter(new NodeClassFilter(LinkTag.class),Framefilter);
			NodeList LinkList = HtmlParser.extractAllNodesThatMatch(Framefilter);
			//得到的这个网页的标签
			for(int i=0;i<LinkList.size();i++)
			{
				Node node = LinkList.elementAt(i);
				
				if (node instanceof LinkTag)
				{
					LinkTag link = (LinkTag)node;
					String Linkurl = link.getLink();
					if(fiter.accept(Linkurl))
						linkSet.add(Linkurl);
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return linkSet;
		
	}
	public static void main(String [] args)
	{
		LinkFilter filter = new LinkFilter(){
			public boolean accept(String url)
			{
				if(url.startsWith("http://news.sina.com.cn/"))
					return true;
				return false;
			}
		};
		Set<String >htmlset = new HashSet<String>();
		htmlset=TestHtmlParser.FilteLink("http://news.sina.com.cn/", filter);
		for(String s: htmlset)
		{
			//System.out.println(s);
		}
	}
	

}
