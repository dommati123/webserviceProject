package testScripts;

import java.sql.Connection;
import java.sql.ResultSet;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.DatabaseConstants;
import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utils.DatabaseUtils;

import utility.CoreSuperHelper;
import utility.SOAPUtil;

public class DatabaseTemplate_TS extends CoreSuperHelper{

	static String url = EnvHelper.getValue("url.mainframe.wgs");
	static String ip=EnvHelper.getValue("base.ip");
	//Mainframe Connection Details
	static String userProfile=EnvHelper.getValue("user.profile.wgs");
	static String ism=EnvHelper.getValue("ISM");
	static String region=EnvHelper.getValue("region");
	static String mainframeProfile=EnvHelper.getValue("profile");
	static String query = "";
	
	public static void aTAFInitParams() { 
		MANUAL_TC_EXECUTION_EFFORT = "00:59:00";
	}

	public static void main(String[] args) { 
		aTAFInitParams(); 
		initiateTestScript();
	}

	public static void executeScript() {

		try {
			// Method call to control the execution when there is a failure
			setIgnoreLastTestFailure(false);
			String tCID = getCellValue("Test_Case_ID").trim();
			String tcDescription = getCellValue("TEST_CASE_DESCRIPTION");
			String hcid = getCellValue("HCID");
			logExtentReport(tcDescription);
			//To Avoid Creating Multiple Browser/Driver Instance
			if(getWebDriver()==null){
				seOpenBrowser(BrowserConstants.Chrome,url);
			}else{
				getWebDriver().navigate().to(url);
			}
			// To Validate the Database
			if (region.equals("QA")) {
				region = "IM$E-IME6";
				query = "Select * from GNCE6.applicationR_DTL where HC_ID = '" + hcid + "';";
			} else {
				log(FAIL, "Enter Correct Region");
			}
			String dbURL = SOAPUtil.get().getDB2Details(region);
			String[] userInfo = getLoginInfo("WGSProfile");
			String username = userInfo[0];
			String password = userInfo[1];
			Connection conn = DatabaseUtils.getConnection(DatabaseConstants.Database_DB2, dbURL, username, password);
			ResultSet rs = DatabaseUtils.executeQuery(conn, query);
			String reportPath = getReportPathFolder() + tCID + "_results.xlsx";
			SOAPUtil.get().exportResults(rs, reportPath, query);
			
		} catch (Exception e) {
			CoreSuperHelper.processException(e, "Exception occured in execute script");
			seMFDisconnect();
		}
		finally {
			setCellValue("COMMENTS", seGetFailureReason());

		}

	}


}
