package edu.jiangxin.searchengine.utils;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jiangxin
 *
 */
public class DeleteDirectoryTest {

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
	public final void testDeleteDir() {
		String newDir2 = "new_dir2";
		boolean success = DeleteDirectory.deleteDir(new File(newDir2));
		if (success) {
			System.out.println("Successfully deleted populated directory: " + newDir2);
		} else {
			System.out.println("Failed to delete populated directory: " + newDir2);
		}
	}

}
