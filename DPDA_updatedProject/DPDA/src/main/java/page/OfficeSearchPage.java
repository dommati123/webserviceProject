package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;


public class OfficeSearchPage extends CoreSuperHelper{
	
	private static OfficeSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton OfficeSearchPage instance.</p>
	 * @return the singleton instance of OfficeSearchPage
	 */
	public static OfficeSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), OfficeSearchPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.ID,using="SearchCriteria")  //select[@id='SearchCriteria']
	public WebElement selSearchCriteria;
	
	@FindBy(how=How.ID,using="TaxIDNo")  //input[@id='TaxIDNo']
	public WebElement txtTaxIdNo;
	
	@FindBy(how=How.ID,using="LegalName")  //input[@id='LegalName']
	public WebElement txtIRSName;
	
	@FindBy(how=How.ID,using="DBAName")  //input[@id='DBAName']
	public WebElement txtDBAName;
	
	@FindBy(how=How.ID,using="NPI")  //input[@id='NPI']
	public WebElement txtClinicCorpNPI;
	
	@FindBy(how=How.ID,using="PhoneNo")  //input[@id='PhoneNo']
	public WebElement txtPhoneNum;

	@FindBy(how=How.ID,using="AddressType")  //select[@id='AddressType']
	public WebElement selAddressType;
	
	@FindBy(how=How.ID,using="Line1")  //input[@id='Line1']
	public WebElement txtAddressLine1;

	@FindBy(how=How.ID,using="Line2")  //input[@id='Line2']
	public WebElement txtAddressLine2;
	
	@FindBy(how=How.ID,using="Line3")  //input[@id='Line3']
	public WebElement txtAddressLine3;
	
	@FindBy(how=How.ID,using="City")  //input[@id='City']
	public WebElement txtCity;
	
	@FindBy(how=How.ID,using="State")  //select[@id='State']
	public WebElement selState;
	
	@FindBy(how=How.ID,using="County")  //input[@id='County']
	public WebElement txtCounty;
	
	@FindBy(how=How.ID,using="ZipCode")  //input[@id='ZipCode']
	public WebElement txtZipCode;
	
	@FindBy(how=How.ID,using="ClinicDPSNo")  //input[@id='ClinicDPSNo']
	public WebElement txtClinicDPSNo;
	
	@FindBy(how=How.ID,using="BeginDateTime")  //input[@id='BeginDateTime']
	public WebElement txtBeginDate;
	
	@FindBy(how=How.ID,using="EndDate")  //input[@id='EndDate']
	public WebElement txtEndDate;
	
	@FindBy(how=How.ID,using="WorkStatus")  //select[@id='WorkStatus']
	public WebElement selCaseStatus;
	
	@FindBy(how=How.ID,using="ActionCodeUniqueIDForDCClinic")
	public WebElement transactionDrpDown;
	
	//Search results Table with DPS Search - pl_prop="D_ClinicSearchAPI.pxResults"   --- //table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicSearchAPI.pxResults')]
	@FindBy(how=How.XPATH,using="//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicSearchAPI.pxResults')]")
	public WebElement dpsSearchResultsTbl;
	
	//Search results Table with Case Search - //table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicCaseSearch.pxResults')]
	@FindBy(how=How.XPATH,using="//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicCaseSearch.pxResults')]")
	public WebElement caseSearchResultsTbl;
	
	//table[@id='grid-desktop-paginator']
	@FindBy(how=How.XPATH,using="//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicCaseSearch.pxResults')]//ancestor::div[@id='PEGA_GRID_SKIN']//a[contains(., 'Next')]")
	public WebElement searchResultsTblPaginator;
	
	//*[@id="grid-desktop-paginator"]//a[contains(., 'Next')]  -- Pagination Next Button
	@FindBy(how=How.XPATH,using="//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicCaseSearch.pxResults')]//ancestor::div[@id='PEGA_GRID_SKIN']//a[contains(., 'Next')]")
	public WebElement searchResultsNextLink;
	
	//div[contains(text(),'Transaction has been created')]
	@FindBy(how=How.XPATH,using="//div[contains(text(),'Transaction has been created')]")
	public WebElement transactionCreated;
	
	//div[contains(text(),'Transaction has been created')]/following-sibling::div//span//a[contains(text(),'TRN-')]
	@FindBy(how=How.XPATH,using="//div[contains(text(),'Transaction has been created')]/following-sibling::div//span//a[contains(text(),'TRN-')]")
	public WebElement transactionCreatedId;
	
	// Popup Confirm Button
	@FindBy(how=How.XPATH,using="//*[@node_name='ConfirmDate']/div[2]/div//button[text()='Confirm']")
	public WebElement popupConfirmButton;
	
	//a[contains(text(),'TRN-901') and contains(@disabled,'') ]
	public void clickOnPopupConfirmButton(String confirmVal) throws Exception {
	    try {
	    	WebElement webElmt = getWebDriver().findElementByXPath("//*[@node_name='"+confirmVal+"']/div[2]/div//button[text()='Confirm']");
	    	seClick(webElmt, "Confirm");
	    	ExtentReportsUtility.log(ApplicationConstants.PASS,"Click Confirm", "Clicked Confirm Button");
	    } catch (Exception Ae) {
	    	ExtentReportsUtility.log(ApplicationConstants.FAIL,"Click Confirm", "Clicked Confirm Button");
	    }
	}
	
	
	//a[contains(text(),'TRN-901') and contains(@disabled,'') ]
	public boolean isClickOnTransaction(String trnID) throws Exception {
		boolean isClicked = false;
	    try {
	    	WebElement webElmt = getWebDriver().findElementByXPath("//a[contains(text(),'"+trnID+"')]");
	    	if (!seIsAttributePresent(webElmt, "disabled")) {
	    		getWebDriver().findElementByLinkText(trnID).click();
	    		ExtentReportsUtility.log(ApplicationConstants.PASS,"Click Created Transaction", "Transaction Id:"+trnID);
	    		isClicked = true;
	    	}
	    } catch (AssertionError Ae) {
	    	ExtentReportsUtility.log(ApplicationConstants.FAIL,"Click Created Transaction", "Unable to Click Transaction with Id:"+trnID);
	    }
	    return isClicked;
	}
	
	//div[contains(text(),'Case Id')]/following-sibling::div//span[contains(text(),'TRN-905')]
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
	
	public boolean validateAndSelectLGTransaction(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(true,validationParams);
			if (rowId > 0) {
				rowId++;
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicCaseSearch.pxResults')]//tr["+rowId+"]//td[1]//a");
				seClick(webElmt, "Click Transaction");
				success = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean validateAndSelectTransaction(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(true,validationParams);
			if (rowId > 0) {
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicCaseSearch.pxResults')]//tr["+rowId+"]//td[1]//a");
				seClick(webElmt, "Click Transaction");
				success = true;
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
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ClinicSearchAPI.pxResults')]//tr["+rowId+"]//td[1]//input[2]");
				seClick(webElmt, "Select Office");
				success = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	String[] dpsSearchIndxVal = new String[] {
			"DPSClinicNo","TaxIdNo","OfficeStatus","DBAName","LargeGroupName","LegalName","DirectoryAddress1","DirectoryAddress2","City","Zip","County","Phone"};
	
	String[] caseSearchIndxVal = new String[] {
			"CaseId","CaseStatus","Transaction","WorkGroup","WorkingStatus","StatusTaken","AssignedTo","DPSClinicNo","TaxIdNo","DBAName","DirectoryAddress1","City","Zip"};
	
	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for DPS Search is 
	 * 	DPSCLINICNO,TAXIDNO,OFFICESTATUS,DBANAME,LARGEGROUPNAME,LEGALNAME,DIRECTORYADDRESS1,DIRECTORYADDRESS2,CITY, ZIP, COUNTY, PHONE </BR>
	 * Order of Parameters for Case Search is 
	 * 	CASEID,CASESTATUS,TRANSACTION,WORKGROUP,WORKINGSTATUS,STATUSTAKEN,ASSIGNEDTO,DPSCLINICNO,TAXIDNO,DBANAME,DIRECTORYADDRESS1,CITY, ZIP
	 * @return
	 * @throws Exception
	 */
	public Integer validateSearchResults(boolean isCaseSearch, String... validationParams) throws Exception {
		
		boolean chkTableValues = true;
		int rowNum = -1;
		
		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
		if (isCaseSearch) {
			searchTableResults = new HTMLTableUtils(caseSearchResultsTbl);
			searchIndxArr = caseSearchIndxVal;
		} else {
			searchTableResults = new HTMLTableUtils(dpsSearchResultsTbl);
			searchIndxArr = dpsSearchIndxVal;
		}
		
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.
		try {
			if (cellValues[1][0].equals("No cases"))
				noCases = true;
		}catch (Exception e) {
		}
		
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
				if (isCaseSearch) {
					searchTableResults = new HTMLTableUtils(caseSearchResultsTbl);
				} else {
					searchTableResults = new HTMLTableUtils(dpsSearchResultsTbl);
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
								 colId = DPDAUtils.getOfficeSearchHeaderValue(true,searchValIndx.toUpperCase());
							}else {
								 colId = DPDAUtils.getOfficeSearchHeaderValue(false,searchValIndx.toUpperCase());
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
				if (chkTableValues && isPresentAndDisplayed(searchResultsTblPaginator)) {
					// Click Next Button
					if (isPresentAndDisplayed(searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
						if (isCaseSearch) {
							seScrollForWebElement(caseSearchResultsTbl);
						} else {
							seScrollForWebElement(dpsSearchResultsTbl);
						}
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
						if (isCaseSearch) {
							seScrollForWebElement(caseSearchResultsTbl);
						} else {
							seScrollForWebElement(transactionDrpDown);
						}
						chkTableValues = false;
						ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.", true);
						System.out.println("No Record matching in entire table.");
						break;
					}
				} else if(chkTableValues) {
					if (isCaseSearch) {
						seScrollForWebElement(caseSearchResultsTbl);
					} else {
						seScrollForWebElement(transactionDrpDown);
					}
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
	
}