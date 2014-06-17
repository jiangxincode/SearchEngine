package Crawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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

public class DownLoadFile {
	
	//根据 url 和网页类型生成需要保存的网页的文件名 去除掉 url 中非文件名字符
	public  String getFileNameByUrl(String url) {
		url=url.substring(7); //remove http://
		url= url.replaceAll("[\\?/:*|<>\"]", "_"); //将特殊字符替换，以生成合法的本地文件名
		return url;
	}

	//保存网页字节数组到本地文件 filePath 为要保存的文件的相对地址
	private void saveToLocal(InputStream data, String filePath) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			BufferedInputStream in = new BufferedInputStream(data);
			int r;
			while((r=in.read())!=-1) {
				out.write((byte)r);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//下载 url 指向的网页 
	public String downloadFile(String url) {
		
		String filePath = null;
		
		HttpClient httpClient = new HttpClient(); //生成 HttpClinet 
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000); //设置 Http 连接超时 5s
		
		GetMethod getMethod = new GetMethod(url); //生成 GetMethod
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000); //设置 get 请求超时 5s
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler()); // 设置请求重试处理

		
		try { // 执行 HTTP GET 请求
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) { //判断访问的状态码
				System.err.println("Method failed: " + getMethod.getStatusLine());
				filePath = null;
			}

			//处理 HTTP 响应内容 
			InputStream responseBody = getMethod.getResponseBodyAsStream(); // 读取为字节数组
			//String type=getMethod.getResponseHeader("Content-Type").getValue(); // 根据网页 url 生成保存时的文件名
			//if(type.contains("html") && url.endsWith(".html")) {
				filePath = "html/" + getFileNameByUrl(url);
				saveToLocal(responseBody, filePath);
			//}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!"); //发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); // 发生网络异常
		} finally {
			getMethod.releaseConnection(); // 释放连接
		}
		System.out.println(filePath);
		return filePath;
	}
}
