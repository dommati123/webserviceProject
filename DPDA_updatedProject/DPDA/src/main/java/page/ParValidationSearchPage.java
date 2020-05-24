package page;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;
import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class ParValidationSearchPage extends CoreSuperHelper{

	private static ParValidationSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LoginPage instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static ParValidationSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), ParValidationSearchPage.class);
		return thisIsTestObj;
	}	

	@FindBy(how=How.ID,using="UniqueSearchBy")
	public WebElement uniqueSearchBy;


	@FindBy(how=How.ID,using="Type1NPIIndividual")
	public WebElement IndividualNPI;

	@FindBy(how=How.ID,using="LastName")
	public WebElement lastName;

	@FindBy(how=How.XPATH,using="//div[@node_name='ParticipationValidationSearchScreen']//table[@id='gridLayoutTable']//table")
	public WebElement parValidationSearchResultsTbl;

	@FindBy(how=How.XPATH,using="//table[@id='grid-desktop-paginator']")
	public WebElement searchResultsTblPaginator;

	@FindBy(how=How.ID,using="LicenseState")
	public WebElement licenseState;

	@FindBy(how=How.ID,using="LicenseNo")
	public WebElement licenseNo;

	@FindBy(how=How.ID,using="FirstName")
	public WebElement firstName;


	// Pagination Next Button
	@FindBy(how=How.XPATH,using="//*[@id='grid-desktop-paginator']//a[contains(., 'Next')]")
	public WebElement searchResultsNextLink;

	String[] parValidationSearchIndxVal = new String[] {
			"Provider Name","Credentialing Level","Specialty"};



	public void SelectUniqueSearchByDiffOptions() {
		String uniqueSearchBy=getCellValue("UniqueSearchBy");
		String lastName=getCellValue("LastName");
		String licenseNo=getCellValue("LicenseNo");
		String licenseState=getCellValue("LicenseState");		
		String firstName=getCellValue("FirstName");
		String individualNPI=getCellValue("IndividualNPI");
		try {
			if(uniqueSearchBy!=null && !uniqueSearchBy.trim().equals("")&&uniqueSearchBy.equals("Last Name and License")) {
				seSelectText(ParValidationSearchPage.get().uniqueSearchBy, uniqueSearchBy, "Selecting Dropdown for License State");				
				seSetText(ParValidationSearchPage.get().lastName,lastName.trim(),"Input Last Name" );
				seSelectText(ParValidationSearchPage.get().licenseState, licenseState, "Selecting Dropdown for License State");
				seSetText(ParValidationSearchPage.get().licenseNo,licenseNo.trim() );
			}
			if(uniqueSearchBy!=null && !uniqueSearchBy.trim().equals("") &&uniqueSearchBy.equals("First Name and License")) {
				seSelectText(ParValidationSearchPage.get().uniqueSearchBy, uniqueSearchBy, "Selecting Dropdown for License State");				
				seSetText(ParValidationSearchPage.get().firstName,firstName.trim(),"Input First Name" );
				seSelectText(ParValidationSearchPage.get().licenseState, licenseState, "Selecting Dropdown for License State");
				seSetText(ParValidationSearchPage.get().licenseNo,licenseNo.trim() );
			}

			if(uniqueSearchBy!=null && !uniqueSearchBy.trim().equals("") &&uniqueSearchBy.equals("Last Name and Individual NPI")) {				
				seSelectText(ParValidationSearchPage.get().uniqueSearchBy, uniqueSearchBy, "Selecting Dropdown for License State");				Thread.sleep(5000);
				seSetText(ParValidationSearchPage.get().lastName,lastName.trim(),"Input Last Name" );
				seSetText(ParValidationSearchPage.get().IndividualNPI,individualNPI );
			}
			if(uniqueSearchBy!=null && !uniqueSearchBy.trim().equals("") &&uniqueSearchBy.equals("First Name and Individual NPI")) {
				seSelectText(ParValidationSearchPage.get().uniqueSearchBy, uniqueSearchBy, "Selecting Dropdown for License State");				
				seSetText(ParValidationSearchPage.get().firstName,firstName.trim(),"Input First Name" );
				seSetText(ParValidationSearchPage.get().IndividualNPI,individualNPI);
			}
			if(uniqueSearchBy!=null && !uniqueSearchBy.trim().equals("") &&uniqueSearchBy.equals("License Number and State")) {
				seSelectText(ParValidationSearchPage.get().uniqueSearchBy, uniqueSearchBy, "Selecting Dropdown for License State");				
				seSelectText(ParValidationSearchPage.get().licenseState, licenseState, "Selecting Dropdown for License State");
				seSetText(ParValidationSearchPage.get().licenseNo,licenseNo.trim() );
			}
			if(uniqueSearchBy!=null && !uniqueSearchBy.trim().equals("") &&uniqueSearchBy.equals("Individual NPI")) {
				seSelectText(ParValidationSearchPage.get().uniqueSearchBy, uniqueSearchBy, "Selecting Dropdown for License State");				
				seSetText(ParValidationSearchPage.get().IndividualNPI,individualNPI);
			}

		} catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Verify Unique search with different inputs executed succussfully",
					"Script is not executed succussfully, check snapshot", true);
			e.printStackTrace();
		}
	}



	public boolean validateParValidationData(String... validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(validationParams);
			if (rowId > 0) {
				ExtentReportsUtility.log(PASS, "Validate Expected data", "Record matching the criteria Found.",true);
				success = true;
			}else {
				ExtentReportsUtility.log(FAIL, "Validate Expected data", "Record matching the criteria NOT Found.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public Integer validateSearchResults(String... validationParams) throws Exception {

		boolean chkTableValues = true;
		int rowNum = -1;

		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;

		searchTableResults = new HTMLTableUtils(parValidationSearchResultsTbl);
		searchIndxArr = parValidationSearchIndxVal;


		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.

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

					searchTableResults = new HTMLTableUtils(parValidationSearchResultsTbl);

					cellValues= searchTableResults.getAllCellsValues();
					// Validate Each Record
					for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
						boolean foundVal = true;
						int varArgId = 0;
						// Search for all Values 

						for (String searchValIndx : searchIndxArr) {
							if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
								int colId = -1;

								colId = DPDAUtils.getParValidationSearchHeaderValue(searchValIndx);

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

							seScrollForWebElement(parValidationSearchResultsTbl);

							ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
							seClick(searchResultsNextLink, "Next in Results");
							System.out.println("Click Next.");
							Thread.sleep(1000);
						} else {

							seScrollForWebElement(parValidationSearchResultsTbl);

							chkTableValues = false;
							ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.", true);
							System.out.println("No Record matching in entire table.");
							break;
						}
					} else if(chkTableValues) {

						seScrollForWebElement(parValidationSearchResultsTbl);

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
