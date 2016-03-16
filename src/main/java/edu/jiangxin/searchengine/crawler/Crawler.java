package edu.jiangxin.searchengine.crawler;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

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
	 * @param seeds
	 *            The seeds used to initialize.
	 */
	public Crawler(final String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	@Override
	public final void run() {
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
				Set<String> links = extracLinks(visitUrl, new LinkFilterImpl()); // 提取出下载网页中的
				// URL
				// 新的未访问的 URL 入队
				for (String link : links) {
					LinkQueue.addUnvisitedUrl(link);
				}
			}
		}
	}

	/**
	 * 获取一个网站上的链接,filter 用来过滤链接.
	 *
	 * @param url url
	 * @param filter filter
	 * @return Set<String>
	 */
	private static Set<String> extracLinks(final String url, final LinkFilter filter) {

		Set<String> links = new HashSet<>();
		try {
			Parser parser = new Parser(url);
			// parser.setEncoding("utf8");

			// 得到所有经过过滤的标签
			NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
			for (int i = 0; i < list.size(); i++) {
				LinkTag link = (LinkTag) list.elementAt(i);
				String linkUrl = link.getLink(); // url可能出现在src,href等属性中
				if (filter.accept(linkUrl)) {
					links.add(linkUrl);
				}

			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
}
