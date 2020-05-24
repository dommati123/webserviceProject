package ataf;

import java.io.File;

import com.anthem.ataf.arch.atdd.ATDDRunner;
import com.anthem.selenium.constants.EnvConstants;
import com.anthem.selenium.utility.EnvHelper;

/**
 * 
 * This file executes all the scripts from features folder and all its
 * sub-folders.
 * 
 * ##### Do not modify this file. #####
 * 
 * @author shiva.katula
 */

public class ATDDDriver {

	public static void main(String[] args) {
		boolean specificFeatures = false;
		if (!EnvHelper.getValue(EnvConstants.atddExecFeaturesNames).equalsIgnoreCase("NO")) {
			specificFeatures = true;
		}
		ATDDRunner.execute(getTestScripstPath(),specificFeatures);
	}
	
	private static String getTestScripstPath() {
		File classpathRoot = new File(ATDDDriver.class.getResource("").getPath());
		return classpathRoot.getPath();
	}
	

}
