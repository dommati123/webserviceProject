package utility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.PageFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.anthem.crypt.EnvHelper;
import com.anthem.selenium.constants.DatabaseConstants;
import com.anthem.selenium.utility.ExcelUtility;
import com.anthem.selenium.utils.DatabaseUtils;
import com.anthem.selenium.utils.DateTimeUtils;
import com.anthem.selenium.utils.ExcelUtils;
import com.anthem.selenium.utils.RESTWebServiceUtils;
import com.anthem.selenium.utils.SOAPServiceUtils;
import com.anthem.selenium.utils.XMLJSONUtils;


public class SOAPUtil extends CoreSuperHelper{

	private static SOAPUtil thisIsTestObj;
	/**
	 * <p>Getter method for the singleton EnrollmentPorcessing instance.</p>
	 * @return the singleton instance of EnrollmentPorcessing
	 */
	public static synchronized SOAPUtil get() {
	thisIsTestObj =  PageFactory.initElements(getWebDriver(), SOAPUtil.class);
		return thisIsTestObj;
	}
	
	/**
	 * Method to get the SOAP End point URL for ODS Accums
	 * @param region
	 * @return
	 */
	public String getEndPointURL(String region)
	{
		String endPointURL = "";
		try
		{
			switch (region) {
			case "IMSN":
			case "IMSW":
				endPointURL = EnvHelper.getValue("end.point.url");
				break;

			default:
				break;
			}
		}
		catch(Exception e)
		{
			CoreSuperHelper.processException(e, "Exception occured in the getEndPointUrl");
		}
		return endPointURL;
	}
	
	
	
	
	public String getAccumMessage(String TCID,String strRegion,String requestBody)
	{
		String strcontractCode = getCellValue("ContractCode").trim();
		String strmemberID = getCellValue("HCID").trim();
		String strmemberCode = getCellValue("MemberCode").trim();
		String strmemberSequenceNumber = getCellValue("MemberSeqNumber").trim();	
		String strdateOfBirth = getCellValue("MemberDOB").trim();
		

		String strgroupNumber = getCellValue("GroupNumber").trim();
		String strcaseNumber = getCellValue("CaseNumber").trim();
		String strmemberType = getCellValue("MemberType").trim();
		String strMemberEligSrcID = getCellValue("MemberEligSrcID").trim();

		String strserviceStartDate= getCellValue("ServiceStartDate").trim();
		String strserviceEndDate= getCellValue("ServiceEndDate").trim();
		String strBenEffecitiveDate = getCellValue("BenEffectiveDate").trim();
		String strBenTermDate = getCellValue("BenTermDate").trim();
		strdateOfBirth = DateTimeUtils.convertDateFormat(strdateOfBirth, "MMddyyyy", "yyyy-MM-dd");

		//			Updating the details in the template file 
		
		requestBody = replaceValue(requestBody, "strRegion", strRegion);
		requestBody = replaceValue(requestBody, "strmemberID", strmemberID);
		requestBody = replaceValue(requestBody, "strcontractCode", strcontractCode);
		requestBody = replaceValue(requestBody, "strmemberCode", strmemberCode);
		requestBody = replaceValue(requestBody, "strmemberSequenceNumber", strmemberSequenceNumber);
		requestBody = replaceValue(requestBody, "strdateOfBirth", strdateOfBirth);
		requestBody = replaceValue(requestBody, "strgroupNumber", strgroupNumber);
		requestBody = replaceValue(requestBody, "strcaseNumber", strcaseNumber);
		requestBody = replaceValue(requestBody, "strmemberType", strmemberType);
		requestBody = replaceValue(requestBody, "strMemberEligSrcID", strMemberEligSrcID);
		requestBody = replaceValue(requestBody, "strserviceStartDate", strserviceStartDate);
		requestBody = replaceValue(requestBody, "strserviceEndDate", strserviceEndDate);
		requestBody = replaceValue(requestBody, "strBenEffecitiveDate", strBenEffecitiveDate);
		requestBody = replaceValue(requestBody, "strBenTermDate", strBenTermDate);
		
		return requestBody;
	}
	
	
	/**
>>>>>>> branch 'develop' of https://AG14541@bitbucket.anthem.com/scm/p2p/accums-reimagine-automation.git
	 * This method will read the data form the Notepad file located by the path
	 * @return
	 */
	public  String readNotePad(String notepadPath)
	{
		String requestTemplate = "";
		try
		{
			FileInputStream fstream = new  FileInputStream(notepadPath);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int counter = 1;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {

				if(counter>1)
				{
					requestTemplate = requestTemplate + "\n";
				}

				requestTemplate = requestTemplate + strLine ;
				counter = counter + 1;

			}

			//Close the input stream
			in.close();




		}
		catch(Exception e)
		{
			e.printStackTrace();
			CoreSuperHelper.processException(e, "Exception occured in the getEndPointUrl");
		}
		return requestTemplate;
	}
	
	public String replaceValue(String strText, String strExpr, String strValue)
	{
		String replaceText = strText.replaceAll(strExpr, strValue);
		return replaceText;

	}
	
	
	public void saveSOAPMessage(String requestMessage, String reqPath) 
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(requestMessage)));

			TransformerFactory transfact = TransformerFactory.newInstance();
			Transformer trans = transfact.newTransformer();
			Source src = new DOMSource(document);
			Result dest = new StreamResult(new File(reqPath));
			trans.transform(src, dest);

		}
		catch(Exception e)
		{
			CoreSuperHelper.processException(e, "Exception occured in the Saving soap request");
		}

	}
	
	
	
	
	
	
	
	/**
	 * Method to get the DB2 url based on the region 
	 * @param region
	 * @return
	 */
	public String getDB2Details(String region)
	{
		String dbURL = "";
		if(region.contains("IM$E"))
		{
			region= "IM$E";
		}
		switch (region.trim()) {
		case "IMSN":
			dbURL = EnvHelper.getValue("IMSN.DB2");
			break;
		case "IMSW":
			dbURL = EnvHelper.getValue("IMSW.DB2");
			break;
		case "IM$E":
		case "QA":
			dbURL = EnvHelper.getValue("IM$E.DB2");
			break;
		default:
			throw new IllegalArgumentException("Please provide a valid region");
		}
		return dbURL;
	}
	
	/**
	 * Method to fetch the schema details based on the Accum
	 * @param region
	 * @return
	 */
	public String getSchema(String region)
	{
		String schema = "";
		if(region.equals("QA"))
		{
			schema = "GNCE6";
		}
		else
		{
			schema = "GNC";
		}
		
		return schema;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Function to validate the SOAP response based on the status
	 * @param strFilePath: file where the SOAP response lies
	 */
	public void validateSOAPResponse(String strFilePath)
	{
		try
		{
			if(isLastTestPassed()|| isIgnoreLastTestFailure())
			{
			File soapResponse = new File(strFilePath);
			if(soapResponse.exists())
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(soapResponse);
				Element rootElement = document.getDocumentElement();
				String strFaultString = getString("faultstring",rootElement);
				if(strFaultString == null)
				{
					String strStatus= "";
					String statusNode = "tns_2:status";
					strStatus = getString(statusNode, rootElement);
					strStatus= strStatus.replaceAll("\n", "");
					validateStatusCode(strStatus);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, "Validate SOAP response", "Please check the input parameters: "+strFaultString);
				}
			}
			else
			{
				setLastTestPassed(false);
				log(FAIL, "Validate SOAP response", "Not able to find the response file.");
			}
			}
			else
			{
				log(SKIP, "Validate SOAP Response has been skipped as the previous step is failure");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			processException(e, "Validate SOAP response");
		}


	}
	
	/**
	 * Function to validate the SOAP response based on the status
	 * @param strFilePath: file where the SOAP response lies
	 */
	public void validategetAccumResponse(String strFilePath)
	{
		try
		{
			if(isLastTestPassed()|| isIgnoreLastTestFailure())
			{
			File soapResponse = new File(strFilePath);
			if(soapResponse.exists())
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(soapResponse);
				Element rootElement = document.getDocumentElement();
				String strFaultString = getString("faultstring",rootElement);
				if(strFaultString == null)
				{
					String strStatus= "";
					String statusNode = "";
						statusNode="tns_1:callStatus";
					strStatus = getString(statusNode, rootElement);
					strStatus= strStatus.replaceAll("\n", "");
					validateStatusCode(strStatus);
				}
				else
				{
					setLastTestPassed(false);
					log(FAIL, "Validate SOAP response", "Please check the input parameters: "+strFaultString);
				}
			}
			else
			{
				setLastTestPassed(false);
				log(FAIL, "Validate SOAP response", "Not able to find the response file.");
			}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			processException(e, "Validate SOAP response");
		}


	}
	
	
	private String getString(String tagName, Element element) {

		NodeList list = element.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			NodeList subList = list.item(0).getChildNodes();

			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}
		return null;
	}
	
	
	/**
	 * This method is used for SOAP Response Code lookup
	 * @param responseCode
	 * @return
	 */
	private boolean validateStatusCode(String responseCode)
	{
		String responseText = "";
		boolean validationStatus = false;
		try
		{
			switch (responseCode) {
			case "01":
				responseText = "Claim processed successfully";
				validationStatus = true;
				break;
			case "02":
				responseText = "Accum not found";
				break;
			case "05":
				responseText = "Claim processed successfully but Overapplied";
				validationStatus = true;
				break;
			case "06":
				responseText = "Claim processed successfully with negative amount";
				validationStatus = true;
				break;
			case "20":
				responseText = "Claim processing failed as Member not found";
				break;
			case "21":
				responseText = "DB unavailable";
				break;
			case "22":
				responseText = "Data is missing";
				break;
			case "23":
				responseText = "Claim processing failed with Data Exception";
				break;
			case "24":
				responseText = "Claim processing failed as Benefit not found";
				break;
			case "25":
				responseText = "Claim processing failed as Contract not found";
				break;
			case "26":
				responseText = "Claim processing failed with Member Coverage error";
				break;
			case "28":
				responseText = "Claim processing failed as Recycle failed";
				break;
			case "29":
				responseText = "Claim processing failed as Last Recycle failed";
				break;
			case "31":
				responseText = "Claim processing failed as Accum over applied";
				break;
			case "32":
				responseText = "Claim processing failed as Source CD Invalid";
				break;
			case "33":
				responseText = "Claim processing failed as PROD Type Invalid";
				break;
			case "34":
				responseText = "Claim processing failed as Invalid FN Code";
				break;
			case "35":
				responseText = "Claim processing failed as Data not found";
				break;
			case "36":
				responseText = "Claim processing failed as Duplicate REQ Found";
				break;
			case "37":
				responseText = "Claim processing failed with -NO-CMNG-ACM-FND";
				break;
			case "38":
				responseText = "Claim processing failed with -ACCM-LKUP-INVALID";
				break;
			case "91":
				responseText = "Claim processing failed with Invalid Accum";
				break;

			default:
				responseText = "Claim processing failed with response code"+responseCode;
				break;
			}
			
			if(validationStatus)
			{
				log(PASS, "Validate status code in the SOAP Response", responseText);
			}
			else
			{
				setLastTestPassed(false);
				log(FAIL, "Validate status code in the SOAP Response", responseText);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			processException(e, "Validate Status code");
		}
		return true;
	}
	
	public static String ParseErrMsg(String msg) {
		String resMsg=msg.replace("\"error\":", "");
		resMsg=resMsg.replace("\"text\":", "");
		resMsg=resMsg.replace("{", "");
		resMsg=resMsg.replace("}", "");
	    return resMsg;
	}
	
	public String getXMLTemplatePath()
	{
		String notepadPath =  System.getProperty("user.dir") + 
				"\\src\\test\\resources\\xmlTemplates\\" ;
		return notepadPath;
		
	}
	
	public String getTestDataFileName()
	{
		return getXMLTemplatePath()+EnvHelper.getValue("testDataFileName");
	}
	
	
	
	
	
	/**
	 * @param claimNumber
	 * @param reasonCode
	 * @param strUserProfile
	 * @param strRegion
	 * @throws Exception
	 */
	public void validateAccumLog(String claimNumber,String reasonCode,String strUserProfile,String strRegion) throws Exception
	{
		Connection conn=null;
		try
		{
			if(isLastTestPassed()||isIgnoreLastTestFailure())
			{
			String dbURL = getDB2Details(strRegion);
			String contractCode = getCellValue("ContractCode");
			String HCID = getCellValue("HCID");

			String query = "SELECT * FROM GNC.ACCUMR_LOG_SMRY where IN_CVRG_PLAN_ID1= '"+contractCode+"' and IN_HC_ID='"+HCID+"' " +
					"and IN_CLM_NBR='"+claimNumber+"'";
			String queryResultPath = getReportPathFolder()+"\\AccumLOGSummary"+getCurrentRow()+"_"+reasonCode+".xlsx";
			String[] userInfo = getLoginInfo(strUserProfile);
			String username = userInfo[0];
			String password = userInfo[1];
			conn = DatabaseUtils.getConnection(DatabaseConstants.DB2_Driver, dbURL, username, password);
			ResultSet set = DatabaseUtils.executeQuery(conn, query);
			exportResults(set, queryResultPath, query);
			
			boolean claimfound = false;
			List<List<String>> rowValues = ExcelUtils.getAllRowValues(queryResultPath, "Results", false);
			
			Iterator<List<String>> rowIterator = rowValues.iterator();
			
			while(rowIterator.hasNext())
			{
				List<String> rowValue = rowIterator.next();
				String actualClaim  = rowValue.get(16);
				String actualReasonCode = rowValue.get(23);
				if(actualClaim.equalsIgnoreCase(claimNumber))
				{
					if(actualReasonCode.equalsIgnoreCase(reasonCode))
					{
						claimfound= true;
						break;
					}
				}
			}
			
			if(claimfound)
			{
				log(PASS, "Validate Accum Log summary", "Accum log has been created, Please refer to the AccumLogSummary.xlsx document for the results");
			}
			else
			{
				setLastTestPassed(false);
				log(FAIL, "Validate Accum Log summary", "Accum log has not been created, Please refer to the AccumLogSummary.xlsx document for the results");
			}
			}
			else
			{
				log(SKIP, "Validate Accum Log has been skipped as the previous step is failure");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			CoreSuperHelper.processException(e, "Exception occured while validating Accum Log in the DB2");
		}
		finally
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
	}
	
	
	
	/**
	 * Method to export the results to excel workbook in the path specified by QueryResultPath
	 * @param set: result set which holds the query results 
	 * @param queryResultPath: Path where excel workbook needs to be saved
	 * @throws Exception
	 */
	public void exportResults(ResultSet set, String queryResultPath,String query) throws Exception
	{
		XSSFWorkbook workbook = null  ;
		try
		{
			workbook = new XSSFWorkbook();
			XSSFSheet querySheet = workbook.createSheet("Query");
			XSSFRow QueryRow = querySheet.createRow(0);
			XSSFCell QueryCell = QueryRow.createCell(0);
			QueryCell.setCellValue(query);
			XSSFSheet sheet = workbook.createSheet("Results");
			XSSFRow row = sheet.createRow(0);
			ResultSetMetaData data = set.getMetaData();
			int columnCount = data.getColumnCount();
			for(int i=1; i<=columnCount;i++)
			{
				String ColumnHeader = data.getColumnLabel(i);
				XSSFCell cell = row.createCell(i-1);
				cell.setCellValue(ColumnHeader);

			}
			int rowCount = 0;
			while(set.next())
			{
				rowCount++;
				row = sheet.createRow(rowCount);
				for(int i=1; i<=columnCount;i++)
				{
					String ColumnHeader = data.getColumnLabel(i);
					String columnValue = set.getString(ColumnHeader).trim();
					XSSFCell cell = row.createCell(i-1);
					cell.setCellValue(columnValue);

				}
			}
			FileOutputStream fileout = new FileOutputStream(queryResultPath);
			workbook.write(fileout);
			fileout.close();

		}
		catch (Exception e) {
			e.printStackTrace();
			processException(e, "Exception occured while exporting the results to Excel workbook");
		}
		finally {
			if(workbook!=null)
			{
				workbook.close();
			}
		}

	}
	
	
	
	
	
	
	

	
	
	
}
