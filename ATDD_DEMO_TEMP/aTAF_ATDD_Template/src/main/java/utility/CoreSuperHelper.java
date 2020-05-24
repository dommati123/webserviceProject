package utility;

import org.openqa.selenium.WebElement;

import com.anthem.ataf.logging.AnthemLogger;
import com.anthem.selenium.SuperHelper;
import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

/**
 * @author shiva.katula
 * 
 * ##### Do not delete this file. ######
 * 
 * This class is specifically only for any Project specific Helper Methods for SuperHelper Extension
 *
 * Any method defined in this class will be automatically available in the project like Super Helper methods.
 * Any methods defined here will need to be called without the Class Name to maintain consistency in calling
 * If Project defines a helper method here and if that method is harvested back into Selenium Framework
 * by Architects, then the local method in this class can be deleted with out changing any of the test scripts.
 * 
 * To maintain naming convention across all the projects do not name the method with the Project Name.
 * Also take the suggestion/Approval for the method name to avoid discrepancy with the Framework methods.
 *  
 */
public class CoreSuperHelper extends SuperHelper {
	
	private static String className = CoreSuperHelper.class.getName();

	private static final AnthemLogger logger = AnthemLogger.getLogger(className);
	
		
	// This is the example method to be followed while coding CoreSuperHelper methods.
	/**
	 * <p>
	 * Click web element
	 * </p>
	 * @param screenshot
	 * 		Enter True or false to capture snap shot before clicking web element
	 * @param testObject
	 *            required test object need to test
	 * @param buttonName 
	 *            name Enter buttonName name to click.
	 * @param step
	 *            Enter steps to perform
	 * @throws Exception 
	 */
	
	public static void seClick1(boolean screenShot, WebElement testObject, String buttonName, String step) throws Exception {
		int successFlag = ApplicationConstants.FAIL;
		
		if (step == null) {
			step = "Click " + buttonName;
		}
		if (testObject.isDisplayed()) {
			testObject.click();
			successFlag = ApplicationConstants.PASS;
			ExtentReportsUtility.log(successFlag, step, "Core Super Helper Expected button \"" + buttonName + "\" clicked successfully");

		} else {

			ExtentReportsUtility.log(successFlag, step, "Core Super Helper Unable to click the button \"" + buttonName + "\" successfully"
					+ seCaptureScreenshot(getWebDriver(), buttonName));
		}
		if (screenShot) {
			ExtentReportsUtility.log(successFlag, step, seCaptureScreenshot(getWebDriver(), buttonName));
		}
	}
	
	public static void logTest() {
		logger.info(new String[]{"logTest from CoresuperHelper"});
	}

}
