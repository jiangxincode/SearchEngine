package edu.jiangxin.searchengine.split;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * split the sentence into words.
 *
 * @author jiangxin
 *
 */
public class Spilt {

	/**  */
	private String sourceDir;

	/**  */
	private String targetDir;

	/**
	 *
	 * @param source source directory.
	 * @param target target directory.
	 */
	public Spilt(final String source, final String target) {
		this.sourceDir = source;
		this.targetDir = target;
	}

	/**
	 *
	 */
	public final void segment() {
		segmentDir(this.sourceDir, this.targetDir);
	}

	/**
	 *
	 * @param source source directory.
	 * @param target target directory.
	 */
	public final void segmentDir(final String source, final String target) {
		File[] file = new File(source).listFiles();
		if (file == null) {
			return;
		}
		for (int i = 0; i < file.length; ++i) {
			if (file[i].isFile()) {
				segmentFile(file[i].getAbsolutePath(), target + File.separator + file[i].getName());
			}
			if (file[i].isDirectory()) {
				String sourceDirTmp = source + File.separator + file[i].getName();
				String targetDirTmp = target + File.separator + file[i].getName();
				new File(targetDirTmp).mkdirs();
				segmentDir(sourceDirTmp, targetDirTmp);
			}
		}
	}

	/**
	 *
	 * @param srcfilename srcfilename
	 * @param resfilename resfilename
	 */
	public final void segmentFile(final String srcfilename, final String resfilename) {
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
		Lexeme lexeme;
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

		IKSegmenter iksegmentation = new IKSegmenter(filereader, true);
		try {
			while ((lexeme = iksegmentation.next()) != null) {
				filewriter.write(lexeme.getLexemeText());
				filewriter.write(" ");
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			if (filewriter != null) {
				try {
					filewriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		System.out.println("成功对" + srcfilename + "进行分词");
	}
}