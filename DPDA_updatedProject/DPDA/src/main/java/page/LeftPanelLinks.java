package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utility.CoreSuperHelper;


public class LeftPanelLinks extends CoreSuperHelper{
	
	private static LeftPanelLinks thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LeftPanelLinks instance.</p>
	 * @return the singleton instance of LeftPanelLinks
	 */
	public static LeftPanelLinks get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), LeftPanelLinks.class);
		return thisIsTestObj;
	}	
	
	// View Workbaskets Menu
	@FindBy(how=How.XPATH,using="//li[@title='View Workbaskets']")
	@CacheLookup
	public WebElement viewWorkbaskets;
	
	// View My Worklist Menu
	@FindBy(how=How.XPATH,using="//li[@title='View My Worklist']")
	@CacheLookup
	public WebElement viewMyWorklist;
	
	// View Team Worklist Menu
	@FindBy(how=How.XPATH,using="//li[@title='View Team Worklist']")
	@CacheLookup
	public WebElement viewTeamWorklist;
	
	// Large Group Menu
	@FindBy(how=How.XPATH,using="//li[@title='Large Group']")
	@CacheLookup
	public WebElement largeGroup;
	
	// Large Group Menu >> Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Send PAR Report']")
	@CacheLookup
	public WebElement sendPARReport;
	
	@FindBy(how=How.XPATH,using="//li[@title='AdHoc Par Report']")
	@CacheLookup
	public WebElement adhocParReport;
	
	@FindBy(how=How.XPATH,using="//li[@title='Group Details']")
	@CacheLookup
	public WebElement groupDetails;
	
	// Directory Accuracy Menu
	@FindBy(how=How.XPATH,using="//li[@title='Directory Accuracy']")
	@CacheLookup
	public WebElement directoryAccuracy;
	
	// Contact Information Menu
	@FindBy(how=How.XPATH,using="//li[@title='Contact Information']")
	@CacheLookup
	public WebElement contactInformation;
	
	// Contact Information Menu >> Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Create A Contact']")
	@CacheLookup
	public WebElement createAContact;
	
	@FindBy(how=How.XPATH,using="//li[@title='Search Contacts']")
	@CacheLookup
	public WebElement searchContacts;
	
	// Contact Information Menu >> Contact Portal >> Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Contact Portal']")
	@CacheLookup
	public WebElement contactPortal;
	
	@FindBy(how=How.XPATH,using="//li[@title='Contact Methods']")
	@CacheLookup
	public WebElement contactMethods;
	
	@FindBy(how=How.XPATH,using="//li[@title='Contact Reasons']")
	@CacheLookup
	public WebElement contactReasons;
	
	// Contact Information Menu >> Contact Portal >> Contact Methods Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Inbound Contact Methods']")
	@CacheLookup
	public WebElement inboundContactMethods;
	
	@FindBy(how=How.XPATH,using="//li[@title='Outbound Contact Methods']")
	@CacheLookup
	public WebElement outboundContactMethods;
	
	// Contact Information Menu >> Contact Portal >> Contact Reasons Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Inbound Contact Reasons']")
	@CacheLookup
	public WebElement inboundContactReasons;
	
	@FindBy(how=How.XPATH,using="//li[@title='Outbound Contact Reasons']")
	@CacheLookup
	public WebElement outboundContactReasons;
	
	@FindBy(how=How.XPATH,using="//li[@title='Update Contact Types']")
	@CacheLookup
	public WebElement updateContactTypes;
	
	// Directory Accuracy Menu >> Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Admin Portal']")
	@CacheLookup
	public WebElement adminPortal;
	
	@FindBy(how=How.XPATH,using="//li[@title='New Campaign']")
	@CacheLookup
	public WebElement newCampaign;
	
	
	// Search Criteria Menu
	@FindBy(how=How.XPATH,using="//li[@title='Search Criteria']")
	@CacheLookup
	public WebElement searchCriteria;
	
	// Search Criteria Menu >> Sub Menu Items
	@FindBy(how=How.XPATH,using="//li[@title='Provider Search']")
	public WebElement providerSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='Office Search']")
	@CacheLookup
	public WebElement officeSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='Provider Office Search']")
	@CacheLookup
	public WebElement providerOfficeSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='w9 Legal Search']")
	public WebElement w9LegalSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='Network Search']")
	public WebElement networkSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='HMO PDO Search']")
	public WebElement hmoPDOSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='HMO Transfer Search']")
	@CacheLookup
	public WebElement hmoTrasferSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='LG Search']")
	public WebElement lgSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='Clinic Search']")
	@CacheLookup
	public WebElement clinicSearch;

	@FindBy(how=How.XPATH,using="//li[@title='PAR Validation']")
	public WebElement parValidationSearch;
	
	@FindBy(how=How.XPATH,using="//li[@title='Case ID Search']")
	public WebElement caseIDSearch;
	
}