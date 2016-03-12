package edu.jiangxin.searchengine.crawler;

import java.util.Set;

import edu.jiangxin.searchengine.constant.Constant;

/**
 *
 * @author jiangxin
 *
 */
public class Crawler implements Runnable {

	/** the page number need to be crawled. */
	private static int pageNumNeedToCrawl = Constant.DEFAULT_PAGE_NUM_NEED_TO_CRAWLER;

	/** the page number has been crawled. */
	private static int downloadNum = 0;

	/**
	 *
	 * @param seeds The seeds used to initialize.
	 */
	public Crawler(final String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	@Override
	public final void run() {
		// 定义过滤器，提取以http://www.nju.edu.cn开头的链接
		LinkFilter filter = new LinkFilter() {
			public boolean accept(final String url) {
				return url.startsWith("http://news.nju.edu.cn")
						&& (url.endsWith(".html") || (url.contains("show_article") && !url.contains("#")));
			}
		};

		// 循环条件：待抓取的链接不空且抓取的网页不多于1000
		// while (!LinkQueue.unVisitedUrlsEmpty() && downloadFileNum <
		// MAXNUM) {
		while (downloadNum < pageNumNeedToCrawl) {
			if (!LinkQueue.unVisitedUrlsEmpty()) {
				// 队头URL出队列
				String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
				if (visitUrl.contains("show_article")) {
					System.out.println(Thread.currentThread().getName());
					DownLoadFile downLoader = new DownLoadFile(); // 下载网页
					downLoader.downloadFile(visitUrl);
					downloadNum++;
				}
				LinkQueue.addVisitedUrl(visitUrl); // 该 url 放入到已访问的 URL 中
				Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter); // 提取出下载网页中的
																					// URL
				// 新的未访问的 URL 入队
				for (String link : links) {
					LinkQueue.addUnvisitedUrl(link);
				}
			}
		}
	}
}
