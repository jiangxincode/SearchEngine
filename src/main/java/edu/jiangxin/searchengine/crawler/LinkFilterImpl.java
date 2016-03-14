package edu.jiangxin.searchengine.crawler;

/**
 *
 * @author jiangxin
 *
 */
public class LinkFilterImpl implements LinkFilter {

	/**
	 *
	 */
	@Override
	public final boolean accept(final String url) {
		return url.startsWith("http://news.nju.edu.cn")
				&& (url.endsWith(".html") || (url.contains("show_article") && !url.contains("#")));
	}

}
