package testScripts.search;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.XMLJSONUtils;

import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.OfficeSearchPage;
import page.TransactionPage;
import utility.CoreSuperHelper;
import utility.DPDAUtils;
import utility.RESTAPICalls;

public class OfficeSearch extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			
			logExtentReport("Clinic TIN Change");
			LoginLogoutPage.get().loginApplication();
			seClick(false,LeftPanelLinks.get().searchCriteria, "Search Criteria","Click Menu Link Search Criteria");
			seClick(LeftPanelLinks.get().officeSearch, "Office Search");
			
			seWaitForElementVisibility(5000,CommonElements.get().iFrameFirstTab);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			
			String clinicDPSNo = getCellValue("CLINIC_DPS_NO");
			String taxID = getCellValue("TAX_ID");
			String dbaName = getCellValue("DBA_NAME");
			String state = getCellValue("STATE");
			String city = getCellValue("CITY");
			String zipCode = getCellValue("ZIP");
			String county = getCellValue("COUNTY");
			String phoneNo = getCellValue("Phone_Number");
			
			String uniqueSearcyBy = getCellValue("Unique_Searcy_By");
			
			boolean isUniqueSearchByClinicDPSNo = false;
			// Unique Search By = Clinic DPS No
			if (uniqueSearcyBy!=null && !uniqueSearcyBy.trim().equals("") && !uniqueSearcyBy.trim().equals("Select")) {
				Thread.sleep(100);
				seSelectText(CommonElements.get().selUniqueSearchBy, "Clinic DPS No","Unique Search by");
				Thread.sleep(500);
				
				isUniqueSearchByClinicDPSNo = true;
				
				if (clinicDPSNo!=null && !clinicDPSNo.trim().equals("")) 	
					seSetText(OfficeSearchPage.get().txtClinicDPSNo, clinicDPSNo, "Clinic DPS No.");
			} 
			
			
			String searchCriteria = getCellValue("Search_Criteria").trim();
			
			boolean isCaseSearch = false;
			// Search Criteria = Case Search
			if (searchCriteria!=null && searchCriteria.trim().equals("Case Search")) {
				seSelectText(OfficeSearchPage.get().selSearchCriteria, searchCriteria,"Select Search Criteria");
				isCaseSearch = true;
				String beginDate = getCellValue("Begin_Date");
				if (beginDate!=null && !beginDate.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtBeginDate, beginDate, "Begin_Date");
				
				String endDate = getCellValue("End_Date");
				if (endDate!=null && !endDate.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtEndDate, endDate, "End_Date");
				
				String caseStatus = getCellValue("Case_Status");
				if (caseStatus!=null && !caseStatus.trim().equals("")) 			
					seSelectText(OfficeSearchPage.get().selCaseStatus, caseStatus, "Select Case Status");
			}
			
			// If Unique Search By is not Clinic DPS Number.
			if (!isUniqueSearchByClinicDPSNo) {
				
				if (taxID!=null && !taxID.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtTaxIdNo, taxID, "Tax Id");
				
				String irsName = getCellValue("IRS_Name");
				if (irsName!=null && !irsName.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtIRSName, irsName, "IRS Name");
				
				if (dbaName!=null && !dbaName.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtDBAName, dbaName, "DBA Name");
				
				String clinicNPI = getCellValue("Clinic_Corporate_NPI");
				if (clinicNPI!=null && !clinicNPI.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtClinicCorpNPI, clinicNPI, "Clinic/Corporate NPI");
				
				if (phoneNo!=null && !phoneNo.trim().equals("")) 			
					seSetText(OfficeSearchPage.get().txtPhoneNum, phoneNo, "Phone Number");
				
				String addressType = getCellValue("Address_Type");
				if (addressType!=null && !addressType.trim().equals("") && !addressType.trim().equals("Select")) {
					seSelectValue(OfficeSearchPage.get().selAddressType, DPDAUtils.getAddressTypeValue(addressType), "Address Type");
					
					String addressLine1 = getCellValue("Address_Line_1");
					if (addressLine1!=null && !addressLine1.trim().equals("")) 			
						seSetText(OfficeSearchPage.get().txtAddressLine1, addressLine1, "Address Line 1");
					
					String addressLine2 = getCellValue("Address_Line_2");
					if (addressLine2!=null && !addressLine2.trim().equals("")) 			
						seSetText(OfficeSearchPage.get().txtAddressLine2, addressLine2, "Address Line 2");
					
					String addressLine3 = getCellValue("Address_Line_3");
					if (addressLine3!=null && !addressLine3.trim().equals("")) 			
						seSetText(OfficeSearchPage.get().txtAddressLine3, addressLine3, "Address Line 3");
					
					if (city!=null && !city.trim().equals("")) 			
						seSetText(OfficeSearchPage.get().txtCity, city, "City");
					
					if (state!=null && !state.trim().equals("")) 			
						seSelectText(OfficeSearchPage.get().selState, state, "State");
					
					if (county!=null && !county.trim().equals("")) 			
						seSetText(OfficeSearchPage.get().txtCounty, county, "County");
					
					if (zipCode!=null && !zipCode.trim().equals("")) 			
						seSetText(OfficeSearchPage.get().txtZipCode, zipCode, "Zip");
				}
			
			}
			clickDPDAElmnt(CommonElements.get().btnSearch);
			seClick(CommonElements.get().btnSearch, "Search");
			Thread.sleep(5000);
			
			// Validating Results and Create Transaction
			
			boolean transactionSuccess = false;
			
			if (isCaseSearch) {
				transactionSuccess = OfficeSearchPage.get().validateAndSelectTransaction(getCellValue("V_Case_Id"), getCellValue("V_Case_Status"),
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
					seSelectValue(OfficeSearchPage.get().transactionDrpDown, DPDAUtils.getTransactionValue(getCellValue("Create_Transaction")), "Select Transaction");
					if (seClick(CommonElements.get().btnCreateTransaction, "Create Transaction")) {
						clickDPDAElmnt(CommonElements.get().btnCreateTransaction);
						System.out.println("Clicked Create Transaction Button");
						seWaitForElementLoad(OfficeSearchPage.get().transactionCreated);
						String transactionId = OfficeSearchPage.get().transactionCreatedId.getText();
						ExtentReportsUtility.log(INFO, "Transaction Created", "Transaction Created Id :"+transactionId);
						System.out.println("TransactionId : "+ transactionId);
						if (OfficeSearchPage.get().isClickOnTransaction(transactionId)) {
							transactionSuccess = true;
						}
					}
				}
			}
			
			if (transactionSuccess) {
				Thread.sleep(1000);
				getWebDriver().switchTo().defaultContent();
				// Wait for Transaction Frame to Load and continue next steps
			//	seWaitForElementVisibility(5000,CommonElements.get().iFrameSecondTab);
				seSwitchToFrame(CommonElements.get().iFrameSecondTab);
				
				if (isElementPresent(TransactionPage.get().trnAssignmentBlank)) {
					ExtentReportsUtility.log(ApplicationConstants.FAIL, "Transaction","Invalid Transaction", true);
				} else {
					String currentClinicTIN = seGetText(TransactionPage.get().txtIRSTIN);
					ExtentReportsUtility.log(INFO, "Clinic TIN", "Previous Clinic TIN :"+ currentClinicTIN);
					// Update Clinic Tin
					String newClinicTIN = getCellValue("New_Clinic_Tin");
					if (newClinicTIN!=null && !newClinicTIN.trim().equals("")) {
						seSetText(TransactionPage.get().txtIRSTIN, newClinicTIN,"Update Clinic TIN");
					}
					// Validate new CLinic Tin is not same as Previous Tin
					if (currentClinicTIN.trim().equals(newClinicTIN)) {
						ExtentReportsUtility.log(FAIL, "Clinic TIN Update", "Trying to Update with same old Clinic TIN:"+ newClinicTIN);
					}
	
					String effectiveDate = "";
					seSetText(TransactionPage.get().effectiveDate, effectiveDate,"Setting Effective date to blank.");
	
					if (!seGetText(TransactionPage.get().effectiveDate).trim().equals("")) {
						ExtentReportsUtility.log(FAIL, "Effective Date", "Effective Date Provided : "+ seGetText(TransactionPage.get().effectiveDate));	
					}
					String workingStatus = getCellValue("Transaction_Working_Status");
					seSelectText(TransactionPage.get().workingStatus, workingStatus, "Working Status Selected as '"+workingStatus+"'");
					
					String dpsClinicNo = seGetText(TransactionPage.get().dpsClinicNo);
					
					clickDPDAElmnt(TransactionPage.get().saveBtn);
					
					if (seGetText(TransactionPage.get().effectiveDate).equals("")) {
						OfficeSearchPage.get().clickOnPopupConfirmButton("ConfirmDate");
					}
					
					// Validate in Backend Swagger
					
					String jsonResponse = RESTAPICalls.getClinicDetails(dpsClinicNo);
					String effectiveToDate = XMLJSONUtils.getStringValue(jsonResponse, "Effective.To");
					
					if ((effectiveToDate != null && effectiveToDate.trim().equals("null")) || (effectiveToDate == null)) {
						ExtentReportsUtility.log(PASS, "Effective Date", "Effective Date  from Back-End : "+effectiveToDate);
					} else {
						ExtentReportsUtility.log(FAIL, "Effective Date", "Effective Date  from Back-End : "+effectiveToDate);
					}
					
					
				}	
				
				
			}
			
			LoginLogoutPage.get().logoutApplication();
			Thread.sleep(100);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}