package testScripts.search;
import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.ParValidationSearchPage;

import utility.CoreSuperHelper;

public class ParValidationSearch extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			String ProviderSearch=getCellValue("V_ProviderName");
			String CredentialingLevel=getCellValue("V_CredentialingLevel");
			String Speciality=getCellValue("V_Speciality");		
			logExtentReport("Par Validation Search");
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().searchCriteria, "Click on Search Criteria");
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().parValidationSearch, "Click on Network Search");
			Thread.sleep(5000);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			ParValidationSearchPage.get().SelectUniqueSearchByDiffOptions();			
			seClick(CommonElements.get().btnSearch, "Click on network search button ");
			Thread.sleep(10000);
			ParValidationSearchPage.get().validateParValidationData(ProviderSearch, CredentialingLevel,Speciality);
			LoginLogoutPage.get().logoutApplication();			
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}