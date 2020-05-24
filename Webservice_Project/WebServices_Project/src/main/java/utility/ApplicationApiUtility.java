package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.core.impl.ThrowableFormatOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anthem.selenium.constants.DatabaseConstants;
import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utils.DatabaseUtils;
import com.anthem.selenium.utils.DateTimeUtils;
import com.anthem.selenium.utils.ExcelUtils;
import com.anthem.selenium.utils.RESTWebServiceUtils;
import com.anthem.selenium.utils.XMLJSONUtils;
import com.ibm.db2.jcc.am.fp;
import com.jayway.jsonpath.JsonPath;

public class ApplicationApiUtility extends CoreSuperHelper {
	
	static String responseBody;

	private static ApplicationApiUtility thisisTestObject;

	public static ApplicationApiUtility get() {
		if (thisisTestObject == null) {
			thisisTestObject = new ApplicationApiUtility();
		}
		return thisisTestObject;
	}

	/**
	 * Method to fetch the member details from the HTTPresponse from the member
	 * demographic API
	 * 
	 * @param response:
	 *            HTTP response of member demographic API
	 * @return: Hashmap containing member details
	 */
	public HashMap<String, String> getMemberDetails(HttpResponse response, int i) {
		HashMap<String, String> memberDetails = new HashMap<>();
		try {
			if (isLastTestPassed() || isIgnoreLastTestFailure()) {
				responseBody=EntityUtils.toString(response.getEntity());
				System.out.println(responseBody);
				int statusCode = response.getStatusLine().getStatusCode();
				
				/*
				 * String responsePath = getReportPathFolder() + tcID + ".json"; FileWriter
				 * writer = new FileWriter(new File(responsePath)); BufferedWriter bwriter = new
				 * BufferedWriter(writer);
				 */
				if (statusCode == 200) {
					//String responseBody = EntityUtils.toString(response.getEntity());
					System.out.println(responseBody);
					
					
					
					  JSONArray jarray = XMLJSONUtils.getJSONArray(responseBody, "data"); 
					 
					
					  System.out.println(jarray); 
					  JSONObject jobject = (JSONObject) jarray.get(i);
						//JSONObject IDobj = (JSONObject) jobject.get("id");
					  
					  String apiEmpID = jobject.get("id").toString();
					  memberDetails.put("id", apiEmpID); 
					  String employeeName = jobject.get("employee_name").toString(); 
					  memberDetails.put("employee_name",employeeName);
					  String employee_age = jobject.get("employee_age").toString(); 
					  memberDetails.put("employee_age",employee_age);
					  String employee_salary = jobject.get("employee_salary").toString(); 
					  memberDetails.put("employee_salary",employee_salary);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Get member details from the Demographic API response",
							"API request failed with status code:" + statusCode);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while parsing the Demographic response");
		}
		return memberDetails;
	}
	
	public static String getEmpName(String id)
	{
		String Name=null;
		
		try {
			String tempXapth="$.data.[?(@.id=='{id}')].employee_name";
			tempXapth=tempXapth.replace("{id}", id);
			Name = (JsonPath.read(responseBody, tempXapth)).toString();
		} catch (Exception e) {
			return null;
		} 
		if(Name == null){
			//throw new Exception("Not able to get the data with the xpath - <b>" + xpath + "</b>");
		}
		
		return Name;
	}

	/**
	 * Method to get the Endpoint URL for applicationTotal
	 * 
	 * @param region
	 * @param HCID
	 * @return
	 */
	public String getapplicationTTLURL(String region, String HCID) {
		String url = "";
		switch (region) {
		case "QA":
			// url="http://internal-a72666fcc80f211e9806502622c366a3-1173268936.us-east-1.elb.amazonaws.com/"+HCID+"/applications";
			url = "http://applications.qa.anthem.com/api/applicationsIo/" + HCID + "/applications";
			break;
		case "DEV":
			url = "http://internal-a72666fcc80f211e9806502622c366a3-1173268936.us-east-1.elb.amazonaws.com/" + HCID
			+ "/applications";
			// url= "http://applications.qa.anthem.com/api/applicationsIo/"+HCID+"/applications";

			break;

		default:
			throw new IllegalArgumentException("Please provide a valid region");
		}

		return url;

	}
	/**
	 * Method to get Replace Error Message to Null 
	 * 
	 * @param region
	 * @param error message
	 *            
	 * @return resMsg Null message as it is replaced
	 */
	
	public  String ParseErrMsg(String msg) {
		String resMsg=msg.replace("\"error\":", "");
		resMsg=resMsg.replace("\"text\":", "");
		resMsg=resMsg.replace("{", "");
		resMsg=resMsg.replace("}", "");
	    return resMsg;
	}

	/**
	 * Method to get the Endpoint URL for Contract LVL API
	 * 
	 * @param region
	 * @param CONTRACT
	 *            CODE
	 * @return
	 */
	public String getContrLVLURL(String region, String contract) {
		String url = "";
		switch (region) {
		case "QA":
			url = "http://applications.qa.anthem.com/api/applicationsIo/" + contract + "/contract";
			break;
		case "DEV":
			url = "http://applications.dev.anthem.com/api/applicationsIo/" + contract + "/contract";
			break;

		default:
			throw new IllegalArgumentException("Please provide a valid region");
		}
		return url;
	}

	public void validateapplicationTotalResponse(HttpResponse response,String region)
	{
		try
		{

			String TC_ID = getCellValue("Test_Case_ID");
			String hcid = getCellValue("HCID");
			String mbrC= "";
			String caseID= "";
			String benefitEffectiveDate= "";
			String		 applicationName= "";
			String		 prodTypeCD="";
			String		 cvgPlanID= "";
			String		 applicationSuffixName= "";
			String overApplyStatusCode= getCellValue("OVER_APPLY");
			String totalBenefitAmount= getCellValue("BNFT_AMT");
			String totalBenefitOccurrenceCount= getCellValue("BNFT_OCC");
			String totalBenefitDayCount= getCellValue("BNFT_DAY");
			String str = "";
			int statusCode = response.getStatusLine().getStatusCode();
			String message = response.getStatusLine().toString();
			HashMap<String, HashMap<String, String>> applicationTotalDetails=getapplicationTotalDetails(response);

			String query = "";

			if(region.equals("QA"))
			{
				region = "IM$E-IME6";
				query = "Select * from GNCE6.applicationR_TOTL where HC_ID = '"+hcid+"';";
			}

			String dbURL = SOAPUtil.get().getDB2Details(region);
			String[] userInfo = getLoginInfo("WGSProfile");
			String username = userInfo[0];
			String password = userInfo[1];
			Connection conn = DatabaseUtils.getConnection(DatabaseConstants.Database_DB2, dbURL, username, password);
			ResultSet rs = DatabaseUtils.executeQuery(conn, query);
			String reportPath = getReportPathFolder()+TC_ID+"_results.xlsx";
			SOAPUtil.get().exportResults(rs, reportPath, query);


			//HashMap<String, String> hciddetails = null;
			switch (TC_ID) {
			case "ACCREM-1704_TC01":// +987A77166
				// statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					log(PASS, "Expected status Code=200 ", "actual Status Code=200");
					compareapplicationValues(reportPath, applicationTotalDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=200 ", "actual Status Code=200" + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1704_TC02":

				// 400(Bad Request)
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 400) {
					log(PASS, "Expected status Code=400 ", "actual Status Code=400");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=400 ", "actual Status Code=" + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1704_TC03":// 401(Unauthorized)
				// hcid = getCellValue("HCID");
				statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 401) {
					log(PASS, "Expected status Code=401 ", "actual Status Code=401");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=401", "actual Status Code=" + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1704_TC04":// 500(Internal Server Failure)
				// hcid = getCellValue("HCID");
				statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 500) {
					log(PASS, "Expected status Code=500 ", "actual Status Code=500");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=500 ", "actual Status Code=" + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1704_TC05":// 404(Not Found)
				// hcid = getCellValue("HCID");
				statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 404) {
					log(PASS, "Expected status Code=404 ", "actual Status Code=404");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=404 ", "actual Status Code=" + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1704_TC06":// 503
				// hcid = getCellValue("HCID");
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 503) {
					log(PASS, "Expected status Code=503 ", "actual Status Code=503", true);

				} else {
					setIgnoreLastTestFailure(true);
					log(FAIL, "Expected status Code=503 ", "actual Status Code=" + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1704_TC07":
				// statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					log(PASS, "Expected status Code=200 ", "actual Status Code=200");
					compareapplicationValues(reportPath, applicationTotalDetails);

				} else {
					setIgnoreLastTestFailure(true);
					log(FAIL, "Expected status Code=200 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1704_TC08":
				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 404) {
					log(PASS, "Expected status Code=404 ", "actual Status Code=404");

				} else {
					setIgnoreLastTestFailure(true);
					log(FAIL, "Expected status Code=404 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;

			case "ACCREM-1704_TC09":

				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					log(PASS, "Expected status Code=200 ", "actual Status Code=200");
					compareapplicationValues(reportPath, applicationTotalDetails);

				} else {
					setIgnoreLastTestFailure(true);
					log(FAIL, "Expected status Code=200 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1704_TC10":

				statusCode = response.getStatusLine().getStatusCode();
				if(statusCode==200)
				{
					log(PASS, "Validate the HTTP response code ","HTTP response code is 200");



				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code","HTTP response code is "+statusCode+":"+message);
				}
				break;

			default:
				break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while validated application total response");
		}
	}

	/**
	 * @author AF12450 Method to compare the DB values and JSON response values
	 * @param resultsPath
	 * @param applicationTotalDetails
	 */
	public void compareapplicationValues(String resultsPath, HashMap<String, HashMap<String, String>> applicationTotalDetails) {
		try {
			String TC_ID = getCellValue("Test_Case_ID");
			String overApplyStatusCode = getCellValue("OVER_APPLY");
			String totalBenefitAmount = getCellValue("BNFT_AMT");
			float ftotalBenefitAmount = Float.parseFloat(totalBenefitAmount);
			String totalBenefitOccurrenceCount = getCellValue("BNFT_OCC");
			String totalBenefitDayCount = getCellValue("BNFT_DAY");
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


					//					if(TC_ID.equalsIgnoreCase("ACCREM-1704_TC10"))
					//					{
					//						
					//						if(dbOverApplyStatus.equals(overApplyStatusCode))
					//						{
					//							
					//						log(PASS,"Expected OverApplyStatus="+overApplyStatusCode,"Actual OverApplyStatus "+dbOverApplyStatus);
					//						}
					//						else
					//						{
					//							setLastTestPassed(false);
					//				
					//						
					//							log(FAIL,"Expected OverApplyStatus"+overApplyStatusCode,"Actual OverApplyStatus "+dbOverApplyStatus);
					//						}
					//
					//						if(fdbTotalBenAmount.equals(ftotalBenefitAmount))
					//						{
					//							
					//							log(PASS,"Expected TotalBenAmount "+ftotalBenefitAmount,"Actual TotalBenAmount "+fdbTotalBenAmount);
					//							
					//						}
					//
					//						else
					//						{
					//							
					//							setLastTestPassed(false);
					//							log(FAIL,"Expected TotalBenAmount "+ftotalBenefitAmount,"Actual TotalBenAmount "+fdbTotalBenAmount);
					//							
					//						}
					//						if(dbTotalBenOcc.equals(totalBenefitOccurrenceCount))
					//						{
					//							log(PASS,"Expected totalBenefitOccurrenceCount "+totalBenefitOccurrenceCount,"Actual totalBenefitOccurrenceCount "+dbTotalBenOcc);
					//						}
					//
					//						else
					//						{
					//							setLastTestPassed(false);
					//							log(FAIL,"Expected totalBenefitOccurrenceCount "+totalBenefitOccurrenceCount,"Actual totalBenefitOccurrenceCount "+dbTotalBenOcc);
					//						}
					//						if(dbTotalBenDay.equals(totalBenefitDayCount))
					//						{
					//							
					//							log(PASS,"Expected totalBenefitDayCount ="+totalBenefitDayCount,"Actual totalBenefitDayCount "+dbTotalBenDay);
					//						}
					//
					//						else
					//						{
					//							setLastTestPassed(false);
					//							
					//						log(FAIL,"Expected totalBenefitDayCount ="+totalBenefitDayCount,"Actual totalBenefitDayCount "+dbTotalBenDay);
					//						}
					//
					//					}
					//

					if (errorMessage.isEmpty()) {
						log(PASS, "Compare the Db and API values for application:" + dbapplicationName + " Member code:" + dbMCode
								+ " Product Type: " + dbProdTypeCode, "Both DB and API values are same");
					} else {
						setLastTestPassed(false);
						log(FAIL,
								"Compare the Db and API values for application:" + dbapplicationName + " Member code:" + dbMCode
								+ " Product Type: " + dbProdTypeCode,
								"Values mismatch for the following fields: " + errorMessage);
					}

					//

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the application details are present in API response",
							"application: " + dbapplicationName + " Member code:" + dbMCode + " Product Type: " + dbProdTypeCode
							+ " is missing in API response");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while comparing DB and api values for application Total API");
		}
	}
	

	/**
	 * @author AF12450 Method to get the application total details from the API
	 *         response
	 * @param response
	 * @return
	 */
	public HashMap<String, HashMap<String, String>> getapplicationTotalDetails(HttpResponse response) {
		HashMap<String, HashMap<String, String>> applicationTotalDetails = new HashMap<>();
		try {
			String tcID = getCellValue("Test_Case_ID");
			String tdsoverApplyStatusCode = getCellValue("OVER_APPLY");
			String tdstotalBenefitAmount = getCellValue("BNFT_AMT");
			String tdstotalBenefitOccurrenceCount = getCellValue("BNFT_OCC");
			String tdstotalBenefitDayCount = getCellValue("BNFT_DAY");
			int statusCode = response.getStatusLine().getStatusCode();
			if ((isLastTestPassed() || isIgnoreLastTestFailure()) && statusCode == 200) {

				String responseBody = EntityUtils.toString(response.getEntity());
				String responsePath = getReportPathFolder() + tcID + ".json";
				FileWriter writer = new FileWriter(new File(responsePath));
				BufferedWriter bwriter = new BufferedWriter(writer);

				JSONArray jarray = XMLJSONUtils.getJSONArray(responseBody, "content");
				bwriter.write(jarray.toString());
				bwriter.close();
				for (int i = 0; i < jarray.length(); i++) {
					HashMap<String, String> applicationTotal = new HashMap<>();
					JSONObject jobject = (JSONObject) jarray.get(i);
					JSONObject IDobj = (JSONObject) jobject.get("id");

					String hcId = IDobj.get("hcId").toString();
					applicationTotal.put("hcId", hcId);

					String memberCode = IDobj.get("memberCode").toString();
					applicationTotal.put("memberCode", memberCode);

					String caseId = IDobj.get("caseId").toString();
					applicationTotal.put("caseId", caseId);

					String benefitEffectiveDate = IDobj.get("benefitEffectiveDate").toString();
					applicationTotal.put("benefitEffectiveDate", benefitEffectiveDate);

					String benefitTerminationDate = IDobj.get("benefitTerminationDate").toString();
					applicationTotal.put("benefitTerminationDate", benefitTerminationDate);

					String applicationulatorName = IDobj.get("applicationulatorName").toString();
					applicationTotal.put("applicationulatorName", applicationulatorName);

					String applicationulatorSuffixName = IDobj.get("applicationulatorSuffixName").toString();
					applicationTotal.put("applicationulatorSuffixName", applicationulatorSuffixName);

					String productTypeCode = IDobj.get("productTypeCode").toString();
					applicationTotal.put("productTypeCode", productTypeCode);

					String coveragePlanId = IDobj.get("coveragePlanId").toString();
					applicationTotal.put("coveragePlanId", coveragePlanId);

					String overApplyStatusCode = jobject.get("overApplyStatusCode").toString();
					applicationTotal.put("overApplyStatusCode", overApplyStatusCode);

					String totalBenefitAmount = jobject.get("totalBenefitAmount").toString();
					applicationTotal.put("totalBenefitAmount", totalBenefitAmount);

					String totalBenefitOccurrenceCount = jobject.get("totalBenefitOccurrenceCount").toString();
					applicationTotal.put("totalBenefitOccurrenceCount", totalBenefitOccurrenceCount);

					String totalBenefitDayCount = jobject.get("totalBenefitDayCount").toString();
					applicationTotal.put("totalBenefitDayCount", totalBenefitDayCount);

					String eligibilitySourceCode = jobject.get("eligibilitySourceCode").toString();
					applicationTotal.put("eligibilitySourceCode", eligibilitySourceCode);

					String totalMaxBenefit = jobject.get("totalMaxBenefit").toString();
					applicationTotal.put("totalMaxBenefit", totalMaxBenefit);
					if(tcID.equalsIgnoreCase("ACCREM-1704_TC10"))
					{
						if(overApplyStatusCode.equals(tdsoverApplyStatusCode))
						{
							log(PASS,"Expected overApplyStatusCode="+tdsoverApplyStatusCode,"Actual overApplyStatusCode="+overApplyStatusCode);
						}
						else
						{
							setLastTestPassed(false);
							log(FAIL,"Expected overApplyStatusCode="+tdsoverApplyStatusCode,"Actual overApplyStatusCode="+overApplyStatusCode);
						}
						if(totalBenefitAmount.equals(tdstotalBenefitAmount))
						{

							log(PASS,"Expected totalBenefitAmount="+tdstotalBenefitAmount,"Actual totalBenefitAmount="+totalBenefitAmount);
						}
						else
						{
							setLastTestPassed(false);
							log(FAIL,"Expected totalBenefitAmount="+tdstotalBenefitAmount,"Actual totalBenefitAmount="+totalBenefitAmount);
						}
						if(totalBenefitOccurrenceCount.equals(tdstotalBenefitOccurrenceCount))
						{

							log(PASS,"Expected totalBenefitOccurrenceCount="+tdstotalBenefitOccurrenceCount,"Actual totalBenefitOccurrenceCount="+totalBenefitOccurrenceCount);
						}
						else
						{
							setLastTestPassed(false);
							log(FAIL,"Expected totalBenefitOccurrenceCount="+tdstotalBenefitOccurrenceCount,"Actual totalBenefitOccurrenceCount="+totalBenefitOccurrenceCount);
						}
						if(totalBenefitDayCount.equals(tdstotalBenefitDayCount))
						{
							log(PASS,"Expected totalBenefitDayCount="+tdstotalBenefitDayCount,"Actual totalBenefitDayCount="+totalBenefitDayCount);
						}
						else
						{
							setLastTestPassed(false);
							log(FAIL,"Expected totalBenefitDayCount="+tdstotalBenefitDayCount,"Actual totalBenefitDayCount="+totalBenefitDayCount);
						}

					}


					applicationTotalDetails.put(applicationulatorName + productTypeCode + memberCode, applicationTotal);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while retrieving details from the application Total API response");
		}
		return applicationTotalDetails;
	}

	/**
	 * @author AF12450 Method to get the Contract Level Info from the API
	 *         response
	 * @param response
	 * @return
	 */
	public HashMap<String, HashMap<String, String>> getContractLevelInfo(HttpResponse response) {
		HashMap<String, HashMap<String, String>> contractLevelInfo = new HashMap<>();
		try {
			String tcID = getCellValue("Test_Case_ID");
			int statusCode = response.getStatusLine().getStatusCode();
			if ((isLastTestPassed() || isIgnoreLastTestFailure()) && statusCode == 200) {

				String responseBody = EntityUtils.toString(response.getEntity());
				String responsePath = getReportPathFolder() + tcID + ".json";
				FileWriter writer = new FileWriter(new File(responsePath));
				BufferedWriter bwriter = new BufferedWriter(writer);

				JSONArray jarray = XMLJSONUtils.getJSONArray(responseBody, "content");
				bwriter.write(jarray.toString());
				bwriter.close();
				for (int i = 0; i < jarray.length(); i++) {
					HashMap<String, String> contractLevel = new HashMap<>();
					JSONObject jobject = (JSONObject) jarray.get(i);

					Set<String> keys = jobject.keySet();
					Iterator<String> keyItr = keys.iterator();
					while (keyItr.hasNext()) {
						String key = keyItr.next();
						if (!key.equals("id")) {
							String value = jobject.get(key).toString();
							contractLevel.put(key, value);
						} else {
							JSONObject IDobj = (JSONObject) jobject.get("id");
							Set<String> idkeys = IDobj.keySet();
							Iterator<String> idkeyItr = idkeys.iterator();
							while (idkeyItr.hasNext()) {
								String idKey = idkeyItr.next();
								String idValue = "";
								try
								{
									idValue = IDobj.get(idKey).toString().trim();
								}
								catch (JSONException e) {
									//									Ignoring the Jsonexception
								}

								contractLevel.put(idKey, idValue);
							}
						}
					}
					String coveragePlanId = contractLevel.get("coveragePlanId");
					String versionNumber = contractLevel.get("versionNumber");
					String benefitStartDate = contractLevel.get("odsBenefitStartDate");
					contractLevelInfo.put(coveragePlanId + versionNumber+benefitStartDate, contractLevel);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while retrieving details from the application Total API response");
		}
		return contractLevelInfo;
	}

	/**
	 * @author ag14528
	 * @param response
	 * @param region
	 */
	public void validateRestAPIResponse(HttpResponse response, String region) {
		try {

			String TC_ID = getCellValue("Test_Case_ID");
			String hcid = getCellValue("HCID");
			String mbrC = "";
			String caseID = "";
			String benefitEffectiveDate = "";
			String applicationName = "";
			String prodTypeCD = "";
			String cvgPlanID = "";
			String applicationSuffixName = "";
			String str = "";
			int statusCode = response.getStatusLine().getStatusCode();
			String message = response.getStatusLine().toString();
			HashMap<String, HashMap<String, String>> applicationDataDetails = getapplicationDetails(response);

			String query = "";

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
			
			String reportPath = getReportPathFolder() + TC_ID + "_results.xlsx";
			SOAPUtil.get().exportResults(rs, reportPath, query);

			// HashMap<String, String> hciddetails = null;
			switch (TC_ID) {
			case "ACCREM-1714_TC01":// +987A77166
				if (statusCode == 200) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 200");
					compareApplicationValues(reportPath, applicationDataDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1714_TC02":

				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 400) {
					log(PASS, "Expected status Code=400 ", "actual Status Code=400");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=400 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1714_TC03":

				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 401) {
					log(PASS, "Expected status Code=401 ", "actual Status Code=401");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=401 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1714_TC04":

				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 500) {
					log(PASS, "Expected status Code=500 ", "actual Status Code=500");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=500 ", "actual Status Code=" + statusCode + ":" + message);
				}
			case "ACCREM-1714_TC05":

				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 404) {
					log(PASS, "Expected status Code=404 ", "actual Status Code=404");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=404 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1714_TC06":

				statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 503) {
					log(PASS, "Expected status Code=503 ", "actual Status Code=503");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Expected status Code=503 ", "actual Status Code=" + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1714_TC07":
				if (statusCode == 200) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 200");
					compareApplicationValues(reportPath, applicationDataDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1714_TC08":

				if (statusCode == 200) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 200");
					compareApplicationValues(reportPath, applicationDataDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}

				break;

			case "ACCREM-1714_TC09":
				if (statusCode == 200) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 200");
					compareApplicationValues(reportPath, applicationDataDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}
				break;
			case "ACCREM-1714_TC10":
				if (statusCode == 200) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 200");
					compareApplicationValues(reportPath, applicationDataDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while validated application total response");
		}
	}

	/**
	 * @author AF12450
	 * @param response
	 * @param region
	 */
	public void validateContrLvlResponse(HttpResponse response, String region, String dbProfile) {
		try {

			String TC_ID = getCellValue("Test_Case_ID");
			String contractCode = getCellValue("ContractCode");
			String str = "";
			int statusCode = response.getStatusLine().getStatusCode();
			String message = response.getStatusLine().toString();
			HashMap<String, HashMap<String, String>> applicationDTLDetails = getContractLevelInfo(response);

			String query = "";
			String tableName = "";
			if (region.equals("QA")) {
				region = "IM$E-IME6";
				tableName="GNCE6.CONTR_LEV_INFO";
			} else {
				tableName="GNC.CONTR_LEV_INFO";
			}
			query= "Select * from "+tableName+" where COVG_PLN_ID='"+contractCode+"';";
			String dbURL = SOAPUtil.get().getDB2Details(region);
			String[] userInfo = getLoginInfo(dbProfile);
			String username = userInfo[0];
			String password = userInfo[1];
			Connection conn = DatabaseUtils.getConnection(DatabaseConstants.Database_DB2, dbURL, username, password);
			ResultSet rs = DatabaseUtils.executeQuery(conn, query);
			String reportPath = getReportPathFolder() + TC_ID + "_results.xlsx";
			SOAPUtil.get().exportResults(rs, reportPath, query);

			// HashMap<String, String> hciddetails = null;
			switch (TC_ID) {
			case "ACCREM-1713_TC01" :
			case "ACCREM-1713_TC02" :
			case "ACCREM-1713" :
			case "ACCREM-1713_TC04" :
				if (statusCode == 200) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 200");
					compareContrLvlValues(reportPath, applicationDTLDetails);

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}

				break;
			case "ACCREM-1713_TC03" :
				if (statusCode == 404) {
					log(PASS, "Validate the HTTP response code ", "HTTP response code is 404");

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the HTTP response code", "HTTP response code is " + statusCode + ":" + message);
				}

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while validated application total response");
		}
	}



	/**
	 * @author ag14528
	 * @param response
	 * @return
	 */
	public HashMap<String, HashMap<String, String>> getapplicationDetails(HttpResponse response) {
		HashMap<String, HashMap<String, String>> applicationDataDetails = new HashMap<>();
		try {		
			String tcID = getCellValue("Test_Case_ID");
			int statusCode = response.getStatusLine().getStatusCode();
			if ((isLastTestPassed() || isIgnoreLastTestFailure()) && statusCode == 200) {

				String responseBody = EntityUtils.toString(response.getEntity());
				String responsePath = getReportPathFolder() + tcID + ".json";
				FileWriter writer = new FileWriter(new File(responsePath));
				BufferedWriter bwriter = new BufferedWriter(writer);
				if(XMLJSONUtils.isJSONObject(responseBody))
					responseBody="["+responseBody+"]";
				JSONArray jarray = XMLJSONUtils.convertToJSONArray(responseBody);
				bwriter.write(jarray.toString());
				bwriter.close();
				for (int i = 0; i < jarray.length(); i++) {
					HashMap<String, String> applicatoinVaraibale = new HashMap<>();
					JSONObject IDobj = (JSONObject) jarray.get(i);			
					String employeeId = IDobj.get("id").toString();
					applicatoinVaraibale.put("employeeId", employeeId);
					String employeeName = IDobj.get("employee_name").toString();
					applicatoinVaraibale.put("employeeName", employeeName);
					String employeeSalary = IDobj.get("employee_salary").toString();
					applicatoinVaraibale.put("employeeSalary", employeeSalary);
					String employeeAge = IDobj.get("employee_age").toString();
					applicatoinVaraibale.put("employeeAge", employeeAge);
					String profileImage = IDobj.get("profile_image").toString();
					applicatoinVaraibale.put("profileImage", profileImage);
					applicationDataDetails.put(
							employeeId + employeeName + employeeSalary + employeeAge + profileImage,
							applicatoinVaraibale);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while retrieving details from the Application Total API response");
		}
		return applicationDataDetails;
	}

	/**
	 * @author ag14528
	 * @param resultsPath
	 * @param applicationDetails
	 */
	public void compareApplicationValues(String resultsPath, HashMap<String, HashMap<String, String>> applicationDTLDetails) {
		try {
			List<List<String>> dbValue = ExcelUtils.getAllRowValues(resultsPath, "Results", false);
			Iterator<List<String>> itr = dbValue.iterator();
			while (itr.hasNext()) {
				List<String> rowValues = itr.next();

				String dbapplicationName = rowValues.get(4);
				String dbProdTypeCode = rowValues.get(6);
				String dbMCode = rowValues.get(1);
				String dbclaimNumber = rowValues.get(9);
				String dbapplicationulatorReasonCode = rowValues.get(17);
				HashMap<String, String> applicationValues = applicationDTLDetails
						.get(dbclaimNumber + dbapplicationName + dbapplicationulatorReasonCode + dbProdTypeCode + dbMCode);
				if (applicationValues != null) {
					String dbHCID = rowValues.get(0);
					String dbCaseID = rowValues.get(2);
					String dbBeneffDate = rowValues.get(3);
					String dbapplicationulatorSuffixName = rowValues.get(5);
					String dbbenTermDate = rowValues.get(7);

					String dbCoveragePlanID = rowValues.get(8);

					String dbclaimSourceCode = rowValues.get(10);
					String dbserviceStartDate = rowValues.get(11);
					String dbserviceEndDate = rowValues.get(12);
					String dbdetailBenefitAmount = rowValues.get(13);
					String dbdetailBenefitOccurrenceCount = rowValues.get(14);
					String dbdetailBenefitDayCount = rowValues.get(15);
					String dbinnOonCode = rowValues.get(16);

					String dboverApplyStatusCode = rowValues.get(18);
					String dbproviderTaxId = rowValues.get(19);
					String dbproviderNPI = rowValues.get(20);
					String dbproviderLicenseNumber = rowValues.get(21);
					String dbdiagnosisCode = rowValues.get(22);
					String dbapplicationulatorReasonText = rowValues.get(25);
					String dbeligibilitySourceCode = rowValues.get(26);

					String apihcId = applicationValues.get("hcId");
					String apiCaseID = applicationValues.get("caseId");
					String apiBenEffDate = applicationValues.get("benefitEffectiveDate");
					String apiapplicationulatorSuffixName = applicationValues.get("applicationulatorSuffixName");
					String apiBenTermDate = applicationValues.get("benefitTerminationDate");
					String apiCoveragePlanID = applicationValues.get("coveragePlanId");
					String apiclaimNumber = applicationValues.get("claimNumber");
					String apiclaimSourceCode = applicationValues.get("claimSourceCode");
					String apiserviceStartDate = applicationValues.get("serviceStartDate");
					String apiserviceEndDate = applicationValues.get("serviceEndDate");
					String apidetailBenefitAmount = applicationValues.get("detailBenefitAmount");
					String apidetailBenefitOccurrenceCount = applicationValues.get("detailBenefitOccurrenceCount");
					String apidetailBenefitDayCount = applicationValues.get("detailBenefitDayCount");
					String apiinnOonCode = applicationValues.get("innOonCode");
					String apiapplicationulatorReasonCode = applicationValues.get("applicationulatorReasonCode");
					String apioverApplyStatusCode = applicationValues.get("overApplyStatusCode");
					String apiproviderTaxId = applicationValues.get("providerTaxId");
					String apiproviderNPI = applicationValues.get("providerNPI");
					String apiproviderLicenseNumber = applicationValues.get("providerLicenseNumber");
					String apidiagnosisCode = applicationValues.get("diagnosisCode");
					String apiapplicationulatorReasonText = applicationValues.get("applicationulatorReasonText");
					String apieligibilitySourceCode = applicationValues.get("eligibilitySourceCode");

					String errorMessage = "";

					// Float fdbTotMaxBen = Float.parseFloat(dbTotMaxBen);
					// Float fapiTotMaxBen = Float.parseFloat(apiTotMaxBen);

					Float fdbdetailBenefitAmount = Float.parseFloat(dbdetailBenefitAmount);
					Float fapidetailBenefitAmount = Float.parseFloat(apidetailBenefitAmount);

					if (!(dbHCID.equals(apihcId))) {
						errorMessage = errorMessage + "HCID,";
					}
					if (!(dbCaseID.equals(apiCaseID))) {
						errorMessage = errorMessage + "Case ID,";
					}
					if (!(dbBeneffDate.equals(apiBenEffDate))) {
						errorMessage = errorMessage + "Benefit Effective date,";
					}
					if (!(dbbenTermDate.equals(apiBenTermDate))) {
						errorMessage = errorMessage + "Benefit Term date,";
					}
					if (!(dbapplicationulatorSuffixName.equals(apiapplicationulatorSuffixName))) {
						errorMessage = errorMessage + "Benefit Term date,";
					}
					if (!(dbCoveragePlanID.equals(apiCoveragePlanID))) {
						errorMessage = errorMessage + "Coverage Plan ID,";
					}
					if (!(dbclaimNumber.equals(apiclaimNumber))) {
						errorMessage = errorMessage + "Claim Number,";
					}
					if (!(dbclaimSourceCode.equals(apiclaimSourceCode))) {
						errorMessage = errorMessage + "Claim Source Code,";
					}
					if (!(dbserviceStartDate.equals(apiserviceStartDate))) {
						errorMessage = errorMessage + "Service Start Date,";
					}
					if (!(dbserviceEndDate.equals(apiserviceEndDate))) {
						errorMessage = errorMessage + "Service End Date,";
					}
					if (!(fdbdetailBenefitAmount.equals(fapidetailBenefitAmount))) {
						errorMessage = errorMessage + "Details Benefit Amount,";
					}
					if (!(dbdetailBenefitOccurrenceCount.equals(apidetailBenefitOccurrenceCount))) {
						errorMessage = errorMessage + "Details Benefit Occurence Count,";
					}
					if (!(dbdetailBenefitDayCount.equals(apidetailBenefitDayCount))) {
						errorMessage = errorMessage + "Details Benefit Day Count,";
					}
					if (!(dbinnOonCode.equals(apiinnOonCode))) {
						errorMessage = errorMessage + "InnOonCode,";
					}
					if (!(dbapplicationulatorReasonCode.equals(apiapplicationulatorReasonCode))) {
						errorMessage = errorMessage + "applicationulator Reason Code,";
					}

					if (!(dboverApplyStatusCode.equals(apioverApplyStatusCode))) {
						errorMessage = errorMessage + "Over Apply status Code,";
					}

					if (!(dbproviderTaxId.equals(apiproviderTaxId))) {
						errorMessage = errorMessage + "Provider TaxID,";
					}
					if (!(dbproviderNPI.equals(apiproviderNPI))) {
						errorMessage = errorMessage + "Provider NPI,";
					}
					if (!(dbproviderLicenseNumber.equals(apiproviderLicenseNumber))) {
						errorMessage = errorMessage + "Provider License Number,";
					}
					if (!(dbdiagnosisCode.equals(apidiagnosisCode))) {
						errorMessage = errorMessage + "Diagnosis Code,";
					}
					if (!(dbapplicationulatorReasonText.equals(apiapplicationulatorReasonText))) {
						errorMessage = errorMessage + "applicationulator Reason Text,";
					}
					if (!(dbeligibilitySourceCode.equals(apieligibilitySourceCode))) {
						errorMessage = errorMessage + "Eligible Source code,";
					}

					if (errorMessage.isEmpty()) {
						log(PASS,
								"Compare the Db and API values for application:" + dbapplicationName + "CLaim Number:"
										+ dbclaimNumber + "applicationulator Reason Code : " + dbapplicationulatorReasonCode
										+ " Member code:" + dbMCode + " Product Type: " + dbProdTypeCode,
								"Both DB and API values are same");
					} else {
						setLastTestPassed(false);
						log(FAIL,
								"Compare the Db and API values for application:" + dbapplicationName + "CLaim Number:"
										+ dbclaimNumber + "applicationulator Reason Code : " + dbapplicationulatorReasonCode
										+ " Member code:" + dbMCode + " Product Type: " + dbProdTypeCode,
										"Values mismatch for the following fields: " + errorMessage);
					}

				} else {
					setLastTestPassed(false);
					log(FAIL, "Validate the application details are present in API response",
							" application:" + dbapplicationName + "CLaim Number:" + dbclaimNumber + "applicationulator Reason Code : "
									+ dbapplicationulatorReasonCode + " Member code:" + dbMCode + " Product Type: "
									+ dbProdTypeCode + " is missing in API response");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while comparing DB and api values for application Details API");
		}
	}

	/**
	 * @author AF12450
	 * @param resultsPath
	 * @param applicationDTLDetails
	 */
	public void compareContrLvlValues(String resultsPath, HashMap<String, HashMap<String, String>> applicationDTLDetails) {
		try {
			HashMap<Integer, HashMap<String, String>> dbValue = getAllRowValues(resultsPath, "Results");
			Set<Integer> itr = dbValue.keySet();
			Iterator<Integer> keyItr = itr.iterator();

			HashMap<String, String> headerMap = new HashMap<>();

			headerMap.put("BEN_SRC_SYS", "benefitSourceSystem");
			headerMap.put("COVG_PLN_ID", "coveragePlanId");
			headerMap.put("ODS_BEN_STRT_DT", "odsBenefitStartDate");
			headerMap.put("ODS_BEN_END_DT", "odsBenefitEndDate");
			headerMap.put("MBR_TIER_IND", "memberTierIndicator");
			headerMap.put("VERS_NBR", "versionNumber");
			headerMap.put("SRC_SYS_PROD_TYPE", "sourceSystemProductType");
			headerMap.put("CHDP_PROD_TYPE", "cdhpProductType");
			headerMap.put("CHDP_VNDR", "cdhpVendor");
			headerMap.put("EMBD_IND", "embededIndicator");
			headerMap.put("CONTR_TYPE", "contractType");
			headerMap.put("DED_IN_OOP_IND", "deductibleIncludedOutOfPocketIndicator");
			headerMap.put("DED_IN_TO_OUT_IND", "deductibleInToOutIndicator");
			headerMap.put("DED_OUT_TO_IN_IND", "deductibleOutToInIndicator");
			headerMap.put("OOP_IN_TO_OUT_IND", "oopInToOutIndicator");
			headerMap.put("OOP_OUT_TO_IN_IND", "oopOutToInIndicator");
			headerMap.put("DED_PRF_PAR_IND", "deductiblePreferredParIndicator");
			headerMap.put("OOP_PRF_PAR_IND", "oopPreferredParIndicator");
			headerMap.put("COPAY_IN_OOP_IND", "copayInOutOfPocketIndicator");
			headerMap.put("COPAY_PRF_PAR_IND", "copayPreferredParIndicator");
			headerMap.put("COPAY_IN_TO_OUT_IND", "copayInToOutIndicator");
			headerMap.put("COPAY_OUT_TO_IN_IND", "copayOutToInIndicator");
			headerMap.put("COINS_IN_OOP_IND", "coInsuranceInOutOfPocketIndicator");
			headerMap.put("COINS_PRF_PAR_IND", "coInsurancePreferredParIndicator");
			headerMap.put("COINS_IN_TO_OUT_IND", "coInsuranceInToOutIndicator");
			headerMap.put("COINS_OUT_TO_IN_IND", "coInsuranceOutToInIndicator");
			headerMap.put("LST_QTR_IND", "lastQuarterIndicator");
			headerMap.put("CRY_OVR_RESET_IND", "carryOverResetIndicator");
			headerMap.put("ADLT_OOP_EXCLD_IND", "adultVisionOOPExcludedIndicator");
			headerMap.put("DUAL_UPD_IND", "dualUpdateIndicator");
			headerMap.put("PROV_TIER_CD", "providerTierCode");
			headerMap.put("ST_CD", "stateCode");
			headerMap.put("EXCHNG_IND", "exchangeIndicator");
			headerMap.put("BNFT_PRD_IND", "benefitProductIndicator");
			headerMap.put("VRTN_CD", "variationCode");
			headerMap.put("MRKT_SGMNT_NM", "marketSegmentName");
			headerMap.put("LST_UPDT_DTM", "lastUpdatedTimestamp");
			headerMap.put("LST_UDT_USR_ID", "lastUpdatedUserId");

			String errorMessage = "";
			while (keyItr.hasNext()) {
				Integer key  = keyItr.next();
				HashMap<String, String> rowValue = dbValue.get(key);
				String contractCode = rowValue.get("COVG_PLN_ID");
				String versionNum = rowValue.get("VERS_NBR");
				String ODSBenStartDate = rowValue.get("ODS_BEN_STRT_DT");
				HashMap<String, String> apiValue = applicationDTLDetails.get(contractCode+versionNum+ODSBenStartDate);
				Set<String> header = rowValue.keySet();
				Iterator<String> headerItr = header.iterator();
				while(headerItr.hasNext())
				{
					String headerName = headerItr.next();
					String columnValue = rowValue.get(headerName);
					String responseValue = apiValue.get(headerMap.get(headerName));
					if(headerName.equals("LST_UPDT_DTM"))
					{
						responseValue = DateTimeUtils.convertDateFormat(responseValue, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", "yyyy-MM-dd HH:mm:ss.SSSSSS");
					}
					if(!columnValue.equals(responseValue))
					{
						errorMessage = headerName+": API value:"+responseValue+" DB value:"+columnValue+"\n"+errorMessage;
					}

				}

				String stepName = "Compare the DB and API values for Coverage Plan ID:"+contractCode+" ,version number:"+versionNum+" and ODS Ben Start Date:"+ODSBenStartDate;
				if(errorMessage.isEmpty())
				{
					log(PASS, stepName,"DB and API values are same "
							);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, stepName,"DB and API values are not same for Columns: \n "+errorMessage);
				}
				errorMessage="";
			}
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while comparing DB and api values for application Details API");
		}
	}






	/**
	 * @author ag14528
	 * @param statusCode
	 * @return
	 */
	public String validateStatusCode(int statusCode) {
		switch (statusCode) {
		case 200:
			log(PASS, "Status Code 200 Detail for particular HCID is displayed ");
			return "details for particular HCID can be viewed ";

		case 400:
			log(FAIL, "Status code 400 - BAD REQUEST");
			setLastTestPassed(false);
			return "Bad Request ";

		case 401:
			log(FAIL, "Status code 401 - UNAUTHORIZED");
			setLastTestPassed(false);
			return "Not authorized ";
		case 500:
			log(FAIL, "Status code 500 - INTERNAL SERVER FAILURE");
			setLastTestPassed(false);
			return "Internal Server Failure";
		case 404:
			log(FAIL, "Status code 404 - NOT FOUND");
			setLastTestPassed(false);
			return "Not Found";
		case 503:
			log(FAIL, "Status code 503 - SERVICE UNAVAILABLE");
			setLastTestPassed(false);
			return "Service Unavailable ";
		default:
			break;
		}
		return null;
	}

	/**
	 * @author ag14528
	 * @param region
	 * @param HCID
	 * @return
	 */
	public String getapplicationDetailsURL(String region, String HCID) {
		String url = "";
		switch (region) {
		case "QA":
			url = "http://applications.qa.anthem.com/api/applicationsIo/" + HCID + "/details";
			break;
		case "DEV":
			url = "http://applications.dev.anthem.com/api/applicationsIo/" + HCID + "/details";
			break;

		default:
			throw new IllegalArgumentException("Please provide a valid region");
		}

		return url;

	}
	
	
	
	
	public String getAccessToken()
	{
		String accessToken = "";
		try
		{
			Map<String,String> headers = new HashMap<String, String>();
			Map<String,String> param = new HashMap<String, String>();
			String endPointURL  = "https://sit.api.anthem.com/v1/oauth/accesstoken";
			String auth = "50da739c10bd444d9eb206134f9b00d7:3a541fe66ef1b30bc529a65c5d9221386daa767fbe881ff233445909a99a93b1";
			byte[] encodedBytes = Base64.getEncoder().encode(auth.getBytes());
            String encoding = new String(encodedBytes);
            String apiKey = EnvHelper.getValue("apikey");;
            headers.put("Authorization",String.format("Basic %s",encoding));
            headers.put("apikey", apiKey);
            param.put("grant_type", "client_credentials");
            param.put("scope", "public");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            
            HttpResponse response = RESTWebServiceUtils.getWebServiceResponse(RESTWebServiceUtils.REQUEST_METHOD_POST, endPointURL, headers, param);
            int responseCode = response.getStatusLine().getStatusCode();
            if( responseCode == 200)
            {
            	String responseBody = EntityUtils.toString(response.getEntity());
				accessToken = XMLJSONUtils.getStringValue(responseBody, "access_token");
            }
            else
            {
            	setLastTestPassed(false);
            	log(FAIL, "Access token api dint give response code 200",String.valueOf(responseCode));
            }
            }
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while getting access token");
		}
		return accessToken;
	}
	public static String getMemberapplicationAcesJsonStrFromFile() {
        String jsonString = "";
        String filePath = "src//test//resources//xmlTemplates//GetMemberapplicationAces.txt";
        try {
              StringBuilder contentBuilder = new StringBuilder();

              Map<String, Object> getMemberapplicationMap = new HashMap<String, Object>();
              getMemberapplicationMap.put("application_Suffix_Name", getCellValue("application_Suffix_Name"));
              getMemberapplicationMap.put("application_Suffix", getCellValue("application_Suffix"));
              getMemberapplicationMap.put("benefit_Effective_Date", getCellValue("benefit_Effective_Date"));
              getMemberapplicationMap.put("benefit_Termination_Date", getCellValue("benefit_Termination_Date"));
              getMemberapplicationMap.put("limit_application_Name", getCellValue("limit_application_Name"));
              getMemberapplicationMap.put("application_Indicator", getCellValue("application_Indicator"));
              getMemberapplicationMap.put("coverage_Plan_Identifier", getCellValue("coverage_Plan_Identifier"));
              getMemberapplicationMap.put("page_Number", Integer.parseInt(getCellValue("page_Number")));
              getMemberapplicationMap.put("page_Size", Integer.parseInt(getCellValue("page_Size")));
              getMemberapplicationMap.put("total_Pages", Integer.parseInt(getCellValue("total_Pages")));
              getMemberapplicationMap.put("total_Records", Integer.parseInt(getCellValue("total_Records")));
              getMemberapplicationMap.put("case_Number", getCellValue("case_Number"));
              getMemberapplicationMap.put("member_Code", getCellValue("member_Code"));
              getMemberapplicationMap.put("member_HcId", getCellValue("member_HcId"));
              getMemberapplicationMap.put("member_Tier_Level", getCellValue("member_Tier_Level"));
              getMemberapplicationMap.put("product_Type", getCellValue("product_Type"));
              getMemberapplicationMap.put("request_Id", getCellValue("request_Id"));
              getMemberapplicationMap.put("service_Start_Date", getCellValue("service_Start_Date"));
              getMemberapplicationMap.put("service_End_Date", getCellValue("service_End_Date"));
              getMemberapplicationMap.put("member_Eligibility_SourceId", getCellValue("member_Eligibility_SourceId"));
              getMemberapplicationMap.put("member_Type", getCellValue("member_Type"));
              getMemberapplicationMap.put("source_System", getCellValue("source_System"));
              getMemberapplicationMap.put("target_System", getCellValue("target_System"));              
              Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8);
              stream.forEach(s -> contentBuilder.append(s).append("\n"));
              jsonString = contentBuilder.toString();
              if (getMemberapplicationMap.size() > 0) {
                    for (String key : getMemberapplicationMap.keySet()) {
                          jsonString = SOAPUtil.get().replaceValue(jsonString, key, getMemberapplicationMap.get(key) + "");
                    }
              }

              System.out.println(jsonString);
        } catch (IOException e) {
              e.printStackTrace();
        } catch (Exception e) {
              e.printStackTrace();
              processException(e, "Exception occured in the getEndPointUrl");
        }
        return jsonString;
  }

}
