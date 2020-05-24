package stepdefinitions;

import org.openqa.selenium.JavascriptExecutor;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.utility.EnvHelper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.GoogleSearch;
import utility.CoreSuperHelper;

public class Test1StepDefination extends CoreSuperHelper  {
	static String baseURL = EnvHelper.getValue("URL1");
	
	@Given("Open Chrome Browser")
	public void openChromeBrowser() {
	    // Write code here that turns the phrase above into concrete actions
	   
	try {
		   System.out.println(" *********Start Open the Chrome ******-> "+ baseURL);
		   seOpenBrowser(BrowserConstants.Chrome, baseURL, "Launching the application");
		   seWaitForPageLoad();
		   System.out.println(" *********Done Open the Chrome ******-> "+ baseURL);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	
		}
	

	@When("Write Text on the Google searchTextBox")
	public void writeOnTheGoogleSearchTextBox()  throws Throwable {
		try {
		System.out.println(" *********Search Box is Found ******-> ");
		seSetText(GoogleSearch.get().searchText,"Sports");
		Thread.sleep(1000);
		System.out.println(" *********Done Searching and Typing******-> ");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@And("Click on the GoogleSearchButton")
	public void ClickSearchButton() throws Throwable {
		try {
		System.out.println(" *********Click on search button ******-> ");
		JavascriptExecutor executor = (JavascriptExecutor)getWebDriver();
		executor.executeScript("arguments[0].click();", GoogleSearch.get().searchButton);
		
		log(true, "Google search Button", "Clicking on the Google search Button Successfully");
		
		//seClick(true,GoogleSearch.get().searchButton, "Google search Button", "Clicking on the Google search Button SearchButton");
		
		Thread.sleep(1000);
		System.out.println(" *********Done Clicking the Button ******-> ");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Then("Close chrome Browser")
	public void closeChromeBrowser()  throws Throwable {
		try {
		System.out.println("*******Start Closing the Browser*******");
		seCloseBrowser();		
		System.out.println("******* Closing the Broser is finished*******");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void log(boolean b, String string, String string2) {
		// TODO Auto-generated method stub
		
	}

}
