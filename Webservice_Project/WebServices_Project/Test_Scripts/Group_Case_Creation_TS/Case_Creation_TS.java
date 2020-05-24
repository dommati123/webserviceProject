package testScripts.Test_Scripts.Group_Case_Creation_TS;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.EnvHelper;

import page.wgs.membership.CASE_MAINTENANCE1_ADD_WGMCSMTA;
import page.wgs.membership.CASE_MAINTENANCE2_ADD_WGMCSM2A;
import page.wgs.membership.CASE_WGMMENU;
import page.wgs.membership.MEMBERSHIP_WGMMENU;
import page.wgs.membership.SYSTEMS_PROFILE_WGMMENU;
import page.wgs.membership.WGSInternalRepAddWGMIAGTA;
import utility.CoreSuperHelper;
import utility.WGSUtility;

public class Case_Creation_TS extends CoreSuperHelper{

	static String url = EnvHelper.getValue("URL");
	static String ip=EnvHelper.getValue("base.ip");

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

			//SYSTEMS PROFILE Screen		
			seSetText(SYSTEMS_PROFILE_WGMMENU.get().SELECT_OPTION,"BB", "Enter 'BB' in 'OPTION'");
			seMFInputKeysFromKeypad(true, KeyConstants.ENTER,  "Hit 'ENTER' to open 'MEMBERSHIP' screen",MEMBERSHIP_WGMMENU.get().PAGE_TITLE, "MEMBERSHIP","Validate the PAGE Title");

			//MEMBERSHIP Screen	 
			seSetText(MEMBERSHIP_WGMMENU.get().SELECT_OPTION_21_4,"AA", "Enter 'AA' in 'OPTION'");
			seMFInputKeysFromKeypad(true, KeyConstants.ENTER,  "Hit 'ENTER' to open 'CASE' screen",CASE_WGMMENU.get().PAGE_TITLE, "CASE","Validate the PAGE Title");


			//CASE Screen
			seSetText(CASE_WGMMENU.get().SELECT_OPTION_21_4,"AB", "Enter 'AB' in 'OPTION'");
			seMFInputKeysFromKeypad(true, KeyConstants.ENTER,  "Hit 'ENTER' to open 'CASE MAINTENANCE 1 - ADD' screen",CASE_MAINTENANCE1_ADD_WGMCSMTA.get().PAGE_TITLE, "CASE MAINTENANCE 1 - ADD","Validate the PAGE Title");


			//Generate Case ID
			String tds_Case=WGSUtility.generateID("CASE_NO", "");
			setCellValue("CASE", tds_Case.trim());
			seSetText(CASE_MAINTENANCE1_ADD_WGMCSMTA.get().CASE_7_2,tds_Case, "Input Group");

			//Input values in Case Maintenance1 Screen
			CASE_MAINTENANCE1_ADD_WGMCSMTA.get().fillCaseMaintenance1();

			//Input values in Case Maintenance2 Screen
			CASE_MAINTENANCE2_ADD_WGMCSM2A.get().fillCaseMaintenance2();

			//Input values in WGS Internal Rep Add Screen
			WGSInternalRepAddWGMIAGTA.get().fillWgsRepScreen();
			seMFInputKeysFromKeypad(true, KeyConstants.F2,  "Hit 'F2' to open 'CASE' screen",CASE_WGMMENU.get().PAGE_TITLE, "CASE","Validate the PAGE Title");
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
















