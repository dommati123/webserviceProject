package testScripts.Test_Scripts.Group_Case_Clone;

import org.openqa.selenium.By;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.EnvHelper;

import page.wgs.membership.CASE_BEG_LIST_WGMCBGL;
import page.wgs.membership.CASE_MAINTENANCE1_INQUIRY_WGMCSMTI;
import page.wgs.membership.CASE_WGMMENU;
import page.wgs.membership.MEMBERSHIP_WGMMENU;
import page.wgs.membership.SYSTEMS_PROFILE_WGMMENU;
import utility.CoreSuperHelper;
import utility.WGSUtility;

public class Case_Clone extends CoreSuperHelper{

	static String url = EnvHelper.getValue("URL");
	static String ip=EnvHelper.getValue("base.ip");
	static String ErrorLog;
	//Mainframe Connection Details
	static String userProfile=EnvHelper.getValue("user.profile.wgs");
	static String ism=EnvHelper.getValue("ISM");
	static String region=EnvHelper.getValue("region");
	static String mainframeProfile=EnvHelper.getValue("mainframeProfile.systems");


	public static void aTAFInitParams() { 
		MANUAL_TC_EXECUTION_EFFORT = "00:59:00";
	}

	public static void main(String[] args) { 
		aTAFInitParams(); 
		initiateTestScript();

	}
	public static void executeScript() {


		try {

			//Method call to control the execution when there is a failure
			//setIgnoreLastTestFailure(false);
			String tcDescription = getCellValue("Test_Case_ID");
			logExtentReport(tcDescription);

			//To Avoid Creating Multiple Browser/Driver Instance
			if(getWebDriver()==null){
				seOpenBrowser(BrowserConstants.Chrome,url);
			}else{
				getWebDriver().navigate().to(url);
			}

			//Mainframe Login
			WGSUtility.get().connectMF(userProfile, region, mainframeProfile);



			seSetText(SYSTEMS_PROFILE_WGMMENU.get().SELECT_OPTION, "BB", "Enter 'BB' in Option field.");
			seMFInputKeysFromKeypad(true,"enter", "Press Enter Key Proceed");
			seSetText(MEMBERSHIP_WGMMENU.get().SELECT_OPTION_21_4, "AA", "Enter 'AA' in Option field.");
			seMFInputKeysFromKeypad(true,"enter", "Press Enter Key Proceed");
			seSetText(CASE_WGMMENU.get().CASE_7_2,getCellValue("SOURCE_CASE"), "Enter the case value into the field 'CASE'");

			/*Selection of the Case operation*/
			seSetText(CASE_WGMMENU.get().SELECT_OPTION_21_4 , "AA", "Enter the text 'AA' into the field 'SELECT OPTION'");
			seMFInputKeysFromKeypad(true,"enter", "Press Enter Key Proceed");

			//Verify whether the case already exists
			if(seGetText(CASE_MAINTENANCE1_INQUIRY_WGMCSMTI.get().MESSAGE).contains("CASE NUMBER NOT FOUND ON DATABASE"))
			{
				ErrorLog=getWebDriver().findElement(By.xpath("(//span[@name='0_MBRSHPLOCN']/following-sibling::span[@class='h3270-intensified'])[1]")).getText();
				setCellValue("COMMENTS",ErrorLog);
					
			}


			//Get the case values from the Inquiry screens
			WGSUtility.get().getCaseMaintenanceInq_1();
			WGSUtility.get().getCaseMaintenanceInq_2();
			WGSUtility.get().getCaseInternalRepInq();
			if(getCellValue("CASE_BEG_FLAG").equalsIgnoreCase("yes")){
				seSetText(CASE_WGMMENU.get().SELECT_OPTION_21_4,"CZ","Input CZ in Option Field");
				seMFInputKeysFromKeypad(KeyConstants.ENTER, "");
				seSetText(CASE_BEG_LIST_WGMCBGL.get().SELECT_OPTION_LIST,"S","Input AB in Option Field");
				seMFInputKeysFromKeypad(KeyConstants.ENTER, "");
				WGSUtility.get().getCaseBegDetails();
			}
			seMFInputKeysFromKeypad(true,"F12", "Press Enter Key Proceed");
			//Input AB option to add a New Case l
			seSetText(CASE_WGMMENU.get().SELECT_OPTION_21_4,"AB","Input AB in Option Field");
			seMFInputKeysFromKeypad(KeyConstants.ENTER, "");

			//Input details in Case Maintenance 1 -Add Screen
			boolean fillCaseMaintenanceAdd_1flag=WGSUtility.get().fillCaseMaintenanceAdd_1();
			if(!fillCaseMaintenanceAdd_1flag){
				ErrorLog=getWebDriver().findElement(By.xpath("//input[@name='field_77_23']/preceding-sibling::span[@class='h3270-intensified'][2]")).getText();
				setCellValue("COMMENTS",ErrorLog);
				
			}
			seMFInputKeysFromKeypad(KeyConstants.F10, "");

			//Input details in Case Maintenance 2 -Add Screen
			boolean fillCaseMaintenanceAdd_2flag=WGSUtility.get().fillCaseMaintenanceAdd_2();
			if(!fillCaseMaintenanceAdd_2flag){
				ErrorLog=getWebDriver().findElement(By.name("1_PRESSPF10TOCONFIRMANDPRO")).getText();
				setCellValue("COMMENTS",ErrorLog);
				
			}
			
			seMFInputKeysFromKeypad(KeyConstants.F10, "");
			//Input details in WGS_ Internal REP Add Screen
			boolean fill_WGSInternalREPAddflag=WGSUtility.get().fill_WGSInternalREPAdd();
			if(!fill_WGSInternalREPAddflag){
				ErrorLog=getWebDriver().findElement(By.xpath("//input[@name='field_77_23']/preceding-sibling::span[@class='h3270-intensified'][2]")).getText();
				setCellValue("COMMENTS",ErrorLog);
			
			}
			seMFInputKeysFromKeypad(KeyConstants.ENTER, "");

			if(getCellValue("CASE_BEG_FLAG").equalsIgnoreCase("yes")){
				seSetText(CASE_WGMMENU.get().SELECT_OPTION_21_4,"CY","Input CY in Option Field");
				seMFInputKeysFromKeypad(KeyConstants.ENTER, "");
				boolean fillCaseBegDetailsflag=WGSUtility.get().fillCaseBegDetails();
				if(!fillCaseBegDetailsflag){
					setCellValue("COMMENTS",ErrorLog);
					
				}
			}
			seMFInputKeysFromKeypad(KeyConstants.ENTER, "");

		}
		catch (Exception e) {
			
			log(ERROR, "Exception has occured for the iteration", e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally{
			setResult("STATUS", getResult());
			seMFDisconnect();
			seCloseBrowser();
			
		}
	}
}






