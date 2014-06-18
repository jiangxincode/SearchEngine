/*
 * 描述：将分好词的文档统计词频并且形成倒排索引文件，存入index.txt文档中
 * 作者：蒋鑫
 * */
package CreateIndex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Main {
	public Main() {
		HashMap<String, String> hashResult = new HashMap<String, String>();
		//String fileName = "";
		File dirFile = new File("wordDoc");
		File[] fileList = dirFile.listFiles();
		for(int i=0;i<fileList.length;i++) {
			String fileName = fileList[i].getName();
			System.out.println(fileName);
		//} //bad
		//for (int fileIndex = 33234; fileIndex < 33244; fileIndex++) { //bad
			//fileName = "news.nju.edu.cn_show_article_1_" + fileIndex + ".txt"; //bad

			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			String content = ReadAndWrite.readFileByChars("wordDoc/" + fileName, "gbk");
			String[] wordArray = content.split(" ");
			for (int j = 0; j < wordArray.length; j++) {
				if (hashMap.keySet().contains(wordArray[j])) {
					Integer integer = (Integer) hashMap.get(wordArray[j]);
					int value = integer.intValue() + 1;
					hashMap.put(wordArray[j], new Integer(value));
				} else
					hashMap.put(wordArray[j], new Integer(1));
			}
			for (String str : hashMap.keySet()) {

				// 获得标题
				//String strNum = fileName.replaceAll("news.nju.edu.cn_show_article_1_", "");
				//strNum = strNum.replaceAll(".txt", "");
				String title = ReadAndWrite.readFileByChars("titleDoc/"
						+ fileName, "gbk");

				// 获得跟词相关的部分内容
				String fullContent = ReadAndWrite.readFileByChars("srcDoc/"
						+ fileName, "gbk");
				String partContent = "";
				int wordStart = fullContent.indexOf(str);// 包含词的位置
				while (wordStart > 0) {
					String strTmp;
					int s = 0, e = fullContent.length();
					if (wordStart > 10)
						s = wordStart - 10;
					else
						s = 0;
					if (e > (wordStart + 10))
						e = wordStart + 10;

					strTmp = fullContent.substring(s, e);

					partContent += (strTmp + "......");
					fullContent = fullContent.substring(e);
					wordStart = fullContent.indexOf(str);
				}
				// 形成倒排索引
				String tmp = fileName + "#split#" + title + "#split#"
						+ partContent + "#split#" + hashMap.get(str);
				// System.out.println(str+"    "+tmp); //bad
				if (hashResult.keySet().contains(str)) {// 包含该词
					String value = (String) hashResult.get(str);
					value += ("#next#" + tmp);
					hashResult.put(str, value);
				} else
					hashResult.put(str, tmp);
			}
		}

		if (hashResult.size() > 0) {
			String value = "";

			for (String str : hashResult.keySet()) {
				String tmp = str + "  " + hashResult.get(str); //两个空格
				//System.out.println(tmp);
				value += (tmp + "#LINE#");

			}

			this.writeFileByChars("WebRoot/index.txt", value);
		}

	}

	public void writeFileByChars(String fileName, String value) {
		String path = fileName;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, false), "UTF-8"));

			String[] arryStr = value.split("#LINE#");
			for (int i = 0; i < arryStr.length; i++) {
				//System.out.println(arryStr[i]);
				// ByteBuffer bb = ByteBuffer.wrap(val.getBytes());
				bw.write(arryStr[i]);
				bw.write(13);
				bw.write(10);
			}

			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Main();

	}

}
