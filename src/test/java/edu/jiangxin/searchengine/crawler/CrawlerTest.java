package edu.jiangxin.searchengine.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import edu.jiangxin.searchengine.crawler.MyCrawler.Crawling;

public class CrawlerTest {
	final static int MAXTHREADNUM = 10;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMyCrawler() {

		long start = 0, end = 0;
		start = System.currentTimeMillis();
		//new MyCrawler(new String[] { "http://news.nju.edu.cn/index.html" },Integer.valueOf(args[0]));
		new MyCrawler(new String[] { "http://news.nju.edu.cn/index.html" }, Integer.valueOf(100));
		ExecutorService executors = Executors.newFixedThreadPool(10);
		for (int i = 0; i < MAXTHREADNUM; i++) {
			executors.execute(new Crawling());
		}
		executors.shutdown();

		while (!executors.isTerminated())
			;

		end = System.currentTimeMillis();
		System.out.println("爬虫程序共花费时间：" + (end - start));

	}

}
