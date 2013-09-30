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
	/* ʹ������ url ��ʼ�� URL ����*/
	private void initCrawlerWithSeeds(String[] seeds)
	{
		for(int i=0;i<seeds.length;i++)
			LinkDB.addUnvisitedUrl(seeds[i]);
	}
	
	/* ��ȡ����*/
	public void crawling(String[] seeds)
	{
		//����Ϊ��ʼ������
		initCrawlerWithSeeds(seeds);
		FileDownLoader filedown = new FileDownLoader();
		int i=0;

		while(!LinkDB.unVisitedUrlsEmpty()&&LinkDB.getVisitedUrlNum()<=10000)
		{
			//��ͷ URL ������
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
				System.out.println("�������"+i+"��URL");
				LinkDB.addVisitedUrl(visitUrl);
			}
			
			//��ȡ��������ҳ�е� URL
			Set<String> links=HtmlParserTool.getLinkStartWithPatten("http://news.sina.com.cn/c/",visitUrl);
			for(String link:links)
			{
				LinkDB.addUnvisitedUrl(link);
			}		
		}
		createRow.closeConn();
			
		
	}
	//main �������
	public static void main(String[]args)
	{
		Crawler crawler = new Crawler();
		crawler.crawling(new String[]{"http://www.sina.com.cn/"});
	}
}