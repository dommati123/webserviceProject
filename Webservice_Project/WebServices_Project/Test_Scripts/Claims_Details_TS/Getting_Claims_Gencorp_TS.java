package testScripts.Test_Scripts.Claims_Details_TS;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.EnvHelper;

import page.wgs.claims.ClaimsInquiryGNCCLMEI;
import page.wgs.membership.GENCORP_PROFILE_WGMMENU;

import utility.CoreSuperHelper;
import utility.WGSUtility;

public class Getting_Claims_Gencorp_TS extends CoreSuperHelper{
	
	
	static String url = EnvHelper.getValue("URL");
	static String ip=EnvHelper.getValue("base.ip");

	//Mainframe Connection Details
	static String userProfile=EnvHelper.getValue("user.profile.wgs");
	static String ism=EnvHelper.getValue("ISM");
	//static String region=EnvHelper.getValue("region");
	static String mainframeProfile=EnvHelper.getValue("mainframeProfile.Gen");



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
			setIgnoreLastTestFailure(true);
			
			String Region= getCellValue("Region");
			logExtentReport("ISGSmallEnrollment_TS");

			//To Avoid Creating Multiple Browser/Driver Instance
			if(getWebDriver()==null){
				seOpenBrowser(BrowserConstants.Chrome,url);
			}else{
				getWebDriver().navigate().to(url);
			}
			

			//Mainframe Login
			WGSUtility.get().connectMF(userProfile, Region, mainframeProfile);
			
			
		//Enter the Screen
		seSetText(GENCORP_PROFILE_WGMMENU.get().SELECT_OPTION_21_4,"AA", "Enter the text 'AA' into the field 'SELECT OPTION'");
		seMFInputKeysFromKeypad(KeyConstants.ENTER, "Hit 'ENTER'");
		seSetText(ClaimsInquiryGNCCLMEI.get().CLAIMS_INQUIRY_DCN,getCellValue("DCN"), "Enter the 'DCN' into the field 'DCN'");
		seMFInputKeysFromKeypad(KeyConstants.ENTER, "Hit 'ENTER'");
		seMFInputKeysFromKeypad(KeyConstants.F10, "Hit 'F10'");
		WGSUtility.get().fnClaimsdetails();
		seMFInputKeysFromKeypad(KeyConstants.F3, "Hit 'F3'");
		seMFInputKeysFromKeypad(KeyConstants.F3, "Hit 'F3'");
		seMFInputKeysFromKeypad(KeyConstants.F3, "Hit 'F3'");
		seMFInputKeysFromKeypad(KeyConstants.F3, "Hit 'F3'");
			
		} catch (Exception e) {

			log(ERROR, "Exception has occured for the iteration", e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally{


			seMFDisconnect();
			seCloseBrowser();
			setResult("STATUS",getResult());
		}
	} 


}
