/**
 * 
 */
package utility;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.json.JSONArray;

import com.anthem.selenium.constants.WebServiceConstants;
import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utility.Utility;
import com.anthem.selenium.utils.RESTWebServiceUtils;
import com.anthem.selenium.utils.XMLJSONUtils;

/**
 * @author AF14734
 *
 */
public class RESTAPICalls {
	
	static String GET_CLINICS_SEARCH = "api/clinics";
	static String GET_CLINICS_SEARCH_BY_CLINICID = "api/clinics/{clinicId}";
	static String GET_CLINICS_E9 = "api/clinics/{clinicId}/w9";
	
	static String execEnv = EnvHelper.getValue("execution.env");
	
	static Map<String,String> headerMap = new HashMap<String,String>();
	
	static {
		headerMap.put("DB-User", EnvHelper.getValue("DB_API_USER"));
		
		String[] userInfo = Utility.getLoginInfo(execEnv+"_API_USER");
		String credentials = userInfo[0] + ":" + userInfo[1];
		byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes());
		  		String encodedStr = new String(encodedBytes);
		headerMap.put("Authorization", String.format("Basic %s", encodedStr));
	}
	
	public static String parseAPIURL(String apiURL, String... placeHolderValues) {
		String webServiceURL = "";
		
		String[] tokens = apiURL.split("\\{|\\}");
		int i =0;
		Map<String,String> valuesMap = new HashMap<String,String>();
		try {
			for (String tokenValue : tokens) {
				if (!(tokenValue.startsWith("/")|| tokenValue.endsWith("/")))
					valuesMap.put(tokenValue,placeHolderValues[i++]);
			}
		}catch (Exception e) {
			System.err.println("In correct number of parameters. Provided Values : "+ placeHolderValues.length + " Required Values are more.");
		}
		StrSubstitutor substitutor = new StrSubstitutor(valuesMap);
		substitutor.setVariablePrefix("{");
		
		webServiceURL = substitutor.replace(apiURL);
		return webServiceURL;
	}
	
	public static List<Integer> getClinicIds(String clinicCommonName){
		
		List<Integer> clinicIds = null;
		
		clinicIds = new ArrayList<Integer>();
		
		//https://va33tuvwbs311.wellpoint.com:8443/api/clinics?commonName=John%20A%20Hudec%20Cleveland%20Dental%20Health%20Center%20Inc"
		
		String webServiceUrl = EnvHelper.getValue(execEnv+"_API_URL")+GET_CLINICS_SEARCH;
		
		Map<String,String> paramValuesMap = new HashMap<String,String>();
		
		paramValuesMap.put("commonName", clinicCommonName);
		
		String jsonResponse = RESTWebServiceUtils.getWebServiceResponseAsString(WebServiceConstants.REQUEST_METHOD_GET, webServiceUrl, headerMap, paramValuesMap);
		
		JSONArray clinicIdArray = XMLJSONUtils.getJSONArray(jsonResponse, "[?(@.CommonName =='"+clinicCommonName+"')].Id");
		
		for (Object clinicId : clinicIdArray) {
			clinicIds.add((Integer) clinicId);
		}
		return clinicIds;
	}
	
	public static String getClinicDetails(String clinicId){
		
		String webServiceUrl = parseAPIURL(EnvHelper.getValue(execEnv+"_API_URL")+GET_CLINICS_SEARCH_BY_CLINICID,clinicId);
		
		String jsonResponse = RESTWebServiceUtils.getWebServiceResponseAsString(WebServiceConstants.REQUEST_METHOD_GET, webServiceUrl, headerMap);
		
		return jsonResponse;
	}
	
	public static void main(String[] args) {
		List<Integer> clinicIds = getClinicIds("John A Hudec DDS Specialty Services Inc");
		System.out.println(clinicIds);
	}
	
}
