package testScripts;

import page.LoginLogoutPage;
import utility.CoreSuperHelper;

public class DPDA_SampleScript extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			
			logExtentReport("LARG Application");
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(10000);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}