package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utility.CoreSuperHelper;


public class CommonElements extends CoreSuperHelper{
	
	private static CommonElements thisIsTestObj;
	/**
	 * <p>Getter method for the singleton CommonElements instance.</p>
	 * @return the singleton instance of CommonElements
	 */
	public static CommonElements get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), CommonElements.class);
		return thisIsTestObj;
	}	
	
	// IFrames 
	@FindBy(how=How.XPATH,using="//iframe[@id='PegaGadget0Ifr']")
	public WebElement iFrameFirstTab;
	
	@FindBy(how=How.XPATH,using="//iframe[@id='PegaGadget1Ifr']")
	public WebElement iFrameSecondTab;
	
	
	//select[@id="UniqueSearchBy"]
	@FindBy(how=How.ID,using="UniqueSearchBy")
	public WebElement selUniqueSearchBy;
	
	// Common Buttons
	@FindBy(how=How.XPATH,using="//button[text()='Search']")
	public WebElement btnSearch;
	
	@FindBy(how=How.XPATH,using="//button[text()='Clear']")
	public WebElement btnClear;
	
	@FindBy(how=How.XPATH,using="//button[text()='Done']")
	public WebElement btnDone;
	
	@FindBy(how=How.XPATH,using="//button[text()='Cancel']")
	public WebElement btnCancel;
	
	@FindBy(how=How.XPATH,using="//button[text()='Submit']")
	public WebElement btnSubmit;
	
	@FindBy(how=How.XPATH,using="//button[text()='Save']")
	public WebElement btnSave;
	
	@FindBy(how=How.XPATH,using="//button[text()='Create Transaction']")
	@CacheLookup
	public WebElement btnCreateTransaction;
	
	@FindBy(how=How.XPATH,using="//button[text()='Clinic Details']")
	public WebElement  btnClinicDetails;
	
	@FindBy(how=How.XPATH,using="//button[text()='License']")
	public WebElement  btnLicense;
	
	@FindBy(how=How.XPATH,using="//button[text()='Networks']")
	public WebElement  btnNetworks;
	
	@FindBy(how=How.XPATH,using="//button[text()='Assign']")
	public WebElement  btnAssign;
	
	@FindBy(how=How.XPATH,using="//button[text()='Apply']")
	public WebElement  btnApply;
	
	@FindBy(how=How.XPATH,using="//table[@id='grid-desktop-paginator']")
	public WebElement searchResultsTblPaginator;
	
	@FindBy(how=How.XPATH,using="//*[@id='grid-desktop-paginator']//a[contains(., 'Next')]")
	public WebElement searchResultsNextLink;
	
	@FindBy(how=How.XPATH,using="//button[text()='Refresh']")
	public WebElement  btnRefresh;
	
	//frames/Tabs Dropdown
	@FindBy(how=How.XPATH,using="//*[@class='textIn' and @xpath='1']")
	public WebElement  tabsDropdown;
	
	//img[@id='poli0' and contains(@style,'visible')]
	@FindBy(how=How.XPATH,using="//img[@id='poli0' and contains(@style,'visible')]")
	public WebElement  smallStatusBar;
	
	//ul[contains(@class,'throbber')]
	@FindBy(how=How.XPATH,using="//ul[contains(@class,'throbber')]")
	public WebElement  bigStatusBar;
	
	
}