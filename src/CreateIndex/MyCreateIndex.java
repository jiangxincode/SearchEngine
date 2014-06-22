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

public class MyCreateIndex {
	public MyCreateIndex() {
		HashMap<String, String> hashResult = new HashMap<String, String>();
		File dirFile = new File("wordDoc");
		File[] fileList = dirFile.listFiles();
		for(int i=0;i<fileList.length;i++) {
			String fileName = fileList[i].getName();
			System.out.println("现在正在对文件" + fileName + "进行分析");
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
			System.out.println("现在正在建立索引内容，可能会需要较长时间，请耐心等待……");
			for (String str : hashResult.keySet()) {
				String tmp = str + "  " + hashResult.get(str); //两个空格
				value += (tmp + "#LINE#");

			}
			System.out.println("现在正在将索引内容写入磁盘，可能会需要较长时间，请耐心等待……");
			this.writeFileByChars("WebRoot/index.txt", value);
			System.out.println("已经完成建立索引工作");
		}

	}

	public void writeFileByChars(String fileName, String value) {
		String path = fileName;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, false), "UTF-8"));

			String[] arryStr = value.split("#LINE#");
			for (int i = 0; i < arryStr.length; i++) {
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
		new MyCreateIndex();

	}

}
