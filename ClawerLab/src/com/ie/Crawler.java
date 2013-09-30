package com.ie;

import java.util.Set;
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
		LinkFilter filter = new LinkFilter(){
			
			public boolean accept(String url) {
				if(url.startsWith("http://www.sina.com.cn/"))
					return true;
				else
					return false;
			}
		};
		
		initCrawlerWithSeeds(seeds);
		
		while(!LinkDB.unVisitedUrlsEmpty()&&LinkDB.getVisitedUrlNum()<=1000)
		{
			//队头 URL 出队列
			String visitUrl=LinkDB.unVisitedUrlDeQueue();
			if(visitUrl==null)
				continue;
			FileDownLoader downLoader=new FileDownLoader();
			//下载网页
			downLoader.downloadFile(visitUrl);
			//该 url 放入到已访问的 URL 中
			LinkDB.addVisitedUrl(visitUrl);
			//提取出下载网页中的 URL
			
			Set<String> links=HtmlParserTool.extracLinks(visitUrl,filter);
			//新的未访问的 URL 入队
			for(String link:links)
			{
					LinkDB.addUnvisitedUrl(link);
			}
		}
	}
	//main 方法入口
	public static void main(String[]args)
	{
		Crawler crawler = new Crawler();
		crawler.crawling(new String[]{"http://www.sina.com.cn/"});
	}
}