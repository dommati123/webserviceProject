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


public class WorkbasketsPage extends CoreSuperHelper{
	
	private static WorkbasketsPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton OfficeSearchPage instance.</p>
	 * @return the singleton instance of OfficeSearchPage
	 */
	public static WorkbasketsPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), WorkbasketsPage.class);
		return thisIsTestObj;
	}	
	
	//Workbasket DropdownList
	@FindBy(how=How.XPATH,using="//div[contains(text(),'Workbasket')]/following-sibling::div/select")  
	public WebElement selWorkbasket;
	
	//div[contains(@gpropindex,'D_WorkBasketViewPpxResults')]//table[@id='gridLayoutTable']
	@FindBy(how=How.XPATH,using="//div[contains(@gpropindex,'D_WorkBasketViewPpxResults')]//table[@id='gridLayoutTable']")  
	public WebElement tblWorkbasket;
	
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
	
	String[] campaignMasterIndx = new String[] {"Due Date","Case ID","Campaign","Year","Working Status","Status Taken","Closure Date"};
	String[] daInaccuraciesIndx = new String[] {"Due Date","Case ID","Receipt Date","Transaction","Large Group Details",
			"Working Status","Status Taken","Last Name","First Name","NPI (Indv)","License#","License State","TIN","Directory Address","City","State"};
	String[] daInquiriesIndx = new String[] {"Urgent","Due Date","Case ID","Receipt Date","Transaction","Large Group Details",
			"Working Status","Status Taken","Last Name","First Name","NPI (Indv)","License#","License State","TIN","Directory Address","City","State"};
	String[] mriIndx = new String[] {"Urgent","Follow-Up Date","Due Date","Case ID","Receipt Date","Transaction",
			"Working Status","Status Taken","Last Name","First Name","NPI (Indv)","License#","License State","TIN","Directory Address","City","State"};
	String[] unresponsiveIndx = new String[] {"Urgent","Case ID","Receipt Date","Transaction","Large Group Details",
			"Working Status","Status Taken","Last Name","First Name","NPI (Indv)","License#","License State","TIN","Directory Address","City","State","County"};
	String[] allOthersIndx = new String[] {"Urgent","Priority","Due Date","Case ID","Receipt Date","Transaction","Large Group Details",
			"Working Status","Status Taken","Last Name","First Name","NPI (Indv)","License#","License State","TIN","Directory Address","City","State"};

	
	
	private String[] getsearchIndxArr(String workbasketTypeVal){
		String[] searchIndxArr = null;
		if (workbasketTypeVal.equals("Campaign Master")) {
			searchIndxArr = campaignMasterIndx;
		} else if (workbasketTypeVal.equals("DA Inaccuracies")) {
			searchIndxArr = daInaccuraciesIndx;
		} else if (workbasketTypeVal.equals("DA Inquiries")) {
			searchIndxArr = daInquiriesIndx;
		} else if (workbasketTypeVal.equals("MRI")) {
			searchIndxArr = mriIndx;
		} else if (workbasketTypeVal.equals("Unresponsive")) {
			searchIndxArr = unresponsiveIndx;
		}  else {
			searchIndxArr = allOthersIndx;
		}
		return searchIndxArr;
	}
	
	
	public Integer getValuesCount(String workbasketTypeVal, String... validationParams){
		int valCount = 0;
		String[] searchIndxArr = getsearchIndxArr(workbasketTypeVal);
		
		int varArgId = 0;
		for(String searchVal : searchIndxArr) {
			if (!searchVal.equals("Select")) {
				if (validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
					valCount++;
				}
				varArgId++;
			}
		}
		return valCount;
	}
	
	
	public void applyFilter(String workbasketTypeVal, String... validationParams) throws InterruptedException {
		String[] searchIndxArr = getsearchIndxArr(workbasketTypeVal);
			
		int varArgId = 0;
			for (String searchVal : searchIndxArr) {
				if (validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
					
					WebElement filterElmt = getWebDriver().findElementByXPath("//div[contains(@gpropindex,'D_WorkBasketViewPpxResults')]//div[text()='"+searchVal+"']/ancestor::div[2]//*[@title='Click to filter']");
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
					seWaitForElementToBeGone(5, CommonElements.get().bigStatusBar);
				}
				if (!searchVal.equals("Select")) {
					varArgId++;
				}
			}
	}

	public boolean selectTransactionAndAssign() throws InterruptedException{
		String workbasketTypeVal = seGetDropDownSelectedText(WorkbasketsPage.get().selWorkbasket);
		String[] valuesArr = WorkbasketsPage.get().getValuesArray(workbasketTypeVal);
		return selectTransactionAndAssign(workbasketTypeVal, getCellValue("AssignToWorkGroup"),getCellValue("AssignToAssignee"),valuesArr);
	}
	
	public boolean selectTransactionAndAssign(String workbasketTypeVal,String assignToWorkGrp, String assignToAssignee, String... validationParams) throws InterruptedException{
		boolean success = false;
		try {
			int rowId = validateSearchResults(workbasketTypeVal,validationParams);
			System.out.println("rowId = "+ rowId);
			if (rowId > 0) {
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@id='bodyTbl_right' and contains(@pl_prop,'D_WorkBasketView.pxResults')]//tr["+rowId+"]//td[1]//input[@type='radio']");
				seMoveToElement(webElmt);
				seClick(webElmt, "Select Transaction");
				success = true;
				ExtentReportsUtility.log(PASS, "Search Results","Selected Transaction from Search Results");
			} else {
				ExtentReportsUtility.log(FAIL, "No Search Results","Search Results are not available");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if (success) {
			Thread.sleep(3000);
			seClick(btnPopulateAssigneeList, "Populate Assign List");
			seWaitForElementVisibility(10, selWorkGroup);
			seSelectText(selWorkGroup, assignToWorkGrp, "Assign To Work Group");
			seSelectText(selAssignee, assignToAssignee, "Assign To Assignee");
			seWaitForElementVisibility(1, selAssignee);
			Thread.sleep(1000);
			String reloginUserProfile = seGetDropDownSelectedValue(selAssignee);
			
			ExtentReportsUtility.log(INFO, "Assigned User",reloginUserProfile);
			setCellValue("RELOGIN_USER_PROFILE_TYPE", reloginUserProfile);
			
			seWaitForElementVisibility(2, CommonElements.get().btnAssign);
			
			seClick(CommonElements.get().btnAssign, "Assign");
			
			seWaitForElementVisibility(5, msgTrnRoutedSuccess);
			
			//Selected DA case: DA-2116 and Associated Transaction cases: have been routed successfully
			if (isElementDisplayed(msgTrnRoutedSuccess)) {
				ExtentReportsUtility.log(PASS, "Transaction Routing",msgTrnRoutedSuccess.getText());
			} else {
				ExtentReportsUtility.log(FAIL, "Transaction Routing",msgTrnRoutedSuccess.getText());
			}
		}
		
		return success;
	}

	
	
	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * @return
	 * @throws Exception
	 */
	public Integer validateSearchResults(String workbasketTypeVal, String... validationParams) throws Exception {
		
		boolean chkTableValues = true;
		int rowNum = -1;
	
		HTMLTableUtils searchTableResults = null;
		Thread.sleep(1000);
		searchTableResults = new HTMLTableUtils(tblWorkbasket);
		
		String[] searchIndxArr = getsearchIndxArr(workbasketTypeVal);
		System.out.println("WorkbasketsPage.validateSearchResults()");
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
		// Check for No Cases.
		try {
			if (cellValues[1][0].equals("Workbasket is empty") || cellValues[1][0].equals("No items for the filters applied"))
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
				searchTableResults = new HTMLTableUtils(tblWorkbasket);
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 1; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					int varArgId = 0;
					// Search for all Values 
					
					for (String searchValIndx : searchIndxArr) {
						if (foundVal && validationParams[varArgId]!=null && !(validationParams[varArgId].trim().equals(""))) {
							int colId = -1;
							
							colId = DPDAUtils.getWorkbasketHeaderValue(workbasketTypeVal,searchValIndx);
							
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
						seScrollForWebElement(tblWorkbasket);
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						seWaitForElementToBeGone(5, CommonElements.get().bigStatusBar);
					} else {
						seScrollForWebElement(searchResultsNextLink);
						chkTableValues = false;
						ExtentReportsUtility.log(FAIL, "Table Results", "No Record in table match the criteria.", true);
						System.out.println("No Record matching in entire table.");
						break;
					}
				} else if(chkTableValues) {
					seScrollForWebElement(btnPopulateAssigneeList);
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
	
	public void openWorkbasket() {
		try {
		seClick(false,LeftPanelLinks.get().viewWorkbaskets, "View Workbaskets","Click Menu Link View Workbaskets");
		seWaitForElementVisibility(10,CommonElements.get().iFrameFirstTab);
		seSwitchToFrame(CommonElements.get().iFrameFirstTab);
		Thread.sleep(1000);
		seMoveToCoordinates(100, 100);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String[] getValuesArray(String workbasketTypeVal) {
		String dueDate = getCellValue("V_DueDate");
		String caseID = getCellValue("V_CaseID");
		String campaign = getCellValue("V_Campaign");
		String year = getCellValue("V_Year");
		String workingStatus = getCellValue("V_WorkingStatus");
		String statusTaken = getCellValue("V_StatusTaken");
		String closureDate = getCellValue("V_ClosureDate");
		String receiptDate = getCellValue("V_ReceiptDate");
		String transaction = getCellValue("V_Transaction");
		String largeGroupDetails = getCellValue("V_LargeGroupDetails");
		String lastName = getCellValue("V_LastName");
		String firstName = getCellValue("V_FirstName");
		String npiIndv = getCellValue("V_NPI_Indv");
		String licenseNum = getCellValue("V_LicenseNum");
		String licenseState = getCellValue("V_LicenseState");
		String tin = getCellValue("V_TIN");
		String directoryAddress = getCellValue("V_DirectoryAddress");
		String city = getCellValue("V_City");
		String state = getCellValue("V_State");
		String county = getCellValue("V_County");
		String followUpDate = getCellValue("V_FollowUpDate");
		String urgent = getCellValue("V_Urgent");
		String priority = getCellValue("V_Priority");
		
		
		String[] valueArr = null;
		if (workbasketTypeVal.equals("Campaign Master")) {
			valueArr = new String[] {dueDate,caseID,campaign,year,workingStatus,statusTaken,closureDate};
		} else if (workbasketTypeVal.equals("DA Inaccuracies")) {
			valueArr = new String[] {dueDate,caseID,receiptDate,transaction,largeGroupDetails,workingStatus,statusTaken,lastName,firstName,
									npiIndv,licenseNum,licenseState,tin,directoryAddress,city,state};
		} else if (workbasketTypeVal.equals("DA Inquiries")) {
			valueArr = new String[] {urgent,dueDate,caseID,receiptDate,transaction,largeGroupDetails,workingStatus,statusTaken,lastName,firstName,
					npiIndv,licenseNum,licenseState,tin,directoryAddress,city,state};
		} else if (workbasketTypeVal.equals("MRI")) {
			valueArr = new String[] {workingStatus,urgent,followUpDate,dueDate,caseID,receiptDate,transaction,workingStatus,statusTaken,
					lastName,firstName,npiIndv,licenseNum,licenseState,tin,directoryAddress,city,state};
		} else if (workbasketTypeVal.equals("Unresponsive")) {
			valueArr = new String[] {urgent,caseID,receiptDate,transaction,largeGroupDetails,workingStatus,statusTaken,lastName,firstName,
					npiIndv,licenseNum,licenseState,tin,directoryAddress,city,state,county};
		}  else {
			valueArr = new String[] {urgent,priority,dueDate,caseID,receiptDate,transaction,largeGroupDetails,workingStatus,statusTaken,
					lastName,firstName,npiIndv,licenseNum,licenseState,tin,directoryAddress,city,state};
		}
		
		return valueArr;
	}
	public void searchWorkbasket() throws InterruptedException{
		Thread.sleep(1000);
		String workbasketVal = getCellValue("WorkbasketType");
		seMoveToCoordinates(400, 100);
		String workbasketTypeVal = seGetDropDownSelectedText(WorkbasketsPage.get().selWorkbasket);
		System.out.println("Excel Input workbasketVal :"+ workbasketVal);
		if (!workbasketTypeVal.equals(workbasketVal)){
			seSelectText(WorkbasketsPage.get().selWorkbasket, workbasketVal, "Workbasket Value");
		}
		workbasketTypeVal = seGetDropDownSelectedText(WorkbasketsPage.get().selWorkbasket);
		
		String[] valueArr = getValuesArray(workbasketTypeVal);
		
		System.out.println("workbasketTypeVal : "+ workbasketTypeVal);
		int valCount = WorkbasketsPage.get().getValuesCount(workbasketTypeVal, valueArr);
		
		if (valCount== 0) {
			ExtentReportsUtility.log(FAIL, "No Filter Provided", "None of the Filter criteria is provided.");
		} else if (valCount>=1){
			applyFilter(workbasketTypeVal, valueArr);
		} 
	}
	
}