package page;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.anthem.selenium.constants.KeyConstants;
import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class CreateContactPage extends CoreSuperHelper{
	private static CreateContactPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton ExtentReportsUtility.loginPage instance.</p>
	 * @return the singleton instance of ExtentReportsUtility.loginPage
	 */
	public static CreateContactPage get() {
		AjaxElementLocatorFactory factory=new AjaxElementLocatorFactory(getWebDriver(), 20);
		PageFactory.initElements(factory,CreateContactPage.class);
		thisIsTestObj=PageFactory.initElements(getWebDriver(),CreateContactPage.class);
		return thisIsTestObj;
	}


	@FindBy(how=How.ID,using="ContactDate")
	public WebElement contactDate;

	@FindBy(how=How.ID,using="VerifiedBy")
	public WebElement verifiedBy;

	@FindBy(how=How.ID,using="ContactName")
	public WebElement contactName;

	@FindBy(how=How.XPATH,using="//*[@id='ContactMethod' and contains(@name,'Inbound')]")
	public WebElement inboundContactMethod;

	@FindBy(how=How.ID,using="ContactInfoInboundPageContactReasonList")
	public WebElement inboundContactReason;

	@FindBy(how=How.XPATH,using="//div[@id='msresults-list']/ul/li//span[@class='match']")
	public WebElement contactReasonMatch;

	@FindBy(how=How.XPATH,using="//*[@id='ContactType' and contains(@name,'Inbound')]")
	public WebElement inboundContactType;

	@FindBy(how=How.XPATH,using="//div[not(@style=' display:none;')]/label[text()='Call Interaction']/following-sibling::div//input[@id='pyTemplateRichTextEditor']")
	public WebElement callInteraction;

	@FindBy(how=How.XPATH,using="//button[text()='Provider Search']")
	public WebElement providerSearch;

	@FindBy(how=How.ID,using="UniqueSearchBy")
	public WebElement uniqueSearchBy;

	@FindBy(how=How.ID,using="Type1NPIIndividual")
	public WebElement individualNPI;

	@FindBy(how=How.XPATH,using="//div[@gpropindex='D_ProviderSearchAPIPpxResults1']//table[@pl_prop='D_ProviderSearchAPI.pxResults']")
	public WebElement providerSearchTable;

	@FindBy(how=How.XPATH,using="//div[not(@style=' display:none;')]/span/button[text()='Tag To Contact']")
	public WebElement tagToContact;

	@FindBy(how=How.XPATH,using="//button[text()='Office Search']")
	public WebElement officeSearch;

	@FindBy(how=How.ID,using="ClinicDPSNo")
	public WebElement clinicDPSNo;

	@FindBy(how=How.ID,using="LegalName")
	public WebElement IRS_Name;

	@FindBy(how=How.NAME,using="$PpyDisplayHarness$pClinicSearchCriteria$pDBAName")
	public WebElement DBAName;

	@FindBy(how=How.NAME,using="$PpyDisplayHarness$pClinicSearchCriteria$pNPI")
	public WebElement clinicNPI;

	@FindBy(how=How.NAME,using="$PpyDisplayHarness$pClinicSearchCriteria$pPhoneNo")
	public WebElement clinicPhoneNo;

	@FindBy(how=How.XPATH,using="//table[@pl_prop='D_ClinicSearchAPI.pxResults']")
	public WebElement clinicSearchTable;

	@FindBy(how=How.XPATH,using="//*[@id='ContactMethod' and contains(@name,'OutboundPage')]")
	public WebElement outboundContactMethod;

	@FindBy(how=How.ID,using="ContactInfoOutboundPageContactReasonList")
	public WebElement outboundContactReason;

	@FindBy(how=How.XPATH,using="//*[@id='ContactType' and contains(@name,'Outbound')]")
	public WebElement outboundContactType;

	@FindBy(how=How.XPATH,using="//button[text()='Show Documents']")
	public WebElement showDocuments;

	@FindBy(how=How.XPATH,using="//div[@class='field-item dataValueWrite']/select[@name='$PpyDisplayHarness$pContactInfo$pOutboundPage$pActionCodeUniqueID']")
	public WebElement transactionType;

	@FindBy(how=How.ID,using="CreateTransactionForContact")
	public WebElement createTransaction;

	@FindBy(how=How.LINK_TEXT,using="+Add note")
	public WebElement addNote;

	@FindBy(how=How.ID,using="Note")
	public WebElement note;

	@FindBy(how=How.XPATH,using="//button[text()='Submit']")
	public WebElement submit;

	@FindBy(how=How.XPATH,using="//button[text()='Create Transaction And Save']")
	public WebElement createTransactionAndSave;

	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-Note']")
	public WebElement noteTable;

	@FindBy(how=How.XPATH,using="//button[text()='No']")
	public WebElement no;

	@FindBy(how=How.XPATH,using="//div[@node_name='CreateContactConfirmation']//div[text()='A case has been created']")
	public WebElement confirmationMessage;

	@FindBy(how=How.NAME,using="CreateContactConfirmation_pyDisplayHarness_2")
	public WebElement TRN_no;

	@FindBy(how=How.XPATH,using="//*[@id='Error_.TaxIDNo']//div[@class='custom_text']")
	public WebElement taxIDError;

	@FindBy(how=How.XPATH,using="//div[@node_name='SaveContactConfirmation']//div[@class='content-item content-label item-1 flex flex-row dataLabelWrite']")
	public WebElement saveConfirmationMSG;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW']")
	public WebElement documentsTable;
	
	@FindBy(how=How.XPATH,using="//table[@id='grid-desktop-paginator']//label[text()='of']/ancestor::td[1]/following-sibling::td[1]//label")
	public WebElement NoOfDocPages;
	
	@FindBy(how=How.XPATH,using="//button[@title='Next Page']")
	public WebElement nextDocPage;
	
	@FindBy(how=How.ID,using="DBAName")
	public WebElement DBANameForDocument;
	
	@FindBy(how=How.ID,using="AddressLine")
	public WebElement AddressLineForDocument;
	
	@FindBy(how=How.ID,using="City")
	public WebElement CityForDocument;
	
	@FindBy(how=How.ID,using="ZipCode")
	public WebElement ZipCodeForDocument;
	
	@FindBy(how=How.ID,using="MailAddress")
	public WebElement MailAddressForDocument;
	
	@FindBy(how=How.ID,using="State")
	public WebElement StateForDocument;
	
	@FindBy(how=How.XPATH,using="//label[@for='DetermineLocationYes']")
	public WebElement sendDocumentYes;
	
	@FindBy(how=How.XPATH,using="//span[text()='City']/following-sibling::div[1]/span")
	public WebElement CityLabel;
	
	@FindBy(how=How.XPATH,using="//span[text()='DBA Name']/following-sibling::div[1]/span")
	public WebElement DBANameLabel;
	
	@FindBy(how=How.XPATH,using="//span[text()='State']/following-sibling::div[1]/span")
	public WebElement StateLabel;
	
	@FindBy(how=How.XPATH,using="//span[text()='Zip Code']/following-sibling::div[1]/span")
	public WebElement ZipCodeLabel;
	
	@FindBy(how=How.XPATH,using="//span[text()='Address']/following-sibling::div[1]/span")
	public WebElement AddressLabel;
	
	@FindBy(how=How.XPATH,using="//span[text()='Phone number']/following-sibling::div[1]/span")
	public WebElement PhoneNumberLabel;
	
	
		
	@FindBy(how=How.ID,using="Email")
	public WebElement Email;
	
	@FindBy(how=How.XPATH,using="//body[@title='This is a rich text editor control.']/p")
	public WebElement EmailTextArea;
	
	@FindBy(how=How.ID,using="FaxNo")
	public WebElement FaxNo;
	
	@FindBy(how=How.XPATH,using="//iframe[@class='cke_wysiwyg_frame cke_reset']")
	public WebElement iFrameEmailTab;
	
	/**
	 * This method fills the basic contact details in Create Contact Screen
	 */
	public void FillContactInfo() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -10);
			String conDate=dateFormat.format(cal.getTime());
			Thread.sleep(4000);
			seWaitForWebElement(10, ExpectedConditions.visibilityOf(CreateContactPage.get().contactDate));
			seSetText(CreateContactPage.get().contactDate, conDate, "Enter Contact Date field value");
			setCellValue("Contact_Date", conDate);

			seSetText(verifiedBy, getCellValue("Verified_By"), "Enter Verified By field value");
			seInputKeys(verifiedBy, KeyConstants.TAB, "Select "+getCellValue("Verified_By"));
			Thread.sleep(2000);
			seSetText(contactName, getCellValue("Contact_Name"), "Enter Contact Name field value");
		}catch (Exception e) {
			ExtentReportsUtility.log(ERROR, "Fill Contact details","Unexpected exception occurred while filling Contact details.");
			e.printStackTrace();
		}
	}

	/**
	 * This method fills the Inbound contact details in Create Contact Screen
	 */
	public void FillInboundContact() throws InterruptedException {
		try {
			seSelectValue(true,CreateContactPage.get().inboundContactMethod, getCellValue("Inbound_Contact_Method"), "Select Inbound Contact Method from dropdown");
			inboundContactMethod.submit();
			Thread.sleep(3000);
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(inboundContactReason));
			selectMultiListValues(inboundContactReason, getCellValue("Inbound_Contact_Reason"));
			seSelectText(inboundContactType, getCellValue("Inbound_Contact_Type"), "Select Inbound Contact Type from dropdown");
		}catch (Exception e) {
			ExtentReportsUtility.log(ERROR, "Fill Inbound contact details","Unexpected exception occurred while filling Inbound contact details.");
			e.printStackTrace();
		}
	}

	public void selectMultiListValues(WebElement element, String values) {
		try {
			String[] dropdownValue=values.split(";");
			seSetText(element, " ");
			Thread.sleep(2000);
			seInputKeys(element, KeyConstants.BACK_SPACE, "Backspace");
			Thread.sleep(2000);
			List<WebElement> dropdownOptions=getWebDriver().findElements(By.xpath("//div[@id='msresults-list']/ul/li//span[@class='ms-primary-option']"));
			ArrayList<String> options=new ArrayList<String>();
			for (WebElement webElement : dropdownOptions) {
				options.add(webElement.getText().trim());
			}
			
			for (String string : dropdownValue) {
				if(!options.contains(string)) {
					ExtentReportsUtility.log(FAIL, "Validate the existence of given option in the list box","Given option "+string+" is not availablein the list");
				}else {
					seSetText(element, string);
					Thread.sleep(2000);
					seInputKeys(element, KeyConstants.TAB, "Select "+string);
					Thread.sleep(1000);
				}
			}
		}catch (Exception e) {
			ExtentReportsUtility.log(FAIL, "Select multi-select values","Unexpected exception occurred. Please check the input values "+values);
			e.printStackTrace();
		}
	}

	/**
	 * This method selects the provider details required to create the contact
	 */
	public void FillProviderInfo() {
		try {
			seClick(true, providerSearch, "ProviderSearch", "Search the Provider");
			Thread.sleep(3000);
			seWaitForWebElement(10, ExpectedConditions.visibilityOf(ProviderSearchPage.get().dentistFirstName));

			String DentistFirstName=getCellValue("Dentist_First_Name");
			String DentistMiddleName=getCellValue("Dentist_Middle_Name");
			String DentistLastName=getCellValue("Dentist_Last_Name");

			seSetText(ProviderSearchPage.get().dentistFirstName, DentistFirstName, "Enter Dentist First Name");
			seSetText(ProviderSearchPage.get().dentistMiddleName, DentistMiddleName, "Enter Dentist Middle Name");
			seSetText(ProviderSearchPage.get().dentistLastName,DentistLastName, "Enter Dentist Last Name");
			seClick(true, CommonElements.get().btnSearch, "Search", "Click on Search Button");
			Thread.sleep(3000);
			seWaitForWebElement(10, ExpectedConditions.visibilityOf(CreateContactPage.get().providerSearchTable));

			String[] providerInputHeaders= {"FirstName","MiddleIntial","LastName","Suffix","Specialty","IndividualNPI","LicenseStatus","ProviderStatus","ProviderDPSNo"};

			int row=ProviderSearchPage.get().searchProviderFromTable(providerSearchTable,DPDAUtils.getProviderOfficeSearchHeadMap(),providerInputHeaders, DentistFirstName, DentistMiddleName, DentistLastName, "", getCellValue("V_Prov_Specialty"), getCellValue("V_Prov_NPI"),"Active","Active", getCellValue("V_Prov_DPS_No"));
			
			if(row!=-1) {
				seClick(true, ProviderSearchPage.get().providerRadioButton.get(row), "Tag To Contact","Click on Tag To Contact button");
				seClick(true, tagToContact, "Tag To Contact","Click on Tag To Contact button");
			}else {
				ExtentReportsUtility.log(FAIL, "Verify Provider Search table data","No records found for the given combination", true);
			}

		}catch (Exception e) {
			ExtentReportsUtility.log(ERROR, "Fill provider details", "Exception while filling Provider details");
			e.printStackTrace();
		}
	}

	/**
	 * This method adds the Office details required to create the contact
	 */
	public void FillOfficeInfo() {
		try {
			Thread.sleep(3000);
			seClick(true, officeSearch, "Office Search", "Click on Office Search Button");
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(IRS_Name));
			seSetText(false, IRS_Name, getCellValue("IRS_Name"), "Enter IRS Name");
			seSetText(false, DBAName, getCellValue("DBA_Name"), "Enter DBA Name");
			seSetText(false, clinicNPI, getCellValue("Clinic_NPI"), "Enter Clinic NPI");
			seSetText(false, clinicPhoneNo, getCellValue("Clinic_PhoneNo"), "Enter Clinic Phone No");
			seClick(true, CommonElements.get().btnSearch, "Search", "Click on Search Button");
			Thread.sleep(3000);
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(clinicSearchTable));
			seClick(true,getWebDriver().findElement(By.xpath("//input[contains(@name,'ClinicSearchAPI') and @type='checkbox']")), "Select the clinic");
			seClick(true, tagToContact, "tag to Contact");
			Thread.sleep(3000);
		}catch (Exception e) {
			// TODO: handle exception
			ExtentReportsUtility.log(ERROR, "Fill Office Information", "Exception while filling Office Information");
			e.printStackTrace();
		}
	}

	/**
	 * This method fills the outbound contact details and adds the notes in the Create Contact Screen
	 */
	public void FillOutboundContact() {
		try {
			seSelectValue(true,outboundContactMethod, getCellValue("Outbound_Contact_Method"), "Select Outbound Contact Method from dropdown");
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(outboundContactReason));
			Thread.sleep(3000);
			selectMultiListValues(outboundContactReason, getCellValue("Outbound_Contact_Reason"));
			seSelectText(outboundContactType, getCellValue("Outbound_Contact_Type"), "Select Outbound Contact Type from dropdown");

			seClick(showDocuments, "Show Documents");
			Thread.sleep(4000);
			seWaitForWebElement(5, ExpectedConditions.visibilityOf(transactionType));
			seSelectText(true,transactionType, getCellValue("Doc_Transaction_Type"), "Select Document Transaction type from dropdown");
			Thread.sleep(3000);
			seWaitForWebElement(5, ExpectedConditions.elementToBeClickable(createTransaction));
			seClick(createTransaction, "Create Transaction");
			Thread.sleep(2000);

			if(getCellValue("Outbound_Contact_Method").matches("Mail|Email|Fax")) {
			validateDocuments();
			seClick(sendDocumentYes, "Yes");
			}
			
			if (getCellValue("Outbound_Contact_Method").equals("Mail")) {
				seSetText(false, DBANameForDocument, seGetText(DBANameLabel), "Enter DBA Name");
				seSetText(false, AddressLineForDocument, seGetText(AddressLabel), "Enter Address");
				seSetText(false, CityForDocument, seGetText(CityLabel), "Enter City");
				seSetText(false, ZipCodeForDocument, seGetText(ZipCodeLabel), "Enter Zip");
				seSetText(false, MailAddressForDocument, getCellValue("V_Email"), "Enter Email");
				seSelectValue(true, StateForDocument, seGetText(StateLabel).trim(), "Select State");
			}else if (getCellValue("Outbound_Contact_Method").equals("Email")) {
				seSetText(true, Email, getCellValue("V_Email"), "Enter Email");
//				getWebDriver().switchTo().defaultContent();
				seSwitchToFrame(iFrameEmailTab);
				seClick(EmailTextArea, "Email Body");
				EmailTextArea.sendKeys("Automation Testing DPDA");
//				seSetText(EmailTextArea, "Test Automation", "Enter Email body");
				getWebDriver().switchTo().defaultContent();
				seSwitchToFrame(CommonElements.get().iFrameFirstTab);
				Email.click();
			}else if (getCellValue("Outbound_Contact_Method").equals("Fax")) {
				seSetText(true, FaxNo, seGetText(PhoneNumberLabel), "Enter Fax No.");
			}
			
System.out.println("");
		}catch (Exception e) {
			// TODO: handle exception
			ExtentReportsUtility.log(ERROR, "Fill outbound details", "Exception while filling outbound details");
			e.printStackTrace();
		}
	}
	
	
	
	public void addNotes() throws InterruptedException {
		try {
		seClick(true, addNote, "Add Note");
		seSetText(note, getCellValue("Note"), "Enter the Note");
		Thread.sleep(2000);
		seInputKeys(note, KeyConstants.TAB,"Press TAB");
		Thread.sleep(1000);
		seClick(true, submit, "Submit", "Click on note Submit button");
		Thread.sleep(5000);
		seWaitForWebElement(5, ExpectedConditions.visibilityOf(noteTable));

		HTMLTableUtils tbl=new HTMLTableUtils(noteTable);
		if(tbl.getRowsCount() ==0) {
			ExtentReportsUtility.log(FAIL, "Verify newly added note","No new notes found", true);
		}else {
			DateFormat dateFormat = new SimpleDateFormat("M/d/yy h:mm a");
			Calendar cal = Calendar.getInstance();
			String sysDate=dateFormat.format(cal.getTime());
			if(sysDate.equalsIgnoreCase(tbl.getCellData(1, 4))) {
				ExtentReportsUtility.log(PASS, "Verify newly added note", "Newly added notes displayed with the date format "+sysDate);
			}else {
				ExtentReportsUtility.log(FAIL, "Verify newly added note", "Newly added notes is not displayed with the date format. Expected :"+sysDate+"; Actual :"+tbl.getCellData(1, 4));
			}
		}
		}catch (Exception e) {
			ExtentReportsUtility.log(ERROR, "Add Notes", "Exception while Adding notes");
			e.printStackTrace();
		}
	}
	
	public void validateCallInteraction() {
		seSetText(callInteraction, "AutoTest");
		if(callInteraction.getAttribute("value").toString().equalsIgnoreCase("AutoTest")) {
			ExtentReportsUtility.log(FAIL, "Validate Call Interaction Field", "User is able to edit the Call Interaction field", true);
			setResult(false);
		}else {
			ExtentReportsUtility.log(PASS, "Validate Call Interaction Field","User is unable to edit the Call Interaction field", true);
		}
	}


	/**
	 * Completes the transaction and validates the TRN#
	 */
	public void SaveTheTransaction() {
		seClick(true, CreateContactPage.get().createTransactionAndSave, "Create Transaction And Save", "Click on Create Transaction And Save butoon to complete the transaction");
		seWaitForWebElement(5, ExpectedConditions.visibilityOf(confirmationMessage));
		String TRN=seGetText(TRN_no);
		setCellValue("TRN_No", TRN);
		seCaptureScreenshotWithWebElement(TRN_no, "TRN#");
		seClick(true,no, "No");
		seWaitForPageLoad();

	}
	
	
public void validateDocuments() {
	try {
		seWaitForWebElement(5, ExpectedConditions.visibilityOf(CreateContactPage.get().documentsTable));
		boolean CR31=false;
		boolean F49=false;
		
		int col=DPDAUtils.getdocumentsHeadMapValue("Document Name");
		int totalPages=Integer.parseInt(seGetText(NoOfDocPages));
		for(int k=0;k<totalPages;k++) {
			HTMLTableUtils docTable=new HTMLTableUtils(CreateContactPage.get().documentsTable);
			String[][] lst=docTable.getAllCellsValues();
			for (int row = 0; row < docTable.getRowsCount(); row++) {
				String cellvalue=lst[row][col].trim();
				System.out.println(""+cellvalue);
				if(cellvalue.equals("CR31 Monthly Re-Cred Letter and Packet")) {
					seClick(true, getWebDriver().findElement(By.xpath("//tr["+(row+2)+"]//input[contains(@name,'$PD_FetchDocumentsForPSD') and @type='checkbox']")), "Select checkbox", "Select CR31 Monthly Re-Cred Letter and Packet");
					CR31=true;
				}else if(cellvalue.equals("F49 Re-Credentialing Application")) {
					seClick(true, getWebDriver().findElement(By.xpath("//tr["+(row+2)+"]//input[contains(@name,'$PD_FetchDocumentsForPSD') and @type='checkbox']")), "Select checkbox", "Select F49 Re-Credentialing Application");
					F49=true;
				}
			}
			if(!CR31 || !F49) {
				seClick(nextDocPage, "next page");
				Thread.sleep(5000);
			}else {break;}
		}
		if(CR31)
			ExtentReportsUtility.log(PASS, "Validate the existence of CR31 file","CR31 file is available in the document table");
		else 
			ExtentReportsUtility.log(FAIL, "Validate the existence of CR31 file","Unable to find CR31 file in the document table");

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
}
