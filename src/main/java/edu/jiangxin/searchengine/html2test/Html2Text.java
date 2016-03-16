package edu.jiangxin.searchengine.html2test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

/**
 * 分析之前抓取的网页，并提取出文本，存储在对应文件中以备用来分词，另外将文章的标题抓下来存入了以新闻编号为名的txt文档中.
 * @author jiangxin
 *
 */
public class Html2Text {

	/**
	 *
	 * @throws IOException IOException
	 */
	public Html2Text() throws IOException {
		File directory = new File("target/html");
		File[] fileList = directory.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				String fileName = fileList[i].getName();
				StringBuilder content = new StringBuilder();
				String str;
				BufferedReader in = new BufferedReader(
						new InputStreamReader(new FileInputStream(fileList[i]), "UTF-8"));
				while ((str = in.readLine()) != null) {
					content.append(str);
				}
				in.close();
				String result = this.dealHtml(content); // 抓取内容
				FileUtils.writeStringToFile(new File("target/srcDoc/" + fileName + ".txt"), result, "UTF-8");

				// 抓取标题
				String titleResult = content.toString();
				int titleStart = titleResult.indexOf("<div class=\"article_title\">");
				if (titleStart > 0) {
					titleResult = titleResult.substring(titleStart);
				}
				int titleEnd = titleResult.indexOf("</div>");
				if (titleEnd > 0) {
					titleResult = titleResult.substring(0, titleEnd);
				}
				titleResult = this.dealHtml(titleResult);
				FileUtils.writeStringToFile(new File("target/titleDoc/" + fileName + ".txt"), titleResult, "UTF-8");
				System.out.println("已经成功对" + fileName + "进行解析");
			}
		}

	}

	/**
	 *
	 * @param o o
	 * @return String
	 */
	public final String dealHtml(final Object o) {
		String str = o.toString();
		str = str.replaceAll("\\<(img)[^>]*>|<\\/(img)>", "");
		str = str.replaceAll("\\<(table|tbody|tr|td|th|)[^>]*>|<\\/(table|tbody|tr|td|th|)>", "");
		str = str.replaceAll("\\<(div|blockquote|fieldset|legend)[^>]*>|<\\/(div|blockquote|fieldset|legend)>", "");
		str = str.replaceAll("\\<(font|i|u|h[1-9]|s)[^>]*>|<\\/(font|i|u|h[1-9]|s)>", "");
		str = str.replaceAll("\\<(style|strong)[^>]*>|<\\/(style|strong)>", "");
		str = str.replaceAll("\\<a[^>]*>|<\\/a>", "");
		str = str.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>",
				"");
		str = str.replaceAll("\\<br[^>]*", "");
		str = str.replaceAll("<script[\\s\\s]+</script *>", "");
		str = str.replaceAll("\\s", "");
		return str;
	}
}
