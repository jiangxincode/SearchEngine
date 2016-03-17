package edu.jiangxin.searchengine.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 搜索引擎，读取索引文件按照索引上词频来排序显示结果.
 * @author jiangxin
 *
 */
public class MyEngine {
	/**  */
	private String indexFile; // the index file

	/**  */
	private Vector<String> vecKey = new Vector<>();

	/**  */
	private HashMap<String, String> hashWord = null;

	/**  */
	private final int isOr = 0;

	/**  */
	private final int isAnd = 1;

	/**  */
	private long time = 0;

	/**  */
	private int symbol = 0;

	/**
	 *
	 */
	public MyEngine() {
	}

	/**
	 *
	 * @param indexFile String
	 */
	public MyEngine(final String indexFile) {
		this.indexFile = indexFile; // 索引文件
		long begin = System.currentTimeMillis();
		hashWord = new HashMap<>();
		try {
			String line;
			File file = new File(indexFile);
			System.out.println(file.getAbsolutePath());
			BufferedReader rin = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((line = rin.readLine()) != null) {
				String[] array = line.split("  ");
				hashWord.put(array[0], array[1]); // array[0]keyword,array[1]others
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

	/**
	 * 获得结果集合.
	 * @param key key
	 * @return ArrayList<ResultModel>
	 */
	public final ArrayList<ResultModel> getResultSet(final String key) {
		int pos = key.indexOf("&");
		if (pos > 0) {
			symbol = 1;
			String keyBefore = key.substring(0, pos);
			String keyAfter = key.substring(pos + 1, key.length());
			vecKey.add(keyBefore);
			vecKey.add(keyAfter);
			System.out.println("keyBefore is:" + keyBefore + "keyAfter is:" + keyAfter);
			ArrayList<ResultModel> modList = new ArrayList<>();
			ArrayList<ResultModel> modListBefore = new ArrayList<>();
			ArrayList<ResultModel> modListAfter = new ArrayList<>();
			if (this.hashWord.size() > 0) {
				long begin = System.currentTimeMillis();
				ResultModel[] modArray;
				String resultBefore = this.hashWord.get(keyBefore);
				String resultAfter = this.hashWord.get(keyAfter);
				String[] array = resultBefore.split("#next#"); // 得到存在该关键字的所有文本文件信息
				modArray = new ResultModel[array.length]; // 每个文本文件信息都可以获得一个ResultModel
				for (int i = 0; i < array.length; i++) {
					modArray[i] = new ResultModel(keyBefore, array[i]);
				}

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListBefore.add(modArray[i]);
					}
					// 将结果按照词频排序
					Collections.sort(modList);
				}
				array = resultAfter.split("#next#"); // 得到存在该关键字的所有文本文件信息
				modArray = new ResultModel[array.length]; // 每个文本文件信息都可以获得一个ResultModel
				for (int i = 0; i < array.length; i++) {
					modArray[i] = new ResultModel(keyAfter, array[i]);
				}

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListAfter.add(modArray[i]);
					}
					// 将结果按照词频排序
					Collections.sort(modList);
				}
				for (int i = 0; i < modListAfter.size(); i++) {
					for (int j = 0; j < modListBefore.size(); j++) {
						if (modListBefore.get(j).getUrl().equals(modListAfter.get(i).getUrl())) {
							modList.add(modListBefore.get(j));
						}
					}
				}
				long end = System.currentTimeMillis();
				this.time += (end - begin);
			}
			return modList;
		}
		int posDiff = key.indexOf("-");
		if (posDiff > 0) {
			symbol = 1;
			String keyBefore = key.substring(0, posDiff);
			String keyAfter = key.substring(posDiff + 1, key.length());
			vecKey.add(keyBefore);
			vecKey.add(keyAfter);
			System.out.println("keyBefore is:" + keyBefore + "keyAfter is:" + keyAfter);
			ArrayList<ResultModel> modList = new ArrayList<>();
			ArrayList<ResultModel> modListBefore = new ArrayList<>();
			ArrayList<ResultModel> modListAfter = new ArrayList<>();
			if (this.hashWord.size() > 0) {
				long begin = System.currentTimeMillis();
				ResultModel[] modArray;
				String resultBefore = this.hashWord.get(keyBefore);
				String resultAfter = this.hashWord.get(keyAfter);
				String[] array = resultBefore.split("#next#"); // 得到存在该关键字的所有文本文件信息
				modArray = new ResultModel[array.length]; // 每个文本文件信息都可以获得一个ResultModel
				for (int i = 0; i < array.length; i++) {
					modArray[i] = new ResultModel(keyBefore, array[i]);
				}

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListBefore.add(modArray[i]);
					}
					// 将结果按照词频排序
					Collections.sort(modList);
				}
				array = resultAfter.split("#next#"); // 得到存在该关键字的所有文本文件信息
				modArray = new ResultModel[array.length]; // 每个文本文件信息都可以获得一个ResultModel
				for (int i = 0; i < array.length; i++) {
					modArray[i] = new ResultModel(keyAfter, array[i]);
				}

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListAfter.add(modArray[i]);
					}
					// 将结果按照词频排序
					Collections.sort(modList);
				}
				for (int i = 0; i < modListAfter.size(); i++) {
					for (int j = 0; j < modListBefore.size(); j++) {
						if (modListBefore.get(j).getUrl().equals(modListAfter.get(i).getUrl())) {
							modListBefore.remove(j);
						}
					}
				}
				for (int i = 0; i < modListBefore.size(); i++) {
					modList.add(modListBefore.get(i));
				}
				long end = System.currentTimeMillis();
				this.time += (end - begin);
			}
			return modList;
		}

		ArrayList<ResultModel> modList = new ArrayList<>();
		if (this.hashWord.size() > 0) {
			long begin = System.currentTimeMillis();
			ResultModel[] modArray = null;
			// 对关键字分词
			IKSegmenter iksegmentation = new IKSegmenter(new StringReader(key), true);
			Lexeme lexeme;
			try {
				while ((lexeme = iksegmentation.next()) != null) {
					System.out.println(lexeme.getLexemeText());
					vecKey.add(lexeme.getLexemeText());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 分别查找各个词在索引中的匹配
			for (String strKey : vecKey) {
				String result = this.hashWord.get(strKey);
				if (result != null) {
					String[] array = result.split("#next#"); // 得到存在该关键字的所有文本文件信息
					modArray = new ResultModel[array.length]; // 每个文本文件信息都可以获得一个ResultModel
					for (int i = 0; i < array.length; i++) {
						modArray[i] = new ResultModel(key, array[i]);
					}
				}
				// }

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modList.add(modArray[i]);
					}

					// 合并相同出处内容的词频
					this.resultMerger(modList);
					// 将结果按照词频排序
					Collections.sort(modList);
				}
			}
			long end = System.currentTimeMillis();
			this.time += (end - begin);
		}
		return modList;
	}

	/**
	 * 获得处理时间.
	 * @return long
	 */
	public final long getTime() {
		return this.time;
	}

	/**
	 * 合并相同出处内容的词频.
	 * @param modList modList
	 */
	private void resultMerger(final ArrayList<ResultModel> modList) {
		for (int i = 0; i < modList.size(); i++) {
			for (int j = i + 1; j < modList.size(); j++) {
				if (modList.get(i) != null && modList.get(j) != null) {
					if (modList.get(i).getUrl().trim().equals(modList.get(j).getUrl().trim())) {
						modList.get(i).addWordV(modList.get(j).getWordV()); // 相加频率
						modList.remove(j);
					}
				}
			}
		}
	}

	/**
	 * 对关键词高亮显示.
	 * @param content content
	 * @return String
	 */
	public final String highLightKey(String content) {
		content = content.replaceAll(" ", "");
		for (String word : this.vecKey) {
			content = content.replaceAll(word, "<font style='color:#ff0000;font-weight:bold;'>" + word + "</font>");
		}

		return content.replaceAll("</font>[\\W]*<font style='color:#ff0000;font-weight:bold;'>", "");
	}
}
