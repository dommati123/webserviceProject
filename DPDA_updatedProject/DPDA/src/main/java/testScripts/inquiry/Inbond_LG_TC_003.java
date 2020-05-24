package testScripts.inquiry;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.CreateContactPage;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.OfficeSearchPage;
import page.SC_OfficeSearch;
import page.TransactionPage;
import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class Inbond_LG_TC_003 extends CoreSuperHelper {
/**Verify that user updates the Working Status as 'Completed', 
 * if inquiry does not require any change to DPS TC_003

	Pre- Requisites:
	* LG name not identified
	* Information not found from search capabilities
	* Contact Information not created.
	* Inquiry completed and does requires changes to DPS"
	
	Test Data: We can't use same test data after submission
	Create Test Data Step: 
	1. Login(Lgm01/LGMAUTO01)> Large Group> AdHoc Par Report> Select 'Aspin' in Large Group name drop down
	> click on Done button
	2. Click on excel image to download> open excel file( Location details sheet)> search for 'Clinic DPS No' column to get test data
**/
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
			
			String dpsNo=getCellValue("Clinic_DPSNo").trim();
			String searchCriteria = getCellValue("SearchCriteria").trim();
			
			// 1. Login to application using LG POC
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(500);
			
			// 2. Select search criteria-> Clinic Search
			seClick(LeftPanelLinks.get().searchCriteria, "Search Criteria");
			seClick(LeftPanelLinks.get().officeSearch, "Office Search");
			
			seWaitForElementVisibility(5000,CommonElements.get().iFrameFirstTab);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			
			//3. Verify that the user enters valid 'Clinic DPS No.' and click on Search #608624
			seSelectText(CommonElements.get().selUniqueSearchBy,"Clinic DPS No", "Select Clinic DPS No from drop down");
			Thread.sleep(500);
			seSetText(OfficeSearchPage.get().txtClinicDPSNo, dpsNo, "Verify and enter Clinic DPS No");
			
			boolean isCaseSearch = false;
			// Search Criteria = Case Search
			if (searchCriteria!=null && searchCriteria.trim().equals("Case Search")) {
				seSelectText(OfficeSearchPage.get().selSearchCriteria, searchCriteria,"Select Search Criteria");
				Thread.sleep(500);
				isCaseSearch = true;
				String beginDate = getCellValue("BeginDate");
				if (beginDate!=null && !beginDate.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtBeginDate, beginDate, "BeginDate");
				
				String endDate = getCellValue("EndDate");
				if (endDate!=null && !endDate.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtEndDate, endDate, "EndDate");
				
				String caseStatus = getCellValue("CaseStatus");
				if (caseStatus!=null && !caseStatus.trim().equals("")) 			
					seSelectText(OfficeSearchPage.get().selCaseStatus, caseStatus, "Select Case Status");
			}
			
			seClick(CommonElements.get().btnSearch, "Search");
			Thread.sleep(5000);
			
			//4. Verify that the Search displays result as per the search criteria
			boolean transactionSuccess = false;
			if (isCaseSearch) {
				// need to check clicking row-1
				transactionSuccess = OfficeSearchPage.get().validateAndSelectLGTransaction(getCellValue("V_Case_Id"), getCellValue("V_Case_Status"),
						getCellValue("V_Transaction"), getCellValue("V_WorkGroup"), getCellValue("V_Working_Status"),
						getCellValue("V_Status_Taken"), getCellValue("V_Assigned_To"), getCellValue("V_Clinic_DPS_No"), 
						getCellValue("V_Tax_ID"),getCellValue("V_DBA_Name"),getCellValue("V_Directory_Address_1"),
						getCellValue("V_City"), getCellValue("V_Zip"));
				
			} else {
				// DPS Search
				if (OfficeSearchPage.get().validateAndSelectOffice(getCellValue("V_Clinic_DPS_No"), getCellValue("V_Tax_ID"),
							getCellValue("V_Office_Status"), getCellValue("V_DBA_Name"), getCellValue("V_Large_Group_Name"),
							getCellValue("V_Legal_Name"), getCellValue("V_Directory_Address_1"), getCellValue("V_Directory_Address_2"), 
							getCellValue("V_City"), getCellValue("V_Zip"), getCellValue("V_County"), getCellValue("V_Phone"))){
					//5.Verify user can select 'LG Inquiry' from create transaction drop down
					seSelectValue(OfficeSearchPage.get().transactionDrpDown, DPDAUtils.getTransactionValue(getCellValue("Create_Transaction")), "Select Transaction");
					if (seClick(CommonElements.get().btnCreateTransaction, "Create Transaction")) {
						//6.Verify that inquiry case is created on clicking 'create transaction' button
						clickDPDAElmnt(CommonElements.get().btnCreateTransaction);
						System.out.println("Clicked Create Transaction Button");
						seWaitForElementLoad(OfficeSearchPage.get().transactionCreated);
						String transactionId = OfficeSearchPage.get().transactionCreatedId.getText();
						ExtentReportsUtility.log(ApplicationConstants.INFO, "Transaction Created", "Transaction Created Id :"+transactionId);
						System.out.println("TransactionId : "+ transactionId);
						if (OfficeSearchPage.get().isClickOnTransaction(transactionId)) {
							transactionSuccess = true;
						}
					}
				}
			}
			
			
//			boolean test=false;
//			 test=seWinCheckForElement(SC_OfficeSearch.get().RadioBtn_SearchResult, "Verify Search dispalys Result");
//			System.out.println("Element found?:"+test);
//			if(test) {
//				log(1, "Verify that the Search displays result as per the search criteria", "Search displays result as per the search criteria");
//			}else {
//				log(0, "Verify that the Search displays result as per the search criteria", "Search NOT displays result as per the search criteria");
//			}
//			seClick(SC_OfficeSearch.get().RadioBtn_SearchResult, "Select Radio button");
			
//			//5.Verify user can select 'LG Inquiry' from create transaction drop down
//			try {
//			seSelectText(SC_OfficeSearch.get().DropDown_SelectTransaction, "Large Group Inquiry", "Select 'Large Group Inquiry' from drop down");
//			}catch(Exception e) {
//				
//			}
			
//			//6.Verify that inquiry case is created on clicking 'create transaction' button
			
//			seClick(CommonElements.get().btnCreateTransaction, "Verify and Click Create Transaction");
//			 test=seWinCheckForElement(SC_OfficeSearch.get().Lnk_NewTransaction, "Verify that inquiry case is created on clicking 'create transaction' button");
//				System.out.println("Element found?:"+test);
//				if(test) {
//					log(1, "Verify that inquiry case is created on clicking 'create transaction' button", "inquiry case is created");
//				}else {
//					log(0, "Verify that inquiry case is created on clicking 'create transaction' button", "inquiry case is NOT created");
//				}
			// As per Tuhin, Step 7 and 8 we can skip as it for invalid data
			
			// 9. Verify that the 'LG Inquiry' case is created with Working Status as 'Review Required' and status taken as blank
				
			if (transactionSuccess) {
				Thread.sleep(1000);
				getWebDriver().switchTo().defaultContent();
				seSwitchToFrame(CommonElements.get().iFrameSecondTab);
				
				if (isElementPresent(TransactionPage.get().trnAssignmentBlank)) {
					ExtentReportsUtility.log(ApplicationConstants.FAIL, "Transaction","Invalid Transaction", true);
				} else {
					seScrollForWebElement(TransactionPage.get().workingStatus);
					//10. Verify that the system populates the due date
					TransactionPage.get().verifyStatusFields();
//					String effectiveDate = seGetElementTextBoxValue(TransactionPage.get().effectiveDate);
//					System.out.println("effectiveDate:"+effectiveDate);
					
					//11. Verify that user reviews information and conducts search from Search within transaction section
					//12. Verify that information is not found
					TransactionPage.get().verifySearchWithinTransactionTbl("Office Search,W9 Legal Search,Case ID Search,Provider Search,LG Office Search,Provider Office Search,HMO PDO Search");
					
					//13. Verify that the user updates the Working Status as 'Completed' and saves the transaction
					seScrollForWebElement(TransactionPage.get().workingStatus);
					Thread.sleep(400);
					seSelectText(TransactionPage.get().workingStatus, "Completed", "Working Status Selected as 'Completed'");
					Thread.sleep(400);
					seScrollForWebElement(TransactionPage.get().saveBtn);
					Thread.sleep(700);
//					clickDPDAElmnt(TransactionPage.get().saveBtn);
					seClick(TransactionPage.get().saveBtn,"Save");
					seClick(TransactionPage.get().saveBtn,"Save");
					Thread.sleep(3000);
					//14. Verify that an error message is generated as "Contact Information must be entered".
					String errMsg=seGetText(TransactionPage.get().errMessage).trim();
					System.out.println(errMsg);
					if(errMsg.equals("Contact information must be added")) {
						ExtentReportsUtility.log(PASS, "Validate Error message","Expected Error message displayed", true);
						
						//15. Verify that the user creates a contact Information.
						
						seClick(TransactionPage.get().contactInformation,"Add Contact Information");
						Thread.sleep(400);
						//Fill the Contact Information
						CreateContactPage.get().FillContactInfo();
						//Fill the Inbound Contact details
						TransactionPage.get().FillInboundContact();
						//Fill the Outbound Contact details
						TransactionPage.get().FillOutboundContact();
						// Add note
						CreateContactPage.get().addNotes();
						
						//16. Verify that the user attaches a document within the transaction to the email.
						//---------------- Need to check
						
						//17. Verify user saves the transaction
						seClick(TransactionPage.get().btnSubmit, "Save");
						Thread.sleep(6000);
						
						
					}else {
						ExtentReportsUtility.log(FAIL, "Validate Error message","Expected Error message NOT displayed", true);
					}
					
					
					
					
					
					
					
					
					
					
					
				}
				
			}
			
			
//				seClick(SC_OfficeSearch.get().Lnk_NewTransaction, "New created transaction link");
//				
//				seSwitchToFrame("Attachment Data");
//				Thread.sleep(500);
//				String defaultDate=SC_OfficeSearch.get().Txt_Status_DataComplete.getText();
//				System.out.println("Date:"+defaultDate);
////				String workStatus=seGetElementValue(SC_OfficeSearch.get().DropDown_Status_WorkingStatus);
//				String workStatus=SC_OfficeSearch.get().DropDown_Status_WorkingStatus.getText();
//				System.out.println("Working Status:"+workStatus);
				
//				String statusToken=seGetElementValue(SC_OfficeSearch.get().DropDown_Status_StatusTaken);
//				String statusToken=SC_OfficeSearch.get().DropDown_Status_StatusTaken.getAttribute("value");
//				System.out.println("statusToken:"+statusToken);
				
//			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}