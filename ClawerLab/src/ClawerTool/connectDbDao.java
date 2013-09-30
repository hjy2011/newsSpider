package ClawerTool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class connectDbDao {
	PreparedStatement   sta=null;
	Connection con=null;
	ResultSet result =null;
	/*-----------------------------------------------------------------*/
	/*
	 * ÿ��Ҫ�������ݿ�ʹ���conn��statement
	 * @param link Ҫ�����ı�
	 * create table pageContent
   (
   id int identity(1,1) ,
   source nvarchar(MAX),
   mainText nvarchar(MAX),
   relatenews nvarchar(MAX),
   newstag nvarchar(MAX),
   dateTime datetime,
   primary key(id)
   );
	 */
	public  void CreateInsertTextIntoDb()
	{
		    String sql ="insert into pageContent(source,mainText,relatenews,newstag,dateTime)values (?,?,?,?,?)";
		    try {
		    	con=DBUtils.getSqlServerConnection();
		    	 sta = con.prepareStatement(sql);
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//ResultSet  rs=sta.executeQuery(sql);
	}

/*
 *  private static String source;
	private static String mainText;
	private static java.sql.Date dateTime;
	private static String relatenews;
	private static String newstag;
 */
	public  void insertTextInto(String source,String mainText,String relatenews,String newstag,Timestamp dateTime)
	{
		try {
			sta.setString(1, source);
			sta.setString(2, mainText);
			sta.setString(3, relatenews);
			sta.setString(4, newstag);
			sta.setTimestamp(5, dateTime);
			
			sta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*-----------------------------------------------------------------*/
	/*
	 * ÿ��Ҫ�������ݿ�ʹ���conn��statement
	 * @param link Ҫ��������
	 */
	public  void CreateInsertLinkIntoDb()
	{
		    String sql ="insert into sinaurl (url)values (?)";
		    try {
				con=DBUtils.getConnection();
				sta = con.prepareStatement(sql);
				//ResultSet  rs=sta.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	public  void insertLinkinto(String link) 
	{
		try {
			sta.setString(1,link);
			sta.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  boolean closeConn()
	{
		try {
			if(!con.isClosed()||!sta.isClosed())
			{
				sta.close();
				con.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
