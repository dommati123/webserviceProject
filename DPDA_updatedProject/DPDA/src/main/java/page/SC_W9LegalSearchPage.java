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

public class SC_W9LegalSearchPage extends CoreSuperHelper{
	private static SC_W9LegalSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton SC_W9LegalSearch instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static SC_W9LegalSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), SC_W9LegalSearchPage.class);
		return thisIsTestObj;
	}	
	@FindBy(how=How.ID,using="LegalName")
	public WebElement Txt_IRSName;
	
	@FindBy(how=How.ID,using="TaxIDNo")
	public WebElement Txt_TaxIdNo;
	
	@FindBy(how=How.ID,using="SearchCriteria")
	public WebElement sel_searchCriteria;
	
	@FindBy(how=How.ID,using="BeginDateTime")
	public WebElement txt_beginDate;
	
	@FindBy(how=How.ID,using="EndDate")
	public WebElement txt_endDate;
	
	@FindBy(how=How.ID,using="WorkStatus") 
	public WebElement sel_CaseStatus;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-W9']")
	public WebElement tblDpsSearchResult;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Work']")
	public WebElement tblcaseSearch;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Int-Clinics']")
	public WebElement tblOfficeSearch;
	
	@FindBy(how=How.ID,using="W9ID")
	public WebElement Txt_DPSLegalNo;
	
	@FindBy(how=How.ID,using="ActionCodeUniqueIDForDCLegal")
	public WebElement DropDown_SelectTransactionForDCLegal;

	
	
	public void clickOnTransaction(String trnID) throws Exception {
	    try {
	    	WebElement webElmt = getWebDriver().findElementByXPath("//a[contains(text(),'"+trnID+"')]");
	    	if (!seIsAttributePresent(webElmt, "disabled")) {
	    		getWebDriver().findElementByLinkText(trnID).click();
	    		ExtentReportsUtility.log(ApplicationConstants.PASS,"Click Created Transaction", "Transaction Id:"+trnID);
	    	}
	    } catch (AssertionError Ae) {
	    	ExtentReportsUtility.log(ApplicationConstants.FAIL,"Click Created Transaction", "Unable to Click Transaction with Id:"+trnID);
	    }
	}
	
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
		boolean caseTble=(getCellValue("V_CaseID")!=null && !getCellValue("V_CaseID").trim().equals("")
				||getCellValue("V_CaseStatus")!=null && !getCellValue("V_CaseStatus").trim().equals("")
						||getCellValue("V_Transaction")!=null && !getCellValue("V_Transaction").trim().equals("")
								||getCellValue("V_WorkGroup")!=null && !getCellValue("V_Transaction").trim().equals("")
										||getCellValue("V_WorkingStatus")!=null && !getCellValue("V_Transaction").trim().equals("")
												||getCellValue("V_StatusTaken")!=null && !getCellValue("V_Transaction").trim().equals("")
														||getCellValue("V_AssginedTo")!=null && !getCellValue("V_Transaction").trim().equals("")
																||getCellValue("V_IRSName") !=null && !getCellValue("V_Transaction").trim().equals("")
																		||getCellValue("V_TIN")!=null && !getCellValue("V_Transaction").trim().equals("")
																				||getCellValue("V_TINType")!=null && !getCellValue("V_Transaction").trim().equals(""));
		boolean searchTbl=(!caseTble&&getCellValue("V_IRSName")!=null && !getCellValue("V_IRSName").trim().equals("") 
				||getCellValue("V_TIN")!=null && !getCellValue("V_TIN").trim().equals("")
						||getCellValue("V_TINType")!=null && !getCellValue("V_TINType").trim().equals("")
								||getCellValue("V_DPSLegalNo") !=null && !getCellValue("V_DPSLegalNo").trim().equals("")
										||getCellValue("V_TINStatus") !=null && !getCellValue("V_TINStatus").trim().equals("") 
				);
		return searchTbl;
	}
	public boolean validateCaseSearchData(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(true,validationParams);
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
	
	public boolean validateAndSelectOffice(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(false,validationParams);
			if (rowId > 0) {
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@pl_prop_class='Antm-FW-DPSFW-Data-W9']//tr["+rowId+"]//td[1]//input[2]");
				seClick(webElmt, "Select Office");
				success = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	String[] dpsSearchIndxVal = new String[] {
			"IRSName","TIN","TinType","DPSLegalNo","TINStatus"};
	
	String[] caseSearchIndxVal = new String[] {
			"CaseID","CaseStatus","Transaction","WorkGroup","WorkingStatus","StatusTaken","AssginedTo","IRSName","TIN","TINType"};
	

	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for DPS Search is 
	 * IRSName,TIN,TinType,DPSLegalNo,TINStatus
	 * 	</BR>
	 * Order of Parameters for Case Search is 
	 * 	CaseID,CaseStatus,Transaction,WorkGroup,WorkingStatus,StatusTaken,AssginedTo,IRSName,TIN,TINType
	 * @return
	 * @throws Exception
	 */
	public Integer validateSearchResults(boolean isCaseSearch, String... validationParams) throws Exception {
		boolean chkTableValues = true;
		int rowNum = -1;
		
		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
		if (isCaseSearch) {
			searchTableResults = new HTMLTableUtils(tblcaseSearch);
			searchIndxArr = caseSearchIndxVal;
		} else {
			searchTableResults = new HTMLTableUtils(tblDpsSearchResult);
			searchIndxArr = dpsSearchIndxVal;
		}
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
				if (isCaseSearch) {
					searchTableResults = new HTMLTableUtils(tblcaseSearch);
				} else {
					searchTableResults = new HTMLTableUtils(tblDpsSearchResult);
				}
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					int varArgId = 0;
					// Search for all Values 
					
					for (String searchValIndx : searchIndxArr) {
						if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
							int colId = -1;
							
							if (isCaseSearch) {
								 colId = DPDAUtils.getw9LegalHeaderValue(true,searchValIndx);
								
							}else {
								 colId = DPDAUtils.getw9LegalHeaderValue(false,searchValIndx);
							}
							
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
						if (isCaseSearch) {
							seScrollForWebElement(tblcaseSearch);
						} else {
							seScrollForWebElement(tblDpsSearchResult);
						}
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
						if (isCaseSearch) {
							seScrollForWebElement(tblcaseSearch);
						} else {
							seScrollForWebElement(tblDpsSearchResult);
						}
						chkTableValues = false;
						ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.", true);
						System.out.println("No Record matching in entire table.");
						break;
					}
				} else if(chkTableValues) {
					if (isCaseSearch) {
						seScrollForWebElement(tblcaseSearch);
					} else {
						seScrollForWebElement(DropDown_SelectTransactionForDCLegal);
					}
					chkTableValues = false;
					ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.",true);
					System.out.println("No Record matching in entire table.");
					break;
				}
			}
			}catch(Exception e) {
				System.out.println("hi");
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
	public void valDataPresentInTbl(boolean isCaseSearch) throws Exception {
		boolean chkTableValues = true;
		List<WebElement> officeBtn=null;
		List<WebElement> radioSelBtn=null;
		HTMLTableUtils searchTableResults = null;
		HTMLTableUtils officeTableSearch = null;
		String[][] officeCellValues=null;
		if (isCaseSearch) {
			searchTableResults = new HTMLTableUtils(tblcaseSearch);
		} else {
			searchTableResults = new HTMLTableUtils(tblDpsSearchResult);
		}
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		try {
			if (cellValues[0][0].equals("No items"))
				noCases = true;
		}catch (Exception e) {
		}
		
		if (noCases) {
			// No Cases to Compare
			ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.",true);
		} else {
			if(isCaseSearch) {
				ExtentReportsUtility.log(PASS, "Table Results", "Data get displays in Case search Table.",true);
			}else {
			try {
			while (chkTableValues) {
					searchTableResults = new HTMLTableUtils(tblDpsSearchResult);
					officeBtn=getWebDriver().findElements(By.xpath("//button[text()='Offices']"));
					radioSelBtn=getWebDriver().findElements(By.xpath("//input[@type='radio']"));
				cellValues= searchTableResults.getAllCellsValues();
				// Verify each table from office button
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					if(i==0) {
						ExtentReportsUtility.log(PASS, "Table Results", "Record Found in table.",true);
					}
						radioSelBtn.get(i).click();
						officeBtn.get(i).click();
						Thread.sleep(1000);
						officeTableSearch= new HTMLTableUtils(tblOfficeSearch);
						officeCellValues= officeTableSearch.getAllCellsValues();
						try {
							if (officeCellValues[0][0].equals("No cases"))
								noCases = true;
						}catch (Exception e) {
						}
						
						if(noCases) {
							ExtentReportsUtility.log(FAIL, "Office Table Results", "No Cases in Offices results table.",true);
							setResult(false);
						}
						radioSelBtn.get(i).click();
						Thread.sleep(1000);
				}
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					// Click Next Button
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
						if (isCaseSearch) {
							seScrollForWebElement(tblcaseSearch);
						} else {
							seScrollForWebElement(tblDpsSearchResult);
						}
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
							seScrollForWebElement(tblDpsSearchResult);
						chkTableValues = false;
						break;
					}
				}
				else if(chkTableValues) {
						seScrollForWebElement(DropDown_SelectTransactionForDCLegal);
					chkTableValues = false;
					break;
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			}
		} 
		
	}
}
