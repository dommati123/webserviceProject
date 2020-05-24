package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class SC_HMOPDOSearchPage extends CoreSuperHelper{
	private static SC_HMOPDOSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton SC_HMOPDOSearch instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static SC_HMOPDOSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), SC_HMOPDOSearchPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.ID,using="PDONumber")
	public WebElement Txt_PDONumber;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-HMOPDOSearchAPI']")
	public WebElement tbl_SearchResult;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-Clinic']")
	public WebElement tbl_Offices;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-Network']")
	public WebElement tbl_networks;
	
	public boolean verifyTransactionFrameIsVisibleAndCorrect(String trnID) {
		boolean status = false;
		try {
	    	WebElement webElmt = getWebDriver().findElementByXPath("//div[contains(text(),'Case Id')]/following-sibling::div//span[contains(text(),'"+trnID+"')]");
	    	
	    	if (webElmt.isDisplayed()) {
	    		ExtentReportsUtility.log(ApplicationConstants.PASS,"Click Transaction", "Transaction Id:"+trnID+ " is Loaded.");
	    		status = true;
	    	}
	    } catch (AssertionError Ae) {
	    	ExtentReportsUtility.log(ApplicationConstants.FAIL,"Click Transaction", "Unable to load Transaction Id:"+trnID);
	    }
		return status;
	}
	public boolean isValidationRequired() {
		boolean searchTbl=false;
		 searchTbl=getCellValue("V_PDONumber")!=null && !getCellValue("V_PDONumber").trim().equals("")
				||getCellValue("V_TIN")!=null && !getCellValue("V_TIN").trim().equals("");
		return searchTbl;
	}
	public boolean validateResultSearchData(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(validationParams);
			if (rowId > 0) {
				ExtentReportsUtility.log(PASS, "Validate Expected data found", "Record matching the criteria Found.",true);
				success = true;
			}else {
				ExtentReportsUtility.log(FAIL, "Validate Expected data found", "Record matching the criteria NOT Found.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	String[] searchResult = new String[] {
			"PDONumber","TIN"};

	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for  Search Result is 
	 * PDONumber,TIN
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
							
								 colId = DPDAUtils.getHMOPDOSearchHeaderValue(searchValIndx);
							
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
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
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
						break;
					}
				} else if(chkTableValues) {
					chkTableValues = false;
					ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.",true);
					System.out.println("No Record matching in entire table.");
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
	 * Validate Search table contian data or not, if case search table pass true, else pass false
	 * 	</BR>
	 * @return
	 * @throws Exception
	 */
	public void valDataPresentInTbl() throws Exception {
		boolean chkTableValues = true;
		List<WebElement> officeBtn=null;
		List<WebElement> networksDtlsBtn=null;
		HTMLTableUtils searchTableResults = null;
		HTMLTableUtils officeTableSearch = null;
		HTMLTableUtils networkDtlsTableSearch = null;
		String[][] officeCellValues=null;
		String[][] networkDtlsCellValues=null;
			searchTableResults = new HTMLTableUtils(tbl_SearchResult);
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		try {
			if (!noCases && cellValues[0][0].equals("No items"))
				noCases = true;
		}catch (Exception e) {
		}
		
		if (noCases) {
			// No Cases to Compare
			ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.",true);
		} else {
			try {
			while (chkTableValues) {
					searchTableResults = new HTMLTableUtils(tbl_SearchResult);
					officeBtn=getWebDriver().findElements(By.xpath("//button[text()='Offices']"));
				cellValues= searchTableResults.getAllCellsValues();
				// Verify each table from office button
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					if(i==0) {
						ExtentReportsUtility.log(PASS, "Table Results", "Record Found in table.",true);
					}
						officeBtn.get(i).click();
						Thread.sleep(1000);
						officeTableSearch= new HTMLTableUtils(tbl_Offices);
						officeCellValues= officeTableSearch.getAllCellsValues();
						try {
							if (officeCellValues[0][0].equals("No items"))
								noCases = true;
						}catch (Exception e) {
						}
						if(noCases) {
							ExtentReportsUtility.log(FAIL, "Office Table Results", "No Cases in Offices results table.",true);
						}else {
							officeTableSearch= new HTMLTableUtils(tbl_Offices);
							officeCellValues= officeTableSearch.getAllCellsValues();
							networksDtlsBtn=getWebDriver().findElements(By.xpath("//button[text()='Network Details']"));
							for(int j=0;j<officeTableSearch.getRowsCount();j++) {
								boolean noCasesnetworkDtls = false;
								networksDtlsBtn.get(j).click();
							Thread.sleep(500);
							networkDtlsTableSearch= new HTMLTableUtils(tbl_networks);
							networkDtlsCellValues= networkDtlsTableSearch.getAllCellsValues();
							
							
							try {
								if (networkDtlsCellValues[0][0].equals("No cases")) {
									noCasesnetworkDtls = true;
								setResult(false);// Set TDS result as FAIL
								}
							}catch (Exception e) {
							}
							
							if(!noCasesnetworkDtls) {
								ExtentReportsUtility.log(FAIL, "Clinic Details Table Results", "No Cases in Clinic Details results table.",true);
							}
							clickXYCoordinateOfWebElement(SC_HMOPDOSearchPage.get().tbl_Offices,700,50);
							}
						}
						
						seClick(SC_HMOPDOSearchPage.get().Txt_PDONumber, "Click PDO Number to remove popup office table");
						Thread.sleep(1000);
				}
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					// Click Next Button
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
						seScrollForWebElement(tbl_SearchResult);
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
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
			seClick(SC_HMOPDOSearchPage.get().Txt_PDONumber, "Click PDO Number to remove popup office table");
			Thread.sleep(1000);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
	}
	
	
		
}