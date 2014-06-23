/**
 * 描述：实现文件的中文分词
 * 作者：蒋鑫
**/
package Split;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.wltea.analyzer.core.*;

public class MySpilt {
	private String sourceDir;
	private String targetDir;

	public MySpilt(String source, String target) {
		this.sourceDir = source;
		this.targetDir = target;
	}

	void segment() {
		segmentDir(this.sourceDir, this.targetDir);
	}

	public void segmentDir(String source, String target) {
		File[] file = new File(source).listFiles();
		for (int i = 0; i < file.length; ++i) {
			if (file[i].isFile()) {
				segmentFile(file[i].getAbsolutePath(), target + File.separator
						+ file[i].getName());
			}
			if (file[i].isDirectory()) {
				String _sourceDir = source + File.separator + file[i].getName();
				String _targetDir = target + File.separator + file[i].getName();
				new File(_targetDir).mkdirs();
				segmentDir(_sourceDir, _targetDir);
			}
		}
	}

	public void segmentFile(String srcfilename, String resfilename) {
		File filetemp = new File(resfilename);
		filetemp.getParentFile().mkdirs();
		if (!(filetemp.exists())) {
			try {
				filetemp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileReader filereader = null;
		FileWriter filewriter = null;
		Lexeme lexeme = null;
		try {
			filereader = new FileReader(srcfilename);
		} catch (FileNotFoundException e0) {
			e0.printStackTrace();
		}

		try {
			filewriter = new FileWriter(resfilename);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		IKSegmenter iksegmentation = new IKSegmenter(filereader,true);
		try {
			while ((lexeme = iksegmentation.next()) != null) {
				filewriter.write(lexeme.getLexemeText());
				filewriter.write(" ");
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			filewriter.close();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		System.out.println("成功对" + srcfilename + "进行分词");
	}
	public static void main(String[] argv) {
		MySpilt myIK_Tokenize = new MySpilt("srcDoc/", "wordDoc/");
		myIK_Tokenize.segment();
	}
}