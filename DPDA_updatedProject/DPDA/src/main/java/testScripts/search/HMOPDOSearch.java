package testScripts.search;

import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.SC_HMOPDOSearchPage;
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
	 * (Step 3.a: Search Criteria> Case ID> search for above TRN-XXX> Click on TRN-XXX link from table> Click on Begin button fron new TRN-XXX page) If this step not worked then follow
	 * below step
	 * Step 3: Open Chrome browser in inCOgnitive mode(CMM01/rules)> WorkBasket> Click on CaseID filer from table> search above TRN-XXX> Click on TRN-XXX from table> 
	 * Step 4: Scroll down to 'Network Association to an office section> we can find PDO number in PDO column or click HMO PDO button from HMO column> find PDO number in new table 
	*/
public class HMOPDOSearch extends CoreSuperHelper {
	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			String pdoNum=getCellValue("PDONumber");
			
			logExtentReport("HMOPDO Search");
			// 1. Login to the application using LG POC(LGMAUTO001/rules).
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(500);

			// 2.Select Search criteria from left navigation bar and click on hmo pdo search.
			seClick(LeftPanelLinks.get().searchCriteria, "Search Criteria");
			seClick(LeftPanelLinks.get().hmoPDOSearch, "HMO PDO Search");

			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			Thread.sleep(2000);
			// 3.Verify that user can search by when they enter any value fields in pdo."810%"
			seSetText(SC_HMOPDOSearchPage.get().Txt_PDONumber, pdoNum, "PDO Number");
			seClick(CommonElements.get().btnSearch, "Search");
			Thread.sleep(1000);
			// 4. Verify that  user can see all the search results in pdo no, tin.
			
			if(SC_HMOPDOSearchPage.get().isValidationRequired()) {
				System.out.println("validation required");
				SC_HMOPDOSearchPage.get().validateResultSearchData(getCellValue("V_PDONumber"), getCellValue("V_TIN"));
			}else {
				System.out.println("no table data validation required, only table verification");
				SC_HMOPDOSearchPage.get().valDataPresentInTbl();
			}

			// 5.Verify that user can see the results when  hmo pdo  search by click on the create button.
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