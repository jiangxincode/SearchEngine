package AllInOne;

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
			String[] deleteDirs = new String[]{"html/","srcDoc/","titleDoc/","wordDoc/"};
			DeleteDirectory.deleteDirs(deleteDirs);
		}
		Crawler.MyCrawler.main(forCrawler);
		HtmlToTest.MyHtmlToTest.main(null);
		Split.MySpilt.main(null);
		CreateIndex.MyCreateIndex.main(null);
	}

}
