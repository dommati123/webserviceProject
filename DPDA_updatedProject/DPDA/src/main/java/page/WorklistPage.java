package page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.constants.ApplicationConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;


public class WorklistPage extends CoreSuperHelper{
	
	private static WorklistPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton OfficeSearchPage instance.</p>
	 * @return the singleton instance of OfficeSearchPage
	 */
	public static WorklistPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), WorklistPage.class);
		return thisIsTestObj;
	}	
	
	//Worklist DropdownList
	@FindBy(how=How.XPATH,using="//div[contains(text(),'Worklist')]/following-sibling::div/select")  
	public WebElement selWorklist;
	
	//Workbasket DropdownList
	@FindBy(how=How.XPATH,using="//div[contains(text(),'Workbasket')]/following-sibling::div/select")  
	public WebElement selWorkbasket;
	
	//div[contains(@gpropindex,'D_WorkBasketViewPpxResults')]//table[@id='gridLayoutTable']
	@FindBy(how=How.XPATH,using="//div[contains(@gpropindex,'D_WorkListViewPpxResults')]//table[@id='gridLayoutTable']")  
	public WebElement tblWorklist;
	
	@FindBy(how=How.XPATH,using="//button[text()='Populate Assignee List']")
	public WebElement btnPopulateAssigneeList;
	
	// CASE ID Filter
	//div[contains(@gpropindex,'D_WorkBasketViewPpxResults')]//div[contains(text(),'Case ID')]/ancestor::div[1]/following-sibling::span/a[@id='pui_filter']
	@FindBy(how=How.XPATH,using="//div[contains(@gpropindex,'D_WorkBasketViewPpxResults')]//div[contains(text(),'Case ID')]/ancestor::div[1]/following-sibling::span/a[@id='pui_filter']")  
	public WebElement filterCaseID;
	
	@FindBy(how=How.ID,using="WorkGroup")
	public WebElement selWorkGroup;
	
	@FindBy(how=How.ID,using="pyUserName")
	public WebElement selAssignee;
	
	// Filter Fields
	
	//Search Text Filter Input
	@FindBy(how=How.XPATH,using="//label[text()='Search Text']/following-sibling::div//input")
	public WebElement txtSearchFilter;
	
	//input[@id='pyStartDate']
	@FindBy(how=How.ID,using="pyStartDate")
	public WebElement txtFromDate;
	
	@FindBy(how=How.ID,using="pyEndDate")
	public WebElement txtToDate;
	
	@FindBy(how=How.ID,using="pyStartInteger")
	public WebElement txtStartNumber;
	
	@FindBy(how=How.ID,using="pyEndInteger")
	public WebElement txtEndNumber;
	
	//*[@id="grid-desktop-paginator"]//a[contains(., 'Next')]  -- Pagination Next Button
	@FindBy(how=How.XPATH,using="//*[@node_name='pyGridPaginator']//a[@title='Next Page']")
	public WebElement searchResultsNextLink;
	
	//Transaction Routed Successfully
	@FindBy(how=How.XPATH,using="//span[contains(text(),'Selected') and contains(text(),'routed successfully')]")
	public WebElement msgTrnRoutedSuccess;
	
	
	//a[contains(text(),'TRN-901') and contains(@disabled,'') ]
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
	
	public Integer getValuesCount(List<String> tblHeadList, String... validationParams){
		int valCount = 0;
		
		int varArgId = 0;
		for(String searchVal : tblHeadList) {
			
			if (!searchVal.equals("Select")) {
				if (validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
					valCount++;
				}
				varArgId++;
			}
		}
		return valCount;
	}
	
	
	public void applyFilter(List<String> tblHeadList, String... validationParams) throws InterruptedException {
			
		int varArgId = 0;
			for (String searchVal : tblHeadList) {
				if (validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
					
					WebElement filterElmt = getWebDriver().findElementByXPath("//div[contains(@gpropindex,'D_WorkListViewPpxResults')]//div[text()='"+searchVal+"']/ancestor::div[2]//*[@title='Click to filter']");
					seClick(filterElmt, "Click on Filter for "+searchVal);
			
					seWaitForElementToBeGone(10, CommonElements.get().smallStatusBar);
					
					if (searchVal.equals("Case ID") || searchVal.equals("TIN") || searchVal.equals("Directory Address")
							|| searchVal.equals("City") || searchVal.equals("State") || searchVal.equals("County") 
							|| searchVal.equals("Urgent") || searchVal.equals("Transaction") 
							|| searchVal.equals("Large Group Details") || searchVal.equals("Working Status")
							|| searchVal.equals("Status Taken") || searchVal.equals("Last Name") || searchVal.equals("First Name")
							|| searchVal.equals("NPI (Indv)") || searchVal.equals("License#") || searchVal.equals("License State")
							|| searchVal.equals("Year")) {
						seWaitForElementVisibility(1, txtSearchFilter);
						seSetText(txtSearchFilter,validationParams[varArgId],searchVal);
						seClick(CommonElements.get().btnApply, "Apply");
					} else if (searchVal.equals("Due Date") || searchVal.equals("Receipt Date") || searchVal.equals("Closure Date")
							|| searchVal.equals("Follow-Up Date")) {
						seWaitForElementVisibility(1, txtFromDate);
						seSetText(txtFromDate,validationParams[varArgId],"From Date "+ searchVal);
						seSetText(txtToDate,validationParams[varArgId],"To Date "+ searchVal);
						seClick(CommonElements.get().btnApply, "Apply");
					}  else if (searchVal.equals("Priority") ) {
						seSetText(txtStartNumber,validationParams[varArgId],"Start " + searchVal);
						seSetText(txtEndNumber,validationParams[varArgId],"End " + searchVal);
						seClick(CommonElements.get().btnApply, "Apply");
					}
					seWaitForElementToBeGone(2, CommonElements.get().bigStatusBar);
				}
				if (!searchVal.equals("Select")) {
					varArgId++;
				}
			}
	}

	
	
	public boolean clickAndOpenTransaction() throws InterruptedException{
		boolean success = false;
		try {
			List<String> tblHeadList = getTableHeaders(tblWorklist, null);
			String[] validationParams = getValuesArray(tblHeadList);
			int rowId = validateSearchResults(tblHeadList,validationParams);
			System.out.println("rowId = "+ rowId);
			if (rowId > 0) {
				String caseID = getCellValue("V_CaseID");
				WebElement webElmt = getWebDriver().findElementByXPath("//div[contains(@gpropindex,'D_WorkListViewPpxResults')]//table[@id='gridLayoutTable']//a[contains(text(),'"+caseID+"')]");
				seMoveToElement(webElmt);
				seClick(webElmt, "Click Transaction");
				success = true;
				ExtentReportsUtility.log(PASS, "Search Results","Selected Transaction from Search Results");
			} else {
				ExtentReportsUtility.log(FAIL, "No Search Results","Search Results are not available");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	
	
	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * @return
	 * @throws Exception
	 */
	public Integer validateSearchResults(List<String> tblHeadList, String... validationParams) throws Exception {
		
		boolean chkTableValues = true;
		int rowNum = -1;
	
		HTMLTableUtils searchTableResults = null;
		Thread.sleep(1000);
		searchTableResults = new HTMLTableUtils(tblWorklist);
		
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.
		try {
			if (cellValues[1][0].equals("No work assigned") || cellValues[1][0].equals("No items for the filters applied"))
				noCases = true;
		}catch (Exception e) {
		}
		
		if (noCases) {
			// No Cases to Compare
			ExtentReportsUtility.log(FAIL, "Table Results", "No Cases in results table.",true);
		} else {
			try {
			while (chkTableValues) {
				Thread.sleep(1000);
				searchTableResults = new HTMLTableUtils(tblWorklist);
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 1; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					int varArgId = 0;
					// Search for all Values 
					
					for (String searchVal : tblHeadList) {
						if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
							int colId = -1;
							colId = tblHeadList.indexOf(searchVal);
							
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
				if (chkTableValues && isPresentAndDisplayed(searchResultsNextLink)) {
					// Click Next Button
					if (isPresentAndDisplayed(searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
						seScrollForWebElement(tblWorklist);
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
						seScrollForWebElement(searchResultsNextLink);
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
	
	public void openWorklist() {
		try {
		seClick(false,LeftPanelLinks.get().viewMyWorklist, "View Worklist","Click Menu Link View Worklist");
		seWaitForElementVisibility(10,CommonElements.get().iFrameFirstTab);
		seSwitchToFrame(CommonElements.get().iFrameFirstTab);
		seMoveToCoordinates(400, 100);
		Thread.sleep(1000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	static Map<String,String> tblHeadCellValMap = new HashMap<String,String>();
	
	static {
		tblHeadCellValMap.put("Urgent", "V_Urgent");
		tblHeadCellValMap.put("Due Date", "V_DueDate");
		tblHeadCellValMap.put("Follow-up Date", "V_FollowUpDate");
		tblHeadCellValMap.put("Case ID", "V_CaseID");
		tblHeadCellValMap.put("Receipt Date", "V_ReceiptDate");
		tblHeadCellValMap.put("Transaction", "V_Transaction");
		tblHeadCellValMap.put("Large Group Details", "V_LargeGroupDetails");
		tblHeadCellValMap.put("Working Status", "V_WorkingStatus");
		tblHeadCellValMap.put("Status Taken", "V_StatusTaken");
		tblHeadCellValMap.put("Last Name", "V_LastName");
		tblHeadCellValMap.put("First Name", "V_FirstName");
		tblHeadCellValMap.put("NPI (Indv)", "V_NPI_Indv");
		tblHeadCellValMap.put("License#", "V_LicenseNum");
		tblHeadCellValMap.put("License State", "V_LicenseState");
		tblHeadCellValMap.put("TIN", "V_TIN");
		tblHeadCellValMap.put("Directory Address", "V_DirectoryAddress");
		tblHeadCellValMap.put("City", "V_City");
		tblHeadCellValMap.put("State", "V_State");
		tblHeadCellValMap.put("Priority", "V_Priority");
		tblHeadCellValMap.put("Status of Office", "V_StatusOfOffice");
		tblHeadCellValMap.put("Overall Due Date", "V_OverallDueDate");	
		tblHeadCellValMap.put("Department Due Date", "V_DepartmentDueDate");
		tblHeadCellValMap.put("Credentialing Type", "V_CredentialingType");
		tblHeadCellValMap.put("Credentialing Level", "V_CredentialingLevel");
		tblHeadCellValMap.put("Last Credentialing Completion date", "V_LastCredentialingCompletionDate");
		
	}
	
	public List<String> getTableHeaders(WebElement tblWebElmt, String delimiter) throws Exception {
		Thread.sleep(1000);
		HTMLTableUtils searchTableResults = new HTMLTableUtils(tblWebElmt);
		
		List<String> tableHeadersList = new ArrayList<String>();
		
		if (!(delimiter != null))
			delimiter = " Enter to sort";
		
		for (int i = 1; i<=searchTableResults.getHeaderColumnCount();i++) {
			tableHeadersList.add(searchTableResults.getHeaderColumnName(i).split(delimiter)[0]);
		}
		
		return tableHeadersList;
	}
	
	public String[] getValuesArray(List<String> tableHeadersList) throws Exception {
		
		List<String> valueList = new ArrayList<String>();
		
		for (String tableColHead : tableHeadersList) {
			if ((!tableColHead.equals("Select")) ) {
				if (!tableColHead.trim().equals("")) {
					valueList.add(getCellValue(tblHeadCellValMap.get(tableColHead)));
				} else {
					valueList.add("");
				}
			}
		}
		 String[] valuesArr = new String[valueList.size()];
		 valueList.toArray(valuesArr);
		
		return valuesArr;
	}
	
	public void searchWorklistWorkbasket() throws Exception{
		Thread.sleep(1000);
		
		String workbasketVal = getCellValue("AU_WorkbasketType");
		String worklistVal = getCellValue("AU_WorklistType");
		
		seMoveToCoordinates(100, 100);
		
		
		String workbasketTypeVal = seGetDropDownSelectedText(WorklistPage.get().selWorkbasket);
		String worklistTypeVal = seGetDropDownSelectedText(WorklistPage.get().selWorklist);
		
		if (!workbasketTypeVal.equals(workbasketVal)){
			seSelectText(WorklistPage.get().selWorkbasket, workbasketVal, "Workbasket Value");
		}
		if (!worklistTypeVal.equals(worklistVal)){
			seSelectText(WorklistPage.get().selWorklist, worklistVal, "Worklist Value");
		}
		
		seClick(CommonElements.get().btnRefresh, "Refresh");
		
		List<String> tblHeadList = getTableHeaders(tblWorklist,null);
		String[] valueArr = getValuesArray(tblHeadList);
		
		int valCount = WorklistPage.get().getValuesCount(tblHeadList, valueArr);
		
		if (valCount== 0) {
			ExtentReportsUtility.log(FAIL, "No Filter Provided", "None of the Filter criteria is provided.");
		} else if (valCount>=1){
			applyFilter(tblHeadList, valueArr);
		} 
	}
	
	
}