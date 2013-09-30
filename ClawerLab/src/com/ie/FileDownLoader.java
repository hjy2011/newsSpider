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
	/*���� url ָ�����ҳ*/
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
			  
			  
          byte[] responseBody = getMethod.getResponseBody();//��ȡΪ�ֽ�����
          String s=getMethod.getResponseBody().toString();
          System.out.println(s);
		  } catch (HttpException e) {
				   // �����������쳣��������Э�鲻�Ի��߷��ص�����������
				   System.out.println("Please check your provided http address!");
				   e.printStackTrace();
				  } catch (IOException e) {
				   // ���������쳣
				   e.printStackTrace();
				  } finally {
				   // �ͷ�����
				   getMethod.releaseConnection();		   
				  }
				  return filePath;
	}
	//���Ե� main ����
	public static void main(String[]args)
	{
		FileDownLoader downLoader = new FileDownLoader();
		downLoader.downloadFile("http://news.sina.com.cn/");
	}
}