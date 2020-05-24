package testScripts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.anthem.selenium.constants.BrowserConstants;
import com.anthem.selenium.constants.DatabaseConstants;
import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utils.DatabaseUtils;
import com.anthem.selenium.utils.ExcelUtils;

import utility.CoreSuperHelper;
import utility.SOAPUtil;

public class ComparingApplicationValuesTemplate_TS extends CoreSuperHelper{

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
	//Comparing Data from Inputed Excel sheet with Database 
		setIgnoreLastTestFailure(false);		
		String resultsPath = null;
		HashMap<String, HashMap<String, String>> applicationTotalDetails = null;					
		String TC_ID = getCellValue("Test_Case_ID");
			
			 String overApplyStatusCode = getCellValue("OVER_APPLY"); 
			 String totalBenefitAmount = getCellValue("BNFT_AMT"); 
			 float ftotalBenefitAmount =	 Float.parseFloat(totalBenefitAmount); 
			 String totalBenefitOccurrenceCount =  getCellValue("BNFT_OCC"); 
			 String totalBenefitDayCount = getCellValue("BNFT_DAY");
			 
		String id = getCellValue("ID");
		String employeeName = getCellValue("EMPLOYEE_NAME");
		
		List<List<String>> dbValue = ExcelUtils.getAllRowValues(resultsPath, "Results", false);
		Iterator<List<String>> itr = dbValue.iterator();
		while (itr.hasNext()) {
			List<String> rowValues = itr.next();
			String dbapplicationName = rowValues.get(4);
			String dbProdTypeCode = rowValues.get(6);
			String dbMCode = rowValues.get(1);
			HashMap<String, String> applicationValues = applicationTotalDetails.get(dbapplicationName + dbProdTypeCode + dbMCode);
			if (applicationValues != null) {
				String dbHCID = rowValues.get(0);
				String dbCaseID = rowValues.get(2);
				String dbBeneffDate = rowValues.get(3);
				String dbCoveragePlanID = rowValues.get(7);
				String dbbenTermDate = rowValues.get(8);
				String dbOverApplyStatus = rowValues.get(9);
				String dbTotalBenAmount = rowValues.get(10);
				String dbTotalBenOcc = rowValues.get(11);
				String dbTotalBenDay = rowValues.get(12);
				String dbEligSRCCD = rowValues.get(15);
				String dbTotMaxBen = rowValues.get(16);
				String apihcId = applicationValues.get("hcId");
				String apiCaseID = applicationValues.get("caseId");
				String apiBenEffDate = applicationValues.get("benefitEffectiveDate");
				String apiBenTermDate = applicationValues.get("benefitTerminationDate");
				String apiCoveragePlanID = applicationValues.get("coveragePlanId");
				String apiOverApplyStatus = applicationValues.get("overApplyStatusCode");
				String apiTotalBenAmount = applicationValues.get("totalBenefitAmount");
				String apiTotalBenOcc = applicationValues.get("totalBenefitOccurrenceCount");
				String apiTotalBenDay = applicationValues.get("totalBenefitDayCount");
				String apiEligSRCCD = applicationValues.get("eligibilitySourceCode");
				String apiTotMaxBen = applicationValues.get("totalMaxBenefit");
				String errorMessage = "";
				Float fdbTotMaxBen = Float.parseFloat(dbTotMaxBen);
				Float fapiTotMaxBen = Float.parseFloat(apiTotMaxBen);
				Float fdbTotalBenAmount = Float.parseFloat(dbTotalBenAmount);
				Float fapiTotalBenAmount = Float.parseFloat(apiTotalBenAmount);
				if (!(dbHCID.equals(apihcId))) {
					errorMessage = errorMessage + "HCID,";
				}
				if (!(dbCaseID.equals(apiCaseID))) {
					errorMessage = errorMessage + "Case ID,";
				}
				if (!(dbBeneffDate.equals(apiBenEffDate))) {
					errorMessage = errorMessage + "Benefit Effective date,";
				}
				if (!(dbCoveragePlanID.equals(apiCoveragePlanID))) {
					errorMessage = errorMessage + "Coverage Plan ID,";
				}
				if (!(dbbenTermDate.equals(apiBenTermDate))) {
					errorMessage = errorMessage + "Benefit Term date,";
				}
				if (!(dbOverApplyStatus.equals(apiOverApplyStatus))) {
					errorMessage = errorMessage + "Over Apply status Code,";
				}
				if (!(fdbTotalBenAmount.equals(fapiTotalBenAmount))) {
					errorMessage = errorMessage + "Total Benefit Amount,";
				}
				if (!(dbTotalBenOcc.equals(apiTotalBenOcc))) {
					errorMessage = errorMessage + "Total Benefit Occurence Count,";
				}
				if (!(dbTotalBenDay.equals(apiTotalBenDay))) {
					errorMessage = errorMessage + "Total Benefit Day Count,";
				}
				if (!(dbEligSRCCD.equals(apiEligSRCCD))) {
					errorMessage = errorMessage + "Eligible Source code,";
				}
				if (!(fdbTotMaxBen.equals(fapiTotMaxBen))) {
					errorMessage = errorMessage + "Total Max Benefit,";
				}
			}}}
			catch (Exception e) {
				setLastTestPassed(false);
				CoreSuperHelper.processException(e, "Exception occured in execute script");
				seMFDisconnect();
			}
			finally {				
				setResult("STATUS", getResult());
				seCloseBrowser();
			}
		}

}

