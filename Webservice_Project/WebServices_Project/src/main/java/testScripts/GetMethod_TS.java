package testScripts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import com.anthem.selenium.utility.EnvHelper;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.ExcelUtils;
import com.anthem.selenium.utils.RESTWebServiceUtils;
import utility.CoreSuperHelper;
import utility.apiTesting;
import utility.ApplicationApiUtility;


public class GetMethod_TS  extends CoreSuperHelper  {

	static String url = EnvHelper.getValue("reImagine.URL");
	static String userProfile = EnvHelper.getValue("reImagine.profile");
	static String browserName = EnvHelper.getValue("browser.name");
	static String region = EnvHelper.getValue("reImagine.region");
	static int count=0;

	public static void aTAFInitParams() {
		MANUAL_TC_EXECUTION_EFFORT = "00:30:00";
	}

	public static void main(String[] args) {
		aTAFInitParams();
		initiateTestScript();
	}




	public static void executeScript() {
		try {

			//String tc_description = getCellValue("TC_DESCRIPTION");
			//logExtentReport(tc_description);

			setIgnoreLastTestFailure(false);
			//String resultsPath = "null";
			HashMap<String, HashMap<String, String>> applicationTotalDetails = null;
			String TC_ID = getCellValue("Test_Case_ID");
			HttpResponse response;

			String employeeId = getCellValue("EMPLOYEE_ID");
			String employeeName = getCellValue("EMPLOYEE_NAME").toString().trim().toLowerCase();
			String employeeSalary = getCellValue("EMPLOYEE_SALARY");
			String employeeAge = getCellValue("EMPLOYEE_AGE");
			String endPointURL = "http://dummy.restapiexample.com/api/v1/employees";
			int rowCount = getRowCount();
			System.out.println("Rowncount" + rowCount);

			HashMap<String, String> params = new HashMap<>();
			params.put("employeeId", employeeId);
			params.put("employeeName", employeeName);
			params.put("employeeSalary", employeeSalary);
			params.put("employeeAge", employeeAge);


			response = RESTWebServiceUtils.getWebServiceResponse(endPointURL, params);

			HashMap<String, String> applicationValues = ApplicationApiUtility.get().getMemberDetails(response,count);

			String apiEmpID = applicationValues.get("id");
			String apiEmpName = applicationValues.get("employee_name").toString().trim().toLowerCase();
			String apiEmpAge = applicationValues.get("employee_age");
			String apiEmpSalary = applicationValues.get("employee_salary");

			//					if(employeeId.equals(apiEmpID) && employeeName.equals(apiEmpName) && employeeAge.equals(apiEmpAge) && employeeSalary.equals(apiEmpSalary))
			//					{
			//						
			//						
			//						log(PASS, "verification is sucessful Values are <p>"
			//								+ "emplopyee ID : "+employeeId +"</p>"
			//										+ "employee Name : "+employeeName+"</p>"
			//												+ "emplopyee Age : "+employeeAge +"</p>"
			//														+ "employee Salary: "+employeeSalary+"</p>",
			//								"Comaparision is successfull" + true);
			//					}else{
			//						log(FAIL, "verification is NOT sucessful Values are <p>"
			//								+ "emplopyee ID : "+employeeId +"</p>"
			//										+ "employee Name : "+employeeName+"</p>"
			//												+ "emplopyee Age : "+employeeAge +"</p>"
			//														+ "employee Salary: "+employeeSalary+"</p>",
			//								"Comaparision is NOT successfull:<p>"
			//								+ "api EmpID : "+apiEmpID+"</p>"
			//										+ "api Emp Name : "+apiEmpName+"</p>"
			//												+ "api Emp Age : "+apiEmpAge+"</p>"
			//														+ "api Emp Salary : "+apiEmpSalary+"</p>");
			//						if(employeeId.equals(apiEmpID)){
			//							
			//						}
			if(employeeId.equals(apiEmpID)){

				log(PASS,"Expected apiEmpID ="+employeeId,"Actual totalBenefitDayCount =  " +apiEmpID);

			}
			else {
				setLastTestPassed(false);
				log(FAIL,"Expected apiEmpID ="+employeeId,"Actual totalBenefitDayCount =  "+apiEmpID);		
			}

			if(employeeName.equals(apiEmpName)){
				log(PASS,"Expected apiEmpName ="+employeeName,"Actual employeeName =  " +apiEmpName);	
			}
			else {
				setLastTestPassed(false);
				log(FAIL,"Expected apiEmpName ="+employeeName," Actual employeeName=   "+apiEmpName);	
			}
			if(employeeAge.equals(apiEmpAge)){
				log(PASS,"Expected apiEmpAge ="+employeeAge,"Actual employeeAge=  "+apiEmpAge);

			}
			else {
				setLastTestPassed(false);
				log(FAIL,"Expected apiEmpAge ="+employeeAge,"Actual employeeAge = "+apiEmpAge);	
			}
			if(employeeSalary.equals(apiEmpSalary)){
				log(PASS,"Expected apiEmpSalary ="+employeeSalary,"Actual employeeSalary =  "+apiEmpSalary);

			}
			else {
				setLastTestPassed(false);
				log(FAIL,"Expected apiEmpSalary ="+employeeSalary,"Actual employeeSalary = "+apiEmpSalary);		
			}



			System.out.println("count is "+count);
			count++;

		} catch (Exception e) {
			setLastTestPassed(false);
			CoreSuperHelper.processException(e, "Exception occured in execute script");

		} finally {
			setResult("TEST_RESULT", getResult());
			//seCloseBrowser();
		}
	}

}
