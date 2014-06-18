/*
 * 描述：搜索引擎，读取索引文件按照索引上词频来排序显示结果
 * 作者：蒋鑫
 * */
package Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class IndexPro {
	String indexFile; // the index file
	String wordtableFile; // the dictionary file
	Vector<String> vecKey;
	HashMap<String, String> hashWord = null;
	long time = 0;

	public IndexPro() {
	}

	public IndexPro(String indexFile, String wordtableFile) {
		this.indexFile = indexFile;// 索引文件
		this.wordtableFile = wordtableFile;// 词表
		long begin = System.currentTimeMillis();
		hashWord = new HashMap<String, String>();
		try {
			String line = null;
			BufferedReader rin = new BufferedReader(new InputStreamReader(new FileInputStream(new File(indexFile)),"UTF-8"));
			while ((line = rin.readLine()) != null) {
				String[] array = line.split("  ");
				hashWord.put(array[0], array[1]); //array[0]keyword,array[1]others
				//System.out.println(array[0]); //bad
				//System.out.println(array[1]); //bad
			}
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		this.time = end - begin;
	}

	// 获得结果集合
	public ArrayList<ResultModel> getResultSet(String key) {
		ArrayList<ResultModel> modList = null;
		if (this.hashWord.size() > 0) {
			long begin = System.currentTimeMillis();
			ResultModel[] modArray = null;
			// 对关键字分词
			//SplitWordPro swp = new SplitWordPro(this.wordtableFile); //bad
			//this.vecKey = swp.getWord(key); //bad
			StringReader strReader = new StringReader(key);
			IKSegmentation iksegmentation = new IKSegmentation(strReader);
			Lexeme lexeme = null;
			try {
				while((lexeme=iksegmentation.next())!=null) {
					vecKey.add(lexeme.getLexemeText());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 分别查找各个词在索引中的匹配
			for (String strKey : vecKey) {
				String result = this.hashWord.get(strKey);
				if (result != null) {
					modList = new ArrayList<ResultModel>();
					String[] array = result.split("#next#");
					modArray = new ResultModel[array.length];
					for (int i = 0; i < array.length; i++)
						modArray[i] = new ResultModel(key, array[i]);
				}
			}

			if (modArray != null) {
				// 合并相同出处内容的词频
				this.ResultMerger(modArray);
				// 将结果按照词频排序
				for (int i = 0; i < modArray.length; i++) {
					for (int j = i + 1; j < modArray.length; j++) {
						if (modArray[i].getWordV() < modArray[j].getWordV()) {
							ResultModel modTmp = modArray[i];
							modArray[i] = modArray[j];
							modArray[j] = modTmp;
						}
					}
				}
				modList = new ArrayList<ResultModel>();
				for (int i = 0; i < modArray.length; i++)
					modList.add(modArray[i]);
			}
			long end = System.currentTimeMillis();
			this.time += (end - begin);
		}
		return modList;
	}

	// 获得处理时间
	public long getTime() {
		return this.time;
	}

	// 合并相同出处内容的词频
	private void ResultMerger(ResultModel[] modArray) {
		for (int i = 0; i < modArray.length; i++)
			for (int j = i + 1; j < modArray.length; j++) {
				if (modArray[i] != null && modArray[j] != null) {
					if (modArray[i].getUrl().trim()
							.equals(modArray[j].getUrl().trim())) {
						modArray[i].addWordV(modArray[j].getWordV());// 相加频率
						modArray[j] = null;
					}
				}
			}
	}

	// 对关键词高亮显示
	public String HighLightKey(String content) {
		content = content.replaceAll(" ", "");
		for (String word : this.vecKey) {
			content = content.replaceAll(word,
					"<font style='color:#ff0000;font-weight:bold;'>" + word
							+ "</font>");
		}

		return content.replaceAll(
				"</font>[\\W]*<font style='color:#ff0000;font-weight:bold;'>",
				"");
	}
}
