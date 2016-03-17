/*
 * 描述：将分好词的文档统计词频并且形成倒排索引文件，存入index.txt文档中
 * 作者：蒋鑫
 * */
package edu.jiangxin.searchengine.createindex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import edu.jiangxin.searchengine.constant.Constant;

/**
 *
 * @author jiangxin
 *
 */
public class CreateIndex {
	/**
	 *
	 */
	private long start, end;

	/**
	 *
	 * @throws IOException IOException
	 */
	public CreateIndex() throws IOException {
		HashMap<String, String> hashResult = new HashMap<>();
		File dirFile = new File("target/wordDoc");
		File[] fileList = dirFile.listFiles();

		if (fileList != null) {
			System.out.println("正在对文本内容进行分析，可能会需要较长时间，请耐心等待……");
			start = System.currentTimeMillis();
			for (int i = 0; i < fileList.length; i++) {
				String fileName = fileList[i].getName();
				System.out.println("\t现在正在对文件" + fileName + "进行分析");
				HashMap<String, Integer> hashMap = new HashMap<>();
				String content = FileUtils.readFileToString(new File("target/wordDoc/" + fileName), "UTF-8");
				String[] wordArray = content.split(" ");
				for (int j = 0; j < wordArray.length; j++) {
					if (hashMap.keySet().contains(wordArray[j])) {
						Integer integer = (Integer) hashMap.get(wordArray[j]);
						int value = integer.intValue() + 1;
						hashMap.put(wordArray[j], new Integer(value));
					} else {
						hashMap.put(wordArray[j], new Integer(1));
					}
				}
				// 获得标题
				String titleOrigin = FileUtils.readFileToString(new File("target/titleDoc/" + fileName), "UTF-8");
				// 获得跟词相关的部分内容
				String fullContentOrigin = FileUtils.readFileToString(new File("target/srcDoc/" + fileName), "UTF-8");
				for (String str : hashMap.keySet()) {
					String title = titleOrigin;
					String fullContent = fullContentOrigin;
					String partContent = "";
					int wordStart = fullContent.indexOf(str); // 包含词的位置
					while (wordStart > 0) {
						String strTmp;
						int s = 0, e = fullContent.length();
						if (wordStart > Constant.DEFAULT_LEN_BEFORE_AFTER_KEYWORD) {
							s = wordStart - Constant.DEFAULT_LEN_BEFORE_AFTER_KEYWORD;
						}
						if (e > (wordStart + Constant.DEFAULT_LEN_BEFORE_AFTER_KEYWORD)) {
							e = wordStart + Constant.DEFAULT_LEN_BEFORE_AFTER_KEYWORD;
						}
						strTmp = fullContent.substring(s, e);
						partContent += (strTmp + "......");
						fullContent = fullContent.substring(e);
						wordStart = fullContent.indexOf(str);
					}
					// 形成倒排索引
					String tmp = fileName + "#split#" + title + "#split#" + partContent + "#split#" + hashMap.get(str);
					if (hashResult.keySet().contains(str)) {
						// 包含该词
						String value = (String) hashResult.get(str);
						value += ("#next#" + tmp);
						hashResult.put(str, value);
					} else {
						hashResult.put(str, tmp);
					}
				}

			}
			end = System.currentTimeMillis();
			System.out.println("文件内容分析完毕，共用时：" + (end - start) + "ms");
		}

		if (hashResult.size() > 0) {
			StringBuilder value = new StringBuilder("");

			System.out.println("现在正在建立索引内容，可能会需要较长时间，请耐心等待……");
			start = System.currentTimeMillis();
			for (String str : hashResult.keySet()) {
				StringBuilder tmp = new StringBuilder(str).append("  ").append(hashResult.get(str));
				value.append(tmp).append("#LINE#");

			}
			end = System.currentTimeMillis();
			System.out.println("索引内容建立完毕，共用时：" + (end - start) + "ms");

			System.out.println("现在正在将索引内容写入磁盘，可能会需要较长时间，请耐心等待……");
			start = System.currentTimeMillis();
			this.writeFileByChars("src/main/webapp/index.idx", value.toString());
			end = System.currentTimeMillis();
			System.out.println("索引内容写入完毕，共用时：" + (end - start) + "ms");
		}

	}

	/**
	 *
	 * @param fileName fileName
	 * @param value value
	 */
	private void writeFileByChars(final String fileName, final String value) {
		String path = fileName;
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), "UTF-8"))) {
			String[] arryStr = value.split("#LINE#");
			for (int i = 0; i < arryStr.length; i++) {
				bw.write(arryStr[i]);
				bw.write('\r');
				bw.write('\n');
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
