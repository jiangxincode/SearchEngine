package edu.jiangxin.searchengine.utils;

import java.io.File;

public class GetPath {

	/**
	 * 获取maven项目的Main资源文件路径（不含包名，以分隔符结尾）.
	 *
	 * @return
	 */
	public static String getMainResourcePath() {
		return "target" + File.separator + "classes" + File.separator;
	}

	/**
	 * 获取maven项目的Test资源文件路径（不含包名，以分隔符结尾）.
	 *
	 * @return
	 */
	public static String getTestResourcePath() {
		return "target" + File.separator + "test-classes" + File.separator;
	}

	/**
	 * 获取maven项目的Main资源文件路径（含包名，以分隔符结尾）.
	 *
	 * @return
	 */
	public static String getMainResourcePathWithPackage(final Object obj) {

		String result = "target" + File.separator + "classes" + File.separator;

		String packageName = obj.getClass().getPackage().getName();

		String[] list = packageName.split("\\.");
		for (int i = 0; i < list.length; i++) {
			result += list[i];
			result += File.separator;
		}

		return result;
	}

	/**
	 * 获取maven项目的Test资源文件路径（含包名，以分隔符结尾）.
	 *
	 * @return
	 */
	public static String getTestResourcePathWithPackage(Object obj) {

		String result = "target" + File.separator + "test-classes" + File.separator;

		String packageName = obj.getClass().getPackage().getName();

		String[] list = packageName.split("\\.");
		for (int i = 0; i < list.length; i++) {
			result += list[i];
			result += File.separator;
		}

		return result;
	}

}
