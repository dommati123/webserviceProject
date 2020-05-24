package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utility.CoreSuperHelper;

public class SC_OfficeSearch extends CoreSuperHelper{
	private static SC_OfficeSearch thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LG_Send_PAR_Report instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static SC_OfficeSearch get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), SC_OfficeSearch.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.XPATH,using="//label[text()='Search Criteria']/following-sibling::div")
	public WebElement DropDown_SearchCriteria;
	
	@FindBy(how=How.XPATH,using="//input[@id='ClinicDPSNo']")
	public WebElement Txt_ClinicDPSNo;
	
	@FindBy(how=How.XPATH,using="//input[@type='radio']")
	public WebElement RadioBtn_SearchResult;
	
	@FindBy(how=How.XPATH,using="//select[@id='ActionCodeUniqueIDForDCClinic']")
	public WebElement DropDown_SelectTransaction;
	
	@FindBy(how=How.XPATH,using="//a[@name='ClinicSearchScreenForLGDataChange_pyDisplayHarness_28']")
	public WebElement Lnk_NewTransaction;
	
	
	//Large group Inquiry Page
//	PegaGadget0Ifr
	@FindBy(how=How.XPATH,using="//input[@id='DateCompleted']")
	public WebElement Txt_Status_DataComplete;
	
	@FindBy(how=How.XPATH,using="//select[@id='NDMWorkingStatus']")
	public WebElement DropDown_Status_WorkingStatus;
	
	@FindBy(how=How.XPATH,using="//select[@id='NDMStatusTaken']")
	public WebElement DropDown_Status_StatusTaken;
	
	
	
	
}
