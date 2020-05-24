/**
 * 
 */
package testScripts.da;

import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LoginLogoutPage;
import page.TransactionPage;
import page.WorklistPage;
import utility.CoreSuperHelper;

/**
 * @author AF14734
 *
 */
public class PerformTransactionAction extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			
			logExtentReport("WorkList Termination Involuntary - Perform Action");
			String loginProfile= getCellValue("USER_PROFILE_TYPE");
			// Use Re-LoginProfile
			LoginLogoutPage.get().loginApplication(loginProfile);
			WorklistPage.get().openWorklist();
			WorklistPage.get().searchWorklistWorkbasket();
			if (WorklistPage.get().clickAndOpenTransaction()) {
				seWaitForElementVisibility(10,CommonElements.get().iFrameSecondTab);
				seSwitchToFrame(CommonElements.get().iFrameSecondTab);
				
				if (!isElementDisplayed(TransactionPage.get().trnUnableToOpenMsg)) {
					// Make sure transaction is Opened with no error.
					String actionType = getCellValue("ActionType");
					// DA Cases - Retire Provider Office
					if (actionType!=null && actionType.trim().equals("RetireProviderOffice")) {
						TransactionPage.get().retireProviderOffice();
					}
				} else {
					ExtentReportsUtility.log(FAIL, "Open Transaction",""+ TransactionPage.get().trnUnableToOpenMsg.getText());
				}
			}
				
			Thread.sleep(100);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
