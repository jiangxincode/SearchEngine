/**
 * @author jiangxin
 * 删除目录
 */

package edu.jiangxin.searchengine.allinone;
import java.io.File;

public class DeleteDirectory {

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 *
	 * @param dir
	 *            :将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			if(children == null)
			{
				return true;
			}
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
	public static boolean deleteDirs(String[] deleteDirs) {
		for(int i=0;i<deleteDirs.length;i++) {
			File dir = new File(deleteDirs[i]);
			boolean isSuccessful = deleteDir(dir);
			if(isSuccessful == true) {
				System.out.println("成功删除目录：" + dir);
			}
		}
		return false;

	}

	/**
	 * 测试
	 */
	public static void main(String[] args) {
		String newDir2 = "new_dir2";
		boolean success = deleteDir(new File(newDir2));
		if (success) {
			System.out.println("Successfully deleted populated directory: "
					+ newDir2);
		} else {
			System.out.println("Failed to delete populated directory: "
					+ newDir2);
		}
	}
}