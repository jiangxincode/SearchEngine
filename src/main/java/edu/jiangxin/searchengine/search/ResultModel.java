/**
 * 描述：结果模型，用来存放单个的检索结果
 * 作者：蒋鑫
**/
package edu.jiangxin.searchengine.search;

// String tmp = str + " " + fileName +"#split#" + title + "#split#" + hashMap.get(str) + "#split#" + num + "#next#"
public class ResultModel implements Comparable {
	private String word; // 词
	private String url; // 源页面url地址
	private int wordV; // 在本文档内的词频
	private String title; // 本文档的标题
	private String partContent; // 包含词的部分文章内容

	/**
	 *
	 */
	public ResultModel() {
	}

	/**
	 *
	 * @param word word
	 * @param result result
	 */
	public ResultModel(final String word, final String result) {
		this.word = word;
		if (result.indexOf("#split#") > 0) {
			String[] array = result.split("#split#");
			this.url = "http://" + array[0].replaceFirst("_", "/").replaceAll(".txt", "");
			this.title = array[1];
			this.partContent = array[2];
			this.wordV = Integer.parseInt(array[3].trim());
		}
	}

	/**
	 *
	 * @return String
	 */
	public final String word() {
		return word;
	}

	/**
	 *
	 * @return String
	 */
	public final String getUrl() {
		return this.url;
	}

	/**
	 *
	 * @return String
	 */
	public final String getTitle() {
		return this.title;
	}

	/**
	 *
	 * @return int
	 */
	public final int getWordV() {
		return this.wordV;
	}

	/**
	 *
	 * @return String
	 */
	public final String getPartContent() {
		return this.partContent;
	}

	/**
	 *
	 * @param v
	 *            int
	 */
	public final void addWordV(final int v) {
		this.wordV += v;
	}

	/**
	 *
	 */
	public final void printInfo() {
		System.out.println(word);
		System.out.println(url);

	}

	/**
	 *
	 */
	@Override
	public final int compareTo(final Object o) {
		if (o.getClass().isInstance(this)) {
			return this.getWordV() - ((ResultModel) o).getWordV();
		}
		return 0;
	}
}
