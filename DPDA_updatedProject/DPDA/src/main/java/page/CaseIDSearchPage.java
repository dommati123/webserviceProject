package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class CaseIDSearchPage extends CoreSuperHelper{
	
	private static CaseIDSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LoginPage instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static CaseIDSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), CaseIDSearchPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.XPATH,using="//input[@id='pyID']")
	public WebElement caseID;
	
	@FindBy(how=How.XPATH, using="//div[@node_name='CaseIdSearchResults']/div[3]//table[@pl_prop='D_CaseIDSearch.pxResults']")
	public WebElement tblCaseSearchResults;
	
	String[] caseSearchIndxArr = new String[] {
			"CaseID","CaseStatus","Transaction","Workgroup","WorkingStatus","StatusTaken","AssignedTo"};
	
	String[] transactionTypeSearch =  new String[] {
			"CaseID","CaseStatus","Transaction","Workgroup","WorkingStatus","StatusTaken","AssignedTo",
			"DPSClinicNo","TaxIDNo","DBAName","DirectoryAddress1","City","Zip"};
	
	String[] inAccuracyTypeSearch =  new String[] {
			"CaseID","CaseStatus","Transaction","Workgroup","WorkingStatus","StatusTaken","AssignedTo",
			"ProviderDPSNo","FirstName","LastName","MiddleInitial","NPI"};
	
	
	
	public boolean validateCaseSearch(Integer caseType, String... validationParams){
		boolean success = false;
		try {
			int rowId = validateCaseSearchResults(caseType,validationParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	public Integer validateCaseSearchResults(Integer caseType,  String... validationParams){
		int rowNum= 1;
		try {
			
			HTMLTableUtils searchResults = null;
			WebElement webTable = null;
			String[] searchIndxArr = null;
			
			if (caseType == 1) {
				webTable = getWebDriver().findElementByXPath("//div[not(contains(@columnlist,'.ClinicDPSNo')) and not(contains(@columnlist,'ProviderDPSNo')) and contains(@columnlist,'pyStatusWork')]//table[@id='gridLayoutTable']//table");
				searchIndxArr = caseSearchIndxArr;
			} else if (caseType == 2) {
				webTable = getWebDriver().findElementByXPath("//div[contains(@columnlist,'.ClinicDPSNo')]//table[@id='gridLayoutTable']//table");
				searchIndxArr = transactionTypeSearch;
			} else if (caseType == 3) {
				webTable = getWebDriver().findElementByXPath("//div[contains(@columnlist,'.ProviderDPSNo')]//table[@id='gridLayoutTable']//table");
				searchIndxArr = inAccuracyTypeSearch;
			}
			System.out.println("################################################");
			searchResults = new HTMLTableUtils(webTable);
			
			String[][] cellValues= searchResults.getAllCellsValues();
			
			boolean noCases = false;
			
			try{
				if (cellValues[0][0].equals("No cases")) {
					noCases = true;
				}
			}catch(Exception e) {
				
			}
			
			if (noCases) {
				// No Cases to Compare
				ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.",true);
			} else {
				int varArgId = 0;
				boolean foundVal = true;
				for (String searchValHeader : searchIndxArr) {
					int colId = -1;
					if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
						System.out.println(searchValHeader + " = "+ DPDAUtils.getCaseIDSearchHeaderValue(searchValHeader));
						colId = DPDAUtils.getCaseIDSearchHeaderValue(searchValHeader);
						String cellValue = cellValues[0][colId];
						ExtentReportsUtility.log(INFO, searchValHeader, searchValHeader+" = "+validationParams[varArgId]);
					//	System.out.println(" Cell Val = "+ cellValue + " | Column Id = "+ colId + " varArgId = "+varArgId +" | Value ="+ validationParams[varArgId]);
						foundVal = (cellValue.equals(validationParams[varArgId])?true:false);
					}
					varArgId++;
				}
				if (foundVal) {
					ExtentReportsUtility.log(PASS, "Case Search Results", "All Results Match",true);
				} else {
					ExtentReportsUtility.log(FAIL, "Case Search Results","All Results do not Match", true);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return rowNum;
	}
	
	public Integer getSearchType(String caseId){
		// Transaction Type
		if (caseId.startsWith("TRN-")) {
			return 2;
		} else if (caseId.startsWith("INQ-")) {
			// Inquiry Type
			return 3;
		} else {
			// Other Case Search (LGDA-## / DA-25 / DA-51-2020 / LGA... /)
			return 1;
		}
		
	}
}
