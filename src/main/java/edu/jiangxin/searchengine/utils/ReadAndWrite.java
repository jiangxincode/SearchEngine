package edu.jiangxin.searchengine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ReadAndWrite {
	public static String readFileByChars(String fileName, String encoding) {
		try {
			String s = null;
			StringBuilder result = new StringBuilder();
			BufferedReader rin = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(fileName)), encoding));
			while ((s = rin.readLine()) != null) {
				result.append(s);
			}
			rin.close();
			return result.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readFileByChars(String fileName) {
		return readFileByChars(fileName, "UTF-8");
	}

	public static void writeFileByChars(String fileName, String value) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(value.getBytes());
		FileChannel fileChannel = null;
		try {
			File file = new File(fileName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(fileName);
			fileChannel = fos.getChannel();
			FileLock fileLock = fileChannel.tryLock(); // 加锁
			fileChannel.write(byteBuffer);
			fileLock.release(); // 解锁
			fileChannel.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
