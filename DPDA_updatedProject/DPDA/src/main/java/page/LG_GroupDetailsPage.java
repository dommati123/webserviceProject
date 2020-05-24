package page;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.anthem.selenium.utility.ExtentReportsUtility;
import com.anthem.selenium.utils.HTMLTableUtils;

import utility.CoreSuperHelper;
import utility.DPDAUtils;

public class LG_GroupDetailsPage extends CoreSuperHelper{
	private static LG_GroupDetailsPage thisIsTestObj;
	/**
	 * <p>Getter method for the singleton LG_GroupDetails instance.</p>
	 * @return the singleton instance of LoginPage
	 */
	public static LG_GroupDetailsPage get() {
		thisIsTestObj =  PageFactory.initElements(getWebDriver(), LG_GroupDetailsPage.class);
		return thisIsTestObj;
	}	
	
	@FindBy(how=How.XPATH,using="//input[@id='pyGridActivePage']")
	public WebElement txt_PageNumber;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-ExtractFrequency']")
	public WebElement Tbl_ParticipentReportMapping;
	
	@FindBy(how=How.XPATH,using="//table[@pl_prop_class='Antm-FW-DPSFW-Data-TinEffectiveDate']")
	public WebElement Tbl_TinEffectiveDate;
	
	@FindBy(how=How.XPATH,using="//button[@id='ModalButtonCancel']")
	public WebElement Btn_Cancel;
	
	
	public static boolean isLinkSelectedFrmTableUsingLargeGroup(String colVal) {
		boolean linkFound=false;
		String[] tempVal= {colVal,"","","","","",""};//select link by validated LargGroupName header, keeping other blank
		
		try {
			Integer row=validateSearchResults(tempVal);
			if(row>0) {
				row++;
				WebElement webElmt = getWebDriver().findElementByXPath("//table[@pl_prop_class='Antm-FW-DPSFW-Data-ExtractFrequency']//tr["+row+"]//td[3]//img");
				seClick(webElmt, "Select "+colVal);
				linkFound=true;
				Thread.sleep(500);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return linkFound;
		
	}
	static String[] groupDtlsTINListTbleResult = new String[] {
	"LargeGroupName","BeginDate","EndDate","ParticipationReport","ParticipationReportFrequency","RevisedParticipationReport","RevisedParticipationReportFrequency"};

	/**
	 * All Parameters should be passed, You can pass null or "", if you don't have value
	 * Order of Parameters for  Search Result is 
	 * LargeGroupName,BeginDate,EndDate,ParticipationReport,ParticipationReportFrequency,RevisedParticipationReport,RevisedParticipationReportFrequency
	 * 	</BR>
	 * @return
	 * @throws Exception
	 */
	public static Integer validateSearchResults(String... validationParams) throws Exception {
		boolean chkTableValues = true;
		int rowNum = -1;
		HTMLTableUtils searchTableResults = null;
		String[] searchIndxArr = null;
			searchTableResults = new HTMLTableUtils(LG_GroupDetailsPage.get().Tbl_ParticipentReportMapping);
			searchIndxArr = groupDtlsTINListTbleResult;
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
					searchTableResults = new HTMLTableUtils(LG_GroupDetailsPage.get().Tbl_ParticipentReportMapping);
				cellValues= searchTableResults.getAllCellsValues();
				// Validate Each Record
				for (int i = 0; i<searchTableResults.getRowsCount(); i++) {
					boolean foundVal = true;
					int varArgId = 0;
					// Search for all Values 
					
					for (String searchValIndx : searchIndxArr) {
						if (foundVal && !(validationParams[varArgId].trim().equals(""))) {
							int colId = -1;
							
								 colId = DPDAUtils.groupDtlsParticipationReportValue(searchValIndx);
							
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
				if (chkTableValues && isPresentAndDisplayed(CommonElements.get().searchResultsTblPaginator)) {
					// Click Next Button
					if (isPresentAndDisplayed(CommonElements.get().searchResultsNextLink) && searchTableResults.getRowsCount() > 9 ) {
						seScrollForWebElement(LG_GroupDetailsPage.get().Tbl_ParticipentReportMapping);
						ExtentReportsUtility.log(INFO, "Table Results", "Results Table Screenshot", true);
						seClick(CommonElements.get().searchResultsNextLink, "Next in Results");
						System.out.println("Click Next.");
						Thread.sleep(1000);
					} else {
							seScrollForWebElement(LG_GroupDetailsPage.get().Tbl_ParticipentReportMapping);
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
	/**
	 * Validate TIN numbers
	 * @param dataSet1, Enter set of data, separated by comma
	 * @param dataSet2, Enter set of data, Separated by comma
	 */
	public static void validateTINNumbers(String dataSet1,String dataSet2) {
	boolean dataMatch=false;
	String[] data1=dataSet1.split(",");
	String[] data2=dataSet2.split(",");
	for (int i=0;i<data1.length;i++) {
		for(int j=0;j<data2.length;j++) {
			if(data1[i].equals(data2[j])) {
				dataMatch=true;
				break;
			}
		}
		if(!dataMatch) {
			break;
		}
	}
	if(dataMatch) {
		ExtentReportsUtility.log(PASS, "Validate TIN Number","All TIN number from LG Group: "+dataSet1+" matches with Group details TIN List Table:"+dataSet2,false);
	}else {
		ExtentReportsUtility.log(FAIL, "Validate TIN Number","All TIN number from LG Group: \"+dataSet1+\" NOT matches with Group details TIN List Table:\"+dataSet2",true);
		setResult(false);
	}
	
	}
	
	
	
}
