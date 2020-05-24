package testScripts.search;

import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.SC_HMOTransferSearchPage;
import utility.CoreSuperHelper;

/**
	 * @author AF11174
	 *Test Case: Verify that user can search and create transaction successfully in
	 * the 'HMO PDO search'
	 * 
	 * Test Data: Create Test Data
	 * -> From NONLGDHMO Data.xlsx> Copy Clinic 'DPS No'
	 * Step: 1. Login(Lgm01/LGMAUTO01)> Search Criteria> Office search> search with above 'CLinic DPS no'
	 * Step 2: Select ' Create Transaction as 'Network Change HMO'> Click on 'Create Transaction' BUtton> Note down TRansaction number (TRN-XXX)
	 * Step 3: Open Chrome browser in in COgnitive mode(CMM01/rules)> WorkBasket> Click on CaseID filer from table> search above TRN-XXX> Click on TRN-XXX from table> 
	 * Step 4: Scroll down to 'Network Association to an office section> we can find PDO number in PDO column or click HMO PDO button from HMO column> find PDO number in new table 
 */
public class HMOTransferSearch extends CoreSuperHelper {
	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			String state=getCellValue("State");
			String conty=getCellValue("County");
			String cty=getCellValue("City");
			String frozenOffStatus=getCellValue("FrozenOfficeStatus");
			String netwrks=getCellValue("Networks");
			String zipCde=getCellValue("ZipCode");
			String zipRng=getCellValue("ZipCodeRange");
			logExtentReport("Create Transaction - HMO Transfer Search");
			
			// 1. Login to the application using LG POC(LGMAUTO001/rules).
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(500);

			// 2.Select Search criteria from left navigation bar and click on hmo pdo transfer search.
			seClick(LeftPanelLinks.get().searchCriteria, "Search Criteria");
			seClick(LeftPanelLinks.get().hmoTrasferSearch, "HMO Transfer Search");
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			Thread.sleep(500);
			
			//3. Verify that user can search by when they enter any value fields in pdo .
			if(!state.equals("")) {
				seSelectValue(SC_HMOTransferSearchPage.get().DropDwn_State, state, "State");
				seClick(SC_HMOTransferSearchPage.get().Lbl_State, "State Label");
			}
			if(!conty.equals("")) {
				seSetText(SC_HMOTransferSearchPage.get().Txt_Country, conty, "County");
			}
			if(!cty.equals("")) {
				seSetText(SC_HMOTransferSearchPage.get().Txt_City, cty, "City");
			}
			if(!frozenOffStatus.equals("")) {
				seSelectText(SC_HMOTransferSearchPage.get().DropDwn_FrozenOfficeStatus, frozenOffStatus, "frozenOffStatus");
				seClick(SC_HMOTransferSearchPage.get().Lbl_State, "State Label");
			}
			Thread.sleep(500);
			if(!netwrks.equals("")) {
				seClick(SC_HMOTransferSearchPage.get().DropDwn_Networksarrow, "Click on Network Dropdown");
				Thread.sleep(500);
				seClick(SC_HMOTransferSearchPage.get().getSpecialistType(netwrks), "Select Network Type");
				seClick(SC_HMOTransferSearchPage.get().Lbl_State, "State Label");
			}
			if(!zipCde.equals("")) {
				seSetText(SC_HMOTransferSearchPage.get().Txt_ZipCode, zipCde, "Zip Code");
			}
			if(!zipRng.equals("")) {
				seSetText(SC_HMOTransferSearchPage.get().Txt_ZipCodeRange, zipRng, "Zip Code Range");
			}
			seClick(CommonElements.get().btnSearch, "Search");
			Thread.sleep(7000);
			
			//	4. Verify that  user can see all the search results in pdo no, tin,dba name, address line 1,2,3,zipcode,phone,state, 
			// city,county,network id,name,status,frozen office status,frozen office status fields.
			
			if(SC_HMOTransferSearchPage.get().isValidationRequired()) {
				System.out.println("validation required");
				SC_HMOTransferSearchPage.get().validateResultSearchData(getCellValue("V_TIN"), getCellValue("V_PDO"));
			}else {
				System.out.println("no table data validation required, only table verification");
				SC_HMOTransferSearchPage.get().valDataPresentInTbl();
			}

			// 5.Verify that user can see the results when  hmo pdo transfer search by click on the create button.
			// Create Transaction option not available on this page
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