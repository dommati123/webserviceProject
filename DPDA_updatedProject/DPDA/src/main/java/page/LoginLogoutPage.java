package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utility.ExtentReportsUtility;

import utility.CoreSuperHelper;


public class LoginLogoutPage extends CoreSuperHelper{
	
	private static LoginLogoutPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LoginPage instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static LoginLogoutPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), LoginLogoutPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.ID,using="txtUserID")
	public WebElement loginUserId;
	
	@FindBy(how=How.ID,using="txtPassword")
	WebElement loginUserPwd;
	
	@FindBy(how=How.ID,using="sub")
	@CacheLookup
	WebElement loginBtn;
	
	@FindBy(how=How.XPATH,using="//div[@node_name='pyPortalHeader']//*[@data-test-id='px-opr-image-ctrl']")
	@CacheLookup
	public WebElement logout;
	
	@FindBy(how=How.XPATH,using="//span[text()='Log off']")
	public WebElement  logoutConfirm;
	
	
	public void loginApplication(){
		try {
		String execEnv = EnvHelper.getValue("execution.env");
		
		String userProfileType = null;
		
		try {
			if (isColumnHeaderExists("USER_PROFILE_TYPE"))
				userProfileType = getCellValue("USER_PROFILE_TYPE");
		} catch(Exception e) {
			
		}
		
		if (userProfileType ==null || (userProfileType !=null && userProfileType.trim().equals(""))) {
			userProfileType = EnvHelper.getValue("USER_PROFILE_TYPE");
		}
		
		seOpenBrowser(BrowserConstants.Chrome,EnvHelper.getValue(execEnv+"_URL"));
		String[] userInfo = getLoginInfo(execEnv+"_"+userProfileType+"_User");
		ExtentReportsUtility.log(INFO, "Logged in Profile",userProfileType);
		setUserName(userInfo[0],"User Name");
		setPassword(userInfo[1]);
		clickSubmit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loginApplication(String profileName){
		try {
		String execEnv = EnvHelper.getValue("execution.env");
		
		String userProfileType = null;
		
		if (profileName!=null && !profileName.trim().equals("")) {
			userProfileType = profileName;
		} else {
			try {
				if (isColumnHeaderExists("USER_PROFILE_TYPE"))
					userProfileType = getCellValue("USER_PROFILE_TYPE");
			} catch(Exception e) {
				
			}
			if (userProfileType ==null || (userProfileType !=null && userProfileType.trim().equals(""))) {
				userProfileType = EnvHelper.getValue("USER_PROFILE_TYPE");
			}
		}
		seOpenBrowser(BrowserConstants.Chrome,EnvHelper.getValue(execEnv+"_URL"));
		String[] userInfo = getLoginInfo(execEnv+"_"+userProfileType+"_User");
		ExtentReportsUtility.log(INFO, "Logged in Profile",userProfileType);
		setUserName(userInfo[0],"User Name");
		setPassword(userInfo[1]);
		clickSubmit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reLoginApplication(String profileName){
		try {
		String execEnv = EnvHelper.getValue("execution.env");
		
		String userProfileType = null;
		
		if (profileName!=null && !profileName.trim().equals("")) {
			userProfileType = profileName;
		} else {
			try {
				if (isColumnHeaderExists("RELOGIN_USER_PROFILE_TYPE"))
					userProfileType = getCellValue("RELOGIN_USER_PROFILE_TYPE");
			} catch(Exception e) {
				
			}
			if (userProfileType ==null || (userProfileType !=null && userProfileType.trim().equals(""))) {
				userProfileType = EnvHelper.getValue("RELOGIN_USER_PROFILE_TYPE");
			}
		}
		seOpenBrowser(BrowserConstants.Chrome,EnvHelper.getValue(execEnv+"_URL"));
		String[] userInfo = getLoginInfo(execEnv+"_"+userProfileType+"_User");
		ExtentReportsUtility.log(INFO, "Re-Logged in Profile",userProfileType);
		setUserName(userInfo[0],"User Name");
		setPassword(userInfo[1]);
		clickSubmit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUserName(String userId,String UserID){
		seSetUserId(LoginLogoutPage.get().loginUserId, userId,UserID);
	}
	
	public void setPassword(String pwd){
		seSetPassword(LoginLogoutPage.get().loginUserPwd, pwd,"Password");
	}
	
	public void clickSubmit(){
		seClick(LoginLogoutPage.get().loginBtn, "Submit");
	}
	
	public void logoutApplication() {
		try {
			getWebDriver().switchTo().defaultContent();
			seClick(logout, "Click on logout button");
			seClick(logoutConfirm, "Click on confirm logout button");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}}