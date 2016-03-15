package edu.jiangxin.searchengine.search;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jiangxin
 *
 */
public class SearchTest {

	/**
	 *
	 */
	@Before
	public void setUp() {
	}

	/**
	 *
	 */
	@Test
	public final void testSearch() {
		MyEngine index = new MyEngine("target/classes/index.idx");
		ArrayList<ResultModel> testList = index.getResultSet("中国&美国");
		for (int i = 0; i < testList.size(); i++) {
			testList.get(i).printInfo();
			System.out.println(i);
		}
	}

}
