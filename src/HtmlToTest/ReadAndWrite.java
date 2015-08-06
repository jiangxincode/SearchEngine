/**
 * 描述：定义文件读写方法类，供主程序使用
 * 作者：蒋鑫
**/
package HtmlToTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadAndWrite {
	public static String readFileByChars(String fileName) {
		try {
			String s = null;
			StringBuffer result = new StringBuffer();
			BufferedReader rin = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "GB2312"));
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

	public static void writeFileByChars(String fileName, String value) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(value.getBytes());
		File filepath = new File(fileName);
		filepath.getParentFile().mkdirs();
		FileChannel out;
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			out = outputStream.getChannel();
			out.write(byteBuffer);
			byteBuffer.clear();
			byteBuffer = null;
			out.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
