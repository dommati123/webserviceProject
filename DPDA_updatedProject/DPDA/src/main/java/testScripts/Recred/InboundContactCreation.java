package testScripts.Recred;

import org.openqa.selenium.support.ui.ExpectedConditions;

import page.CommonElements;
import page.CreateContactPage;
import page.LeftPanelLinks;
import page.LoginLogoutPage;
import utility.CoreSuperHelper;

public class InboundContactCreation extends CoreSuperHelper{
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
			
			//Check if Call Interaction editable
			if(getCellValue("Inbound_Contact_Method").equalsIgnoreCase("Call")) {
				CreateContactPage.get().validateCallInteraction();
			}
			
			//Fill Provider Information
			CreateContactPage.get().FillProviderInfo();
			
			//Fill Office Information
			CreateContactPage.get().FillOfficeInfo();
			
			//Fill Outbound Contact Details
			CreateContactPage.get().FillOutboundContact();
		
			//Add the notes
			CreateContactPage.get().addNotes();
			
			//Save the transaction and validate the TRN#
			CreateContactPage.get().SaveTheTransaction();
			
			
		}catch (Exception e) {
			// TODO: handle exception
			log(ERROR, "Script execution stoped due to the unexpected exception. Please refer the console for more info.");
			e.printStackTrace();
		}finally {
			seCloseBrowser();
		}
	}

}
