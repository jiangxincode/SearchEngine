package edu.jiangxin.searchengine.allinone;

import java.io.IOException;


public class Easy {
	static boolean isDeleteDir = false;
	static String[] forCrawler;
	static int tempCrawler = 0;
	public static void main(String[] argv) throws IOException {
		for(int i=0;i<argv.length;i++) {
			//System.out.println(i); //bad
			String temp = argv[i].substring(1);
			System.out.println(temp);
			if(temp.equalsIgnoreCase("deleteDir")) { //-deleteDir
				isDeleteDir = true;
				continue;
			}
			if(temp.equals("n")) { //-n 50
				forCrawler = new String[]{argv[++i]};
				//System.out.println(i);
			}
		}
		if(isDeleteDir == true) {
			String[] deleteDirs = new String[]{"target/html/","target/srcDoc/","target/titleDoc/","target/wordDoc/","src/main/webapp/index.txt"};
			DeleteDirectory.deleteDirs(deleteDirs);
		}
		edu.jiangxin.searchengine.crawler.MyCrawler.main(forCrawler);
		edu.jiangxin.searchengine.html2test.MyHtmlToTest.main(null);
		edu.jiangxin.searchengine.split.MySpilt.main(null);
		edu.jiangxin.searchengine.createindex.MyCreateIndex.main(null);
	}

}
