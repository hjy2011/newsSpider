package ClawerTool;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;


public class Crawler {
	/* 使用种子 url 初始化 URL 队列*/
	private void initCrawlerWithSeeds(String[] seeds)
	{
		for(int i=0;i<seeds.length;i++)
			LinkDB.addUnvisitedUrl(seeds[i]);
	}
	
	/* 爬取方法*/
	public void crawling(String[] seeds)
	{
		//以下为初始化工作
		initCrawlerWithSeeds(seeds);
		FileDownLoader filedown = new FileDownLoader();
		int i=0;

		while(!LinkDB.unVisitedUrlsEmpty()&&LinkDB.getVisitedUrlNum()<=10000)
		{
			//队头 URL 出队列
			String visitUrl=LinkDB.unVisitedUrlDeQueue();
			if(visitUrl==null)
				continue;
			else{
				try {
					createRow.createUser(visitUrl);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
				System.out.println("加入队列"+i+"条URL");
				LinkDB.addVisitedUrl(visitUrl);
			}
			
			//提取出下载网页中的 URL
			Set<String> links=HtmlParserTool.getLinkStartWithPatten("http://news.sina.com.cn/c/",visitUrl);
			for(String link:links)
			{
				LinkDB.addUnvisitedUrl(link);
			}		
		}
		createRow.closeConn();
			
		
	}
	//main 方法入口
	public static void main(String[]args)
	{
		Crawler crawler = new Crawler();
		crawler.crawling(new String[]{"http://www.sina.com.cn/"});
	}
}