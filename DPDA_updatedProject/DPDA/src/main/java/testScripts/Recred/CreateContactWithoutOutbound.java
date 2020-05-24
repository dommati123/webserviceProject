package testScripts.Recred;

import org.openqa.selenium.support.ui.ExpectedConditions;

import com.anthem.selenium.utility.ExtentReportsUtility;

import page.CommonElements;
import page.CreateContactPage;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import utility.CoreSuperHelper;

public class CreateContactWithoutOutbound extends CoreSuperHelper{
	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:25:00";
	}
	
	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}
	
	public static void executeScript() {
		try {
			logExtentReport("Verify that system should allow the user to upload the revised par report ");
			LoginLogoutPage.get().loginApplication();

			seWaitForPageLoad();

			seClick(LeftPanelLinks.get().contactInformation, "Contact Information");
			seClick(LeftPanelLinks.get().createAContact, "Create Contact");
			Thread.sleep(3000);
			
			//Switch to the Create Contact tab
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(CommonElements.get().iFrameFirstTab));
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
			
			Thread.sleep(3000);
			//Fill the Contact Information
			CreateContactPage.get().FillContactInfo();
			
			//Fill the Inbound Contact details
			CreateContactPage.get().FillInboundContact();
			
			//Fill Provider Information
			CreateContactPage.get().FillProviderInfo();
			
			//Add Notes
			CreateContactPage.get().addNotes();
			
			//Validate the error
			seClick(CommonElements.get().btnSave, "Save");
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(CreateContactPage.get().taxIDError));
			seCompareStrings(true, "Inbound Contact cannot be created without Tax Id No", seGetText(CreateContactPage.get().taxIDError), "=", "Validate the TAX ID error when the field is left blank");
			
			//Fill Office Information
			CreateContactPage.get().FillOfficeInfo();
			
			//Save the transaction
			seClick(true, CommonElements.get().btnSave, "Save", "Click on save button to save the transaction");
			seWaitForElementVisibility(5, CreateContactPage.get().saveConfirmationMSG);
			seCompareText(true, CreateContactPage.get().saveConfirmationMSG, "Contact Case has been created.Do you want to create another contact ?", "Validate the confirmation message");
			seClick(true, CreateContactPage.get().no, "No");
			
		}catch (Exception e) {
			// TODO: handle exception
			ExtentReportsUtility.log(ERROR, "Script execution stoped due to the unexpected exception. Please refer the console for more info.");
			
		}finally {
			seCloseBrowser();
		}
	}
}
