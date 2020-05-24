package testScripts.search;
import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.OfficeSearchPage;
import page.ProviderOfficeSearchPage;
import page.ProviderSearchPage;
import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class ProviderOfficeSearch extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			logExtentReport("Provider Office Search");
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().searchCriteria, "Click on Search Criteria");
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().providerOfficeSearch, "Click on Provider Office Search");
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			Thread.sleep(5000);					
			boolean isCaseSearch=false;
			String uniqueSearchByOffice = getCellValue("UniqueSearchByOffice");
			String uniqueSearchByProvider = getCellValue("UniqueSearchByProvider");
			String providerDPSNo=getCellValue("providerDPSNo");
			String searchCriteria = getCellValue("SearchCriteria").trim();
			
			if (uniqueSearchByOffice.trim().equals("Select")) {
				if(uniqueSearchByProvider.trim().equals("Select")) {
					Thread.sleep(500);
					ProviderOfficeSearchPage.get().SearchProviderOfficeByName();
					ProviderOfficeSearchPage.get().providerfieldCaseSearch();


				}

				if (uniqueSearchByProvider.trim().equals("DPS Provider No")) {

					Thread.sleep(100);
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByProvider, uniqueSearchByProvider, "Unique Search by Provider");

					seSetText(ProviderOfficeSearchPage.get().providerDPSNo, providerDPSNo, "provider DPS No");
					ProviderOfficeSearchPage.get().SearchProviderOfficeByName();
					
				}
				if (uniqueSearchByProvider.trim().equals("Individual NPI")) {
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByOffice, uniqueSearchByOffice, "Unique Search by office");

					if(searchCriteria.equals("Case Search")){
						isCaseSearch=true;

						ProviderOfficeSearchPage.get().providerfieldCaseSearch();


					}
				}
			}     

			String clinicCorporateNPI=getCellValue("ClinicCorporateNPI");
			providerDPSNo=getCellValue("providerDPSNo");
			String individualNPI=getCellValue("IndividualNPI");
			String dpsClinic=getCellValue("DPSClinic");

			if (uniqueSearchByOffice.trim().equals("Clinic/Corporate NPI")) {
				if(uniqueSearchByProvider.trim().equals("Select")) {
					seSetText(ProviderOfficeSearchPage.get().DPSclinicOrCorporateNPI, clinicCorporateNPI, "clinicCorporateNPI");
					Thread.sleep(500);
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByProvider, uniqueSearchByProvider, "Unique Search by provider");
					seSetText(ProviderOfficeSearchPage.get().providerDPSNo, providerDPSNo, "provider DPS No");					
					ProviderOfficeSearchPage.get().SearchProviderOfficeByName();				
	
					}
				

				else if (uniqueSearchByProvider.trim().equals("DPS Provider No")) {
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByOffice, uniqueSearchByOffice, "Unique Search by office");
					seSetText(ProviderOfficeSearchPage.get().providerDPSNo, providerDPSNo, "provider DPS No");
					seSetText(ProviderOfficeSearchPage.get().DPSclinicOrCorporateNPI, clinicCorporateNPI, "clinicCorporateNPI");
					
				}
				else if (uniqueSearchByProvider.trim().equals("Individual NPI")) {
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByOffice, uniqueSearchByOffice, "Unique Search by office");
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByProvider, uniqueSearchByProvider, "Unique Search by provider");
					Thread.sleep(2000);
					seSetText(ProviderOfficeSearchPage.get().DPSclinicOrCorporateNPI, clinicCorporateNPI, "clinicCorporateNPI");
					seSetText(ProviderOfficeSearchPage.get().type1NPIIndividual, individualNPI, "individualNPI");
					
				}     
			}

			if (uniqueSearchByOffice.trim().equals("DPS Clinic No")) {
				if(uniqueSearchByProvider.trim().equals("Select")) {
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByOffice, uniqueSearchByOffice, "Unique Search by office");
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByProvider, uniqueSearchByProvider, "Unique Search by provider");
					seSetText(ProviderOfficeSearchPage.get().providerDPSNo, providerDPSNo, "provider DPS No");
					
				}

				else	if (uniqueSearchByProvider.trim().equals("DPS Provider No")) {
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByOffice, uniqueSearchByOffice, "Unique Search by office");
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByProvider, uniqueSearchByProvider, "Unique Search by provider");
					seSetText(ProviderOfficeSearchPage.get().ClinicDPSNo, dpsClinic, "ClinicDPSNo");
					seSetText(ProviderOfficeSearchPage.get().providerDPSNo, providerDPSNo, "provider DPS No");
					
				}
				else	if (uniqueSearchByProvider.trim().equals("Individual NPI")) {
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByOffice, uniqueSearchByOffice, "Unique Search by office");
					seSelectText(ProviderOfficeSearchPage.get().uniqueSearchByProvider, uniqueSearchByProvider, "Unique Search by provider");
					seSetText(ProviderOfficeSearchPage.get().ClinicDPSNo, dpsClinic, "ClinicDPSNo");
					seSetText(ProviderOfficeSearchPage.get().type1NPIIndividual, individualNPI, "individualNPI");
					
				}  
			}
			if(searchCriteria.equals("Case Search")){
				isCaseSearch=true;
				ProviderOfficeSearchPage.get().providerfieldCaseSearch();
			}

			seClick(CommonElements.get().btnSearch, "Click on the Search Button");
			Thread.sleep(15000);

			boolean transactionSuccess = false;

			if (isCaseSearch) {
				// Validate data in case search table
				ProviderOfficeSearchPage.get().validateCaseSearchData(getCellValue("V_Case_Id"),
						getCellValue("V_Case_Status"), getCellValue("V_Transaction"), getCellValue("V_WorkGroup"),
						getCellValue("V_Working_Status"), getCellValue("V_Status_Taken"), getCellValue("V_Assigned_To"),
						getCellValue("V_DPSClinicNo"), getCellValue("V_TaxIDNo"), getCellValue("V_DBAName"),
						getCellValue("V_DirectoryAddress1"), getCellValue("V_City"),getCellValue("V_Zip"),getCellValue("V_Reopen"));

			} else {
				// Validate data DPS Search Table
				if (ProviderOfficeSearchPage.get().validateAndSelectDPSsearch(getCellValue("V_First_Name"),
						getCellValue("V_Middle_Intial"), getCellValue("V_Last_Name"), getCellValue("V_Suffix"),
						getCellValue("V_Specialty"),getCellValue("V_Degree"),getCellValue("V_IndividualNPI"),getCellValue("V_ProviderStatus"),
						getCellValue("V_ProviderDPSNo")))  {
					transactionSuccess = true;
				}
			}
			if(ProviderSearchPage.get().selectTransaction.isDisplayed()) {		
				transactionSuccess = true;
				if (transactionSuccess) {

					Thread.sleep(2000);
					seSelectValue(ProviderSearchPage.get().selectTransaction,
							DPDAUtils.getTransactionValue(getCellValue("SelectTransaction")), "Select Transaction");
					Thread.sleep(2000);
					if (seClick(CommonElements.get().btnCreateTransaction, "Create Transaction")) {
						clickDPDAElmnt(CommonElements.get().btnCreateTransaction);
						Thread.sleep(15000);
						System.out.println("Clicked Create Transaction Button");
						if(ProviderOfficeSearchPage.get().transactionID.isDisplayed()) {
							String transactionId = ProviderOfficeSearchPage.get().transactionID.getText();
							ExtentReportsUtility.log(ApplicationConstants.INFO, "Transaction Created",
									"Transaction Created Id :" + transactionId);
							System.out.println("TransactionId : " + transactionId);
						}
					}
				}
			}
			else {
				System.out.println("Transaction button is Not Displayed");
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

