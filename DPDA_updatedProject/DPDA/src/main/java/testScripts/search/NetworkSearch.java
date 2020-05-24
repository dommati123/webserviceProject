package testScripts.search;

import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import page.NetworkSearchPage;
import utility.CoreSuperHelper;

public class NetworkSearch extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {
		try {
			logExtentReport("Network Search");			
			LoginLogoutPage.get().loginApplication();
			Thread.sleep(5000);
			seClick(LeftPanelLinks.get().searchCriteria, "Click on Search Criteria");
			seClick(LeftPanelLinks.get().networkSearch, "Click on Network Search");
			Thread.sleep(5000);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			seSelectText(NetworkSearchPage.get().State, getCellValue("State"), "selecting state OH as required dropdown");
			seSelectText(NetworkSearchPage.get().ActiveStatus, getCellValue("ActiveStatus"), "selecting Activestatus Active as required dropdown");
			seSetText(NetworkSearchPage.get().County, getCellValue("County"));
			Thread.sleep(5000);			
			seClick(NetworkSearchPage.get().networksDropdownBut, "Click on networks Dropdown ");
			Thread.sleep(5000);
			seClick(NetworkSearchPage.get().getNetworkType(getCellValue("NetworkName")), "click on required Network type");
			Thread.sleep(5000);
			seClick(NetworkSearchPage.get().specialityDropdownBut, "Click on speciality Dropdown");
			seClick(NetworkSearchPage.get().getSpecialistType(getCellValue("Specialities" )), "click on required Network type");
			seSetText(NetworkSearchPage.get().ZipCode, getCellValue("ZipCode"));
			seClick(NetworkSearchPage.get().ZipCode, "ZipCode");
			Thread.sleep(5000);
			seClick(CommonElements.get().btnSearch, "Click on network search button ");
			NetworkSearchPage.get().validateNetworkData(getCellValue("V_NetworkID"), getCellValue("V_NetworkName"));
			NetworkSearchPage.get().valDataPresentInTbl();			
			LoginLogoutPage.get().logoutApplication();
			Thread.sleep(1000);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}