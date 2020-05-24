package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class ProviderOfficeSearchPage extends CoreSuperHelper{

	private static ProviderOfficeSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LoginPage instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static ProviderOfficeSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), ProviderOfficeSearchPage.class);
		return thisIsTestObj;
	}	

	@FindBy(how=How.ID,using="UniqueSearchByClinic")
	public WebElement uniqueSearchByOffice;

	@FindBy(how=How.ID,using="UniqueSearchByProv")
	public WebElement uniqueSearchByProvider;

	@FindBy(how=How.ID,using="NPI")
	public WebElement DPSclinicOrCorporateNPI;
	@FindBy(how=How.ID,using="ClinicDPSNo")
	public WebElement ClinicDPSNo;
	@FindBy(how=How.ID,using="ProviderDPSNo")
	public WebElement providerDPSNo;
	@FindBy(how=How.ID,using="Type1NPIIndividual")
	public WebElement type1NPIIndividual;


	@FindBy(how=How.ID,using="Line1")
	public WebElement directoryAddressLine1;
	@FindBy(how=How.ID,using="Line2")
	public WebElement directoryAddressLine2;
	@FindBy(how=How.ID,using="Line3")
	public WebElement directoryAddressLine3;
	@FindBy(how=How.ID,using="City")
	public WebElement city;
	@FindBy(how=How.ID,using="State")
	public WebElement state;
	@FindBy(how=How.ID,using="County")
	public WebElement county;
	@FindBy(how=How.ID,using="ZipCode")
	public WebElement zip;
	@FindBy(how=How.ID,using="PhoneNo")
	public WebElement phoneNo;
	@FindBy(how=How.ID,using="TaxIDNo")
	public WebElement taxId;

	@FindBy(how=How.ID,using="LegalName")
	public WebElement irsName;

	@FindBy(how=How.ID,using="DBAName")
	public WebElement dbaName;
	@FindBy(how=How.ID,using="DentistFirstName")
	public WebElement firstName;
	@FindBy(how=How.ID,using="DentistMiddleInitial")
	public WebElement mi;
	@FindBy(how=How.ID,using="DentistLastName")
	public WebElement lastName;
	@FindBy(how=How.ID,using="NameSuffix")
	public WebElement suffix;
	@FindBy(how=How.ID,using="SSN")
	public WebElement ssn;
	@FindBy(how=How.ID,using="LicenseID")
	public WebElement licenseNo;
	@FindBy(how=How.ID,using="LicenseState")
	public WebElement licenseState;
	@FindBy(how=How.ID,using="BeginDateTime")
	public WebElement beginDateTime;
	@FindBy(how=How.ID,using="EndDate")
	public WebElement endDate;
	@FindBy(how=How.ID,using="WorkStatus")
	public WebElement workStatus;	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class= 'Antm-FW-DPSFW-Work']")
	public WebElement caseSearchResultsTbl;
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class= 'Antm-FW-DPSFW-Data-ProviderClinicAssociation']")
	public WebElement dpsSearchResultsTbl;

	@FindBy(how=How.ID,using="SearchCriteria")
	public WebElement searchCriteria;

	@FindBy(how=How.ID,using="ProviderDPSNo")
	public WebElement providerNo;

	@FindBy(how=How.XPATH,using="//div[@node_name='ProvClinicDPSSearchResults']//table[@id='gridLayoutTable']//table")
	public WebElement  searchResultsTbl;

	@FindBy(how=How.ID,using="ActionCodeUniqueIDForDC")
	public WebElement selectTransaction;

	@FindBy(how=How.XPATH,using="//div[@node_name='ProvClinicDPSSearchResults']//table[@id='gridLayoutTable']//table/tbody/tr[2]//input[@type='radio']")
	public WebElement radioButton;

	@FindBy(how=How.XPATH,using="//div[@data-node-id='NewChildCaseConfirmation']//a[1]")	
	public WebElement transactionID;

	@FindBy(how=How.XPATH,using="//input[@type='radio']")
	public WebElement RadioBtn_SearchResult;
	@FindBy(how=How.XPATH,using="//table[@id='grid-desktop-paginator']")
	public WebElement searchResultsTblPaginator;
	@FindBy(how=How.XPATH,using="//*[@id='grid-desktop-paginator']//a[contains(., 'Next')]")
	public WebElement searchResultsNextLink;
	@FindBy(how=How.ID,using="ActionCodeUniqueIDForDCClinic")
	public WebElement transactionDrpDown;

	public WebElement getSpecialistType(String specialistType){
		WebElement specialist = getWebDriver().findElement(By.xpath("//div[@class='multiselect-main multiselect-scroll']//span[text()='"+specialistType+"']"));
		return specialist;
	}

	public WebElement getNetworkType(String networkType){
		WebElement network = getWebDriver().findElement(By.xpath("//div[@class='multiselect-main multiselect-scroll']//span[text()='"+networkType+"']"));
		return network;
	}

	public void SearchProviderOfficeByName() {
		String DirectoryAddLine1=getCellValue("DirectoryAddLine1");
		String DirectoryAddLine2=getCellValue("DirectoryAddLine2");
		String DirectoryAddLine3=getCellValue("DirectoryAddLine3");
		String City=getCellValue("City");				
		String State=getCellValue("State");
		String County=getCellValue("County");
		String Zip=getCellValue("Zip");
		String PhoneNumber=getCellValue("PhoneNumber");
		String TaxID=getCellValue("TaxID");
		String IRSName=getCellValue("IRSName");
		String DBAName=getCellValue("DBAName");				
		String FirstName=getCellValue("FirstName");
		String MiddleIntial=getCellValue("MiddleIntial");
		String LastName=getCellValue("LastName");
		String Suffix=getCellValue("Suffix");
		String SSN=getCellValue("SSN");
		String LicenseNo=getCellValue("licenseNo");
		String LicenseState=getCellValue("LicenseState");

		try {

			if (!DirectoryAddLine1.equals(""))
				seSetText(directoryAddressLine1, DirectoryAddLine1, "Enter the directoryAddressLine1");
			if (!DirectoryAddLine2.equals(""))
				seSetText(directoryAddressLine2, DirectoryAddLine2, "Enter the directoryAddressLine2");
			if (!DirectoryAddLine3.equals(""))
				seSetText(directoryAddressLine3, DirectoryAddLine3, "Enter the directoryAddressLine3");
			if (!City.equals(""))
				seSetText(city, City, "Enter City");
			if (!State.equals(""))
				seSetText(state, State, "Enter the State");
			if (!County.equals(""))
				seSetText(county, County, "Enter the county");
			if (!Zip.equals(""))
				seSetText(zip, Zip, "Enter the Zip");
			if (!PhoneNumber.equals(""))
				seSetText(phoneNo, PhoneNumber, "Enter the PhoneNumber");
			if (!TaxID.equals(""))
				seSetText(taxId, TaxID, "Enter the TaxID");
			if (!IRSName.equals(""))
				seSetText(irsName, IRSName, "Enter the IRSName");
			if (!DBAName.equals(""))
				seSetText(dbaName, DBAName, "Enter the DBAName");			
			if (!FirstName.equals(""))
				seSetText(firstName, FirstName, "Enter the FirstName");
			if (!MiddleIntial.equals(""))
				seSetText( mi, MiddleIntial, "Enter the MiddleIntial");
			if (!LastName.equals(""))
				seSetText(lastName, LastName, "Enter the  Last Name");
			if (!Suffix.equals(""))
				seSetText( suffix, Suffix, "Enter the Suffix");
			if (!SSN.equals(""))
				seSelectText(ssn, SSN, "Enter the Provider SSN");
			if (!LicenseNo.equals(""))
				seSetText(licenseNo, LicenseNo, "Enter the Provider Licence No");
			if (!LicenseState.equals(""))
				seSelectText( licenseState, LicenseState, "Enter the Provider State");

		} catch (Exception e) {
			// TODO: handle exception
			log(FAIL, "Exception Occurred in Provider search");
		}

	}
	
	public void providerfieldCaseSearch() {
		boolean isCaseSearch = false;
		String beginDate = getCellValue("BeginDate");
		try {
		String searchCriteria = getCellValue("SearchCriteria").trim();
		seSelectText(ProviderSearchPage.get().searchCriteria, searchCriteria, "Select Search Criteria");
		isCaseSearch = true;
        if (beginDate != null && !beginDate.trim().equals(""))
              seSetText(ProviderOfficeSearchPage.get().beginDateTime, beginDate, "BeginDate");

        String endDate = getCellValue("EndDate");
        if (endDate != null && !endDate.trim().equals(""))
              seSetText(ProviderOfficeSearchPage.get().endDate, endDate, "EndDate");

        String caseStatus = getCellValue("CaseStatus");
        if (caseStatus != null && !caseStatus.trim().equals(""))
              seSelectText(OfficeSearchPage.get().selCaseStatus, caseStatus, "Select Case Status");
		}
		catch(Exception e) {
			e.getMessage();
		}
}

	public boolean validateCaseSearchData(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateCaseSearchResults(true,validationParams);
			if (rowId > 0) {
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@id='bodyTbl_right' and contains(@pl_prop,'D_ProviderClinicCaseSearch.pxResults')]//tr["+rowId+"]//td[1]//a");
				seClick(webElmt, "Click Transaction");
				success = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}


	public Integer validateCaseSearchResults(boolean isCaseSearch, String... validationParams) throws Exception {

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
									colId = DPDAUtils.getProviderOfficeSearchHeaderValue(true,searchValIndx);
								}else {
									colId = DPDAUtils.getProviderOfficeSearchHeaderValue(false,searchValIndx);
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

	public boolean validateAndSelectDPSsearch(String... validationParams) {
		boolean success = false;
		try {
			int rowId = validateSearchResults(false, validationParams);
			if (rowId > 0) {
				WebElement webElmt = getWebDriver().findElementByXPath(
						"//table[@pl_prop= 'D_ProvClinicSearchAPI.pxResults']//tr["+(rowId)+"]//td[1]//input[@type='radio']");			
				seClick(webElmt, "Select Office");
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	String[] dpsSearchIndxVal = new String[] { "FirstName", "MiddleIntial", "LastName", "Suffix", "Specialty","Degree",
			"IndividualNPI", "ProviderStatus", "ProviderDPSNo" };

	String[] caseSearchIndxVal = new String[] { "CaseId", "CaseStatus", "Transaction", "WorkGroup", "WorkingStatus",
			"StatusTaken", "AssignedTo", "DPSClinicNo", "TaxIDNo", "DBAName", "DirectoryAddress1", "City" };

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

		String[][] cellValues = searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.
		try {
			if (cellValues[1][0].equals("No cases"))
				noCases = true;
		} catch (Exception e) {
		}

		try {
			if (!noCases && cellValues[0][0].equals("No items"))
				noCases = true;
		} catch (Exception e) {
		}

		if (noCases) {
			// No Cases to Compare
			ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.", true);
		} else {
			try {
				while (chkTableValues) {
					if (isCaseSearch) {
						searchTableResults = new HTMLTableUtils(caseSearchResultsTbl);
					} else {
						searchTableResults = new HTMLTableUtils(dpsSearchResultsTbl);
					}
					cellValues = searchTableResults.getAllCellsValues();
					System.out.println(cellValues);
					// Validate Each Record
					for (int i = 0; i < searchTableResults.getRowsCount(); i++) {
						boolean foundVal = true;
						int varArgId = 0;
						// Search for all Values

						for (String searchValIndx : searchIndxArr) {
							if (foundVal && validationParams[varArgId] != null
									&& !(validationParams[varArgId].trim().equals(""))) {
								int colId = -1;

								if (isCaseSearch) {
									colId = DPDAUtils.getProviderOfficeSearchHeaderValue(true,searchValIndx);
								}else {
									colId = DPDAUtils.getProviderOfficeSearchHeaderValue(false,searchValIndx);
								}

								String cellValue = cellValues[i][colId-1];
								foundVal = (cellValue.equals(validationParams[varArgId]) ? true : false);
							}
							varArgId++;
						}

						if (foundVal) {
							System.out.println("All Elements Found");
							if (searchTableResults.getRowsCount() == 1) {
								rowNum = 2;
							} else {
								rowNum = i + 1;
							}
							ExtentReportsUtility.log(PASS, "Table Results", "Record matching the criteria Found.",
									true);
							chkTableValues = false;
							break;
						}
					}
					if (chkTableValues && isPresentAndDisplayed(searchResultsTblPaginator)) {
						// Click Next Button
						if (isPresentAndDisplayed(searchResultsNextLink) && searchTableResults.getRowsCount() > 9) {
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
								seScrollForWebElement(selectTransaction);
							}
							chkTableValues = false;
							ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.",
									true);
							System.out.println("No Record matching in entire table.");
							break;
						}
					} else if (chkTableValues) {
						if (isCaseSearch) {
							seScrollForWebElement(caseSearchResultsTbl);
						} else {
							seScrollForWebElement(selectTransaction);
						}
						chkTableValues = false;
						ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.", true);
						System.out.println("No Record matching in entire table.");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return rowNum;

	}

}
