package ClawerTool;
/*
 * http://blog.csdn.net/cxwen78/article/details/6863696
 */
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.ResourceBundle;
	public class DBUtils { 
	    private static final String OPTION_FILE_NAME = "DatabaseLink"; 
	    private static String drivers; 
	    private static String url; 
	    private static String user; 
	    private static String password; 
	    
	    static { 
	        ResourceBundle res = ResourceBundle.getBundle(OPTION_FILE_NAME); 
	        drivers = res.getString("SQLDRIVERS").trim(); 
	        url = res.getString("SQLURL").trim(); 
	        user = res.getString("SQLUSER").trim(); 
	        password = res.getString("SQLPASSWORD").trim(); 
	        
	    } 
	    
	    public static Connection connect() throws SQLException{
	    	Connection con = null; 
	        try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            con = DriverManager.getConnection("jdbc:sqlserver://localhost;databasename=sinaNews;user=sa;password=123");
	            return con;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            
	        }
	        if (con == null) { 
	            throw new SQLException("ctcjz.DBUtils: Cannot get connection."); 
	        } 
	        return con;
	    }
	    public static Connection getConnection() throws SQLException { 
	        Connection conn = null; 
	        try { 
	            Class.forName(drivers).newInstance(); 
	            conn = DriverManager.getConnection(url,user, password); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	        if (conn == null) { 
	            throw new SQLException("ctcjz.DBUtils: Cannot get connection."); 
	        } 
	        return conn; 
	    } 
	    
	    
	    public static Connection getSqlServerConnection() throws SQLException { 
	        Connection conn = null; 
	        try { 
	            Class.forName(drivers).newInstance(); 
	            conn = DriverManager.getConnection(url, user, password); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	        if (conn == null) { 
	            throw new SQLException("ctcjz.DBUtils: Cannot get connection."); 
	        } 
	        return conn; 
	    } 
	    
	    public static void close(Connection conn) { 
	        if (conn == null) 
	            return; 
	        try { 
	            conn.close(); 
	        } catch (SQLException e) { 
	            System.out.println("ctcjz.DBUtils: Cannot close connection."); 
	        } 
	    } 
	    public static void close(Statement stmt) { 
	        try { 
	            if (stmt != null) { 
	                stmt.close(); 
	            } 
	        } catch (SQLException e) { 
	            System.out.println("ajax.DBUtils: Cannot close statement."); 
	        } 
	    } 
	    public static void close(ResultSet rs) { 
	        try { 
	            if (rs != null) { 
	                rs.close(); 
	            } 
	        } catch (SQLException e) { 
	            System.out.println("ctcjz.DBUtils: Cannot close resultset."); 
	        } 
	    } 
	    public static PreparedStatement createPreparedStatement(Connection con,String sql) 
	    {
	    	PreparedStatement preSta=null;
			try {
				preSta = con.prepareStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return preSta;
	    }
	    
	    public static ResultSet getResultSet(Connection con,String sql)
	    {
	    	ResultSet result=null;
	    	try {
	    	    Statement sta = con.createStatement();
				result = sta.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return result;
	    }
	} 