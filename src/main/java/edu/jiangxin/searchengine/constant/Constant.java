package edu.jiangxin.searchengine.constant;

/**
 * The Constant used in this project.
 *
 * @author jiangxin
 *
 */
public final class Constant {

	/**
	 * avoid to be construct a object.
	 */
	private Constant() {

	}

	/**
	 * the default number of thread.
	 */
	public static final int DEFAULT_NUM_OF_THREAD = 10;

	/**
	 * the default connect timeout.
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5000;

	/**
	 * the default connect request timeout.
	 */
	public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 5000;

	/**
	 * the max amount of the seeds.
	 */
	public static final int MAX_SEEDS_NUM = 10;

	/**
	 * the default page number need to crawler.
	 */
	public static final int DEFAULT_PAGE_NUM_NEED_TO_CRAWLER = 10;

}
