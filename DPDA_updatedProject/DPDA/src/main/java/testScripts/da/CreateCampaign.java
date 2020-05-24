/**
 * 
 */
package testScripts.da;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CampaignCreationPage;
import page.CommonElements;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import utility.CoreSuperHelper;

/**
 * @author AF14734
 *
 */
public class CreateCampaign extends CoreSuperHelper {

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:55:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			
			logExtentReport("Create Campaign");
			LoginLogoutPage.get().loginApplication();
			seClick(false,LeftPanelLinks.get().directoryAccuracy, "Directory Accuracy","Click Menu Link Directory Accuracy");
			seClick(false,LeftPanelLinks.get().newCampaign, "Office Search", "Click Menu Sub-Link New Campaign");
			
			seWaitForElementVisibility(5000,CommonElements.get().iFrameFirstTab);
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);

			Thread.sleep(1000);
			String mandateCode = getCellValue("Mandate_Code");
			String campaignSummaryName = getCellValue("Campaign_Summary_Name");
			String year = getCellValue("Year");
			String stateCode = getCellValue("V_State");
			
			seSelectText(CampaignCreationPage.get().selectMandate, mandateCode,"Mandate #");
			Thread.sleep(100);
			seSetText(CampaignCreationPage.get().campaignSummaryName, campaignSummaryName,"Campaign Summary");
			
			if (year !=null && !year.trim().equals("")) {
				CampaignCreationPage.get().year.clear();
				Thread.sleep(100);
				seSetText(CampaignCreationPage.get().year, year,"Year");
			}
			seClick(CommonElements.get().btnDone, "Click Done");
			
			Thread.sleep(1000);
			
			//"Name","State#","Year","MandateCode"
			if (CampaignCreationPage.get().validateSummary(campaignSummaryName,stateCode,year,mandateCode)){
				// Campaign Created Successfully
				seClick(CommonElements.get().btnSubmit,"Click Submit");
				ExtentReportsUtility.log(ApplicationConstants.PASS, "Campaing Created","Campaign Created Sucessfully", true);
				getWebDriver().switchTo().defaultContent();
				ExtentReportsUtility.log(ApplicationConstants.PASS, "Campaing Created","Campaign ID : "+CampaignCreationPage.get().tabIdLabel.getText());
			} 
			
			
			LoginLogoutPage.get().logoutApplication();
			Thread.sleep(100);
			seCloseBrowser();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
