package edu.jiangxin.searchengine;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import edu.jiangxin.searchengine.constant.Constant;
import edu.jiangxin.searchengine.crawler.Crawler;
import edu.jiangxin.searchengine.createindex.MyCreateIndex;
import edu.jiangxin.searchengine.html2test.MyHtmlToTest;
import edu.jiangxin.searchengine.split.MySpilt;
import edu.jiangxin.searchengine.utils.DeleteDirectory;

public class SearchEngineTest {

	static boolean isDeleteDir = false;
	static String[] forCrawler;
	static int tempCrawler = 0;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSearchEngine() {
		String[] argv = { "-n", "10", "-deleteDir" };
		for (int i = 0; i < argv.length; i++) {
			//System.out.println(i); //bad
			String temp = argv[i].substring(1);
			System.out.println(temp);
			if (temp.equalsIgnoreCase("deleteDir")) { //-deleteDir
				isDeleteDir = true;
				continue;
			}
			if (temp.equals("n")) { //-n 50
				forCrawler = new String[] { argv[++i] };
				//System.out.println(i);
			}
		}
		if (isDeleteDir == true) {
			String[] deleteDirs = new String[] { "target/html/", "target/srcDoc/", "target/titleDoc/",
					"target/wordDoc/", "src/main/webapp/index.txt" };
			DeleteDirectory.deleteDirs(deleteDirs);
		}

		long start = 0, end = 0;
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
			new MyHtmlToTest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MySpilt myIK_Tokenize = new MySpilt("target/srcDoc/", "target/wordDoc/");
		myIK_Tokenize.segment();

		new MyCreateIndex();

	}

}
