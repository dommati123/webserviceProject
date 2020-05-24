package utility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anthem.selenium.SuperHelper;
import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.DateTimeUtils;

/**
 * @author shiva.katula
 * 
 *         ##### Do not delete this file. ######
 * 
 *         This class is specifically only for any Project specific Helper Methods for SuperHelper Extension
 *
 *         Any method defined in this class will be automatically available in the project like Super Helper methods. Any methods defined here will need to be called without the Class Name to maintain consistency in
 *         calling If Project defines a helper method here and if that method is harvested back into Selenium Framework by Architects, then the local method in this class can be deleted with out changing any of the test
 *         scripts.
 * 
 *         To maintain naming convention across all the projects do not name the method with the Project Name. Also take the suggestion/Approval for the method name to avoid discrepancy with the Framework methods.
 * 
 */
public class CoreSuperHelper extends SuperHelper {


	// This is the example method to be followed while coding CoreSuperHelper methods.
	/**
	 * <p>
	 * Click web element
	 * </p>
	 * 
	 * @param screenShot
	 *            Enter True or false to capture snap shot before clicking web element
	 * @param testObject
	 *            required test object need to test
	 * @param buttonName
	 *            name Enter buttonName name to click.
	 * @param step
	 *            Enter steps to perform
	 */

	public static void seClick1(boolean screenShot, WebElement testObject, String buttonName, String step) {
		int successFlag = ApplicationConstants.FAIL;

		if (step == null) {
			step = "Click " + buttonName;
		}

		if (testObject.isDisplayed()) {
			testObject.click();
			successFlag = ApplicationConstants.PASS;
			ExtentReportsUtility.log(successFlag, step, "Core Super Helper Expected button \"" + buttonName + "\" clicked successfully");

		} else {
			ExtentReportsUtility.log(successFlag, step, "Core Super Helper Unable to click the button \"" + buttonName + "\" successfully" + seCaptureScreenshot(getWebDriver(), buttonName));
		}

		if (screenShot) {
			ExtentReportsUtility.log(successFlag, step, seCaptureScreenshot(getWebDriver(), buttonName));
		}
	}
	
	public static void seWaitForElement(String xPathStr) {
		WebDriverWait wait = new WebDriverWait(getWebDriver(), 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPathStr)));
	}
	
	public static void seWaitForElementVisibility(long timeOutInMins, WebElement webElmt) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getWebDriver()).withTimeout(Duration.ofMinutes(timeOutInMins)).
			 	pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
		 wait.until(ExpectedConditions.visibilityOf(webElmt));
	}
	
	public static boolean isElementDisplayed(WebElement element) {
	    try {
	        WebDriverWait wait = new WebDriverWait(getWebDriver(), 1);
	        wait.until(ExpectedConditions.visibilityOf(element));
	        return element.isDisplayed();
	    } catch (org.openqa.selenium.NoSuchElementException
	            | org.openqa.selenium.StaleElementReferenceException
	            | org.openqa.selenium.TimeoutException e) {
	        return false;
	    }
	}
	
	public static void seWaitForElementToBeGone(long timeOutInMins, WebElement webElmt) {
		System.out.println("CoreSuperHelper.seWaitForElementToBeGone()");
		try {
			if (webElmt.isDisplayed()) {
				System.out.println("CoreSuperHelper.seWaitForElementToBeGone() Visible @ "+ DateTimeUtils.getTime()+" : "+webElmt);
				FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getWebDriver()).withTimeout(Duration.ofMinutes(timeOutInMins)).
						 	pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOf(webElmt));
				
			}
		}catch(Exception e) {
			
		}
		System.out.println("CoreSuperHelper.seWaitForElementToBeGone() Exiting @ "+ DateTimeUtils.getTime());
	}
	
	public static void seMoveToElement(WebElement webElmt) {
		Actions actions = new Actions(getWebDriver());
		actions.moveToElement(webElmt).build().perform();
	}
		
	public static void clickDPDAElmnt(WebElement webElmt) {
		Actions action = new Actions(getWebDriver());
		action.click(webElmt).sendKeys(Keys.ENTER).build().perform();
		
	}
	public static void clickXYCoordinateOfWebElement(WebElement ele, int x,int y) {
		Actions actions = new Actions(getWebDriver());
		actions.moveToElement(ele, 0,0);
		actions.moveByOffset(x, y).click().build().perform();
	}
	
	public static void seMoveToCoordinates(int x, int y) {
		Actions action = new Actions(getWebDriver());
		action.moveByOffset(x,y).build().perform();
	}
	
	public static String seGetDropDownSelectedText(WebElement testObj) {
		String value = null;
			try {
				Select target = new Select(testObj);
				value = target.getFirstSelectedOption().getText();
			} catch (org.openqa.selenium.NoSuchElementException e) {
				setLastTestPassed(false);
				e.printStackTrace();
			}
		return value;
	}
	
	public static String seGetDropDownSelectedValue(WebElement testObj) {
		String value = null;
			try {
				Select selectObj = new Select(testObj);
				value = selectObj.getFirstSelectedOption().getAttribute("value");
			} catch (Exception e) {
				setLastTestPassed(false);
				e.printStackTrace();
			}
		return value;
	}
	
	public void selectMultiListValues(WebElement element, String values) {
		try {
			String[] dropdownValue=values.split(";");
			seSetText(element, " ");
			Thread.sleep(2000);
			seInputKeys(element, KeyConstants.BACK_SPACE, "Backspace");
			Thread.sleep(2000);
			List<WebElement> dropdownOptions=getWebDriver().findElements(By.xpath("//div[@id='msresults-list']/ul/li//span[@class='ms-primary-option']"));
			ArrayList<String> options=new ArrayList<String>();
			for (WebElement webElement : dropdownOptions) {
				options.add(webElement.getText().trim());
			}
			
			for (String string : dropdownValue) {
				if(!options.contains(string)) {
					ExtentReportsUtility.log(FAIL, "Validate the existence of given option in the list box","Given option "+string+" is not availablein the list");
				}else {
					seSetText(element, string);
					Thread.sleep(2000);
					seInputKeys(element, KeyConstants.TAB, "Select "+string);
					Thread.sleep(1000);
				}
			}
		}catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Select multi-select values","Unexpected exception occurred. Please check the input values "+values);
			e.printStackTrace();
		}
	}
}