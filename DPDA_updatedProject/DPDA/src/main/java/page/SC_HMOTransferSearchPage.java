package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class SC_HMOTransferSearchPage extends CoreSuperHelper{
	private static SC_HMOTransferSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton SC_HMOTranseferSearch instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static SC_HMOTransferSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), SC_HMOTransferSearchPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.XPATH,using="//label[text()='State']")
	public  WebElement Lbl_State;
	@FindBy(how=How.ID,using="State")
	public WebElement DropDwn_State;
	
	@FindBy(how=How.ID,using="County")
	public WebElement Txt_Country;
	
	@FindBy(how=How.ID,using="City")
	public WebElement Txt_City;
	
	@FindBy(how=How.ID,using="FrozenOfficeStatus")
	public WebElement DropDwn_FrozenOfficeStatus;
	
	@FindBy(how=How.ID,using="Networks")
	public WebElement DropDwn_Networks;
	
	@FindBy(how=How.XPATH,using="//input[@id='Networks']/following-sibling::i[@class='pi pi-caret-down']")
	public WebElement DropDwn_Networksarrow;
	
	@FindBy(how=How.ID,using="ZipCode")
	public WebElement Txt_ZipCode;
	
	@FindBy(how=How.ID,using="ZipCodeRangeList")
	public WebElement Txt_ZipCodeRange;

	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-HMOTransferSearch']")
	public  WebElement tbl_SearchResult;
	
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-Clinic']")
	 public WebElement tbl_Clinics;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-Network']")
	public  WebElement tbl_network;
	
	
	public WebElement getSpecialistType(String specialistType){
		WebElement specialist = getWebDriver().findElement(By.xpath("//div[@class='multiselect-main multiselect-scroll']//span[text()='"+specialistType+"']"));
		return specialist;
	}

	
	public boolean isValidationRequired() {
		boolean searchTbl=false;
		 searchTbl=getCellValue("V_TIN")!=null && !getCellValue("V_TIN").trim().equals("")
				||getCellValue("V_PDO")!=null && !getCellValue("V_PDO").trim().equals("");
		return searchTbl;
	}
	
	public boolean validateResultSearchData(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(validationParams);
			if (rowId > 0) {
				ExtentReportsUtility.log(PASS, "Validate Expected data found in Search Table", "Record matching the criteria Found.",true);
				success = true;
			}else {
				ExtentReportsUtility.log(FAIL, "Validate Expected data found in Search Table", "Record matching the criteria NOT Found.");
				setResult(false);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	String[] searchResult = new String[] {
			"TIN","PDONumber"};
	

	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for  Search Result is 
	 * TIN,PDO Number
	 * 	</BR>
	 * @return
	 * @throws Exception
	 */
	public Integer validateSearchResults(String... validationParams) throws Exception {
		boolean chkTableValues = true;
		int rowNum = -1;
		
		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
			searchTableResults = new HTMLTableUtils(tbl_SearchResult);
			searchIndxArr = searchResult;
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.
		
		try {
			if (cellValues[0][0].equals("No items"))
				noCases = true;
		}catch (Exception e) {
		}
		
		if (noCases) {
			// No Cases to Compare
			ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.",true);
			setResult(false);
		} else {
			try {
			while (chkTableValues) {
					searchTableResults = new HTMLTableUtils(tbl_SearchResult);
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					int varArgId = 0;
					// Search for all Values 
					
					for (String searchValIndx : searchIndxArr) {
						if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
							int colId = -1;
							
								 colId = DPDAUtils.getHMOTransferearchHeaderValue(searchValIndx);
							
							String cellValue = cellValues[i][colId-1];
							foundVal = (cellValue.equals(validationParams[varArgId])?true:false);
						}
						varArgId++;
					}
					
					if (foundVal) {
						System.out.println("All Elements Found");
						if (searchTableResults.getRowsCount() == 1) {
							rowNum = 2;
						} else {
							rowNum = i+1;
						}
						ExtentReportsUtility.log(PASS, "Table Results", "Record matching the criteria Found.",true);
						chkTableValues = false;
						break;
					}
				}
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					// Click Next Button
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 19 ) {
						seScrollForWebElement(tbl_SearchResult);
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
							seScrollForWebElement(tbl_SearchResult);
						chkTableValues = false;
						ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.", true);
						System.out.println("No Record matching in entire table.");
						setResult(false);
						break;
					}
				} else if(chkTableValues) {
					chkTableValues = false;
					ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.",true);
					System.out.println("No Record matching in entire table.");
					setResult(false);
					break;
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
		
		return rowNum;
	}

	/**
	 * Validate Search table contain data or not, if case search table pass true, else pass false
	 * 	</BR>
	 * @return
	 * @throws Exception
	 */
	public void valDataPresentInTbl() throws Exception {
		boolean chkTableValues = true;
		List<WebElement> clinicsBtn=null;
		List<WebElement> networksBtn=null;
		HTMLTableUtils searchTableResults = null;
		HTMLTableUtils clinicsTableSearch = null;
		HTMLTableUtils networkTableSearch = null;
		String[][] clinicCellValues=null;
		String[][] networkCellValues=null;
			searchTableResults = new HTMLTableUtils(tbl_SearchResult);
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.
		
		try {
			if (cellValues[0][0].equals("No items"))
				noCases = true;
		}catch (Exception e) {
		}
		
		if (noCases) {
			// No Cases to Compare
			ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.",true);
			setResult(false);
		} else {
			try {
			while (chkTableValues) {
					searchTableResults = new HTMLTableUtils(tbl_SearchResult);
					clinicsBtn=getWebDriver().findElements(By.xpath("//button[text()='Clinics']"));
				cellValues= searchTableResults.getAllCellsValues();
				// Verify each table from Clinics button
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					if(i==0) {
						ExtentReportsUtility.log(PASS, "Table Results", "Record Found in table.",true);
					}
					clinicsBtn.get(i).click();
						Thread.sleep(500);
						clinicsTableSearch= new HTMLTableUtils(tbl_Clinics);
						clinicCellValues= clinicsTableSearch.getAllCellsValues();
						
						try {
							if (clinicCellValues[0][0].equals("No items"))
								noCases = true;
						}catch (Exception e) {
						}
						if(noCases) {
							ExtentReportsUtility.log(FAIL, "Office Table Results", "No Cases in Offices results table.",true);
							setResult(false);
						}else {
							
						// Verify each network table
						networksBtn=getWebDriver().findElements(By.xpath("//button[text()='Network']"));
						for(int j=0;j<clinicsTableSearch.getRowsCount();j++) {
						networksBtn.get(j).click();
						Thread.sleep(500);
						networkTableSearch= new HTMLTableUtils(tbl_network);
						networkCellValues= networkTableSearch.getAllCellsValues();
						
						
						try {
							if (networkCellValues[0][0].equals("No cases"))
								noCases = true;
						}catch (Exception e) {
						}
						
						if(noCases) {
							ExtentReportsUtility.log(FAIL, "Network Table Results", "No Cases in Network results table.",true);
							setResult(false);
						}
						}
						}
						seClick(SC_HMOTransferSearchPage.get().Lbl_State, "Click State label to remove popup office table");
						Thread.sleep(500);
				}
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 19 ) {
							seScrollForWebElement(tbl_SearchResult);
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(500);
					} else {
							seScrollForWebElement(tbl_SearchResult);
						chkTableValues = false;
						break;
					}
				}
				else if(chkTableValues) {
					chkTableValues = false;
					break;
				}
			}
			seClick(SC_HMOTransferSearchPage.get().Lbl_State, "State Label");
			Thread.sleep(500);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
	}
}
