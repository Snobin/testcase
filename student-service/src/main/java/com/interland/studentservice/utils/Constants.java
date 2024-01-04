/****************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Program description  : Constants
** Version No           : 1.0.0
** Author               : Yadhu,adarsh,Harichand
** Date Created         : 15-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.interland.studentservice.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public final static String AA_DATA = "aaData";
	public final static String DATA = "data";
	public final static String TOTAL_DISPLAY_RECORD = "iTotalDisplayRecords";
	public final static String TOTAL_RECORD = "iTotalRecords";
	public final static String DELETED = "DELETED";
	public final static String VERIFIED = "VERIFIED";
	public final static String PROCESSD = "PROCESSD";
	public static final String MESSAGE = "message";
	public static final String STATUS = "status";
	public static final String FAILED = "FAILED";
	public static final String SUCCESS = "SUCCESS";
	public final static String FILE_NAME = "fileName";
	public final static String TYPE = "type";
	public final static String NEW_OBJ = "newObj";
	public final static String OLD_OBJ = "oldObj";
	public final static String NULL = "null";
	public final static String COUNT_BY_STATUS = "countByStatus";
	public final static String NAME= "name";
	public final static String COUNT= "count";
	public final static String PENDING = "PENDING";
	public final static String INSERTED= "INSERTED";
	public final static String ALREADY_VERIFIED= "ALREADY VERIFIED";
	public final static String ID= "id";
	public final static String FIRST_NAME= "firstName";
	public final static String LAST_NAME= "lastName";
	public final static String COLLEGE= "college";
	public final static String DEPARTMENT= "department";
	public final static String PLACE= "place";
	public final static String QUALIFICATION= "qualification";
	public final static String RESULT= "result";
	public final static String EMAIL= "email";
	public final static String PHONENO= "phoneNo";
	
	
	
	
	//common
		//public final static String MESSAGE = "message";
		//public final static String STATUS = "status";
		public final static String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
		public final static String PDF_CONTENT_TYPE = "application/x-pdf";
		public final static String DELETE = "DELETE";
		public final static String ALPHA_NUMERS = "^[a-zA-Z0-9]+$";
		
		//security user log
		public final static String USER_LOG_SHEET_NAME = "Login Details Report";
		public final static String USER_LOG_MSG_USER_ID = "User ID";
		public final static String USER_LOG_MSG_LOGIN_TIME = "Login Time";
		public final static String USER_LOG_MSG_LOGIN_STATUS = "Login Status";
		public final static String USER_LOG_MSG_REASON = "Reason";
		public final static String USER_LOG_MSG_IP_ADDRESS = "IP Address";
		public final static String USER_LOG_MSG_SYSTEM_DATE = "System Date";
		public final static String USER_LOG_XLS_FILE_NAME = "User_Log.xlsx";
		public final static String USER_LOG_PDF_NAME = "Log Report";
		public final static String USER_LOG_PDF_FILE_NAME = "UserLog.pdf";
		public final static String USER_LOG_FAIL = "Fail";
		public final static String USER_LOG_S = "S";
		public final static String USER_LOG_SUCCESS = "Success";
		public final static String USER_LOG_USER_ID = "userId";
		public final static String USER_LOG_LOG_TIME = "logTime";
		public final static String USER_LOG_REASON = "reason";
		public final static String USER_LOG_STATUS = "logStatus";
		public final static String USER_LOG_SYSTEM_DATE = "sysDate";
		public final static String USER_LOG_IP_ADDRESS = "ipAddress";
		public final static String USER_LOG_SUCCESSFUL = "Successful";
		public final static String USER_LOG_FAILURE = "Failure";
		public final static String USER_LOG_DATE_RANGE = "dateRange";
		public final static String USER_LOG_PK = "pk";
		public final static String USER_LOG_DATE_FORMAT1 = "dd/MM/yyyy";
		public final static String USER_LOG_DATE_FORMAT2 = "dd-MM-yyyy";

		
		//security group
		public final static String GROUP_SCREEN_ID = "SECACGRP";
		public final static String SEC_GRP_SCREEN_NAME = "Security Group";
		public final static String GROUP_ID = "groupId";
		public final static String GROUP_NAME = "groupName";
		public final static String GROUP_HOME_SCREEN = "homeScreen";
		public final static String GROUP_TYPE = "groupType";
		public final static String GROUP_STATUS = "status";
		public final static String GROUP_HOME_URL = "homeUrl";
		public final static String GROUP_EXIST = "Group already exist.";
		public final static String GROUP_CREATED = "Group created successfully.";
		public final static String GROUP_CREATION_FAILED = "Group creation failed.";
		public final static String GROUP_NOT_EXIST = "Group doesn't exist.";
		public final static String GROUP_UPDATED = "Group updated successfully.";
		public final static String GROUP_UPDATE_FAILED = "Group update failed.";
		public final static String GROUP_DELETED = "Group Deleted successfully.";
		public final static String GROUP_DELETE_FAILED = "Group Deletion failed.";
		public final static String GROUP_VERIFIED = "Group Verified successfully.";
		public final static String GROUP_VERIFICATION_FAILED = "Group verification failed.";
		public final static String GROUP_REACTIVATED = "Group Re-Activation successfull.";
		public final static String GROUP_REACTIVATION_FAILED = "Group Re-Activation failed.";
		public final static String GROUP_ALREADY_ACTIVATED = "Group is already Active.";
		public final static String GROUP_CREATOR_AND_VERIFIER_SAME = "Group Creator and Verifier cannot be Same.";
		public final static String GROUP_CREATOR_AND_DELETE_SAME = "Same proscessor and verifier can't Delete.";
		public final static String INTERNAL_SERVER_ERROR = "Server error, contact Admin.";
		
		//security permission
		public final static String PERMISSION_SCREEN_TYPE = "screenType";
		public final static String PERMISSION_SCREEN_ID = "screenId";
		public final static String PERMISSION_SCREEN_NAME = "screenName";
		public final static String PERMISSION_ADD_FLAG = "addFlag";
		public final static String PERMISSION_UPDATE_FLAG = "updateFlag";
		public final static String PERMISSION_DISPLAY_FLAG = "disFlag";
		public final static String PERMISSION_DELETE_FLAG = "delFlag";
		public final static String PERMISSION_VERIFY_FLAG = "verFlag";
		public final static String PERMISSION_CATEGORY_NAME = "categoryName";
		public final static String PERMISSION_CATEGORY_NO = "categoryNo";
		public final static String PERMISSION_NO = "N";
		public final static String PERMISSION_YES = "Y";
		public final static String PERMISSION_GROUP_ID_REGEX_MSG = "Group ID shouldn't contains space or any invalid character";
		public final static String PERMISSION_SCREEN_CREATED = "Screen For Group Created";
		public final static String PERMISSION_GROUP_EXIST = "Already Group is Exist in this Group ID";
		public final static String PERMISSION_GROUP_ARRAY = "groupArr";
		public final static String PERMISSION_DEFAULT_CLIENT_ID = "999";
		public final static String PERMISSION_FUNCTION_FLAG = "A";
		
		//security user
		public final static String USER_USER_ID = "userId";
		public final static String USER_GROUP_ID = "groupId";
		public final static String USER_GROUP_NAME = "groupName";
		public final static String USER_USER_NAME = "userName";
		public final static String USER_STATUS = "status";
		public final static String USER_EMAIL_ID = "emailId";
		public final static String USER_CELLPHONE = "cellPhone";
		public final static String USER_NOT_EXIST = "No user record exist for given id";
		public final static String USER_EXIST = "User already exist.";
		public final static String USER_ADMINV = "ADMINV";
		public final static String USER_CRO = "CRO";
		public final static String USER_MSG_USER_TYPE = "S";
		public final static String USER_CREATED = "User created successfully.";
		public final static String USER_CREATION_FAILED = "User creation failed.";
		public final static String USER_UPDATED = "User updated successfully.";
		public final static String USER_UPDATE_FAILED = "User update failed.";
		public final static String USER_DELETED = "User deleted successfully.";
		public final static String USER_DELETION_FAILED = "User deleted failed.";
		public final static String USER_VERIFIED = "Verified Successfully. ";
		public final static String USER_VERIFICATION_FAILED = " Verification failed.";
		public final static String USER_NO_RECORD = "No record to verify";
		public final static String USER_USER_TYPE = "userType";
		public final static String USER_CATEGORY = "userCategory";
		public final static String BANK_ID= "bankId";
		////////////////////////////////////////////////////////////
		
		
		

		public static class SERVICES {
			public static final String PAYROLL = "PAYROLL";
			public static final String STATEMENT = "Statement";
			public static final String PAYMENT = "payment";
			public static final String BILL_PAY = "bilpay";
			public static final String DEFAULT = "Default";
		}

		public static class MESSAGE_STATUS {
			public static final String VERIFIED = "VERIFIED";
			public static final String PROCESSED = "PROCESSD";
			public static final String SUCCESS = "SUCCESS";
			public static final String DELETED = "DELETED";
			public static final String DELETE = "DELETE";
			public static final String FAILED = "FAILED";
			public static final String DRAFT = "DRAFT";
			public static final String GENERATED = "GENERATED";
			public static final String RECEIVED = "RECEIVED";
			public static final String REPAIR = "REPAIR";
			public static final String NEW = "NEW";
			public static final String APPROVED = "APPROVED";
			public static final String INITIATED = "INITIATED";
			public static final String COMPLETED = "COMPLETED";
		}

		public static class USERLOG {
			// security user log
			public static final String USER_LOG_SHEET_NAME = "Login Details Report";
			public static final String USER_LOG_MSG_USER_ID = "User ID";
			public static final String USER_LOG_MSG_LOGIN_TIME = "Login Time";
			public static final String USER_LOG_MSG_LOGIN_STATUS = "Login Status";
			public static final String USER_LOG_MSG_REASON = "Reason";
			public static final String USER_LOG_MSG_IP_ADDRESS = "IP Address";
			public static final String USER_LOG_MSG_SYSTEM_DATE = "System Date";
			public static final String USER_LOG_XLS_FILE_NAME = "User_Log.xls";
			public static final String USER_LOG_PDF_NAME = "Log Report";
			public static final String USER_LOG_PDF_FILE_NAME = "UserLog.pdf";
			public static final String USER_LOG_FAIL = "Fail";
			public static final String USER_LOG_S = "S";
			public static final String USER_LOG_SUCCESS = "Success";
			public static final String USER_LOG_USER_ID = "userId";
			public static final String USER_LOG_LOG_TIME = "logTime";
			public static final String USER_LOG_REASON = "reason";
			public static final String USER_LOG_STATUS = "logStatus";
			public static final String USER_LOG_SYSTEM_DATE = "sysDate";
			public static final String USER_LOG_IP_ADDRESS = "ipAddress";
			public static final String USER_LOG_SUCCESSFUL = "Successful";
			public static final String USER_LOG_FAILURE = "Failure";
			public static final String USER_LOG_DATE_RANGE = "dateRange";
			public static final String USER_LOG_PK = "pk";
		}

		public static class GROUP {
			// security group
			public static final String GROUP_ID = "groupId";
			public static final String GROUP_NAME = "groupName";
			public static final String GROUP_HOME_SCREEN = "homeScreen";
			public static final String GROUP_TYPE = "groupType";
			public static final String GROUP_STATUS = "status";
			public static final String GROUP_HOME_URL = "homeUrl";
			public static final String GROUP_EXIST = "Group already exist.";
			public static final String GROUP_CREATED = "Group created successfully.";
			public static final String GROUP_CREATION_FAILED = "Group creation failed.";
			public static final String GROUP_NOT_EXIST = "Group doesn't exist.";
			public static final String GROUP_UPDATED = "Group updated successfully.";
			public static final String GROUP_UPDATE_FAILED = "Group update failed.";
			public static final String GROUP_DELETED = "Group Deleted successfully.";
			public static final String GROUP_DELETE_FAILED = "Group Deletion failed.";
			public static final String GROUP_VERIFIED = "Group Verified successfully.";
			public static final String GROUP_VERIFICATION_FAILED = "Group verification failed.";
			public static final String GROUP_REACTIVATED = "Group Re-Activation successfull.";
			public static final String GROUP_REACTIVATION_FAILED = "Group Re-Activation failed.";
			public static final String GROUP_ALREADY_ACTIVATED = "Group is already Active.";
		}

		public static class PERMISSION {
			// security permission
			public static final String PERMISSION_SCREEN_TYPE = "screenType";
			public static final String PERMISSION_SCREEN_ID = "screenId";
			public static final String PERMISSION_SCREEN_NAME = "screenName";
			public static final String PERMISSION_ADD_FLAG = "addFlag";
			public static final String PERMISSION_UPDATE_FLAG = "updateFlag";
			public static final String PERMISSION_DISPLAY_FLAG = "disFlag";
			public static final String PERMISSION_DELETE_FLAG = "delFlag";
			public static final String PERMISSION_VERIFY_FLAG = "verFlag";
			public static final String PERMISSION_CATEGORY_NAME = "categoryName";
			public static final String PERMISSION_CATEGORY_NO = "categoryNo";
			public static final String PERMISSION_NO = "N";
			public static final String PERMISSION_YES = "Y";
			public static final String PERMISSION_GROUP_ID_REGEX_MSG = "Group ID shouldn't contains space or any invalid character";
			public static final String PERMISSION_SCREEN_CREATED = "Screen For Group Created";
			public static final String PERMISSION_GROUP_EXIST = "Already Group is Exist in this Group ID";
			public static final String PERMISSION_GROUP_ARRAY = "groupArr";
			public static final String PERMISSION_DEFAULT_CLIENT_ID = "999";
			public static final String PERMISSION_FUNCTION_FLAG = "A";
		}

		public static class USER {
			// security user
			public static final String USER_USER_ID = "userId";
			public static final String USER_GROUP_ID = "groupId";
			public static final String USER_GROUP_NAME = "groupName";
			public static final String USER_USER_NAME = "userName";
			public static final String USER_STATUS = "status";
			public static final String USER_EMAIL_ID = "emailId";
			public static final String USER_CELLPHONE = "cellPhone";
			public static final String USER_NOT_EXIST = "No user record exist for given id";
			public static final String USER_EXIST = "User already exist.";
			public static final String USER_ADMINV = "ADMINV";
			public static final String USER_CRO = "CRO";
			public static final String USER_MSG_USER_TYPE = "S";
			public static final String USER_CREATED = "User created successfully.";
			public static final String USER_CREATION_FAILED = "User creation failed.";
			public static final String USER_UPDATED = "User updated successfully.";
			public static final String USER_UPDATE_FAILED = "User update failed.";
			public static final String USER_DELETED = "User deleted successfully.";
			public static final String USER_DELETION_FAILED = "User deleted failed.";
			public static final String USER_VERIFIED = "Verified Successfully. ";
			public static final String USER_VERIFICATION_FAILED = " Verification failed.";
			public static final String USER_NO_RECORD = "No record to verify";
			public static final String USER_USER_TYPE = "userType";
			public static final String USER_CATEGORY = "userCategory";
			public static final String VALID_FAILED = "Validation Failed";
			public static final String VALID_CODE = "VALERRCOD";
		}
		
		public static class NOTIFICATION_TYPE {
			public static final String TASK = "TASK";
			public static final String INFO = "INFO";
		}
		public static class SCREEN_ID {
			public static final String SEC_GRP = "SECACGRP";
		}


	public final static String MIME_TYPE_OCTET_STREAM = "application/octet-stream";

	public final static String CONTENT_TYPE= "contentType";
	public final static String TEXT_CONTENT_TYPE= "text/csv";
	
	public static class STREAM_DEFINITION{
		public static final String ST = "ST";
		public static final String IN = "IN";
		public static final String UP = "UP";
		public static final String DN = "DN";
		
	}
	public static class EXCEPTIONS {
		public static final String DATAEXISTS="DATA ALREADY EXISTS";
	}

	public static class PATTERN {
		public static final String ALPHANUMERISPC = "^([a-zA-Z0-9](?!.*  )[a-zA-Z0-9 ]*)*$";
		public static final String ALPHANUMERICONLY = "^[a-zA-Z0-9]*$";
		public static final String NUMERICSONLY = "^[0-9]+$";
		public static final String ALPHABETSONLY = "^[a-zA-Z ]+$";
		public static final String YESORNO = "^(Y|N){1}$";
		public static final String IPADD = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";
		public static final String CONNTYP = "^(S|C){1}$";
		public static final String DIRECTION = "^(I|O){1}$";
		public static final String ALPHANUMERISPCSLASH = "^([a-zA-Z0-9]+(?!(.*  |\\\\\\\\))[a-zA-Z0-9\\\\ ]*)*$";
		public static final String XSD = "(.).+\\.(xsd|XSD)$";
		public static final String EXE = ".+\\.(?!(EXE|exe)$)([^.]+)";
		public static final String NUMERICSWITHDOT = "^[0-9.]+$";
		public static final String FILENAME = "(.*)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
		public static final String CUTTIME="^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$";
		public static final String ALPHANUMERIC_WITH_N_SPACE="^[a-zA-Z0-9]+([a-zA-Z0-9 ]+)*$";
		public static final String ACC_WITH_INST_BIC = "^(?=(?:.{8}|.{11})$)[0-9a-zA-Z]*$";
		public static final String ACC_WITH_INST_AC = "(?:(^/[a-zA-Z0-9]{1,34}$)|(^/[A-Z]{1}/[a-zA-Z0-9]{1,34}$))";
		public static final String CUSTOM_INSTITUTION_BIC="^(?=(?:.{8}|.{11})$)[a-zA-Z0-9]*$";
		public static final String CUSTOM_INSTITUTION_ID="(?:(^/[a-zA-Z0-9]{1,34}$)|(^/[A-Z]{1}/[a-zA-Z0-9]{1,34}$))";
		public static final String CUSTOM_INSTITUTION_ID_C="^/[a-zA-Z0-9]{1,34}$";
		
	}
	
	public static class ACCOUNT_STATUS {
		public static final String ACTIVE = "ACTIVE";
		public static final String INACTIVE = "INACTIVE";
	}
	
	public static class STANDIN_PAYMENTS {
		public static final String MESSAGE_ID = "messageId";
		public static final String CREDITOR_BIC = "creditorBic";
		public static final String INSTRUCTION_ID = "instructionId";
		public static final String TRANSACTION_ID = "transactionId";
		public static final String ENT2END_ID = "end2endId";
		public static final String DEPARTMENT = "department";
		public static final String TRANS_DATE = "transactionDate";
		public static final String DEBIT_DATE = "debitDate";
		public static final String VALUE_DATE = "valueDate";
		public static final String TRANS_AMT = "transactionAmount";
		public static final String DEBITOR_ID = "debiterId";
		public static final String CREDITOR_ID = "crediterId";
		public static final String BEN_CUST_ACC = "benCustomerAccount";
		public static final String ORDERING_CUST_ACC = "orderingCustomerAccount";
		public static final String REMITANCE_INFO = "remittanceInfo";
		public static final String USER_CMNT = "userComment";
		public static final String STATUS = "status";
		public static final String STATUS_MSG = "statusMessage";
		public static final String BANK_REF = "bankReference";
		
		public static final String DEPARTMENT_NAME = "departmentName";
		
		public static final String PURPOSE_OF_PAYMENT = "purposeOfPayment";
		public static final String ADVICE_LINE1 = "adviceLine1";
		public static final String ADVICE_LINE2 = "adviceLine2";
		public static final String ADVICE_LINE3 = "adviceLine3";
		public static final String ADVICE_LINE4 = "adviceLine4";
		public static final String PAYMEMT_INFO = "paymentInfo";
		public static final String CHARGE = "charge";
		public static final String TRSANSACTION_AMOUNT = "transactionAmount";
		public static final String CUR_CODE ="curCode";
		public static final String DIRECTION = "direction";
		public static final String CRDR_FLAG = "crdrFlag";
		public static final String TRANSACTION_TIME = "transactionTime";
		public static final String TRANSACTION_DATE = "transactionDate";
		public static final String ACCOUNT_NUMBER = "accountNumber";
		public static final String BANK_REFERENCE = "bankRef";
		public static final String TRANSACTION_REFERENCE = "transRef";
		public static final String CHANNEL_ID = "channelId";
		public static final String BANK_ID = "bankId";
		public static final String TRANS_ID = "trnId";
	}
	
	public static class SERVICE_TYPE {
		public static final String INTERBANK_PAYMENT = "Interbank Payment";
		public static final String OUTGOING_CUSTOMER_PAYMENT = "Outgoing Customer Payment";
		public static final String TSM_MESSAGE = "TSM Message";
	}
	public static class PSH_CORE_SADAD {
		public static final String BANK_ID = "bankId";
		public static final String BILLER_ID = "billerId";
		public static final String SADAD_PAYMENT_ID = "sadadPaymentId";
		public static final String CHANNEL_ID = "channelId";
		public static final String INSTRUCTION_UUID = "instructionUuid";
		public static final String SERVICE_ID = "serviceId";
		public static final String TRANSACTION_AMOUNT = "transactionAmount";
		public static final String BANK_REFERENCE = "bankReference";
		public static final String ORDERING_CUSTOMER_NAME = "orderingCustomerName";
		public static final String ORDERING_CUSTOMER_ACCOUNT = "orderingCustomerAccount";
		public static final String DEP = "dep";
		public static final String PRODUCT_ID = "productId";
		public static final String CURRENCY_CODE = "currencyCode";
		public static final String BENEFICIARY_CUSTOMER_ACCOUNT = "beneficiaryCustomerAccount";
		public static final String BENEFICIARY_CUSTOMER_NAME = "beneficiaryCustomerName";
		public static final String STATUS = "status";
		public static final String VALUE_DATE = "valueDate";
		public static final String DEBIT_DATE = "debitDate";
		public static final String DATE_CRE = "dateCre";
		public static final String PSH_REF = "pshReference";
		public static final String BILL_ACC = "billingAccount";
		public static final String SADAD_CUS_ID = "sadadCustomerId";
		
		
	}
	public static class PSH_CORE_RECON{
		public static final String ID = "id";
		public static final String PROCESS_DATE ="processDate";
		public static final String TOTAL_RECORDS = "totalRecords";
		public static final String FINAL_REPORT = "finalReportFilePath";
		public static final String SUBMITTED_REPORT= "submittedReportFilePath";
		public static final String RECONCILLATION_TYPE="reconcillationType";
		public static final String FAILED_RECORDS ="failedRecords";
		public static final String STATUS ="status";
		public static final String REQUEST ="request";
		public static final String RESPONSE ="response";
		public static final String APPLICATION_ID ="applicationId";
	}
	public static class PSH_CORE_RECONDET{
		public static final String HEADER_ID ="headerId";
		public static final String RECORD_ID ="recordId";
		public static final String BANK_TRANSACTION_REF ="bankTrasactionRef";
		public static final String CUSTOMER_TRANSACTION_REF = "customerTransactionRef";
		public static final String SAMA_TRANSACTION_REF = "samaTransactionRef";
		public static final String BANK_REVERSAL_REF ="bankReversalRef";
		public static final String SAMA_STATUS = "samaStatus";
		public static final String AMOUNT = "amount";
		public static final String DEBIT_ACCOUNT = "debitAccount";
		public static final String CREDIT_ACCOUNT = "creditAccount";
		public static final String SERVICE_TYPE = "serviceType";
		public static final String STATUS ="status";
		
	}
	public static final String BENFADD = "BENFADD";
	public static final String ALPHA_NUMER_SPACES = "^[a-zA-Z0-9 ]+$";
	public static final String PATTERN_SPECIAL = "^[a-zA-Z0-9 ]*$";
	public static final String PATTERN_ZIP = "^[0-9]{5}|^$";
	public static final String PATTERN_NUMBER = "^[0-9]+(\\.[0-9]*)?$";
	public static final String PATTERN_FAX = "[\\\\+? *[1-9]+]?[0-9 ]+|^$";
	public static final String PATTERN_ACCOUNT = "^[A-Z0-9]*$";
	public static final String PATTERN_MOBILE = "^[0-9]{10}|^$";
	public static final String PATTERN_ARABIC = "^[\u0621-\u064A ]*$";
	public static final String PATTERN_ENGLISH = "^[a-zA-Z ]*$";
	public static final String PATTERN_CEILING_AMOUNT = "^\\d+(\\.\\d{1,4})?$";
	public static final String PATTERN_PROXY_ID ="^[a-zA-Z0-9]+$|^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PATTERN_ROUTING_CODE = "^(([/]{1,2})?[a-zA-Z0-9]+([/]{1,2})?)*$";
	public static final String PATTERN_BEN_ACCOUNT = "^[A-Z0-9]{24}$";
	public static final String ERROR_CODE = "VALERRCOD";
	
	public static final String REJ_AMOUNT = "^[0-9]{1,11}(\\.[0-9]{1,4})?$";
	public static final String REJ_SPECFICDAY = "^([1-9]|[12][0-9]|3[01])?$";
	public static final String REJ_MONTH = "^(1[0-2]|[1-9])$";
	
	public static List<String> statusList() {
		List<String> statusList = new  ArrayList<String>();
		statusList.add(MESSAGE_STATUS.VERIFIED);
		statusList.add(MESSAGE_STATUS.PROCESSED);
		statusList.add(MESSAGE_STATUS.DELETED);
		statusList.add(MESSAGE_STATUS.APPROVED);
		statusList.add(MESSAGE_STATUS.DELETE);
		return statusList;
		
	}
	
	public final static String ACC_MST_ID="id";
	public final static String ACC_MST_VALUE="value";
	public final static String ACC_MST_BALANCE="balance";
	public final static String ACC_MST_NAME="name";
	public final static String ACC_MST_ADDRESS1="address1";
	public final static String ACC_MST_ADDRESS2="address2";
	public final static String ACC_MST_ADDRESS3="address3";
	public final static String ACC_MST_CUR_CODE="curCode";
	public final static String NICK_NAME="nickName";
	public final static String BEN_BIC="benBic";
	public final static String POP="pop";
	
	
	public static class TSMMESSAGE{
		public final static String SAMA="SAMASARIXXX";
		public final static String RTGS="RTGS";
		public final static String TSM="TSM";
		public final static String PROCESSED="PROCESSD";
		public final static String CRE_MSG="TSM Message created successfully";
		public final static String OUTGOING="O";
		
	}
	
	public static class EVENT_ID {
		public static final String FIN_POST = "FIN_POST";
		public static final String PAYMENT_CREATE = "PAYMENT_CREATE";
		public static final String PAYMENT_VERIFICATION = "PAYMENT_VERIFICATION";
		public static final String TRN_UPDATE = "TRANSACTION_UPDATE";
		public static final String TRN_VERIFY = "TRANSACTION_VERIFY";
		public static final String FIN_POST_REQ = "FIN_POST_REQ";
		public static final String FIN_POST_RES = "FIN_POST_RES";
		public static final String TRN_RETURN = "TRANSACTION_RETURN";
		public static final String TRN_COMPLETE = "TRANSACTION_COMPLETE";
	}
	
	public static final String BIPAY = "BIPAY";
}
