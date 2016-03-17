package edu.jiangxin.searchengine.crawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author jiangxin
 *
 */
public final class LinkQueue {

	/** a lock against competition error. */
	private static Lock lock = new ReentrantLock();

	/**  */
	private static Condition isEmpty = lock.newCondition();

	/** 已访问的 url 集合，之所以设为Set，是要保证其所包含的元素不重复. */
	private static Set<String> visitedUrl = new HashSet<>();

	/** 待访问的 url 集合. */
	private static Queue<String> unVisitedUrl = new PriorityQueue<>();

	/**
	 *
	 */
	private LinkQueue() {

	}

	/**
	 * 添加到访问过的URL队列中.
	 * @param url url
	 */
	public static synchronized void addVisitedUrl(final String url) {
		visitedUrl.add(url);
		System.out.println("现在visitedUrl集合中共有：" + LinkQueue.getVisitedUrlNum() + "个元素");
	}

	/**
	 * 移除访问过的URL.
	 * @param url url
	 */
	public static synchronized void removeVisitedUrl(final String url) {
		visitedUrl.remove(url);
	}

	/**
	 * get the number that has been visited.
	 * @return The number that has been visited.
	 */
	public static synchronized int getVisitedUrlNum() {
		return visitedUrl.size();
	}

	/**
	 * 获得URL队列.
	 * @return Queue<String>
	 */
	public static synchronized Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}

	/**
	 * 未访问的URL出队列.
	 * @return Object
	 */
	public static Object unVisitedUrlDeQueue() {
		lock.lock();
		String visitUrl = null;

		try {
			while (unVisitedUrlsEmpty()) {
				System.out.println("我正在阻塞");
				isEmpty.await();
				System.out.println("我已经解除阻塞");
			}
			visitUrl = unVisitedUrl.poll();
			System.out.println(visitUrl + "离开unVisitedUrl队列"); // bad

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		return visitUrl;
	}

	/**
	 * 保证每个 url 只被访问一次.
	 * @param url url
	 */
	public static void addUnvisitedUrl(final String url) {
		lock.lock();
		try {
			if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url)) {
				unVisitedUrl.add(url);
				System.out.println(url + "进入unVisitedUrl队列"); // not very bad
				System.out.println("现在unVisitedUrl队列中共有:" + LinkQueue.getUnVisitedUrlNum() + "个元素"); // not
																										// very
																										// bad
				isEmpty.signalAll();
			}
		} finally {
			lock.unlock();
		}

	}

	/**
	 *
	 * @return int
	 */
	public static synchronized int getUnVisitedUrlNum() {
		return unVisitedUrl.size();
	}

	/**
	 * 判断未访问的URL队列中是否为空.
	 * @return boolean
	 */
	public static synchronized boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.isEmpty();
	}

}
