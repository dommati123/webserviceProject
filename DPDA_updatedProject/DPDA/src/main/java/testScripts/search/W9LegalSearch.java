package testScripts.search;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.OfficeSearchPage;
import page.SC_W9LegalSearchPage;
import utility.CoreSuperHelper;
import utility.DPDAUtils;

/**
 	 * @author AF11174
  	 * Test Case: Verify that user can search and create transaction successfully in
	 * the 'W9 Legal Search'
	 * 
	 * Test Data: Create Test Data
	 * Step: 1. Login(Lgm01/LGMAUTO01)> Large Group> AdHoc Par Report> Select
	 * 'Aspin' in Large Group name drop down > click on Done button 2. Click on
	 * excel image to download> open excel file( Location details sheet)> search for test data
 */
public class W9LegalSearch extends CoreSuperHelper {
	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			String uniquSrchBy=getCellValue("UniqueSearchBy");
			String irsName=getCellValue("IRSName");
			String tinNmr=getCellValue("TINNumber");
			String srchCriteria=getCellValue("SearchCriteria");
			String dpsLegNbr=getCellValue("DPSLegalNo");
			String startDate= getCellValue("BeginDate");
			String endDate= getCellValue("EndDate");
			String caseStatus= getCellValue("CaseStatus");
			String selTrans=getCellValue("SelectTransaction");
			logExtentReport("Create Transaction - W9 Legal Search");
			// 1. Login to the application using LG POC(LGMAUTO001/rules).
			LoginLogoutPage.get().loginApplication();

			// 2.Select Search criteria from left navigation bar and click on w9 legal
			// search.
			seClick(LeftPanelLinks.get().searchCriteria, "Search Criteria");
			seClick(LeftPanelLinks.get().w9LegalSearch, "W9 Legal Search");
			seWaitForElementVisibility(5000,CommonElements.get().iFrameFirstTab);
			Thread.sleep(500);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			
	
			boolean isUniqueSearchByClinicDPSNo = false;
			// Unique Search By = Clinic DPS No
			if (uniquSrchBy!=null && !uniquSrchBy.trim().equals("") && !uniquSrchBy.trim().equals("Select")) {
				Thread.sleep(100);
				seSelectText(CommonElements.get().selUniqueSearchBy, uniquSrchBy,"Unique Search by");
				Thread.sleep(500);
				
				isUniqueSearchByClinicDPSNo = true;
				
				if (dpsLegNbr!=null && !dpsLegNbr.trim().equals("")) 	
					seSetText(SC_W9LegalSearchPage.get().Txt_DPSLegalNo, dpsLegNbr, "DPS Legal No.");
			} 
			
			
			boolean isCaseSearch = false;
			// Search Criteria = Case Search
			if (srchCriteria!=null && srchCriteria.trim().equals("Case Search")) {
				seSelectText(SC_W9LegalSearchPage.get().sel_searchCriteria, srchCriteria,"Select Search Criteria");
				isCaseSearch = true;
				if (startDate!=null && !startDate.trim().equals("")) 			
					seSetText(SC_W9LegalSearchPage.get().txt_beginDate, startDate, "Begin Date");
				
				if (endDate!=null && !endDate.trim().equals("")) 			
					seSetText(SC_W9LegalSearchPage.get().txt_endDate, endDate, "End Date");
				
				if (caseStatus!=null && !caseStatus.trim().equals("")) 			
					seSelectText(SC_W9LegalSearchPage.get().sel_CaseStatus, caseStatus, "Select Case Status");
			}
			
			
			// If Unique Search By is not Clinic DPS Number.
			if (!isUniqueSearchByClinicDPSNo) {
				
				if (irsName!=null && !irsName.trim().equals("")) 			
					seSetText(SC_W9LegalSearchPage.get().Txt_IRSName, irsName, "IRS Name");
				
				if (tinNmr!=null && !tinNmr.trim().equals("")) 			
					seSetText(SC_W9LegalSearchPage.get().Txt_TaxIdNo, tinNmr, "TIN Number");
			}
			seClick(CommonElements.get().btnSearch, "Search");
			Thread.sleep(2000);
			// 3.Verify that user can search by when they enter vany value in fields IRS
			// name,TIN.
			
			// 4. Verify that user can see all the search results in IRS Name,TIN,TIN
			// Type,DPS Legal No, TIN status,DBA name,large group name,clinics status,hmo pdo
			// office,delegated cred,
			// Sharing Space, Diversify, Directory Address 1,city,zip,phone,state.
			// 5.Verify that user see search results when they choose this fields Dps legal
			// no, case search.s

			boolean createTransaction=false;
			if(SC_W9LegalSearchPage.get().isValidationRequired()) {
			if (isCaseSearch) {
				// Validate data in case search table
				 SC_W9LegalSearchPage.get().validateCaseSearchData(getCellValue("V_CaseID"), getCellValue("V_CaseStatus"),
						getCellValue("V_Transaction"), getCellValue("V_WorkGroup"), getCellValue("V_WorkingStatus"),
						getCellValue("V_StatusTaken"), getCellValue("V_AssginedTo"), getCellValue("V_IRSName"), 
						getCellValue("V_TIN"),getCellValue("V_TINType"));
			}else {
				// Validate data in DPS Search
				if (SC_W9LegalSearchPage.get().validateAndSelectOffice(getCellValue("V_IRSName"), getCellValue("V_TIN"),
							getCellValue("V_TINType"), getCellValue("V_DPSLegalNo"), getCellValue("V_TINStatus"))){
					createTransaction=true;
				}
			}
			}else {
				System.out.println("no table data validation only table verification");
				if (isCaseSearch) {
					SC_W9LegalSearchPage.get().valDataPresentInTbl(true);
				}else {
					SC_W9LegalSearchPage.get().valDataPresentInTbl(false);
					createTransaction=true;
				}
			}
			
			// 6.Verify that user can successfully created transaction in the W9 legal
			// search by click on the create button.
			if(createTransaction) {
				Thread.sleep(1000);
				seSelectValue(SC_W9LegalSearchPage.get().DropDown_SelectTransactionForDCLegal, DPDAUtils.getTransactionValue(selTrans), "Select Transaction");
				if (seClick(CommonElements.get().btnCreateTransaction, "Create Transaction")) {
					clickDPDAElmnt(CommonElements.get().btnCreateTransaction);
					Thread.sleep(1000);
					System.out.println("Clicked Create Transaction Button");
					seWaitForElementLoad(OfficeSearchPage.get().transactionCreated);
					String transactionId = OfficeSearchPage.get().transactionCreatedId.getText();
					ExtentReportsUtility.log(ApplicationConstants.INFO, "Transaction Created", "Transaction Created Id :"+transactionId);
					System.out.println("TransactionId : "+ transactionId);
					
				}
			}
			LoginLogoutPage.get().logoutApplication();
			seCloseBrowser();
		} catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Verify script executed succussfully",
					"Script is not executed succussfully, check snapshot", true);
			setResult(false);
			e.printStackTrace();
		}
	}

}