/**
 * 
 */
package utility;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shiva.katula
 *
 */
public class DPDAUtils {

	static Map<String,String> transactionList = new HashMap<String,String>();
	
	static {
		transactionList.put("Clinic TIN Change", "D01");
		transactionList.put("Directory Address Change","D02");
		transactionList.put("Legal Name Change","D03");
		transactionList.put("Location Closed ","D04");
		transactionList.put("Billing Address Change","D05");
		transactionList.put("Correspondence Address Change","D06");
		transactionList.put("DBA Name Change","D07");
		transactionList.put("Misc Change-Clinic","D08");
		transactionList.put("Location - New","D09");
		transactionList.put("Clinic NPI Delete  ","D10");
		transactionList.put("Clinic NPI Add  ","D11");
		transactionList.put("Clinic NPI Change","D12");
		transactionList.put("Corporate NPI Delete  ","D13");
		transactionList.put("Corporate NPI Add  ","D14");
		transactionList.put("Corporate NPI Change","D15");
		transactionList.put("Specialty Change","D16");
		transactionList.put("License Change","D17");
		transactionList.put("Provider Name Change ","D18");
		transactionList.put("Remove Provider from directory (all networks)","D19");
		transactionList.put("Add provider to directory (all networks)","D20");
		transactionList.put("Misc Change-Provider","D21");
		transactionList.put("Individual NPI Add","D22");
		transactionList.put("Individual NPI Delete","D23");
		transactionList.put("Individual NPI Change","D24");
		transactionList.put("Add Provider to Location","D25");
		transactionList.put("Network Change","D27");
		transactionList.put("Network Change(Provider)","D27");
		transactionList.put("No Longer at Location","D29");
		transactionList.put("Termination (Voluntary)","D31");
		transactionList.put("Location Closed-HMO","D32");
		transactionList.put("DHMO Termination - Voluntary ","D33");
		transactionList.put("Open to New Enrollment","D35");
		transactionList.put("Closed to New Enrollment","D36");
		transactionList.put("Payment Address Change","D37");
		transactionList.put("Legal Address Change","D38");
		transactionList.put("ECP Information ","D39");
		transactionList.put("Add Provider to New State","D40");
		transactionList.put("Focus Review for Provider","D41");
		transactionList.put("Focus Review(Provider)","D41");
		transactionList.put("Focus Review for Clinic","D42");
		transactionList.put("Focus Review","D42");
		transactionList.put("835 Transaction","D43");
		transactionList.put("Add Directory-Clinic (all networks, all Providers)","D44");
		transactionList.put("Add Directory-Legal (all Clinics, networks, all Providers)","D45");
		transactionList.put("Add HMO PDO Location/Provider (CA, WI, Empire)","D46");
		
		transactionList.put("American Board Certified","D48");
		transactionList.put("Contract Change Anthem to Diversified","D49");
		transactionList.put("Contract Change Diversified to Anthem","D50");
		transactionList.put("DHMO Termination - In-Voluntary ","D51");
		transactionList.put("Directory Inaccuracy","D52");
		transactionList.put("Fee Update","D54");
		transactionList.put("Grid Inquiry","D55");
		transactionList.put("Grievance Inquiry","D56");
		transactionList.put("Initial Contracting","D58");
		transactionList.put("Large Group Inquiry","D60");
		transactionList.put("Location Existing","D61");
		transactionList.put("Location Termination - In-Voluntary","D62");
		transactionList.put("Location Termination - Voluntary","D63");
		transactionList.put("Network Change HMO PDO","D64");
		
		transactionList.put("Ownership Change","D66");
		transactionList.put("Provider Inquiry","D67");
		transactionList.put("ReCredentialing","D68");
		transactionList.put("Remove Directory-Clinic (all networks, all Providers)","D69");
		transactionList.put("Remove Directory-Legal (all Clinics, networks, all Providers)","D70");
		
		transactionList.put("Termination (Involuntary)","D73");
		transactionList.put("Capitation Fee Update","D76");
		transactionList.put("Department Inquiry","D77");
		transactionList.put("External Inquiry","D78");
		transactionList.put("SIU Inquiry","D79");
		transactionList.put("Legal/Compliance Inquiry","D80");
		transactionList.put("Cred Misc Change","D81");
		transactionList.put("Verification Process","D82");
		transactionList.put("Sanction","D83");
		transactionList.put("Legal Name Change(Legal)","D84");		
		transactionList.put("Legal Address Change(Legal)","D85");
		transactionList.put("Directory Accuracy Inquiry","D86");
		transactionList.put("Network Change","D88");
		transactionList.put("Sanction(Provider)","D90");
		transactionList.put("Credentialing Provider Inquiry","D91");
	}
	public static String getTransactionValue(String transactionName){
		return transactionList.get(transactionName.trim());
	}
	
	static Map<String, String> addressTypeList = new HashMap<String, String>();

	static {
		addressTypeList.put("Billing Address", "BillingAddress");
		addressTypeList.put("Correspondence Address", "MailingAddress");
		addressTypeList.put("Directory Address", "DirectoryAddress");
	}

	public static String getAddressTypeValue(String addressType) {
		return addressTypeList.get(addressType.trim());
	}

	static Map<String, String> workbasketList = new HashMap<String, String>();

	static {
		workbasketList.put("Approval Required", "DAApprovalRequired");
		workbasketList.put("Assessment Required", "DAAssessmentRequired");
		workbasketList.put("Campaign Master", "DACampaignMaster");
		workbasketList.put("DA Inaccuracies", "DAInaccuracies");
		workbasketList.put("DA Inquiries", "DAInquiries");
		workbasketList.put("Escalation", "DAEscalation");
		workbasketList.put("Follow-up Required", "DAFollowUpRequired");
		workbasketList.put("MRI", "DAMissingRequiredInformation");
		workbasketList.put("Research Required", "DAResearchRequired");
		workbasketList.put("Unassigned Data Changes", "DAUnassignedDataChanges");
		workbasketList.put("Unassigned Verification", "DAUnassignedVerifications");
		workbasketList.put("Unresponsive", "DAUnResponsive");
	}

	public static String getWorkbasketValue(String workbasketName) {
		return workbasketList.get(workbasketName.trim());
	}
	
	static Map<String, Integer> officeSearchDPSSearchHeadMap = new HashMap<String, Integer>();
	// Only DPS Search with or without Unique Search "Clinic DPS No"
	static {
		officeSearchDPSSearchHeadMap.put("SELECT", 1);
		officeSearchDPSSearchHeadMap.put("DPSCLINICNO", 2);
		officeSearchDPSSearchHeadMap.put("TAXIDNO", 3);
		officeSearchDPSSearchHeadMap.put("OFFICESTATUS", 4);
		officeSearchDPSSearchHeadMap.put("DBANAME", 5);
		officeSearchDPSSearchHeadMap.put("LARGEGROUPNAME", 6);
		officeSearchDPSSearchHeadMap.put("LEGALNAME", 7);
		officeSearchDPSSearchHeadMap.put("DIRECTORYADDRESS1", 8);
		officeSearchDPSSearchHeadMap.put("DIRECTORYADDRESS2", 9);
		officeSearchDPSSearchHeadMap.put("CITY", 10);
		officeSearchDPSSearchHeadMap.put("ZIP", 11);
		officeSearchDPSSearchHeadMap.put("COUNTY", 12);
		officeSearchDPSSearchHeadMap.put("PHONE", 13);
		officeSearchDPSSearchHeadMap.put("SHOWDETAILS", 14);
	}

	static Map<String, Integer> officeSearchCaseSearchHeadMap = new HashMap<String, Integer>();
	// Only Case Search with or without Unique Search "Clinic DPS No"
	static {
		officeSearchCaseSearchHeadMap.put("CASEID", 1);
		officeSearchCaseSearchHeadMap.put("CASESTATUS", 2);
		officeSearchCaseSearchHeadMap.put("TRANSACTION", 3);
		officeSearchCaseSearchHeadMap.put("WORKGROUP", 4);
		officeSearchCaseSearchHeadMap.put("WORKINGSTATUS", 5);
		officeSearchCaseSearchHeadMap.put("STATUSTAKEN", 6);
		officeSearchCaseSearchHeadMap.put("ASSIGNEDTO", 7);
		officeSearchCaseSearchHeadMap.put("DPSCLINICNO", 8);
		officeSearchCaseSearchHeadMap.put("TAXIDNO", 9);
		officeSearchCaseSearchHeadMap.put("DBANAME", 10);
		officeSearchCaseSearchHeadMap.put("DIRECTORYADDRESS1", 11);
		officeSearchCaseSearchHeadMap.put("CITY", 12);
		officeSearchCaseSearchHeadMap.put("ZIP", 13);
		officeSearchCaseSearchHeadMap.put("REOPEN", 14);
	}

	public static Integer getOfficeSearchHeaderValue(boolean isCaseSearch, String headerKey) {
		if (isCaseSearch) {
			return officeSearchCaseSearchHeadMap.get(headerKey);
		}
		return officeSearchDPSSearchHeadMap.get(headerKey.trim());
	}
	
	static Map<String,Integer> officeSearchHeadMap = new HashMap<String,Integer>();
	static {
		officeSearchHeadMap.put("SELECT", 1);
		officeSearchHeadMap.put("DPSCLINICNO", 2);
		officeSearchHeadMap.put("TAXIDNO", 3);
		officeSearchHeadMap.put("OFFICESTATUS", 4);
		officeSearchHeadMap.put("DBANAME", 5);
		officeSearchHeadMap.put("LARGEGROUPNAME", 6);
		officeSearchHeadMap.put("LEGALNAME", 7);
		officeSearchHeadMap.put("DIRECTORYADDRESS1", 8);
		officeSearchHeadMap.put("DIRECTORYADDRESS2", 9);
		officeSearchHeadMap.put("CITY", 10);
		officeSearchHeadMap.put("ZIP", 11);
		officeSearchHeadMap.put("COUNTY", 12);
		officeSearchHeadMap.put("PHONE", 13);
		officeSearchHeadMap.put("SHOWDETAILS", 14);
	}
	public static Integer getOfficeSearchHeaderValue(String headerKey){
		return officeSearchHeadMap.get(headerKey.trim());
	}
	
	static Map<String,Integer> searchNetworkHeadMap = new HashMap<String,Integer>();
	static {
				
		searchNetworkHeadMap.put("DBAName", 1);
		searchNetworkHeadMap.put("DirectoryAddressLine1", 2);
		searchNetworkHeadMap.put("DirectoryCity", 3);
		searchNetworkHeadMap.put("DirectoryState", 4);
		searchNetworkHeadMap.put("DirectoryZipCode", 5);
		searchNetworkHeadMap.put("DirectoryCounty", 6);
		searchNetworkHeadMap.put("Providers", 7);
	}
	public static Integer getNetworkSearchHeaderValue(String headerKey){
		return searchNetworkHeadMap.get(headerKey.trim());
	}
	
	public static Integer getNetworkValidationSearchHeaderValue(String headerKey){
		return searchNetworkValidationHeadMap.get(headerKey.trim());
	}
	
	static Map<String,Integer> searchNetworkValidationHeadMap = new HashMap<String,Integer>();
	static {
		searchNetworkValidationHeadMap.put("Network ID", 1);
		searchNetworkValidationHeadMap.put("Network Name", 2);
	}
	
	
	public static Integer getParValidationSearchHeaderValue(String headerKey){
		return searchParValidationHeadMap.get(headerKey.trim());
	}
	
	static Map<String,Integer> searchParValidationHeadMap = new HashMap<String,Integer>();
	static {
				
		searchParValidationHeadMap.put("Provider Name", 1);
		searchParValidationHeadMap.put("Credentialing Level", 2);
		searchParValidationHeadMap.put("Specialty", 3);
	}
	
	static Map<String,Integer> w9LegalSearchHeadMap = new HashMap<String,Integer>();
	static {
		w9LegalSearchHeadMap.put("DBAName", 1);
		w9LegalSearchHeadMap.put("LargeGroupName", 2);
		w9LegalSearchHeadMap.put("ClinicStatus", 3);
		w9LegalSearchHeadMap.put("HMOPDOOffice", 4);
		w9LegalSearchHeadMap.put("DelegatedCred", 5);
		w9LegalSearchHeadMap.put("SharingSpace", 6);
		w9LegalSearchHeadMap.put("Diversify", 7);
		w9LegalSearchHeadMap.put("DirectoryAddress1", 8);
		w9LegalSearchHeadMap.put("DirectoryAddress2", 9);
		w9LegalSearchHeadMap.put("City", 10);
		w9LegalSearchHeadMap.put("State", 11);
		w9LegalSearchHeadMap.put("Zip", 12);
		w9LegalSearchHeadMap.put("Phone", 13);
	}
	public static Integer getW9LegalSearchHeaderValue(String headerKey){
		return w9LegalSearchHeadMap.get(headerKey.trim());
	}
	
	static Map<String,Integer> caseIDSearchHeadMap = new HashMap<String,Integer>();
	static {
		// LG Case Type
		caseIDSearchHeadMap.put("CaseID", 0);
		caseIDSearchHeadMap.put("CaseStatus", 1);
		caseIDSearchHeadMap.put("Transaction", 2);
		caseIDSearchHeadMap.put("Workgroup", 3);
		caseIDSearchHeadMap.put("WorkingStatus", 4);
		caseIDSearchHeadMap.put("StatusTaken", 5);
		caseIDSearchHeadMap.put("AssignedTo", 6); 
		// TRN Case Type
		caseIDSearchHeadMap.put("DPSClinicNo", 7); 
		caseIDSearchHeadMap.put("TaxIDNo", 8); 
		caseIDSearchHeadMap.put("DBAName", 9); 
		caseIDSearchHeadMap.put("DirectoryAddress1", 9); 
		caseIDSearchHeadMap.put("City", 10); 
		caseIDSearchHeadMap.put("Zip", 11); 
		//INQ Case Type
		caseIDSearchHeadMap.put("ProviderDPSNo", 7);
		caseIDSearchHeadMap.put("FirstName", 8);
		caseIDSearchHeadMap.put("LastName", 9);
		caseIDSearchHeadMap.put("MiddleInitial", 10);
		caseIDSearchHeadMap.put("NPI", 11);
	}
	static Map<String,Integer> providerOfficeSearchHeadMap = new HashMap<String,Integer>();
	static {
		providerOfficeSearchHeadMap.put("Select", 0);
		providerOfficeSearchHeadMap.put("FirstName", 1);
		providerOfficeSearchHeadMap.put("MiddleIntial", 2);
		providerOfficeSearchHeadMap.put("LastName", 3);
		providerOfficeSearchHeadMap.put("Suffix", 4);
		providerOfficeSearchHeadMap.put("Specialty", 5);
		providerOfficeSearchHeadMap.put("Degree", 6);
		providerOfficeSearchHeadMap.put("IndividualNPI", 7);	
		providerOfficeSearchHeadMap.put("ProviderStatus", 8);
		providerOfficeSearchHeadMap.put("ProviderDPSNo", 9);
		
	}
	
	static Map<String,Integer> providerSearchDPSSearchHeadMap = new HashMap<String,Integer>();
	static {
		providerSearchDPSSearchHeadMap.put("Select", 0);
		providerSearchDPSSearchHeadMap.put("FirstName", 1);
		providerSearchDPSSearchHeadMap.put("MiddleIntial", 2);
		providerSearchDPSSearchHeadMap.put("LastName", 3);
		providerSearchDPSSearchHeadMap.put("Suffix", 4);
		providerSearchDPSSearchHeadMap.put("Specialty", 5);
		providerSearchDPSSearchHeadMap.put("IndividualNPI", 6);
		providerSearchDPSSearchHeadMap.put("LicenseStatus", 7);
		providerSearchDPSSearchHeadMap.put("ProviderStatus", 8);
		providerSearchDPSSearchHeadMap.put("ProviderDPSNo", 9);
		
	}
	
	static Map<String,Integer> providerSearchCaseSearchHeadMap = new HashMap<String,Integer>();
	static {
		providerSearchCaseSearchHeadMap.put("CaseId", 0);
		providerSearchCaseSearchHeadMap.put("CaseStatus", 1);
		providerSearchCaseSearchHeadMap.put("Transaction", 2);
		providerSearchCaseSearchHeadMap.put("WorkGroup", 3);
		providerSearchCaseSearchHeadMap.put("WorkingStatus", 4);
		providerSearchCaseSearchHeadMap.put("StatusTaken", 5);
		providerSearchCaseSearchHeadMap.put("AssignedTo", 6);
		providerSearchCaseSearchHeadMap.put("ProviderDPSNo", 7);
		providerSearchCaseSearchHeadMap.put("FirstName", 8);
		providerSearchCaseSearchHeadMap.put("LastName", 9);
		providerSearchCaseSearchHeadMap.put("MiddleIntial", 10);
		providerSearchCaseSearchHeadMap.put("NPI", 11);
		}	
	static Map<String,Integer> providerOfficeDPSSearchHeadMap = new HashMap<String,Integer>();
	static {
		providerOfficeDPSSearchHeadMap.put("Select", 1);
		providerOfficeDPSSearchHeadMap.put("FirstName", 2);
		providerOfficeDPSSearchHeadMap.put("MiddleIntial", 3);
		providerOfficeDPSSearchHeadMap.put("LastName", 4);
		providerOfficeDPSSearchHeadMap.put("Suffix", 5);
		providerOfficeDPSSearchHeadMap.put("Specialty", 6);
		providerOfficeDPSSearchHeadMap.put("Degree", 7);
		providerOfficeDPSSearchHeadMap.put("IndividualNPI", 8);	
		providerOfficeDPSSearchHeadMap.put("ProviderStatus", 9);
		providerOfficeDPSSearchHeadMap.put("ProviderDPSNo", 10);
		
	}
	
	public static Integer getProviderOfficeSearchHeaderValue(boolean isCaseSearch,String headerKey){
		if (isCaseSearch) {
		return providerOfficeCaseSearchHeadMap.get(headerKey.trim());
	}
		return providerOfficeDPSSearchHeadMap.get(headerKey.trim());
	}
		
	static Map<String,Integer> providerOfficeCaseSearchHeadMap = new HashMap<String,Integer>();
	static {
		
		providerOfficeCaseSearchHeadMap.put("CaseId", 1);
		providerOfficeCaseSearchHeadMap.put("CaseStatus", 2);
		providerOfficeCaseSearchHeadMap.put("Transaction", 3);
		providerOfficeCaseSearchHeadMap.put("WorkGroup", 4);
		providerOfficeCaseSearchHeadMap.put("WorkingStatus", 5);
		providerOfficeCaseSearchHeadMap.put("StatusTaken", 6);
		providerOfficeCaseSearchHeadMap.put("AssignedTo", 7);
		providerOfficeCaseSearchHeadMap.put("DPSClinicNo", 8);	
		providerOfficeCaseSearchHeadMap.put("TaxIDNo", 9);
		providerOfficeCaseSearchHeadMap.put("DBAName", 10);
		providerOfficeCaseSearchHeadMap.put("DirectoryAddress1", 11);
		providerOfficeCaseSearchHeadMap.put("City", 12);
		
	}
	static Map<String,Integer> providerOfficeLicenseSearchHeadMap = new HashMap<String,Integer>();
	static {
		providerOfficeSearchHeadMap.put("LicenseNumber", 0);
		providerOfficeSearchHeadMap.put("LicenseState", 1);
		providerOfficeSearchHeadMap.put("LicenseStatus", 2);
		
		
	}
	public static Integer getProviderOfficeLicenseSearchValue(String headerKey){
		return providerOfficeLicenseSearchHeadMap.get(headerKey.trim());
	}
	
	public static Integer getProviderOfficeSearchValue(String headerKey){
		return providerOfficeSearchHeadMap.get(headerKey.trim());
	}
	
	public static Integer getProviderSearchHeaderValue(boolean isCaseSeacrh, String headerKey){
		if (isCaseSeacrh) {
			return providerSearchCaseSearchHeadMap.get(headerKey);
		}
		return providerSearchDPSSearchHeadMap.get(headerKey.trim());
	}
	
	
	public static Integer getCaseIDSearchHeaderValue(String headerKey){
		return caseIDSearchHeadMap.get(headerKey.trim());
	}
		
	static Map<String, Integer> w9LegalSearchDPSSearchHeadMap = new HashMap<String, Integer>();
	// Only DPS Search with or without Unique Search "Clinic DPS No"
	static {
		w9LegalSearchDPSSearchHeadMap.put("Select", 1);
		w9LegalSearchDPSSearchHeadMap.put("IRSName", 2);
		w9LegalSearchDPSSearchHeadMap.put("TIN", 3);
		w9LegalSearchDPSSearchHeadMap.put("TinType", 4);
		w9LegalSearchDPSSearchHeadMap.put("DPSLegalNo", 5);
		w9LegalSearchDPSSearchHeadMap.put("TINStatus", 6);
	}

	static Map<String, Integer> w9LegalSearchCaseSearchHeadMap = new HashMap<String, Integer>();
	// Only Case Search with or without Unique Search "Clinic DPS No"
	static {
		w9LegalSearchCaseSearchHeadMap.put("CaseID", 1);
		w9LegalSearchCaseSearchHeadMap.put("CaseStatus", 2);
		w9LegalSearchCaseSearchHeadMap.put("Transaction", 3);
		w9LegalSearchCaseSearchHeadMap.put("WorkGroup", 4);
		w9LegalSearchCaseSearchHeadMap.put("WorkingStatus", 5);
		w9LegalSearchCaseSearchHeadMap.put("StatusTaken", 6);
		w9LegalSearchCaseSearchHeadMap.put("AssignedTo", 7);
		w9LegalSearchCaseSearchHeadMap.put("IRSName", 8);
		w9LegalSearchCaseSearchHeadMap.put("TIN", 9);
		w9LegalSearchCaseSearchHeadMap.put("TINType", 10);
	}

	public static Integer getw9LegalHeaderValue(boolean isCaseSearch, String headerKey) {
		if (isCaseSearch) {
			return w9LegalSearchCaseSearchHeadMap.get(headerKey);
		}
		return w9LegalSearchDPSSearchHeadMap.get(headerKey.trim());
	}
	
	static Map<String, Integer> hmoPdoSearchCaseSearchHeadMap = new HashMap<String, Integer>();
	// Only HMO PDO Search 
	static {
		hmoPdoSearchCaseSearchHeadMap.put("PDONumber", 1);
		hmoPdoSearchCaseSearchHeadMap.put("TIN", 2);
	}

	public static Integer getHMOPDOSearchHeaderValue(String headerKey) {
			return hmoPdoSearchCaseSearchHeadMap.get(headerKey);
	}
	
	static Map<String, Integer> hmoTransferSearchHeadMap = new HashMap<String, Integer>();
	// Only HMO PDO Search 
	static {
		hmoTransferSearchHeadMap.put("TIN", 1);
		hmoTransferSearchHeadMap.put("PDONumber", 2);
		
	}

	public static Integer getHMOTransferearchHeaderValue(String headerKey) {
			return hmoTransferSearchHeadMap.get(headerKey);
	}
	

	static Map<String, Integer> lgSearchHeadMap = new HashMap<String, Integer>();
	// Only LG Search 
	static {
		lgSearchHeadMap.put("TIN", 1);
	}

	public static Integer getLGSearchHeaderValue(String headerKey) {
			return lgSearchHeadMap.get(headerKey);
	}
	
	static Map<String, Integer> workbasketHeadMap = new HashMap<String, Integer>();
	static {
		workbasketHeadMap.put("Select", 1);
		workbasketHeadMap.put("Urgent", 2);
		workbasketHeadMap.put("Priority", 3);
		workbasketHeadMap.put("Due Date", 4);
		workbasketHeadMap.put("Case ID", 5);
		workbasketHeadMap.put("Receipt Date", 6);
		workbasketHeadMap.put("Transaction", 7);
		workbasketHeadMap.put("Large Group Details", 8);
		workbasketHeadMap.put("Working Status", 9);
		workbasketHeadMap.put("Status Taken", 10);
		workbasketHeadMap.put("Last Name", 11);
		workbasketHeadMap.put("First Name", 12);
		workbasketHeadMap.put("NPI (Indv)", 13);
		workbasketHeadMap.put("License#", 14);
		workbasketHeadMap.put("License State", 15);
		workbasketHeadMap.put("TIN", 16);
		workbasketHeadMap.put("Directory Address", 17);
		workbasketHeadMap.put("City", 18);
		workbasketHeadMap.put("State", 19);
	}
	
	static Map<String, Integer> workbasketCMHeadMap = new HashMap<String, Integer>();
	static {
		workbasketCMHeadMap.put("Due Date", 1);
		workbasketCMHeadMap.put("Case ID", 2);
		workbasketCMHeadMap.put("Priority", 3);
		workbasketCMHeadMap.put("Campaign", 4);
		workbasketCMHeadMap.put("Year", 5);
		workbasketCMHeadMap.put("Working Status", 6);
		workbasketCMHeadMap.put("Status Taken", 7);
		workbasketCMHeadMap.put("Closure Date", 8);
	}
	
	static Map<String, Integer> workbasketDAInaccuHeadMap = new HashMap<String, Integer>();
	static {
		workbasketDAInaccuHeadMap.put("Select", 1);
		workbasketDAInaccuHeadMap.put("Due Date", 2);
		workbasketDAInaccuHeadMap.put("Case ID", 3);
		workbasketDAInaccuHeadMap.put("Receipt Date", 4);
		workbasketDAInaccuHeadMap.put("Transaction", 5);
		workbasketDAInaccuHeadMap.put("Large Group Details", 6);
		workbasketDAInaccuHeadMap.put("Working Status", 7);
		workbasketDAInaccuHeadMap.put("Status Taken", 8);
		workbasketDAInaccuHeadMap.put("Last Name", 9);
		workbasketDAInaccuHeadMap.put("First Name", 10);
		workbasketDAInaccuHeadMap.put("NPI (Indv)", 11);
		workbasketDAInaccuHeadMap.put("License#", 12);
		workbasketDAInaccuHeadMap.put("License State", 13);
		workbasketDAInaccuHeadMap.put("TIN", 14);
		workbasketDAInaccuHeadMap.put("Directory Address", 15);
		workbasketDAInaccuHeadMap.put("City", 16);
		workbasketDAInaccuHeadMap.put("State", 17);
	}
	
	
	static Map<String, Integer> workbasketDAInquiHeadMap = new HashMap<String, Integer>();
	static {
		workbasketDAInquiHeadMap.put("Select", 1);
		workbasketDAInquiHeadMap.put("Urgent", 2);
		workbasketDAInquiHeadMap.put("Due Date", 3);
		workbasketDAInquiHeadMap.put("Case ID", 4);
		workbasketDAInquiHeadMap.put("Receipt Date", 5);
		workbasketDAInquiHeadMap.put("Transaction", 6);
		workbasketDAInquiHeadMap.put("Large Group Details", 7);
		workbasketDAInquiHeadMap.put("Working Status", 8);
		workbasketDAInquiHeadMap.put("Status Taken", 9);
		workbasketDAInquiHeadMap.put("Last Name", 10);
		workbasketDAInquiHeadMap.put("First Name", 11);
		workbasketDAInquiHeadMap.put("NPI (Indv)", 12);
		workbasketDAInquiHeadMap.put("License#", 13);
		workbasketDAInquiHeadMap.put("License State", 14);
		workbasketDAInquiHeadMap.put("TIN", 15);
		workbasketDAInquiHeadMap.put("Directory Address", 16);
		workbasketDAInquiHeadMap.put("City", 17);
		workbasketDAInquiHeadMap.put("State", 18);
	}
	
	static Map<String, Integer> workbasketMRIHeadMap = new HashMap<String, Integer>();
	static {
		workbasketMRIHeadMap.put("Select", 1);
		workbasketMRIHeadMap.put("Urgent", 2);
		workbasketMRIHeadMap.put("Follow-Up Date", 3);
		workbasketMRIHeadMap.put("Due Date", 4);
		workbasketMRIHeadMap.put("Case ID", 5);
		workbasketMRIHeadMap.put("Receipt Date", 6);
		workbasketMRIHeadMap.put("Transaction", 7);
		workbasketMRIHeadMap.put("Working Status", 8);
		workbasketMRIHeadMap.put("Status Taken", 9);
		workbasketMRIHeadMap.put("Last Name", 10);
		workbasketMRIHeadMap.put("First Name", 11);
		workbasketMRIHeadMap.put("NPI (Indv)", 12);
		workbasketMRIHeadMap.put("License#", 13);
		workbasketMRIHeadMap.put("License State", 14);
		workbasketMRIHeadMap.put("TIN", 15);
		workbasketMRIHeadMap.put("Directory Address", 16);
		workbasketMRIHeadMap.put("City", 17);
		workbasketMRIHeadMap.put("State", 18);
	}

	static Map<String, Integer> workbasketUnRespHeadMap = new HashMap<String, Integer>();
	static {
		workbasketUnRespHeadMap.put("Select", 1);
		workbasketUnRespHeadMap.put("Urgent", 2);
		workbasketUnRespHeadMap.put("Case ID", 3);
		workbasketUnRespHeadMap.put("Receipt Date", 4);
		workbasketUnRespHeadMap.put("Transaction", 5);
		workbasketUnRespHeadMap.put("Large Group Details", 6);
		workbasketUnRespHeadMap.put("Working Status", 7);
		workbasketUnRespHeadMap.put("Status Taken", 8);
		workbasketUnRespHeadMap.put("Last Name", 9);
		workbasketUnRespHeadMap.put("First Name", 10);
		workbasketUnRespHeadMap.put("NPI (Indv)", 11);
		workbasketUnRespHeadMap.put("License#", 12);
		workbasketUnRespHeadMap.put("License State", 13);
		workbasketUnRespHeadMap.put("TIN", 14);
		workbasketUnRespHeadMap.put("Directory Address", 15);
		workbasketUnRespHeadMap.put("City", 16);
		workbasketUnRespHeadMap.put("State", 17);
		workbasketUnRespHeadMap.put("County", 18);
	}
	
	public static Integer getWorkbasketHeaderValue(String workbasketVal, String headerKey) {
		if (workbasketVal.equals("Approval Required") || workbasketVal.equals("Assessment Required") ||
				workbasketVal.equals("Escalation") || workbasketVal.equals("Follow-up Required") ||
				workbasketVal.equals("Research Required") || workbasketVal.equals("Unassigned Data changes") || 
				workbasketVal.equals("Unassigned Verification") )  {
			return workbasketHeadMap.get(headerKey);
		} else if (workbasketVal.equals("Campaign Master")) {
			return workbasketCMHeadMap.get(headerKey);
		} else if (workbasketVal.equals("DA Inaccuracies")) {
			return workbasketDAInaccuHeadMap.get(headerKey);
		} else if (workbasketVal.equals("DA Inquiries")) {
			return workbasketDAInaccuHeadMap.get(headerKey);
		} else if (workbasketVal.equals("MRI")) {
			workbasketMRIHeadMap.get(headerKey);
		} else if (workbasketVal.equals("Unresponsive")) {
			workbasketUnRespHeadMap.get(headerKey);
		} 
		
		
		return -1;
	}
	
	static Map<String, Integer> groupDtlsParticipationReportHeadMap = new HashMap<String, Integer>();
	// Only group Details Participation Report Mapping
	static {
		groupDtlsParticipationReportHeadMap.put("LargeGroupName", 4);
		groupDtlsParticipationReportHeadMap.put("BeginDate", 5);
		groupDtlsParticipationReportHeadMap.put("EndDate", 6);
		groupDtlsParticipationReportHeadMap.put("ParticipationReport", 7);
		groupDtlsParticipationReportHeadMap.put("ParticipationReportFrequency", 8);
		groupDtlsParticipationReportHeadMap.put("RevisedParticipationReport", 9);
		groupDtlsParticipationReportHeadMap.put("RevisedParticipationReportFrequency", 10);
		
	}

	public static Integer groupDtlsParticipationReportValue(String headerKey) {
			return groupDtlsParticipationReportHeadMap.get(headerKey);
	}	
	
	public static Map<String,Integer> getProviderOfficeSearchHeadMap() {
		return providerSearchDPSSearchHeadMap;
	}

	static Map<String, Integer> documentsHeaderMap = new HashMap<String, Integer>();
	static {
		documentsHeaderMap.put("Select",0);
		documentsHeaderMap.put("Document Name", 1);
	}
	public static int getdocumentsHeadMapValue(String string) {
		return documentsHeaderMap.get(string);
	}
	
	}
