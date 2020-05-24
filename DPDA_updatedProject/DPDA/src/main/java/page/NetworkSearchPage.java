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

public class NetworkSearchPage extends CoreSuperHelper{

	private static NetworkSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LoginPage instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static NetworkSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), NetworkSearchPage.class);
		return thisIsTestObj;
	}	

	@FindBy(how=How.ID,using="State")
	public WebElement State;
	@FindBy(how=How.ID,using="ActiveStatus")
	public WebElement ActiveStatus;
	@FindBy(how=How.ID,using="County")
	public WebElement County;
	@FindBy(how=How.ID,using="ZipCode")
	public WebElement ZipCode;
	@FindBy(how=How.XPATH,using="//label[@for='Specialties']/following-sibling::div[1]/div/i[1]")	
	public WebElement specialityDropdownBut;

	@FindBy(how=How.ID,using="ZipCodeRangeList")
	public WebElement zipcodeRange;
	@FindBy(how=How.XPATH,using="//label[@for='Networks']/following-sibling::div[1]/div/i[1]")	
	public WebElement networksDropdownBut;
	@FindBy(how=How.XPATH,using="//button[text()='Clinic Details']")
	public WebElement  clinicButton;

	@FindBy(how=How.XPATH,using="//table[@pl_prop='D_NetworkSearchAPI.pxResults']")
	public WebElement  networkSearchResultsTbl;

	public WebElement getSpecialistType(String specialistType){
		WebElement specialist = getWebDriver().findElement(By.xpath("//div[@class='multiselect-main multiselect-scroll']//span[text()='"+specialistType+"']"));
		return specialist;
	}

	public WebElement getNetworkType(String networkType){
		WebElement network = getWebDriver().findElement(By.xpath("//div[@class='multiselect-main multiselect-scroll']//span[text()='"+networkType+"']"));
		return network;
	}

	@FindBy(how=How.XPATH,using="//table[@id='grid-desktop-paginator']")
	public WebElement searchResultsTblPaginator;

	//  -- Pagination Next Button
	@FindBy(how=How.XPATH,using="//*[@id='grid-desktop-paginator']//a[contains(., 'Next')]")
	public WebElement searchResultsNextLink;

	@FindBy(how=How.XPATH,using="//div[@id='modaldialog_hd']//following-sibling::div[1]//table[@id='gridLayoutTable']")
	public WebElement clinicDetailsSearch;

	@FindBy(how=How.ID,using="container_close")
	public WebElement containerClose;

	String[] networkValidationSearchIndxVal = new String[] {
			"Network ID","Network Name"};

	public boolean validateNetworkData(String... validationParams){
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

	public Integer validateSearchResults(String... validationParams) throws Exception {

		boolean chkTableValues = true;
		int rowNum = -1;

		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;

		searchTableResults = new HTMLTableUtils(networkSearchResultsTbl);
		searchIndxArr = networkValidationSearchIndxVal;


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

					searchTableResults = new HTMLTableUtils(networkSearchResultsTbl);

					cellValues= searchTableResults.getAllCellsValues();
					// Validate Each Record
					for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
						boolean foundVal = true;
						int varArgId = 0;
						// Search for all Values 

						for (String searchValIndx : searchIndxArr) {
							if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
								int colId = -1;


								colId = DPDAUtils.getNetworkValidationSearchHeaderValue(searchValIndx);


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

				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 

		return rowNum;

	}


	public void valDataPresentInTbl() throws Exception {
		boolean chkTableValues = true;
		List<WebElement> clinicDetailsBtn=null;
		//List<WebElement> radioSelBtn=null;
		HTMLTableUtils searchTableResults = null;
		HTMLTableUtils clinicDetailsTableSearch = null;
		String[][] clinicDetailsCellvalues=null;

		searchTableResults = new HTMLTableUtils(networkSearchResultsTbl);

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

			ExtentReportsUtility.log(PASS, "Table Results", "Data get displays in Case search Table.",true);

			try {
				while (chkTableValues) {
					//searchTableResults = new HTMLTableUtils(tblDpsSearchResult);
					clinicDetailsBtn=getWebDriver().findElements(By.xpath("//button[text()='Clinic Details']"));
					//radioSelBtn=getWebDriver().findElements(By.xpath("//input[@type='radio']"));
					cellValues= searchTableResults.getAllCellsValues();
					// Verify each table from office button
					for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
						if(i==0) {
							ExtentReportsUtility.log(PASS, "Table Results", "Record Found in table.",true);
						}
						//radioSelBtn.get(i).click();
						clinicDetailsBtn.get(i).click();
						Thread.sleep(1000);
						clinicDetailsTableSearch= new HTMLTableUtils(clinicDetailsSearch);
						clinicDetailsCellvalues= clinicDetailsTableSearch.getAllCellsValues();

						try {
							if (clinicDetailsCellvalues[1][0].equals("No cases"))
								noCases = true;
						}catch (Exception e) {
						}

						try {
							if (!noCases && clinicDetailsCellvalues[0][0].equals("No items"))
								noCases = true;
						}catch (Exception e) {
						}
						if(noCases) {
							ExtentReportsUtility.log(FAIL, "Office Table Results", "No Cases in Offices results table.",true);
						}
						seClick(containerClose, "Click on Close Container when the table got displayed on the screen");
						Thread.sleep(1000);
					}

					if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
						// Click Next Button
						if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
							seScrollForWebElement(networkSearchResultsTbl);
							seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
							System.out.println("Click Next.");
							Thread.sleep(1000);
						} else {
							seScrollForWebElement(networkSearchResultsTbl);
							chkTableValues = false;
							break;
						}
					}

					else if(chkTableValues) {
						chkTableValues = false;
						break;
					}


					Thread.sleep(1000);
				}}catch(Exception e) {
					System.out.println("hi");
					e.printStackTrace();
				}
		} 
	}
}