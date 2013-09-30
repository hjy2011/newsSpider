package com.ie;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class FileDownLoader {
	/*下载 url 指向的网页*/
	public String  downloadFile(String url)
	{
		  String filePath=null;
		  
		  HttpClient httpClient=new HttpClient();
		  
		  httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		  
		  
		  GetMethod getMethod=new GetMethod(url);	 
		  
		  getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
		  
		  getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			new DefaultHttpMethodRetryHandler());
		  
		  
		  try{ 
			  int statusCode = httpClient.executeMethod(getMethod);
			  
			  if (statusCode != HttpStatus.SC_OK) 
			  {
                  System.err.println("Method failed: "+ getMethod.getStatusLine());
				  filePath=null;
			  }
			  
			  
          byte[] responseBody = getMethod.getResponseBody();//读取为字节数组
          String s=getMethod.getResponseBody().toString();
          System.out.println(s);
		  } catch (HttpException e) {
				   // 发生致命的异常，可能是协议不对或者返回的内容有问题
				   System.out.println("Please check your provided http address!");
				   e.printStackTrace();
				  } catch (IOException e) {
				   // 发生网络异常
				   e.printStackTrace();
				  } finally {
				   // 释放连接
				   getMethod.releaseConnection();		   
				  }
				  return filePath;
	}
	//测试的 main 方法
	public static void main(String[]args)
	{
		FileDownLoader downLoader = new FileDownLoader();
		downLoader.downloadFile("http://news.sina.com.cn/");
	}
}