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

public class SC_LGSearchPage extends CoreSuperHelper{
	private static SC_LGSearchPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton SC_LGSearchPage instance.</p>
	 * @return the singleton instance of SC_LGSearchPage
	 */
	public static SC_LGSearchPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), SC_LGSearchPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.ID,using="LGName")
	public WebElement Txt_LGName;
	
	@FindBy(how=How.XPATH,using="//label[text()='LG Name']")
	public WebElement Lbl_LGName;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-LGSearchAPI']")
	public WebElement tbl_SearchResult;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-Clinic']")
	public WebElement tbl_ClinicDetails;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW']")
	public WebElement tbl_clinicNpi;
	
	
	@FindBy(how=How.XPATH,using="//div[@node_name='DisplayClinicDetailsForLGSearch']//div[@node_name='pyGridPaginator']")
	public WebElement clinicsPaginator;
	
	@FindBy(how=How.XPATH,using="//div[@node_name='DisplayClinicDetailsForLGSearch']//a[@title='Next Page']")
	public WebElement clinicsNextPage;
	
	static String[] lgSearchResult = new String[] {
			"TIN"};

	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for  Search Result is 
	 * PDONumber,TIN
	 * 	</BR>
	 * @return
	 * @throws Exception
	 */
	public static String getTableData(String columnHeader,String searchTableName) throws Exception {
		String data="";
		String tempData="";
		boolean chkTableValues = true;
//		int rowNum = -1;
		
		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
		if(searchTableName.equals("LGSearch")) {
			searchTableResults = new HTMLTableUtils(SC_LGSearchPage.get().tbl_SearchResult);
		}else {
			searchTableResults = new HTMLTableUtils(LG_GroupDetailsPage.get().Tbl_TinEffectiveDate);
		}
			searchIndxArr = lgSearchResult;
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
			try {
			while (chkTableValues) {
				if(searchTableName.equals("LGSearch")) {
					searchTableResults = new HTMLTableUtils(SC_LGSearchPage.get().tbl_SearchResult);
				}else {
					searchTableResults = new HTMLTableUtils(LG_GroupDetailsPage.get().Tbl_TinEffectiveDate);
				}
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					// Search for all Values 
					
					for (String searchValIndx : searchIndxArr) {
						if (foundVal && columnHeader!=null && !(columnHeader.trim().equals(""))) {
							int colId = -1;
							
								 colId = DPDAUtils.getLGSearchHeaderValue(searchValIndx);
							if(!searchTableName.equals("LGSearch")){
								colId++;
							}
							tempData = cellValues[i][colId-1];
							data=data+","+tempData;
						}
					}
				}
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					// Click Next Button
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
						if(searchTableName.equals("LGSearch")){
						seScrollForWebElement(SC_LGSearchPage.get().tbl_SearchResult);
						}else {
							seScrollForWebElement(LG_GroupDetailsPage.get().Tbl_TinEffectiveDate);
						}
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
						if(searchTableName.equals("LGSearch")){
							seScrollForWebElement(SC_LGSearchPage.get().tbl_SearchResult);
							}else {
								seScrollForWebElement(LG_GroupDetailsPage.get().Tbl_TinEffectiveDate);
							}
						chkTableValues = false;
						break;
					}
				} else if(chkTableValues) {
					chkTableValues = false;
					break;
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
		if (data!=null)
			data=data.substring(1);
		return data;
		
	}
	
	public boolean isValidationRequired() {
		boolean searchTbl=false;
		 searchTbl=getCellValue("V_TIN")!=null && !getCellValue("V_TIN").trim().equals("");
		return searchTbl;
	}
	
	public boolean validateResultSearchData(String validationParams){
		boolean success = false;
		try {
			int rowId = validateSearchResults(validationParams);
			if (rowId > 0) {
				System.out.println("Data found");
				ExtentReportsUtility.log(PASS, "Validate Expected data found", "Record matching the criteria Found.",true);
				success = true;
			}else {
				ExtentReportsUtility.log(FAIL, "Validate Expected data found", "Record matching the criteria NOT Found.");
				setResult(false);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for  Search Result is 
	 * TIN
	 * 	</BR>
	 * @return
	 * @throws Exception
	 */
	public Integer validateSearchResults(String validationParams) throws Exception {
		
		boolean chkTableValues = true;
		int rowNum = -1;
		
		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
			searchTableResults = new HTMLTableUtils(tbl_SearchResult);
			searchIndxArr = lgSearchResult;
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
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					// Search for all Values 
					
					for (String searchValIndx : searchIndxArr) {
						if (foundVal && validationParams!=null && !(validationParams.trim().equals(""))) {
							int colId = -1;
							
								 colId = DPDAUtils.getLGSearchHeaderValue(searchValIndx);
							
							String cellValue = cellValues[i][colId-1];
							foundVal = (cellValue.equals(validationParams)?true:false);
						}
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
	 * @throws Exception
	 */
	public void valDataPresentInTbl() throws Exception {
		boolean chkTableValues = true;
		
		List<WebElement> clinicsDtlsBtn=null;
		List<WebElement> clinicNPIBtn=null;
		HTMLTableUtils searchTableResults = null;
		HTMLTableUtils clinicsDtlsTableSearch = null;
		HTMLTableUtils clinicNPITableSearch = null;
		String[][] clinicDtlsCellValues=null;
		String[][] clinicNPICellValues=null;
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
		} else {
			try {
			while (chkTableValues) {
					searchTableResults = new HTMLTableUtils(tbl_SearchResult);
					clinicsDtlsBtn=getWebDriver().findElements(By.xpath("//button[text()='Clinic Details']"));
				cellValues= searchTableResults.getAllCellsValues();
				// Verify each table from Clinics button
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					boolean chkClinicValue=true;
					boolean noCasesClinics = false;
					if(i==0) {
						ExtentReportsUtility.log(PASS, "Table Results", "Record Found in table.",true);
					}
					clinicsDtlsBtn.get(i).click();
						Thread.sleep(500);
						clinicsDtlsTableSearch= new HTMLTableUtils(tbl_ClinicDetails);
						clinicDtlsCellValues= clinicsDtlsTableSearch.getAllCellsValues();
						try {
							if (clinicDtlsCellValues[0][0].equals("No items"))
								noCasesClinics = true;
						}catch (Exception e) {
						}
						if(noCasesClinics) {
							ExtentReportsUtility.log(FAIL, "Clinics Details Table Results", "No Cases in Clinic Details results table.",true);
							setResult(false);
						}else {
							
							while (chkClinicValue) {
						// Verify each network table
								clinicsDtlsTableSearch= new HTMLTableUtils(tbl_ClinicDetails);
								clinicDtlsCellValues= clinicsDtlsTableSearch.getAllCellsValues();
						clinicNPIBtn=getWebDriver().findElements(By.xpath("//button[text()='Clinic NPI']"));
						for(int j=0;j<clinicsDtlsTableSearch.getRowsCount();j++) {
							boolean noCasesclnicNPI = false;
							clinicNPIBtn.get(j).click();
						Thread.sleep(500);
						clinicNPITableSearch= new HTMLTableUtils(tbl_clinicNpi);
						clinicNPICellValues= clinicNPITableSearch.getAllCellsValues();
						
						
						try {
							if (clinicNPICellValues[0][0].equals("No cases"))
								noCasesclnicNPI = true;
						}catch (Exception e) {
						}
						
						if(noCasesclnicNPI) {
							ExtentReportsUtility.log(FAIL, "Clinic NPI Table Results", "No Cases in Clinic NPI results table.",true);
							setResult(false);
						}
						seClick(SC_LGSearchPage.get().tbl_ClinicDetails, "Clinic Details Table to remove popup clinic NPI table");
						}
						if (chkClinicValue && isPresentAndDisplayed(clinicsPaginator)) {
							if (isPresentAndDisplayed(clinicsNextPage) && clinicsDtlsTableSearch.getRowsCount() > 9 ) {
								seScrollForWebElement(tbl_SearchResult);
								seClick(clinicsNextPage, "Next in Results");
								System.out.println("Click Next.");
								Thread.sleep(500);
							} else {
									seScrollForWebElement(tbl_SearchResult);
									chkClinicValue = false;
								break;
							}
						}else if(chkClinicValue){
							chkClinicValue = false;
							break;
						}
						}
				}
						Thread.sleep(500);
						clickDPDAElmnt(SC_LGSearchPage.get().Txt_LGName);
						Thread.sleep(500);
				}
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
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
			clickDPDAElmnt(SC_LGSearchPage.get().Txt_LGName);
			Thread.sleep(500);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
	}

	
}
