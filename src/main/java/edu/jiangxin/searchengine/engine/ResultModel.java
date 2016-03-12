/**
 * 描述：结果模型，用来存放单个的检索结果
 * 作者：蒋鑫
**/
package edu.jiangxin.searchengine.engine;

// String tmp = str + " " + fileName +"#split#" + title + "#split#" + hashMap.get(str) + "#split#" + num + "#next#"
public class ResultModel {
	private String word; //词
	private String url; //源页面url地址
	private int wordV; //在本文档内的词频
	private String title; //本文档的标题
	private String partContent; //包含词的部分文章内容
	
	public ResultModel() {
	}

	public ResultModel(String word, String result) {
		this.word = word;
		if (result.indexOf("#split#") > 0) {
			String[] array = result.split("#split#");
			this.url = "http://" + array[0].replaceFirst("_", "/").replaceAll(".txt", "");
			this.title = array[1];
			this.partContent = array[2];
			this.wordV = Integer.parseInt(array[3].trim());
		}
	}

	public String word() {
		return word;
	}

	public String getUrl() {
		return this.url;
	}

	public String getTitle() {
		return this.title;
	}

	public int getWordV() {
		return this.wordV;
	}

	public String getPartContent() {
		return this.partContent;
	}

	public void addWordV(int v) {
		this.wordV += v;
	}

	public void printInfo() {
		System.out.println(word);
		System.out.println(url);
		
	}
}
