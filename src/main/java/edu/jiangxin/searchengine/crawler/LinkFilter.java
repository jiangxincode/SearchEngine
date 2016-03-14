package edu.jiangxin.searchengine.crawler;

/**
 *
 * @author jiangxin
 *
 */
public interface LinkFilter {
	/**
	 *
	 * @param url url
	 * @return boolean
	 */
	boolean accept(String url);
}
