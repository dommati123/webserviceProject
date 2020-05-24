package stepdefinitions;

import org.junit.Assert;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utility.CoreSuperHelper;

public class ATAF_Feature extends CoreSuperHelper  {
	
		
		@Given("there are {int} cucumbers")
		public void there_are_cucumbers(Integer int1) {
			try {
				seOpenBrowser(BrowserConstants.Chrome, "https://www.google.com");
				logTest();
				Assert.assertTrue(true);
				}catch(Exception e) {
					e.printStackTrace();
				} finally {
					seCloseBrowser();
				}
		}

		@When("I eat {int} cucumbers")
		public void i_eat_cucumbers(Integer int1) 
		{
			ExtentReportsUtility.log(PASS, "Inside When Clause");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Then("I should have {int} cucumbers")
		public void i_should_have_cucumbers(Integer int1) {
			ExtentReportsUtility.log(PASS, "Inside Then Block");
		}

}