/**
 * 
 */
package testScripts.da;

import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LoginLogoutPage;
import page.TransactionPage;
import page.WorkbasketsPage;
import page.WorklistPage;
import utility.CoreSuperHelper;

/**
 * @author AF14734
 *
 */
public class RouteTransaction extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			
			logExtentReport("Termination Involuntary - Provider Spawned transaction");
			LoginLogoutPage.get().loginApplication();
			WorkbasketsPage.get().openWorkbasket();
			WorkbasketsPage.get().searchWorkbasket();
			
		
			boolean transactionRouted = WorkbasketsPage.get().selectTransactionAndAssign();
		
			LoginLogoutPage.get().logoutApplication();
			
			if (transactionRouted) {
				// Use Re-LoginProfile
				LoginLogoutPage.get().reLoginApplication(getCellValue("RELOGIN_USER_PROFILE_TYPE"));
				WorklistPage.get().openWorklist();
				WorklistPage.get().searchWorklistWorkbasket();
				if (WorklistPage.get().clickAndOpenTransaction()) {
					seWaitForElementVisibility(10,CommonElements.get().iFrameSecondTab);
					seSwitchToFrame(CommonElements.get().iFrameSecondTab);
					
					if (!isElementDisplayed(TransactionPage.get().trnUnableToOpenMsg)) {
						ExtentReportsUtility.log(PASS, "Open Transaction","Transaction is Opened Successfully.");
					} else {
						ExtentReportsUtility.log(FAIL, "Open Transaction",""+ TransactionPage.get().trnUnableToOpenMsg.getText());
					}
				}
			} else {
				ExtentReportsUtility.log(FAIL, "Route Transaction","Transaction Routing Failed");
			}
			Thread.sleep(100);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
