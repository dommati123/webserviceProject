package testScripts.search;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.OfficeSearchPage;
import page.ProviderSearchPage;
import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class ProviderSearch extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			String uniqueSearchBy = getCellValue("UniqueSearchBy");
			
			logExtentReport("Provider Search");
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().searchCriteria, "Click on Search Criteria");
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().providerSearch, "Click on Provider Search");
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			Thread.sleep(5000);

			boolean isUniqueSearchByProviderDPSNo = false;
			if (uniqueSearchBy != null && !uniqueSearchBy.trim().equals("")
					&& !uniqueSearchBy.trim().equals("Select")) {
				String providerDPSNo = getCellValue("ProviderDPSNo");
				String individualNPI = getCellValue("IndividualNPI");
				Thread.sleep(100);
				seSelectText(CommonElements.get().selUniqueSearchBy, uniqueSearchBy, "Unique Search by");
				Thread.sleep(500);

				isUniqueSearchByProviderDPSNo = true;

				if (providerDPSNo != null && !providerDPSNo.trim().equals(""))
					seSetText(ProviderSearchPage.get().DPSProviderNo, providerDPSNo, "provider DPS No");

				if (individualNPI != null && !individualNPI.trim().equals(""))
					seSetText(ProviderSearchPage.get().individualNPI, individualNPI, "individualNPI");
			}
			if (!isUniqueSearchByProviderDPSNo) {
				String firstName = getCellValue("FirstName");
				String middleIntial = getCellValue("MiddleIntial");
				String lastName = getCellValue("LastName");	
				String suffix = getCellValue("Suffix");
				String specialty = getCellValue("Specialty");
				String ssn = getCellValue("SSN");
				String licenseNo = getCellValue("LicenseNo");
				String licState = getCellValue("LicenseState");	
				ProviderSearchPage.get().searchProviderByName(firstName, middleIntial, lastName, suffix, specialty, ssn,
						licenseNo, licState);
			}
			String searchCriteria = getCellValue("Search_Criteria").trim();
			boolean isCaseSearch = false;
			// Search Criteria = Case Search
			if (searchCriteria != null && searchCriteria.trim().equals("Case Search")) {
				seSelectText(ProviderSearchPage.get().searchCriteria, searchCriteria, "Select Search Criteria");
				isCaseSearch = true;
				String beginDate = getCellValue("Begin_Date");
				if (beginDate != null && !beginDate.trim().equals(""))
					seSetText(ProviderSearchPage.get().beginDate, beginDate, "Begin_Date");

				String endDate = getCellValue("End_Date");
				if (endDate != null && !endDate.trim().equals(""))
					seSetText(ProviderSearchPage.get().endDate, endDate, "End_Date");

				String caseStatus = getCellValue("Case_Status");
				if (caseStatus != null && !caseStatus.trim().equals(""))
					seSelectText(ProviderSearchPage.get().caseStatus, caseStatus, "Select Case Status");
			}

			seClick(CommonElements.get().btnSearch, "Click on the Search Button");
			Thread.sleep(50000);

			boolean transactionSuccess = false;
			if (isCaseSearch) {
				// Validate data in case search table
				ProviderSearchPage.get().validateAndSelectTransaction(getCellValue("V_Case_Id"),
						getCellValue("V_Case_Status"), getCellValue("V_Transaction"), getCellValue("V_WorkGroup"),
						getCellValue("V_Working_Status"), getCellValue("V_Status_Taken"), getCellValue("V_Assigned_To"),
						getCellValue("V_Provider_DPS_No"), getCellValue("V_First_Name"), getCellValue("V_Last_Name"),
						getCellValue("V_Middle_Intial"), getCellValue("V_NPI"));

			} else {
				// Validate data DPS Search Table
				if (ProviderSearchPage.get().validateAndSelectOffice(getCellValue("V_First_Name"),
						getCellValue("V_Middle_Intial"), getCellValue("V_Last_Name"), getCellValue("V_Suffix"),
						getCellValue("V_Specialty"), getCellValue("V_Individual_NPI"), getCellValue("V_License_Status"),
						getCellValue("V_Provider_Status"), getCellValue("V_Provider_DPS_NO"))) {
					transactionSuccess = true;
				}
			}
			if (transactionSuccess) {
				Thread.sleep(1000);							
				seSelectValue(ProviderSearchPage.get().selectLargeGroup, getCellValue("SelectLargeGroup").trim(),
						"slect dropdown value from selectLargeGroup");
				Thread.sleep(2000);		
	
				seSelectValue(ProviderSearchPage.get().selectTransaction,
						DPDAUtils.getTransactionValue(getCellValue("SelectTransaction")), "Select Transaction");
				Thread.sleep(5000);
				if (seClick(CommonElements.get().btnCreateTransaction, "Create Transaction")) {
					clickDPDAElmnt(CommonElements.get().btnCreateTransaction);
					Thread.sleep(5000);
					System.out.println("Clicked Create Transaction Button");
					seWaitForElementLoad(OfficeSearchPage.get().transactionCreated);
					String transactionId = ProviderSearchPage.get().transactionID.getText();
					ExtentReportsUtility.log(ApplicationConstants.INFO, "Transaction Created",
							"Transaction Created Id :" + transactionId);
					System.out.println("TransactionId : " + transactionId);

				}
			}

			LoginLogoutPage.get().logoutApplication();
			Thread.sleep(2000);
			seCloseBrowser();
		} catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Verify script executed succussfully",
					"Script is not executed succussfully, check snapshot", true);
			e.printStackTrace();
		}
	}
}
