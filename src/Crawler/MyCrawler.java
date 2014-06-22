/**
 * 描述：爬虫主程序
 * 作者：蒋鑫
 **/
package Crawler;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyCrawler {
	final static int MAXNUM = 50; // the max amount of the web pages that
									// crawled.
	static int downloadFileNum = 0;
	final int MAXSEEDSNUM = 10; // the max amount of the seeds.
	String[] seeds = new String[MAXSEEDSNUM];
	static long timeForDownLoad = 0;
	static long timeForHtmlParser = 0;

	public MyCrawler() {
	}

	public MyCrawler(String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	private static class Crawling implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 定义过滤器，提取以http://www.nju.edu.cn开头的链接
			LinkFilter filter = new LinkFilter() {
				public boolean accept(String url) {
					if (url.startsWith("http://news.nju.edu.cn")
							&& (url.endsWith(".html") || (url
									.contains("show_article") && !url
									.contains("#"))))
						return true;
					else
						return false;
				}
			};

			// 循环条件：待抓取的链接不空且抓取的网页不多于1000
			while (!LinkQueue.unVisitedUrlsEmpty() && downloadFileNum < MAXNUM) {
				// 队头URL出队列
				String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
				if (visitUrl.contains("show_article")) {
					long start = System.currentTimeMillis();
					DownLoadFile downLoader = new DownLoadFile(); // 下载网页
					downLoader.downloadFile(visitUrl);
					long end = System.currentTimeMillis();
					timeForDownLoad += (end - start);
					downloadFileNum++;
				}
				LinkQueue.addVisitedUrl(visitUrl); // 该 url 放入到已访问的 URL 中
				long start = System.currentTimeMillis();
				Set<String> links = HtmlParserTool
						.extracLinks(visitUrl, filter); // 提取出下载网页中的 URL
				long end = System.currentTimeMillis();
				timeForHtmlParser += end - start;
				// 新的未访问的 URL 入队
				for (String link : links) {
					LinkQueue.addUnvisitedUrl(link);
				}
			}

		}

	}

	public static void main(String[] args) {
		long start=0,end=0;
		start = System.currentTimeMillis();
		new MyCrawler(new String[] { "http://news.nju.edu.cn/index.html" });
		ExecutorService executors = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executors.execute(new Crawling());
		}
		while (executors.isTerminated()) {
			end = System.currentTimeMillis();
			System.out.println("爬虫程序共花费时间：" + (end - start));
			System.out.println("下载网页共花费时间：" + timeForDownLoad);
			System.out.println("分析网页共花费时间：" + timeForHtmlParser);
		}

	}
}
