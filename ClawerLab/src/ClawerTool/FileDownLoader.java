package ClawerTool;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class FileDownLoader {
	/*下载 url 指向的网页*/
	public String  downloadFile(String url) throws HttpException, IOException
	{
		  String filePath=null;  
		  String result=null;
		  GetMethod getMethod=null;
		 
		  
		  HttpClient httpClient=new HttpClient();	  
		  httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);		  
		  getMethod=new GetMethod(url);	 		  
		  getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);	  
		  getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			new DefaultHttpMethodRetryHandler());
		  
			  int statusCode = httpClient.executeMethod(getMethod); 
			  if (statusCode != HttpStatus.SC_OK) 
			  {
                  System.err.println("Method failed: "+ getMethod.getStatusLine());
				  filePath=null;
			  } 		  
          InputStream responseBody = getMethod.getResponseBodyAsStream();//读取为字节数组
          //byte[] temS=getMethod.getResponseBody(0);
          result = responseBody.toString();//new String (responseBody);
          //System.out.println(result);
		  
		 return result;
	}
	//测试的 main 方法
//	public static void main(String[]args)
//	{
//		FileDownLoader downLoader = new FileDownLoader();
//		downLoader.downloadFile("http://www.baidu.com/");
//	}
}