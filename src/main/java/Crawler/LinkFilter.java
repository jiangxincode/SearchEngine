/**
 * 描述：LinkFilter接口定义
 * 作者：蒋鑫
**/
package Crawler;

public interface LinkFilter {
	public boolean accept(String url);
}

