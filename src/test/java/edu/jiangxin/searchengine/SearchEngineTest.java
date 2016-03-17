package edu.jiangxin.searchengine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import edu.jiangxin.searchengine.constant.Constant;
import edu.jiangxin.searchengine.crawler.Crawler;
import edu.jiangxin.searchengine.createindex.CreateIndex;
import edu.jiangxin.searchengine.html2test.Html2Text;
import edu.jiangxin.searchengine.split.Spilt;

/**
 * test the main function of SearchEngine.
 *
 * @author jiangxin
 *
 */
public class SearchEngineTest {

	/**
	 *
	 */
	@Before
	public void setUp() {
	}

	/**
	 * @throws IOException IOException
	 *
	 */
	@Test
	public final void testSearchEngine() throws IOException {

		String[] deleteDirs = new String[] { "target/html/", "target/srcDoc/", "target/titleDoc/", "target/wordDoc/",
				"src/main/webapp/index.txt" };
		for (String dirName : deleteDirs) {
			try {
				FileUtils.deleteDirectory(new File(dirName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		long start, end;
		start = System.currentTimeMillis();
		ExecutorService executors = Executors.newFixedThreadPool(Constant.DEFAULT_NUM_OF_THREAD);
		for (int i = 0; i < Constant.DEFAULT_NUM_OF_THREAD; i++) {
			executors.execute(new Crawler(new String[] { "http://news.nju.edu.cn/index.html" }));
		}
		executors.shutdown();

		while (!executors.isTerminated()) {
			continue;
		}

		end = System.currentTimeMillis();
		System.out.println("爬虫程序共花费时间：" + (end - start));

		try {
			new Html2Text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Spilt spilt = new Spilt("target/srcDoc/", "target/wordDoc/");
		spilt.segment();

		new CreateIndex();

	}

}
