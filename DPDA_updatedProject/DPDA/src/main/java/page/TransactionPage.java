package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;


public class TransactionPage extends CoreSuperHelper{
	
	private static TransactionPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton TransactionPage instance.</p>
	 * @return the singleton instance of TransactionPage
	 */
	public static TransactionPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), TransactionPage.class);
		return thisIsTestObj;
	}
	
	//input[@id='TaxIDNo' and @data-changed]  -- IRS INformation ->TIN
	@FindBy(how=How.XPATH,using="//input[@id='TaxIDNo' and @data-changed]")
	public WebElement txtIRSTIN;
	
	//input[@id='EffectiveDate']  -- Status -> Effective Date
	@FindBy(how=How.ID,using="EffectiveDate")
	public WebElement effectiveDate;
	
	//select[@id='NDMWorkingStatus']  -- Status -> Working Status
	@FindBy(how=How.ID,using="NDMWorkingStatus")
	public WebElement workingStatus;
	
	@FindBy(how=How.ID,using="DAOfficeStatus")
	public WebElement selOfficeStatus;

	//div[contains(@class,'content-inner')]//span[@class='field-caption dataLabelForRead' and contains(text(),'DPS Clinic No')]/following-sibling::div//span
	@FindBy(how=How.XPATH,using="//div[contains(@class,'content-inner')]//span[@class='field-caption dataLabelForRead' and contains(text(),'DPS Clinic No')]/following-sibling::div//span")
	public WebElement dpsClinicNo;
	
	//button[@type='button' and contains(text(), 'Save') and contains(@name,'ActionCodeButtons')]  -- Save Button
	@FindBy(how=How.XPATH,using="//button[@type='button' and contains(text(), 'Save') and contains(@name,'ActionCodeButtons')]")
	public WebElement saveBtn;
	
	@FindBy(how=How.XPATH,using="//div[@class='content-item content-field item-1 flex flex-row dataValueRead']")
	public WebElement trnAssignmentBlank;
	
	@FindBy(how=How.ID,using="NDMStatusTaken")
	public WebElement statusTaken;
	
	@FindBy(how=How.ID,using="DateCompleted")
	public WebElement dateCompleted;
	
	@FindBy(how=How.ID,using="container_close")
	public WebElement closeButton;
	
	@FindBy(how=How.XPATH,using="//ul[@class='custom_errorlist_ul']")
	public WebElement errMessage;
	
	
	@FindBy(how=How.XPATH,using="//a[text()='+ Add Contact Information']")
	public WebElement contactInformation;
	
	@FindBy(how=How.XPATH,using="//*[@id='ContactMethod' and contains(@name,'Inbound')]")
	public WebElement inboundContactMethod;
	
	@FindBy(how=How.XPATH,using="//div[@node_name='InboundContactInfo']//*[@id='ContactReasonList']")
	public WebElement inboundContactReason;
	
	@FindBy(how=How.XPATH,using="//*[@id='ContactType' and contains(@name,'Inbound')]")
	public WebElement inboundContactType;
	
	@FindBy(how=How.XPATH,using="//*[@id='ContactMethod' and contains(@name,'OutboundPage')]")
	public WebElement outboundContactMethod;

	@FindBy(how=How.XPATH,using="//div[@node_name='OutboundContactInfo']//*[@id='ContactReasonList']")
	public WebElement outboundContactReason;

	@FindBy(how=How.XPATH,using="//*[@id='ContactType' and contains(@name,'Outbound')]")
	public WebElement outboundContactType;
	
	@FindBy(how=How.XPATH,using="//div[@node_name='AddDocuments']//h2[text()='Documents']")
	public WebElement btnDocuments;
	@FindBy(how=How.XPATH,using="//table[@id='grid-desktop-paginator']//label[text()='of']/ancestor::td[1]/following-sibling::td[1]//label")
	public WebElement NoOfDocPages;
	
	@FindBy(how=How.XPATH,using="//button[@title='Next Page']")
	public WebElement nextDocPage;
	
	@FindBy(how=How.XPATH,using="//label[@for='DetermineLocationYes']")
	public WebElement sendDocumentYes;
	
	@FindBy(how=How.ID,using="ModalButtonSubmit")
	public WebElement btnSubmit;
	
	
	//div[contains(@gpropindex,'PProviderList')]//table//table[@pl_prop='.ProviderList'] -- List of Providers table
	@FindBy(how=How.XPATH,using="//div[contains(@gpropindex,'PProviderList')]//table//table[@pl_prop='.ProviderList']")
	public WebElement tblListOfProviders;
	
	
	//div[text()='Thanks for Your Input.'] -- Submit Confirmation Page
	@FindBy(how=How.XPATH,using="//div[text()='Thanks for Your Input.']")
	public WebElement trnSubmitConfirmationMsg;
	
	//div[contains(text(),'Unable to open an instance using the given inputs')] -- Unable to open Transaction
	@FindBy(how=How.XPATH,using="//div[contains(text(),'Unable to open an instance using the given inputs')]")
	public WebElement trnUnableToOpenMsg;
	
	//div[not(@style="display:none;") and @data-context="pyWorkPage"]//*[text()='Submit']
	@FindBy(how=How.XPATH,using="//div[not(@style='display:none;') and @data-context='pyWorkPage']//*[text()='Submit']")
	public WebElement btnTrnSubmit;
	
	public WebElement linkText(String linkName) {
		return getWebDriver().findElementByLinkText(linkName);
	}
	
	/**
	 * This method fills the outbound contact details and adds the notes in the Create Contact Screen
	 */
	public void FillOutboundContact() {
		try {
			seSelectValue(true,outboundContactMethod, getCellValue("Outbound_Contact_Method"), "Select Outbound Contact Method from dropdown");
			Thread.sleep(2000);
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(outboundContactReason));
			Thread.sleep(6000);
			selectMultiListValues(outboundContactReason, getCellValue("Outbound_Contact_Reason"));
			seSelectText(outboundContactType, getCellValue("Outbound_Contact_Type"), "Select Outbound Contact Type from dropdown");
			seClick(btnDocuments, "Documents");
			Thread.sleep(1000);
			validateDocuments();
			seScrollForWebElement(sendDocumentYes);
			Thread.sleep(1000);
			seClick(sendDocumentYes, "Yes");
			seSetText(true, CreateContactPage.get().Email, getCellValue("Email"), "Enter Email");
			seSwitchToFrame(CreateContactPage.get().iFrameEmailTab);
			seClick(CreateContactPage.get().EmailTextArea, "Email Body");
			CreateContactPage.get().EmailTextArea.sendKeys("Automation Testing DPDA");
			getWebDriver().switchTo().defaultContent();
			seSwitchToFrame(CommonElements.get().iFrameFirstTab);
//			CreateContactPage.get().Email.click();	
			Thread.sleep(1000);
			
		}catch (Exception e) {
			// TODO: handle exception
			ExtentReportsUtility.log(ERROR, "Fill outbound details", "Exception while filling outbound details");
			e.printStackTrace();
		}
	}
	public void validateDocuments() {
		try {
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(CreateContactPage.get().documentsTable));
			boolean MRI=false;
			boolean F49=false;
			int totalPages=1;
			seScrollForWebElement(CreateContactPage.get().documentsTable);
			Thread.sleep(1000);
			int col=DPDAUtils.getdocumentsHeadMapValue("Document Name");
			try {
			 totalPages=Integer.parseInt(seGetText(NoOfDocPages));
			}catch(Exception e) {
				
			}
			for(int k=0;k<totalPages;k++) {
				HTMLTableUtils docTable=new HTMLTableUtils(CreateContactPage.get().documentsTable);
				String[][] lst=docTable.getAllCellsValues();
				for (int row = 0; row < docTable.getRowsCount(); row++) {
					String cellvalue=lst[row][col].trim();
					System.out.println(""+cellvalue);
					if(cellvalue.equals("MRI-Email")) {
						seClick(true, getWebDriver().findElement(By.xpath("//tr["+(row+3)+"]//input[contains(@name,'$PD_FetchDocumentsForPSD') and @type='checkbox']")), "Select checkbox", "Select MRI-Email");
						MRI=true;
					}else if(cellvalue.equals("F49 Re-Credentialing Application")) {
						seClick(true, getWebDriver().findElement(By.xpath("//tr["+(row+2)+"]//input[contains(@name,'$PD_FetchDocumentsForPSD') and @type='checkbox']")), "Select checkbox", "Select F49 Re-Credentialing Application");
						F49=true;
					}
				}
				if(!MRI || !F49) {
					seClick(nextDocPage, "next page");
					Thread.sleep(5000);
				}else {break;}
			}
			if(MRI)
				ExtentReportsUtility.log(PASS, "Validate the existence of MRI file","MRI file is available in the document table");
			else 
				ExtentReportsUtility.log(FAIL, "Validate the existence of MRI file","Unable to find MRI file in the document table");

			if(F49)
				ExtentReportsUtility.log(PASS, "Validate the existence of F49 file","F49 file is available in the document table");
			else 
				ExtentReportsUtility.log(FAIL, "Validate the existence of F49 file","Unable to find F49 file in the document table");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ExtentReportsUtility.log(FAIL, "Unexpected exception occurred - unable to validate documents");
		}
	}
	public  void verifySearchWithinTransactionTbl(String tables) {
		String[] tbls=tables.split(",");
		HTMLTableUtils searchTableResults = null;
		for(int i=0;i<tbls.length;i++) {
		try {
		if (tbls[i].trim().equals("Office Search")) {
			
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
				searchTableResults = new HTMLTableUtils(OfficeSearchPage.get().dpsSearchResultsTbl);
		}
		if (tbls[i].trim().equals("W9 Legal Search")) {
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
				searchTableResults = new HTMLTableUtils(SC_W9LegalSearchPage.get().tblDpsSearchResult);
		}
		if (tbls[i].trim().equals("Case ID Search")) {
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
			seClick(CommonElements.get().btnSearch,"Search");
			Thread.sleep(400);
				searchTableResults = new HTMLTableUtils(CaseIDSearchPage.get().tblCaseSearchResults);
		}
		if (tbls[i].trim().equals("Provider Search")) {
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
			searchTableResults = new HTMLTableUtils(ProviderSearchPage.get().dpsSearchResultsTable);
		}
		if (tbls[i].trim().equals("LG Office Search")) {
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
			searchTableResults = new HTMLTableUtils(SC_LGSearchPage.get().tbl_SearchResult);
		}
		if (tbls[i].trim().equals("Provider Office Search")) {
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
			searchTableResults = new HTMLTableUtils(ProviderOfficeSearchPage.get().searchResultsTbl);
		}
		if (tbls[i].trim().equals("HMO PDO Search")) {
			seClick(linkText(tbls[i].trim()), tbls[i].trim()+ " link");
			searchTableResults = new HTMLTableUtils(SC_HMOPDOSearchPage.get().tbl_SearchResult);
		}
		Thread.sleep(1000);
		seClick(closeButton, "CloseButton");
			
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
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
			ExtentReportsUtility.log(PASS, "Table Results", "No Items in results table of "+tbls[i]);
		}else {
			ExtentReportsUtility.log(FAIL, "Table Results", "Items in results table of "+tbls[i],true);
			setResult(false);
		}
		} catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Table Results", "NOT able to find table for " +tbls[i],true);
			try {
				seClick(closeButton, "CloseButton");
			}catch(Exception e1) {
			}
		}
		}
	}
	
	public void verifyStatusFields() {
		try {
		seScrollForWebElement(TransactionPage.get().workingStatus);
		Thread.sleep(400);
		String workingStatus = seGetDropDownValue(TransactionPage.get().workingStatus);
		if(workingStatus.equals("Review Required")) {
			ExtentReportsUtility.log(PASS, "Validate Working status field displays as 'Review Required'",  "Working status field displays as '"+workingStatus+"'");
		}else {
			ExtentReportsUtility.log(FAIL, "Validate Working status field displays as 'Review Required'",  "Working status field displays as '"+workingStatus+"'",true);
		}
		String statusTaken = seGetDropDownValue(TransactionPage.get().statusTaken);
		if(statusTaken.equals("Select..")) {
			ExtentReportsUtility.log(PASS, "Validate Status Taken field displays as 'Select..'",  "Status Taken field displays as '"+statusTaken+"'");
		}else {
			ExtentReportsUtility.log(FAIL, "Validate Status Taken field displays as 'Select..'",  "Status Taken field displays as '"+statusTaken+"'",true);
		}
		String dateCompleted = seGetElementTextBoxValue(TransactionPage.get().dateCompleted);
		if(!dateCompleted.equals("")) {
			ExtentReportsUtility.log(PASS, "Validate system populates the due date",  "Date Completed field displays as '"+dateCompleted+"'");
		}else {
			ExtentReportsUtility.log(FAIL, "Validate system populates the due date",  "Date Completed field displays as '"+dateCompleted+"'",true);
		}
		}catch(Exception e) {
			
		}
		
	}
	/**
	 * This method fills the Inbound contact details in Create Contact Screen
	 */
	public void FillInboundContact() throws InterruptedException {
		try {
			seSelectValue(true,inboundContactMethod, getCellValue("Inbound_Contact_Method"), "Select Inbound Contact Method from dropdown");
			Thread.sleep(2000);
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(inboundContactReason));
			selectMultiListValues(inboundContactReason, getCellValue("Inbound_Contact_Reason"));
			seSelectText(inboundContactType, getCellValue("Inbound_Contact_Type"), "Select Inbound Contact Type from dropdown");
		}catch (Exception e) {
			ExtentReportsUtility.log(ERROR, "Fill Inbound contact details","Unexpected exception occurred while filling Inbound contact details.");
			e.printStackTrace();
		}
	}
	
	
	public void expandSection(String sectionName) {
		//div[@title='Disclose Office Status']
		
		// Section is currently Close
		int elmtCloseCount = getWebDriver().findElements(By.xpath("//div[@title='Disclose '"+sectionName+"]")).size();
		
		/*// Section is Open
		int elmtOpenCount = getWebDriver().findElements(By.xpath("//div[@title='Hide '"+sectionName+"]")).size();
		*/
		if (elmtCloseCount == 1) {
			getWebDriver().findElement(By.xpath("//div[@title='Disclose '"+sectionName+"]")).click();
		}
	}
	
	public void retireProviderOffice() throws Exception{
		
		expandSection("Office Status");
		String officeStatus = getCellValue("AU_OfficeStatus");
		seSelectText(selOfficeStatus, officeStatus, "Select Office Status");
		
		expandSection("Provider Association at Office");
		
		// Set the FirstProvider status to retire
		// DAProvAssOfcStatus1
		
		WebElement providerAssociationOfficeStatusElmt = getWebDriver().findElementById("DAProvAssOfcStatus1");
		
		String providerAssociationOfficeStatus = getCellValue("AU_ProviderOfficeAssociationStatus");
		
		if (!(providerAssociationOfficeStatus!=null && !providerAssociationOfficeStatus.trim().equals(""))) {
			providerAssociationOfficeStatus = "Retired";
		}
		
		// Make Sure List of Providers are present.
		if (!isListOfProvidersEmpty()) {
			seSelectText(providerAssociationOfficeStatusElmt, providerAssociationOfficeStatus, "Provider Association Office Status");
			String workingStatusVal = getCellValue("AU_WorkingStatus");
			seSelectText(workingStatus, workingStatusVal, "Working Status");
			seClick(btnTrnSubmit, "Submit");
			
			if (isElementDisplayed(trnSubmitConfirmationMsg)) {
				ExtentReportsUtility.log(PASS,"Submit Transaction", "Transaction Submitted successfully.");
			} else {
				ExtentReportsUtility.log(PASS,"Submit Transaction", "Transaction Submission failed.");
			}
		}
		
		
	}
	
	public boolean isListOfProvidersEmpty() throws Exception{
		
		HTMLTableUtils searchTableResults = new HTMLTableUtils(tblListOfProviders);
		String[][] cellValues= searchTableResults.getAllCellsValues();
		boolean noCases = false;
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
		} 
		
		return noCases;
	}
		
	
	
	
}