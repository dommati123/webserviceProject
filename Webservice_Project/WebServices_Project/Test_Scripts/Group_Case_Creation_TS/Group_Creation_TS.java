package testScripts.Test_Scripts.Group_Case_Creation_TS;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.EnvHelper;
import page.wgs.membership.BENT_REMIT_BILL_FINANCE_WGMMENU;
import page.wgs.membership.BILL_ENTITY_WGMMENU;
import page.wgs.membership.GROUP_SUFFIX_WGMMENU;
import page.wgs.membership.MEMBERSHIP_WGMMENU;
import page.wgs.membership.SYSTEMS_PROFILE_WGMMENU;
import utility.CoreSuperHelper;
import utility.WGSUtility;

public class Group_Creation_TS extends CoreSuperHelper{

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

			//Enter into Systems profile
			seSetText(SYSTEMS_PROFILE_WGMMENU.get().SELECT_OPTION,"BB", "Enter BB");
			seMFInputKeysFromKeypad(true, KeyConstants.ENTER,  "Hit 'ENTER' to open 'MEMBERSHIP' screen",MEMBERSHIP_WGMMENU.get().PAGE_TITLE, "MEMBERSHIP","Validate the PAGE Title");

			seSetText(MEMBERSHIP_WGMMENU.get().SELECT_OPTION_21_4,"BB", "Enter 'BB' in 'OPTION'");
			seMFInputKeysFromKeypad(true, KeyConstants.ENTER,  "Hit 'ENTER' to open 'GROUP / SUFFIX' screen",GROUP_SUFFIX_WGMMENU.get().PAGE_TITLE, "GROUP / SUFFIX","Validate the PAGE Title");

			seSetText(GROUP_SUFFIX_WGMMENU.get().SELECT_OPTION_21_4,"AA", "Enter the text 'AA' into the field 'SELECT OPTION'");	

			//Enroll new group under required CASE
			WGSUtility.get().groupEnrollment();

			//Request bill
			seMFInputKeysFromKeypad(true, KeyConstants.F3,  "Hit 'F3' to open 'BILL ENTITY' screen",BILL_ENTITY_WGMMENU.get().PAGE_TITLE, "BILL ENTITY","Validate the PAGE Title");
			seMFInputKeysFromKeypad(true, KeyConstants.F3,  "Hit 'F3' to open 'BENT / REMIT / BILL / FINANCE' screen",BENT_REMIT_BILL_FINANCE_WGMMENU.get().PAGE_TITLE, "BENT / REMIT / BILL / FINANCE","Validate the PAGE Title");

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

