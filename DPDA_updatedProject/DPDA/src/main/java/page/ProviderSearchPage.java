package page;


import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.ataf.logging.AnthemLogger;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class ProviderSearchPage extends CoreSuperHelper {

	private static ProviderSearchPage thisIsTestObj;
	private static String className = ProviderSearchPage.class.getName();
	private static final AnthemLogger logger = AnthemLogger.getLogger(className);
	/**
	 * <p>
	 * Getter method for the singleton LoginPage instance.
	 * </p>
	 * 
	 * @return the singleton instance of LoginPage
	 */
	public static ProviderSearchPage get() {
		thisIsTestObj = PageFactory.initElements(getWebDriver(), ProviderSearchPage.class);
		return thisIsTestObj;
	}

	@FindBy(how = How.ID, using = "UniqueSearchBy")
	public WebElement uniqueSearchBy;

	@FindBy(how = How.ID, using = "ProviderDPSNo")
	public WebElement DPSProviderNo;

	@FindBy(how = How.ID, using = "SearchCriteria")
	public WebElement searchCriteria;

	@FindBy(how = How.ID, using = "Type1NPIIndividual")
	public WebElement individualNPI;

	@FindBy(how = How.ID, using = "BeginDateTime")
	public WebElement beginDate;

	@FindBy(how = How.ID, using = "EndDate")
	public WebElement endDate;

	@FindBy(how = How.ID, using = "WorkStatus")
	public WebElement caseStatus;

	@FindBy(how = How.XPATH, using = "//table[@pl_prop_class= 'Antm-FW-DPSFW-Data-Provider']")
	public WebElement dpsSearchResultsTable;

	@FindBy(how = How.XPATH, using = "//table[@pl_prop_class='Antm-FW-DPSFW-Work']")
	public WebElement caseSearchResultsTable;

	@FindBy(how = How.XPATH, using = "//table[@id='grid-desktop-paginator']")
	public WebElement searchResultsTblPaginator;

	@FindBy(how = How.XPATH, using = "//*[@id='grid-desktop-paginator']//a[contains(., 'Next')]")
	public WebElement searchResultsNextLink;

	@FindBy(how = How.XPATH, using = "//input[@type='radio']")
	public WebElement selectRadioButton;

	@FindBy(how = How.ID, using = "LGName")
	public WebElement selectLargeGroup;

	@FindBy(how = How.XPATH, using = "//select[@id='ActionCodeUniqueIDForDC' and @name='$PpyDisplayHarness$pActionCodeUniqueIDForDC']")
	public WebElement selectTransaction;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Transaction has been created')]/following-sibling::div//span//a")
	public WebElement transactionID;

	@FindBy(how = How.ID, using = "DentistFirstName")
	public WebElement dentistFirstName;

	@FindBy(how = How.ID, using = "DentistMiddleInitial")
	public WebElement dentistMiddleName;

	@FindBy(how = How.ID, using = "DentistLastName")
	public WebElement dentistLastName;

	@FindBy(how = How.ID, using = "NameSuffix")
	public WebElement nameSuffix;

	@FindBy(how = How.ID, using = "SpecialtyCode")
	public WebElement SpecialtyCode;

	@FindBy(how = How.ID, using = "SSN")
	public WebElement SSN;

	@FindBy(how = How.ID, using = "LicenseID")
	public WebElement licenseID;

	@FindBy(how = How.ID, using = "LicenseState")
	public WebElement licenseState;

	@FindBy(how=How.LINK_TEXT,using="Next")
	public WebElement paginationNext;
	
	
	@FindAll(@FindBy(how=How.XPATH,using="//input[@type='radio' and contains(@name,'$PD_ProviderSearchAPI')]"))
	public List<WebElement> providerRadioButton;
	
	public void searchProviderByName(String FirstName, String MiddleIntial, String LastName, String Suffix,
			String Specialty, String ssn, String licenseNo, String licState) {
		try {
			if (!FirstName.equals(""))
				seSetText(false, dentistFirstName, FirstName, "Enter the Provider First Name");
			if (!MiddleIntial.equals(""))
				seSetText(false, dentistMiddleName, MiddleIntial, "Enter the Provider Middle Name");
			if (!LastName.equals(""))
				seSetText(false, dentistLastName, LastName, "Enter the Provider Last Name");
			if (!Suffix.equals(""))
				seSetText(false, nameSuffix, Suffix, "Enter the Suffix");
			if (!Specialty.equals(""))
				seSelectText(SpecialtyCode, Specialty, "Enter the Provider Specialty");
			if (!ssn.equals(""))
				seSetText(false, SSN, ssn, "Enter the SSN No");
			if (!licenseNo.equals(""))
				seSetText(false, licenseID, licenseNo, "Enter the Provider Licence No");
			if (!licState.equals(""))
				seSelectText(false, licenseState, licState, "Enter the Provider State");

		} catch (Exception e) {
			// TODO: handle exception
			ExtentReportsUtility.log(ERROR, "Fill Provider details", "Exception while searching with provider details");
			e.printStackTrace();
		}

	}

	public boolean validateAndSelectTransaction(String... validationParams) {
		boolean success = false;
		try {
			int rowId = validateSearchResults(true, validationParams);
			if (rowId > 0) {
				ExtentReportsUtility.log(PASS, "Validate Expected data found", "Record matching the criteria Found.",
						true);
				success = true;
			} else {
				ExtentReportsUtility.log(FAIL, "Validate Expected data found",
						"Record matching the criteria NOT Found.");
			}
//				WebElement webElmt = getWebDriver().findElementByXPath(
//						"//table[@pl_prop_class='Antm-FW-DPSFW-Work']//tr["+(rowId+1)+ "]//td[1]//a");
//				seClick(webElmt, "Click Transaction");
//				success = true;
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean validateAndSelectOffice(String... validationParams) {
		boolean success = false;
		try {
			int rowId = validateSearchResults(false, validationParams);
			if (rowId > 0) {
				WebElement webElmt = getWebDriver()
						.findElementByXPath("//table[@pl_prop_class= 'Antm-FW-DPSFW-Data-Provider']//tr["+(rowId+1)+"]//td[1]//input[@type='radio']");
				seClick(webElmt, "Select Office");
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	String[] dpsSearchIndxVal = new String[] { "FirstName", "MiddleIntial", "LastName", "Suffix", "Specialty",
			"IndividualNPI", "LicenseStatus", "ProviderStatus", "ProviderDPSNo" };

	String[] caseSearchIndxVal = new String[] { "CaseId", "CaseStatus", "Transaction", "WorkGroup", "WorkingStatus",
			"StatusTaken", "AssignedTo", "ProviderDPSNo", "FirstName", "LastName", "MiddleIntial", "NPI" };

	public Integer validateSearchResults(boolean isCaseSearch, String... validationParams) throws Exception {

		boolean chkTableValues = true;
		int rowNum = -1;

		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
		if (isCaseSearch) {
			searchTableResults = new HTMLTableUtils(caseSearchResultsTable);
			searchIndxArr = caseSearchIndxVal;
		} else {
			searchTableResults = new HTMLTableUtils(dpsSearchResultsTable);
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
						searchTableResults = new HTMLTableUtils(caseSearchResultsTable);
					} else {
						searchTableResults = new HTMLTableUtils(dpsSearchResultsTable);
					}
					cellValues = searchTableResults.getAllCellsValues();

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
									colId = DPDAUtils.getProviderSearchHeaderValue(true, searchValIndx);
								} else {
									colId = DPDAUtils.getProviderSearchHeaderValue(false, searchValIndx);
								}

								String cellValue = cellValues[i][colId];
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
								seScrollForWebElement(caseSearchResultsTable);
							} else {
								seScrollForWebElement(dpsSearchResultsTable);
							}
							ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
							seClick(searchResultsNextLink, "Next in Results");
							System.out.println("Click Next.");
							Thread.sleep(1000);
						} else {
							if (isCaseSearch) {
								seScrollForWebElement(caseSearchResultsTable);
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
							seScrollForWebElement(caseSearchResultsTable);
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


	public int searchProviderFromTable(WebElement resultTable, Map<String,Integer> tableHeaders, String[] inputHeaders, String... inputArguments){
		int rowId=-1;
		try {
			boolean isRecordFound=false;
			Thread.sleep(3000);
			seWaitForElementVisibility(10, resultTable);
			HTMLTableUtils searchTable=new HTMLTableUtils(resultTable);
			if(searchTable.getRowsCount()==0) {
				ExtentReportsUtility.log(FAIL, "Search Results from the table","No records found for the given search combination", true);
				setResult(false);
			}else {
				while(!isRecordFound) {
					searchTable=new HTMLTableUtils(resultTable);
					String[][] cellValues= searchTable.getAllCellsValues();
					for(int row=0;row< searchTable.getRowsCount();row++) {
						int index=-1;
						for (String strings : inputHeaders) {
							int col=tableHeaders.get(strings);
							index++;
							if(!inputArguments[index].equals("") && inputArguments[index]!=null) {
								isRecordFound = (cellValues[row][col].equals(inputArguments[index])?true:false);
								System.out.println("Cell Value : "+cellValues[row][col]+" Input value :"+inputArguments[index]);
								if(!isRecordFound)
									break;
							}
						}
						if(isRecordFound) {
							rowId=row;
							break;
						}
					}
					if(!isRecordFound && isPresentAndDisplayed(paginationNext)) {
						seScrollForWebElement(resultTable);
						seClick(paginationNext, "Next");
						Thread.sleep(3000);
					}else {
						break;
					}
				}
			}
		}catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Unecpected exception occurred while searching the data from Results table");
			logger.error(e,"searchProviderFromTable");
			e.printStackTrace();
		}

		return rowId;
	}
	

}
