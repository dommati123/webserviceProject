/**
 * 
 */
package utility;



import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anthem.ataf.logging.AnthemLogger;
import com.anthem.selenium.SuperHelper;
import com.anthem.selenium.constants.DatabaseConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.DatabaseUtils;
import com.anthem.selenium.utils.ExcelUtils;

import utility.CoreSuperHelper;


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


	private static String failureReason="";

	/**
	 * <p>
	 * This method is to check whether the element is present
	 * </p>
	 * @param WebElement
	 * @author AF38593
	 * @return boolean true/false
	 * @throws Exception
	 */
	public static boolean checkElementPresent(WebElement elem)
	{
		boolean returnValue = false;
		try
		{

			if(elem.toString().contains(getWebDriver().toString()))
			{
				returnValue = true;
			}
			else
			{
				returnValue = false;
			}
		}
		catch (NoSuchElementException e){
			returnValue = false;
		}
		catch (Exception e) {
			returnValue = false;
		}

		return returnValue;

	}


	public static void processException(Exception e,String strStepDetails)
	{
		setLastTestPassed(false);
		AnthemLogger.getLogger().debug(e.getLocalizedMessage());
		log(FAIL, strStepDetails, e.getLocalizedMessage());
	}


	/**
	 * Method to Set Failure Reason
	 * @param failureReason
	 * @author AF17404
	 */
	public static void seSetFailureReason(String Reason){
		failureReason=failureReason.isEmpty() ? Reason : failureReason;
	}

	/**
	 * Method to Get Failure Reason
	 * @return
	 * @author AF17404
	 */
	public static String seGetFailureReason(){
		String temp=failureReason;
		failureReason="";
		return temp;
	}




	/**
	 * <p>
	 * Press SoftKeys to perform action and verify expected String after keystrokes
	 * </p>
	 * @param screenshot
	 * 		Enter True or false to capture snap shot before clicking web element
	 * @param expectedKeys
	 *            Enter Keys like Enter,F3,F5,F7,F9,F10,F11,F12
	 * @param steps
	 *            Enter description of Entered Keys
	 * @param sourceString
	 *            Enter Source String
	 * @param subString
	 *            Enter Sub String to compare
	 * @param stepSummary Test Step Summary
	 * @return boolean true/false
	 * @throws Exception
	 */

	public static boolean seMFInputKeysFromKeypad(boolean screenshot, String expectedKeys, String steps, WebElement element, String subString,String stepSummary)
			throws Exception {
		boolean lastTestResult =false;
		try
		{
			if(isLastTestPassed()||isIgnoreLastTestFailure())
			{
				lastTestResult=seMFInputKeysFromKeypad(screenshot, expectedKeys,steps);
				if(lastTestResult){
					seWaitForPageLoad();
					lastTestResult= seCompareText(element,subString,stepSummary);
				}
			}

		}

		catch (Exception e) {
			processException(e,"Exception occured while pressing keys");
		}
		return lastTestResult;
	}

	/**
	 * @param screenshot
	 * @param expectedValue
	 * @return
	 * @throws Exception
	 */
	public static boolean seMFVerifySpanValueExistsinPageSource(boolean screenshot, String expectedValue)
			throws Exception {
		boolean dataFound = false;
		try
		{
			if(isLastTestPassed()||isIgnoreLastTestFailure())
			{
				seWaitForPageLoad();

				String text = "";
				List<WebElement> span = getWebDriver().findElements(By.tagName("SPAN"));

				for (WebElement spans : span) {
					text = spans.getText().trim();

					if (text.contains(expectedValue)) {
						dataFound = true;

						break;
					}
				}
			}
		}

		catch (Exception e) {
			processException(e,"Exception occured in verifying span value from page source");
		}

		return dataFound; 

	}


	/**
	 * Method to set the text of theelement by identifying using By param
	 * @param param: By variable that will be used to identify the element
	 * @param strInput: Text that needs to entered
	 * @param strStepDetails: Step details to be logged into the report
	 */
	public static void seSetText(By param,String strInput,String strStepDetails)
	{
		try
		{
			if(isLastTestPassed()||isIgnoreLastTestFailure())
			{
				WebElement testObject=getWebDriver().findElement(param);
				seSetText(testObject, strInput, strStepDetails);

			}
		}
		catch (Exception e) {
			processException(e, "Exception occured while trying to enter text");
		}
	}




	public  static String getReportPathFolder()
	{
		String reportPath = "";
		try
		{

			reportPath = ExtentReportsUtility.getExtentReportsPath();
			File f = new File(reportPath);
			reportPath = f.getAbsolutePath()+"\\";
		}
		catch (Exception e) {
			processException(e, "Exception occured while trying to enter text");
		}

		return reportPath;

	}


	/**
	 * Method to verify whether element is clickable or not
	 * @param clickable: Webelement that needs to be verified
	 * @param waitTime: Amount of time script needs to wait for element to be clickable 
	 * @param stepName: Step name 
	 * @param captureScreenshot: indicator to capture the screenshot
	 */
	public static void seCheckElementClickable(WebElement clickable,int waitTime,String stepName,boolean captureScreenshot) {
		try {
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				WebDriverWait wait = new WebDriverWait(getWebDriver(),waitTime);
				boolean result = false;
				try
				{
					result	= wait.until(ExpectedConditions.elementToBeClickable(clickable))!=null?true:false;
				}
				catch (TimeoutException e) {
					//					ignoring the timeout expression
				}

				if(result)
				{
					log(PASS, stepName, "Element is clickable", captureScreenshot);
				}
				else
				{
					setLastTestPassed(result);
					log(FAIL, stepName, "Element is either not clickable or not present in the Application", captureScreenshot);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
	}



	/**
	 * Method to verify whether element is clickable or not
	 * @param clickable: Webelement that needs to be verified
	 * @param waitTime: Amount of time script needs to wait for element to be clickable 
	 * @param stepName: Step name 
	 */
	public static void seCheckElementClickable(WebElement clickable,int waitTime,String stepName) {
		seCheckElementClickable(clickable, waitTime, stepName, false);
	}


	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seWaitForElement(WebElement element,int waitTime)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				WebDriverWait wait = new WebDriverWait(getWebDriver(),waitTime);

				try
				{
					result	= wait.until(ExpectedConditions.visibilityOf((element)))!=null?true:false;
				}
				catch (TimeoutException e) {
					//					ignoring the timeout expression
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seWaitForElement(WebElement element,int waitTime,ExpectedCondition<Boolean> expect)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				WebDriverWait wait = new WebDriverWait(getWebDriver(),waitTime);
				try
				{
					result	= wait.until(expect)!=null?true:false;
				}
				catch (TimeoutException e) {
					//					ignoring the timeout expression
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seWaitForElement(int waitTime,ExpectedCondition<Boolean> expect)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				WebDriverWait wait = new WebDriverWait(getWebDriver(),waitTime);
				try
				{
					result	= wait.until(expect)!=null?true:false;
				}
				catch (TimeoutException e) {
					//					ignoring the timeout expression
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	

	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seVerifyElementVisible(WebElement element,int waitTime,String stepName,boolean captureScreenshot)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				result = seWaitForElement(element, waitTime);
				if(result)
				{
					log(PASS, stepName, "Element is visible", captureScreenshot);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, stepName, "Element is not visible", captureScreenshot);

				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seVerifyElementVisible( By element,int waitTime,String stepName,boolean captureScreenshot)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				
				WebDriverWait wait = new WebDriverWait(getWebDriver(),waitTime);
				result	= wait.until(ExpectedConditions.visibilityOfElementLocated(element))!=null?true:false;
				if(result)
				{
					log(PASS, stepName, "Element is visible", captureScreenshot);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, stepName, "Element is not visible", captureScreenshot);

				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	
	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seVerifyElementInVisible(WebElement element,int waitTime,String stepName,boolean captureScreenshot)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				result = seWaitForElement(element, waitTime);
				if(!result)
				{
					log(PASS, stepName, "Element is not visible", captureScreenshot);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, stepName, "Element is visible", captureScreenshot);

				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	
	/**
	 * Method to wait for the element to be visible
	 * @param element
	 * @param waitTime
	 * @return
	 */
	public static boolean seVerifyElementInVisible(By locator,int waitTime,String stepName,boolean captureScreenshot)
	{
		boolean result = false;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				WebDriverWait wait = new WebDriverWait(getWebDriver(),waitTime);
				result	= wait.until(ExpectedConditions.visibilityOfElementLocated(locator))!=null?true:false;
				if(!result)
				{
					log(PASS, stepName, "Element is not visible", captureScreenshot);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, stepName, "Element is visible", captureScreenshot);

				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return result;
	}
	
	
	
	
	


	/**
	 * Method to identify webelement by using the locator
	 * @param locator
	 * @return
	 */
	public static WebElement seFindELement(By locator)
	{
		WebElement wElement=null;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				WebDriverWait wait = new WebDriverWait(getWebDriver(), 20);
				wElement=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
		return wElement;
	}

	/**
	 * Method to scroll to the given web element
	 * @param Web element
	 */
	public static void seScrollToElement(WebElement ele)
	{
		try{

			WebElement element = ele;
			int elementPosition = element.getLocation().getY();
			String js = String.format("window.scroll(0, %s-250)", elementPosition);
			((org.openqa.selenium.JavascriptExecutor)getWebDriver()).executeScript(js);
			log(PASS,"Capture Screenshot","Captured",true);
		}
		catch(Exception e)
		{
			log(FAIL,"Capture Screenshot","Unable to capture screenshot",true);
			e.printStackTrace();
			processException(e, "Exception occured in check element clickable method");
		}
	} 
	
	
	/**
	 * Method to get All the data from Excel including Header
	 * @param ExcelPath
	 * @param sheetName
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, String>> getAllRowValues(String ExcelPath,String sheetName)
	{
		HashMap<Integer, HashMap<String, String>> excelData = new HashMap<>();
		try
		{
			List<List<String>> dbValue = ExcelUtils.getAllRowValues(ExcelPath, sheetName, true);
			List<String> headerValues = dbValue.get(0);
			
			for(int i =1; i<dbValue.size();i++)
			{
				List<String> rowValues = dbValue.get(i);
				HashMap<String, String> rowValue = new HashMap<>();
				for(int j=0;j<rowValues.size();j++)
				{
					String headerName = headerValues.get(j);
					String rowdata = rowValues.get(j);
					rowValue.put(headerName, rowdata);
				}
				excelData.put(i, rowValue);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while reading the values from Excel");
		}
		return excelData;
	}
	
	
	
	
	/**
	 * Method to fetch the values of the drop down
	 * @param testObjec
	 * @return
	 */
	public static List<String>  getDropDownValues(WebElement testObjec)
	{
		List<String> dropDownValues = new ArrayList<String>();
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				Select select = new Select(testObjec); 
				List<WebElement> option = select.getOptions();
				for (WebElement webElement : option) {
					
					String value = webElement.getText();
					dropDownValues.add(value);
				}	
			
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while reading the values drop down");
		}
		return dropDownValues;
		
	}
	
	
	
	/**
	 * Method to fetch the values of the drop down
	 * @param testObjec
	 * @return
	 * @throws SQLException 
	 */
	public static HashMap<Integer,HashMap<String, String>>  executeQuery(String dbURL,String userProfile, String query,int DBType) throws SQLException
	{
		HashMap<Integer,HashMap<String, String>> dbValues = new HashMap<>();
		Connection	 conn = null;
		try
		{
			if(isLastTestPassed() || isIgnoreLastTestFailure())
			{
				String[] userInfo = getLoginInfo(userProfile);
				String username = userInfo[0];
				String password = userInfo[1];
				
				conn = DatabaseUtils.getConnection(DBType, dbURL, username, password);
				ResultSet rs = DatabaseUtils.executeQuery(conn, query);
				
				ResultSetMetaData data = rs.getMetaData();
				int columnCount = data.getColumnCount();
				int rowCount =0;
				while(rs.next())
				{
					rowCount++;
					HashMap<String, String> rowValues = new HashMap<>();
					for(int i=1; i<=columnCount;i++)
					{
						String ColumnHeader = data.getColumnLabel(i);
						String columnValue = rs.getString(ColumnHeader).trim();
						rowValues.put(ColumnHeader, columnValue);
					}
					dbValues.put(rowCount, rowValues);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while reading the values drop down");
		}
		finally {
			if(conn!=null)
			{
				conn.close();
			}
		}
		return dbValues;
		
	}
	
	
	
	/**
	 * Method to fetch the value of the option selected in the drop down
	 * @param dropdown
	 */
	public static String segetSelectedOptionValue(WebElement dropdown)
	{
		String selectedOption = "";
		try
		{
			if(isLastTestPassed()||isIgnoreLastTestFailure())
			{
				Select sel = new Select(dropdown);
				selectedOption = sel.getFirstSelectedOption().getText();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while fetch the selected option value");
		}
		return selectedOption;
	}
	
	
	

}
