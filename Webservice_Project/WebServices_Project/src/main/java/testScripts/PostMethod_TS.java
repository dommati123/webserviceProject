package testScripts;


import java.util.HashMap;
import java.util.Map;

import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utils.RESTWebServiceUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utility.CoreSuperHelper;


import utility.ApplicationApiUtility;
import utility.SOAPUtil;


public class PostMethod_TS extends CoreSuperHelper {
	static String url = EnvHelper.getValue("reImagine.URL");
	static String userProfile = EnvHelper.getValue("reImagine.profile");
	static String browserName = EnvHelper.getValue("browser.name");
	static boolean dataFetch = false;
	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:20:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}

	public static void executeScript() {

		try {
			// Method call to control the execution when there is a failure
			setIgnoreLastTestFailure(false);
			String tcDescription = getCellValue("TEST_CASE_DESCRIPTION");
			logExtentReport(tcDescription);
				String apiKey = EnvHelper.getValue("apikey");
           		String addMemberPath =  SOAPUtil.get().getTestDataFileName();
           		String jsonString = SOAPUtil.get().readNotePad(addMemberPath);
           		String accessToken = ApplicationApiUtility.get().getAccessToken();			
           		Map<String,String> headers = new HashMap<String, String>();
           		Map<String,String> param = new HashMap<String, String>();
           		headers.put("apikey", apiKey);
           		headers.put("Authorization", "Bearer "+accessToken);
           		String endPointURL = "http://dummy.restapiexample.com/api/v1/create";
           		String response= RESTWebServiceUtils.getWebServiceResponseAsString(RESTWebServiceUtils.REQUEST_METHOD_POST, endPointURL,headers,param,jsonString);
           		//Validating Response Creation is Done SuccessFully or Not
           		if(!response.contains("\"error\":"))
				{
           			JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
           			log(PASS, "Response Validation","Success: Employee Id ("+jsonObject.get("id").getAsNumber()+")");
				}
				else
				{
					log(FAIL, "Response Validation","Fail: "+ApplicationApiUtility.get().ParseErrMsg(response));
				}           		
		} catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured in the iteration execution");
		} finally {
			seCloseBrowser();
		}
	}
	
	

}
