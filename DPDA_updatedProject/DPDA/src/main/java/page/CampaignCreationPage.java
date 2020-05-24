package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

import utility.CoreSuperHelper;

public class CampaignCreationPage extends CoreSuperHelper{
	
	private static CampaignCreationPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LoginPage instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static CampaignCreationPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), CampaignCreationPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.ID,using="MandateCode")
	public WebElement selectMandate;
	
	@FindBy(how=How.ID, using="MandateInformation")
	public WebElement campaignSummaryName;
	
	@FindBy(how=How.ID,using="Date")
	public WebElement year;
	
	// Summary Page Web Elements
	
	// Campaign Summary Header
	
	@FindBy(how=How.XPATH,using="//div[@swp='.pyDefaultTaskStatus,.pyCurrentActionLabel,.pxFlowName']//span[text()='Campaign Summary']")
	public WebElement summaryHeader;
	
	//select[@id='MandateCode']//option[@selected]
	@FindBy(how=How.XPATH,using="//select[@id='MandateCode']//option[@selected]")
	public WebElement summarySelectMandate;
	
	// Campaign Created Label ID
	//table[@id='RULE_KEY']//label
	@FindBy(how=How.XPATH,using="//table[@id='RULE_KEY']//label")
	public WebElement tabIdLabel;
	
	
	String summaryArray[] = new String[] {"Name","State#","Year","MandateCode"};
	
	public boolean validateSummary(String... summaryParams){
		boolean validInputs = false;
		
		if (summaryHeader.isDisplayed()) {
			int varArgId = 0;
			for (String summaryVal : summaryArray) {
				if (summaryParams[varArgId]!=null && !(summaryParams[varArgId].trim().equals(""))) {
					WebElement webElmt = null;
					if (varArgId<3) {
						webElmt = getWebDriver().findElementByXPath("//span[contains(text(),'"+summaryVal+"')]/following-sibling::div/span");
					} else {
						webElmt = summarySelectMandate;
					}
					
					if (summaryParams[varArgId].equals(webElmt.getText())) {
						ExtentReportsUtility.log(ApplicationConstants.PASS, summaryVal, "Compare "+summaryVal +" Input: "+ summaryParams[varArgId]+" | Summary: "+ webElmt.getText() );
						validInputs = true;
					} else {
						ExtentReportsUtility.log(ApplicationConstants.FAIL, summaryVal, "Compare "+summaryVal +" Input: "+ summaryParams[varArgId]+" | Summary: "+ webElmt.getText() );
						validInputs = false;
					}
				}
				varArgId++;
			}
		}
		return validInputs;
	}
}
