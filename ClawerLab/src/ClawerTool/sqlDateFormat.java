package ClawerTool;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sqlDateFormat {
	public static Timestamp getSqlDate(String sinaTime)
	{
		sinaTime=sinaTime.replace("年","-");
		sinaTime=sinaTime.replace("月","-");
		sinaTime=sinaTime.replace("日"," ");
		sinaTime=sinaTime+":00";
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Timestamp dateTime=null;
	    
		try {
             Date date = dateFormat.parse(sinaTime);//将形如"yyyy-MM-dd kk:mm:ss"的字符串转换为util的date
             String time = dateFormat.format(date);//再将date转换为timestamp
             dateTime=Timestamp.valueOf(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	public static java.sql.Date getSqlDate2(String sinaTime)
	{
		sinaTime=sinaTime.replace("年","-");
		sinaTime=sinaTime.replace("月","-");
		sinaTime=sinaTime.replace("日"," ");
		sinaTime=sinaTime+":00";
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		java.sql.Date dateTime=null;
	    
		try {
             Date date = dateFormat.parse(sinaTime);//将形如"yyyy-MM-dd kk:mm:ss"的字符串转换为util的date
             
             dateTime=new java.sql.Date(date.getTime());
             System.out.println(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	public static void main(String []args)
	{
		String sinaTime="2013年09月28日00:38";
		
		sinaTime=sinaTime.replace("年","-");
		sinaTime=sinaTime.replace("月","-");
		sinaTime=sinaTime.replace("日"," ");
		sinaTime=sinaTime+":00";
		System.out.println(sinaTime);
		Timestamp dateTime=null;
		dateTime = sqlDateFormat.getSqlDate(sinaTime);
	    System.out.println(dateTime);
	}
}
