package testScripts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.utility.EnvHelper;

import utility.CoreSuperHelper;

public class TemplateScript extends CoreSuperHelper{

	static String url = EnvHelper.getValue("url.mainframe.wgs");
	static String ip=EnvHelper.getValue("base.ip");
	
	//Mainframe Connection Details
	static String userProfile=EnvHelper.getValue("user.profile.wgs");
	static String ism=EnvHelper.getValue("ISM");
	static String region=EnvHelper.getValue("region");
	static String mainframeProfile=EnvHelper.getValue("profile");

	
	public static void aTAFInitParams() { 
		MANUAL_TC_EXECUTION_EFFORT = "00:59:00";
	}

	public static void main(String[] args) { 
		aTAFInitParams(); 
		initiateTestScript();
	}

	public static void executeScript() {
		
				try {
//					 Method call to control the execution when there is a failure
					setIgnoreLastTestFailure(false);
					String tCID = getCellValue("Test_Case_ID").trim();
					String tcDescription = getCellValue("TEST_CASE_DESCRIPTION");
					logExtentReport(tcDescription);
						
						//To Avoid Creating Multiple Browser/Driver Instance
						if(getWebDriver()==null){
							seOpenBrowser(BrowserConstants.Chrome,url);
						}else{
							getWebDriver().navigate().to(url);
						}
						//String date="March2020";
						String Month="March";
						String year="2020";
						String day="17";
						
						getWebDriver().findElement(By.xpath("//*[@id='divMain']/div/app-main-page/div[1]/div/div[1]/div/div/div[1]/div/app-jp-input/div[3]/form/div[3]/p-calendar/span/button")).click();
						
					//if(month1.equals(anObject))
				//	System.out.println(month1);
					
				//	WebElement text1=getWebDriver().findElement(By.xpath("//div[@class='ui-datepicker-title']//span[text()="+year+"] "));
				//	String year1=	text1.getText();
					//String monthyear=month1+year1;
				//	System.out.println(monthyear);
					//System.out.println(year1);
					
						while(true) {
							//WebElement text=getWebDriver().findElement(By.xpath("//div[@class='ui-datepicker-title']//span[text()="+Month+"] "));
									//+ "/following-sibling::span[contains(text(),'2020')] "));
							
							WebElement text=getWebDriver().findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div[1]/div/div[1]/div/div/div[1]/div/app-jp-input/div[3]/form/div[3]/p-calendar/span/div/div/div/span[1] "));
							String month1=	text.getText();
							System.out.println(month1);
							if(month1.equals(Month)) {
								String monthyear=month1+year;
								System.out.println(monthyear);
								break;
							}
							else {
								getWebDriver().findElement(By.xpath("//*[@id='divMain']/div/app-main-page/div[1]/div/div[1]/div/div/div[1]/div/app-jp-input/div[3]/form/div[3]/p-calendar/span/div/div/a[2]/span")).click();	
							}
							
						}
						getWebDriver().findElement(By.xpath("//div/table/tbody/tr/td[@class='ng-tns-c14-6 ng-star-inserted']//a[contains(@class,'ng-tns-c14-6 ng-star-inserted') and text()="+day+"]")).click();
						
					
						
				} catch (Exception e) {
					CoreSuperHelper.processException(e, "Exception occured in execute script");					
					seMFDisconnect();
				}
				finally {
					setCellValue("COMMENTS", seGetFailureReason());
					
				}
		
	}
	
	
}
