/**
 * 描述：主要数据结构的类定义
 * 作者：蒋鑫
**/
package Crawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Queue;

public class LinkQueue {
	//已访问的 url 集合，之所以设为Set，是要保证其所包含的元素不重复
	private static Set<String> visitedUrl = new HashSet<String>();
	//待访问的 url 集合
	private static Queue<String> unVisitedUrl = new PriorityQueue<String>();
	
    //添加到访问过的URL队列中
	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
		System.out.println("现在visitedUrl集合中共有：" + LinkQueue.getVisitedUrlNum() + "个元素"); //not very bad
	}

    //移除访问过的URL
	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}
	
    //获得已经访问的URL数目
	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}
	
	//获得URL队列
	public static Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}

    //未访问的URL出队列
	public static Object unVisitedUrlDeQueue() {
		String visitUrl = unVisitedUrl.poll();
		System.out.println(visitUrl + "离开unVisitedUrl队列"); //bad
		return visitUrl;
	}

	// 保证每个 url 只被访问一次
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url)) {
				unVisitedUrl.add(url);
			System.out.println(url + "进入unVisitedUrl队列"); //not very bad
			System.out.println("现在unVisitedUrl队列中共有:" + LinkQueue.getUnVisitedUrlNum() + "个元素"); //not very bad
		}
			
	}

	public static int getUnVisitedUrlNum() {
		return unVisitedUrl.size();
	}
    //判断未访问的URL队列中是否为空
	public static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.isEmpty();
	}

}
