package testScripts.Test_Scripts.Group_Case_Clone;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.EnvHelper;

import page.wgs.membership.GROUP_SUFFIX_WGMMENU;
import page.wgs.membership.MEMBERSHIP_WGMMENU;
import page.wgs.membership.SYSTEMS_PROFILE_WGMMENU;
import utility.CoreSuperHelper;
import utility.WGSUtility;

public class Group_Clone_TS extends CoreSuperHelper{

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

			//Navigation to Group general info screen
			seSetText(SYSTEMS_PROFILE_WGMMENU.get().SELECT_OPTION, "BB","Enter 'BB' in select option");
			seMFInputKeysFromKeypad(KeyConstants.ENTER, "Hit 'ENTER' to open 'MEMBERHIP SCREEN' screen");
			seSetText(MEMBERSHIP_WGMMENU.get().SELECT_OPTION_21_4, "BB", "");
			seMFInputKeysFromKeypad(KeyConstants.ENTER, "Hit 'ENTER' to open 'GROUP SUFIX' screen");
			seSetText(GROUP_SUFFIX_WGMMENU.get().SELECT_OPTION_21_4, "GA", ""); 
			seSetText(GROUP_SUFFIX_WGMMENU.get().GROUP_ID, getCellValue("SOURCE_GROUP"), "Enter group");
			seMFInputKeysFromKeypad(KeyConstants.ENTER, "Hit 'ENTER' to open 'GROUP GENERAL INFO INQUIRY' screen");

			//Validation of Group
			if (seMFVerifySpanValueExists("GROUP NOT FOUND")==true)
			{
				log(FAIL, "Given Group is NOT FOUND", "Group Not Found", true);
				
			}

			//Group Cloning --- getting data from existing Group
			WGSUtility.get().largeGroupCloning();


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





