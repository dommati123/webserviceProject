package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utility.CoreSuperHelper;

public class GoogleSearch extends CoreSuperHelper {

	private static GoogleSearch thisIsTestObj;

	// So that there only one object accesses this class at any moment
	public synchronized static GoogleSearch get() {
		thisIsTestObj = PageFactory.initElements(getWebDriver(), GoogleSearch.class);
		return thisIsTestObj;
	}

	// Recommended model for all objects
	@FindBy(how = How.XPATH, using = "//input[@aria-label='Search']")
	@CacheLookup
	public WebElement searchText;
	
	@FindBy(how = How.XPATH, using = "//input[@id='gbqfbb']/../input[1]")
	@CacheLookup
	public WebElement searchButton;
	
}

//input[@aria-label='Search']