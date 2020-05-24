package testScripts.search;

import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.LG_GroupDetailsPage;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.SC_LGSearchPage;
import utility.CoreSuperHelper;

/**
 	 * @author AF11174
 	 * Test Case: Verify that user can search and create transaction successfully in
	 * the 'LG search'
	 * 
	 * Test Data: Create Test Data
	 * Step: 1. Login(Lgm01/LGMAUTO01)> Large Group> AdHoc Par Report> Select
	 * 'Aspin' in Large Group name drop down > click on Done button 2. Click on
	 * excel image to download> open excel file( Location details sheet)> search for test data
 */
public class LGSearch extends CoreSuperHelper {
	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			String lgName=getCellValue("LGName");
			logExtentReport("LG Search");
			// 1. Login to the application using LG POC(LGMAUTO001/rules).
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(500);

			// 2.Select Search criteria from left navigation bar and click on lg search.
			seClick(LeftPanelLinks.get().searchCriteria, "Search Criteria");
			seClick(LeftPanelLinks.get().lgSearch, "LG Search");
			Thread.sleep(1000);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			
			//3.Verify that user can search by when they enter any value in fields  LG name.
			seSetText(SC_LGSearchPage.get().Txt_LGName, lgName, "LG Name");
			Thread.sleep(500);
			seClick(SC_LGSearchPage.get().Lbl_LGName, "LG Name Label");
			seClick(CommonElements.get().btnSearch, "Search");
			Thread.sleep(7000);
			
			// 4. Verify that user see search results in these fields tin clinic details.
			
			String searchTableTINNumbers =SC_LGSearchPage.getTableData("TIN","LGSearch");
			if(SC_LGSearchPage.get().isValidationRequired()) {
				System.out.println("validation required");
				SC_LGSearchPage.get().validateResultSearchData(getCellValue("V_TIN"));
			}else {
				System.out.println("no table data validation required, only table verification");
				SC_LGSearchPage.get().valDataPresentInTbl();
			}
			
			//6.Verify that user can successfully  do the lg search by click on the create button.
			// Note: No Create button available in this page
			
			//7. Verify displays TIN number in Group details page for LG Name( under edit screen)-- This is an additional step( Not available in test case)
			// 7 Validation Steps, Capture TIN number> Navigate to Large Group> Group Details
			//-  Search for Test Data' in Large group Name column> Click on Edit button> Scroll down
			//- Validate TIn number List with step 7> CLose
			getWebDriver().switchTo().defaultContent();
			seClick(LeftPanelLinks.get().largeGroup, "Large Group");
			seClick(LeftPanelLinks.get().groupDetails, "Group Details");
			Thread.sleep(5000);
			seSwitchToFrame(CommonElements.get().iFrameSecondTab);
			LG_GroupDetailsPage.isLinkSelectedFrmTableUsingLargeGroup(lgName);
			seScrollForWebElement(LG_GroupDetailsPage.get().Tbl_TinEffectiveDate);
			String tinListFrmTable=SC_LGSearchPage.getTableData("TIN","GroupDetails");
			seClick(LG_GroupDetailsPage.get().Btn_Cancel, "Cancel");
			LG_GroupDetailsPage.validateTINNumbers(searchTableTINNumbers,tinListFrmTable);
			LoginLogoutPage.get().logoutApplication();
						seCloseBrowser();
		} catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Verify script execution",
					"Script is not executed succussfully, check snapshot", true);
			setResult(false);
			e.printStackTrace();
		}
	}

}