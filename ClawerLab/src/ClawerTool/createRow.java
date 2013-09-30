package ClawerTool;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.httpclient.HttpException;

public class createRow {
	private static String source;
	private static String mainText;
	//private static java.sql.Date dateTime;
	private static Timestamp dateTime;
	private static String relatenews;
	private static String newstag;
	static FileDownLoader filedown = new FileDownLoader();
	static connectDbDao codbdao= new connectDbDao();
	public static void createUser(String visitUrl) throws HttpException, IOException
	{
		String result=filedown.downloadFile(visitUrl);
		
		codbdao.CreateInsertTextIntoDb();
	    
	    //!!获取标题
		List<String> mainTitle=HtmlParserTool.
		        getContentByTagNameAndAttribute(visitUrl, "h1", "id", "artibodyTitle");
		
		//!!获取正文
		List<String> newsText=HtmlParserTool.
				getHtmlByTagNameAndAttribute(visitUrl, "div", "id", "artibody");
		ArrayList<String> totalText=ReglarExpression.RegularArray("<p>(.*)</p>", newsText.toString(), 1);
		for(String s:totalText)
		{
			mainText+=s;
		}
		
		//!!获取日期
		List<String> TimeText=HtmlParserTool.
				getContentByTagNameAndAttribute(visitUrl, "span", "id", "pub_date");
		String gotTime = null;
		if(TimeText.size()!=0)
		{
		   gotTime=(String) TimeText.toArray()[0];
		   dateTime=sqlDateFormat.getSqlDate(gotTime);
		}
		gotTime = null;

		
		//!!获取相关新闻
		List<String> relateNews=HtmlParserTool.
		getContentByTagNameAndAttribute(visitUrl, "div", "class", "cblk_01");
		for(String news:relateNews)
		{
			relatenews+=news;
			relatenews+=" ";
		}
		//!!获取标签
		List<String> newsTag=HtmlParserTool.
				getContentByTagNameAndAttribute(visitUrl, "div", "class", "content_label_list");
		String tag=null;
		if(newsTag.toArray().length!=0)
		{
			 tag= (String) newsTag.toArray()[0];
			 String[] sp=tag.split("：");
		     newstag=sp[1];
		}
		//获取来源
		List<String> newsSource=HtmlParserTool.
				getContentByTagNameAndAttribute(visitUrl, "span", "data-sudaclick", "media_name");
		if(newsSource.toArray().length==1)
		 source=(String) newsSource.toArray()[0];
		codbdao.insertTextInto(source, mainText, relatenews, newstag, dateTime);
		mainText=null;
		relatenews=null;
		
	}
	public static  void closeConn()
	{
		codbdao.closeConn();
	}
}
