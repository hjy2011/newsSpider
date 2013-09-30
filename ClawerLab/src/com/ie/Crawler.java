package com.ie;

import java.util.Set;
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
			//��ͷ URL ������
			String visitUrl=LinkDB.unVisitedUrlDeQueue();
			if(visitUrl==null)
				continue;
			FileDownLoader downLoader=new FileDownLoader();
			//������ҳ
			downLoader.downloadFile(visitUrl);
			//�� url ���뵽�ѷ��ʵ� URL ��
			LinkDB.addVisitedUrl(visitUrl);
			//��ȡ��������ҳ�е� URL
			
			Set<String> links=HtmlParserTool.extracLinks(visitUrl,filter);
			//�µ�δ���ʵ� URL ���
			for(String link:links)
			{
					LinkDB.addUnvisitedUrl(link);
			}
		}
	}
	//main �������
	public static void main(String[]args)
	{
		Crawler crawler = new Crawler();
		crawler.crawling(new String[]{"http://www.sina.com.cn/"});
	}
}