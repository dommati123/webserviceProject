package utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.anthem.selenium.utils.ExcelUtils;

public class apiTesting extends CoreSuperHelper{

	
	
	public static void readExcelsheet()  {
	String resultsPath = "C:\\aTAF_Application\\data\\GetMethod.xls";
	HashMap<String, HashMap<String, String>> applicationTotalDetails = null;					
	String TC_ID = getCellValue("Test_Case_ID");
	String id = getCellValue("ID");
	String employeeName = getCellValue("EMPLOYEE_NAME");
	String errorMessage = null;
	List<List<String>> dbValue = ExcelUtils.getAllRowValues(resultsPath, "TC101", false);
	Iterator<List<String>> itr = dbValue.iterator();
	while (itr.hasNext()) {
		List<String> rowValues = itr.next();
		id = rowValues.get(4);
		employeeName = rowValues.get(6);
		
		HashMap<String, String> applicationValues = applicationTotalDetails.get(id + employeeName);
		if (applicationValues != null) {
			//String dbHCID = rowValues.get(0);
			
			String apiId = applicationValues.get("id");
			
				
				 if (!(id.equals(apiId))) 
				 { 
					 errorMessage = errorMessage + "id,";
				 }
				 
			}
}
	}
}