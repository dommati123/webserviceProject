package testScripts.search;




import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CaseIDSearchPage;
import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import utility.CoreSuperHelper;

public class CaseIDSearch extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			
			logExtentReport("Case ID Search");
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().searchCriteria, "Click on Search Criteria");
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().caseIDSearch, "Click on CaseID Search");
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			Thread.sleep(5000);
			
			String caseID = getCellValue("CaseID").trim().toUpperCase();
			
			if (!caseID.equals("")) {
				seSetText(CaseIDSearchPage.get().caseID, getCellValue("CaseID"));
				Thread.sleep(50);
				seClick(CommonElements.get().btnSearch, "Click on network search button ");
				Thread.sleep(5000);
				String[] searchIndxValArr = null;
				
				Integer caseType = CaseIDSearchPage.get().getSearchType(caseID);
				
				if (caseType == 2) {
					searchIndxValArr= new String[] {getCellValue("V_CaseID"),getCellValue("V_CaseStatus"),getCellValue("V_Transaction"),
							getCellValue("V_Workgroup"),getCellValue("V_WorkingStatus"),getCellValue("V_StatusTaken"),getCellValue("V_AssignedTo"),
							getCellValue("V_DPSClinicNo"),getCellValue("V_TaxIDNo"),getCellValue("V_DBAName"),
							getCellValue("V_DirectoryAddress1"),getCellValue("V_City"),getCellValue("V_Zip")};
				} else if (caseType == 3) {
					searchIndxValArr= new String[] {getCellValue("V_CaseID"),getCellValue("V_CaseStatus"),getCellValue("V_Transaction"),
							getCellValue("V_Workgroup"),getCellValue("V_WorkingStatus"),getCellValue("V_StatusTaken"),getCellValue("V_AssignedTo"),
							getCellValue("V_ProviderDPSNo"),getCellValue("V_FirstName"),getCellValue("V_LastName"),
							getCellValue("V_MiddleInitial"),getCellValue("V_NPI")};
				} else if (caseType == 1){
					searchIndxValArr= new String[] {getCellValue("V_CaseID"),getCellValue("V_CaseStatus"),getCellValue("V_Transaction"),
							getCellValue("V_Workgroup"),getCellValue("V_WorkingStatus"),getCellValue("V_StatusTaken"),getCellValue("V_AssignedTo"),};
				}
				
				CaseIDSearchPage.get().validateCaseSearch(caseType,searchIndxValArr);
			} else {
				ExtentReportsUtility.log(FAIL, "Case ID","Case ID is Empty or not provided.");
			}
			LoginLogoutPage.get().logoutApplication();
			Thread.sleep(100);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}