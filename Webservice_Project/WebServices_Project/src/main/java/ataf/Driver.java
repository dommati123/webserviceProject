package ataf;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 
 * This file executes all the scripts from testScripts package and all its
 * sub-packages.
 * 
 * ##### Do not modify this file. #####
 * 
 * @author shiva.katula
 *
 */
public class Driver {

	public static void main(String[] args) {

		com.anthem.selenium.Driver.execute(getTestScripstPath());
	}

	private static String getTestScripstPath() {
		File classpathRoot = new File(Driver.class.getResource("").getPath());
		File[] classFiles = classpathRoot.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});

		return classFiles[0].getParent();
	}

}
