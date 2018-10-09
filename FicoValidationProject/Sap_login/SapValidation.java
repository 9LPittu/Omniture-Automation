package Sap_login;
import resources.Sap_login.SapValidationHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.sun.org.apache.xpath.internal.operations.And;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;
//import com.ibm.xtq.common.utils.Assert;
/**
 * Description   : Functional Test Script
 * @author bjose
 */
public class SapValidation extends SapValidationHelper
{
    public SapValidation() {

    	 
    }
	/**
	 * Script Name   : <b>SapValidation</b>
	 * Generated     : <b>Jan 23, 2018 4:33:40 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 6.1  Build 7601 (S)
	 * 
	 * @since  2018/01/23
	 * @author venkat
	 */
    
    private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
    SAPApplicationFunctions sapfunctions = new SAPApplicationFunctions();
    ExcelUtils xlFicoOutputWriter = null;
    int xlRowno=0;
    boolean testStatus=true;
	public void testMain(Object[] args) 
	{
		startApp("saplogon");
		
		
		// Window: saplogon.exe: SAP Logon 730
		sysListView32table().doubleClick(atCell(atRow(atText("QAX-Rackspace")), 
                                    atColumn(atText("Name"))));
		
		// Window: SAP
		//window_sap().maximize();
		
	
		text_rsystbname().setText("9bjose");
		text_rsystbcode().setText("Jcrew456");
		text_rsystbcode().setFocus();
		text_rsystbcode().setCaretPosition(8);
		window_sap5().sendVKey(SAPTopLevelTestObject.VKEY_ENTER);
		logTestResult("Step Description :Login to SAP Verizon QAX"+ "\n Expected Results :Login should be successful"+ "\n Actual Results :Successfully logged into SAP Verizon QAX",true);
		
		// New logic : reaad from Xls:
		
		ExcelUtils testDataReader = null;
		Boolean testStatus = true;
		String strTestPath = dbReader.getProperty("FicoValidationSheet");
		try {
				testDataReader = new ExcelUtils(strTestPath, "Order_Input", "");
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//ExcelUtils testDataReader1 = null;
		String strTestPath1 = dbReader.getProperty("FicoValidationSheetOutput");
		try {
			  xlFicoOutputWriter = new ExcelUtils(strTestPath1, "Sheet1", "");
//				try {
//					System.out.println(">>>>>>>>>For loop excel main " );
//					testDataReader1.setCellValueInExcel(1, "Error Log", "Pass Pass \n Pass Pass Pass");
//					System.out.println(">>>>>>>>>For loop excel main " );
//					xlFicoOutputWriter.setCellValueInExcel(2, "Web shipment Number", "Pass Pass");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		//System.out.println(System.getProperty("user.dir"));
		//Retrieve Orders to be validated from test data sheet
		Map<String, Object> testdataRowMap = null;
		int firstRowNum = testDataReader.getSearchTextFirstRowNum();
		int maxRowCount = testDataReader.getSearchTextLastRowNum();
		//Loop through each input order
		for(int intCounter = firstRowNum ;intCounter<=maxRowCount;intCounter++)
		{
			System.out.println(">>>>>>>>>For loop excel main " );
			try{
				testdataRowMap = testDataReader.getDataFromExcel(intCounter);
				if (((String) testdataRowMap.get("Execute")).equalsIgnoreCase("YES") && !((String) testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")) 
				{
					String strOrderNumber = (String)testdataRowMap.get("OrderNumber");
					System.out.println(">>>>>>>>>order Number  " + strOrderNumber);
		
					//Retrieve Order details from Blue Martini Database
					BMOrderDetails bmOrderDeatils = null;
					bmOrderDeatils = new BMOrderDetails();				
						
					String[][] arrBMOrderDetails = bmOrderDeatils.getBMOrderDetails(strOrderNumber, "");
					if(arrBMOrderDetails.length == 0 || arrBMOrderDetails.equals(null)) {
						logTestResult("Failed to retrive blue Martini Order Results: " , false);
						testStatus=false;
						break;
					}
					logTestResult("Blue Martini Order Results: No of records : " + arrBMOrderDetails.length , true);
					
					//Retrieve billing details from Blue Martini database
					String[][] arrBMBillingDetails = sapfunctions.funcRetrieveBillingDetailsFromBlueMartini(strOrderNumber);
					if(arrBMBillingDetails.length == 0 || arrBMBillingDetails.equals(null)) {
						logTestResult("Failed to retrive blue Martini Billing Results: " , false);
						testStatus=false;
						break;
					}
					logTestResult("Blue Martini Billing Results: No of records : " + arrBMBillingDetails.length , true);
					//Retrieve Order details for Return Order
					String strOrderType = arrBMOrderDetails[0][42].trim().toUpperCase();
					String strInputDataValidation = (String)testdataRowMap.get("Input Data Validation?");
					
					if (strInputDataValidation.equals("YES")  && strOrderType.equals("ORD")) {
						String strCountry = (String)testdataRowMap.get("Country");
						String strProductCode =(String)testdataRowMap.get("Product Code");
						String strProductSize = (String)testdataRowMap.get("Product Size");
						String strProductColor=(String)testdataRowMap.get("Product Color");
						String strProductQuantity = (String)testdataRowMap.get("Product Quantity");
						String strPromoCode= (String)testdataRowMap.get("Promo code");
						String strShippingMethod = (String)testdataRowMap.get("Shipping Methodr");
						String strPayment1 = (String)testdataRowMap.get("CreditCard1");
						String strPayment2 = (String)testdataRowMap.get("CreditCard2");
						String strMonogram = (String)testdataRowMap.get("Monogram");
						String strGiftWrap = (String)testdataRowMap.get("GiftWrap");
						boolean blnBMOrderMatch = bmOrderDeatils.func_Compare_OrderInput_BlueMartini(strOrderNumber, strCountry, strProductCode,strProductColor,strProductSize,strProductQuantity,strPromoCode,strShippingMethod,strPayment1,strPayment2,strMonogram, strGiftWrap, arrBMOrderDetails, arrBMBillingDetails);
				      }
	
					validateSAPOrders(arrBMOrderDetails,arrBMBillingDetails);
					try {
						if (!testStatus || !sapfunctions.testStatus) {
						xlRowno++;
						xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "TEST FAILED");
						xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", strOrderNumber);
						}
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
				unregisterAll();

			 }
			catch(Exception e){
				e.printStackTrace();
				unregisterAll();

			}
		}
		try {

			xlFicoOutputWriter.writeAndSaveExcel();
	
		} catch ( Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void scrubTestDataSheet(ExcelUtils testDataReader, Map<String, Object> testdataRowMap, int j, Boolean testStatus){

			try {
				if (testStatus) {
					
				testDataReader.setCellValueInExcel(j, "Execution Completed", "Pass");
			
				} else {
					testDataReader.setCellValueInExcel(j, "Execution Completed", "Failed");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
		}		
		
		try {
			testDataReader.writeAndSaveExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void validateSAPOrders(String[][] arrBMOrderDetails, String[][] arrBMBillingDetails) 
	{


	//Retrieve Order Type
	String strOrderType = arrBMOrderDetails[0][42];
	//boolean gblnConsolidatePlanShipment=false;
	

	//Retrieve Order Number
	String strOrderNumber = arrBMOrderDetails[0][0];

	
	if(strOrderNumber.equals("")) {
		logTestResult("Step Description :Search in SAP for the BM Order number: " + strOrderNumber+"\n Expected Results :Order should be available in SAP"+ "Order number is blank",false);
		testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				//xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :Search in SAP for the BM Order number: " + strOrderNumber+"\n Expected Results :Order should be available in SAP"+ "Order number is blank");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return;
	}
	
	//'Navigate to ZDIRECT1
	tree_sapTableTreeControl1().setSelectedNode(atPath("Favorites->ZDIRECT1 - Direct Order Maintenance"));
	tree_sapTableTreeControl1().doubleClickNode(atPath("Favorites->ZDIRECT1 - Direct Order Maintenance"));
	
	//enter Web Order number in direct order screen
	text_s_WEBORDLOW().setText(strOrderNumber);
	text_s_WEBORDLOW().setFocus();
	text_s_WEBORDLOW().setCaretPosition(10);
	//Click on  function to execute
	button_execute().press();
	
	sleep(2);

	//Verify Grid is populated with SAP orders
	
	if(grid_sapguiGridViewCtrl1().exists())
	{
		if(((ITestDataTable) (grid_sapguiGridViewCtrl1().getTestData("list"))).getRowCount() == 0) {
			logTestResult("Step Description :Enter web external order as: " + strOrderNumber + " and Execute Query"+"\n Expected Results :Results should be dipslayed on Grid"+"\n Actual Results :No records are displayed on the grid. Order may not available in SAP yet. Please review your order and try after some time", false);
			testStatus=false;
			return;
		}	
		
		logTestResult("Step Description :Enter the BM Order number: " + strOrderNumber + " and Execute Query"+"\n Expected Results :Results should be dipslayed on Grid"+"\n Actual Results :Results are displayed in Grid",true);
		
	    //Retrieve Row Count
		int intRowCount=((ITestDataTable) (grid_sapguiGridViewCtrl1().getTestData("list"))).getRowCount();
		
		
		//Replace all plan shipment numbers from Blue Martini with a unique number when it is a return Order
		sapfunctions.gblnConsolidatePlanShipment = false;
		if(strOrderType.equals("RET") && intRowCount == 1) {
			boolean blnWebShipMatch = false;
			String strConsolidatedWebShipment = grid_sapguiGridViewCtrl1().getCellValue(1,"BSTKD_M");
			for( int intIterator = 0;intIterator<arrBMOrderDetails.length; intIterator++) {
				String strPlanShipment = arrBMOrderDetails[intIterator][41].trim().toUpperCase();
				if(strPlanShipment.equals(strConsolidatedWebShipment)) {
					blnWebShipMatch = true;
					break;
				}
			}
			
			if(blnWebShipMatch) {
				for (int intIterator = 0;intIterator<arrBMOrderDetails.length; intIterator++)
					arrBMOrderDetails[intIterator][41] = strConsolidatedWebShipment;
				sapfunctions.gblnConsolidatePlanShipment = true;				
			}
		}
		
			
		
		//Verify if each web shipment number from Blue Martini is available in SAP
		boolean blnStatus = sapfunctions.VerifyWebShipmentInDirectOrdersScreen(intRowCount, arrBMOrderDetails, strOrderNumber);
		
		
		boolean blnVerifyOrder = true;
		for( int intGridCounter = 0; intGridCounter<intRowCount-1; intGridCounter++ ) 
		{
			try 
			{
			do  {
				//set blnVerifyOrder to False so that the validation is done only once per each SAP order
				blnVerifyOrder = false;	
				sapfunctions.gstrCountryCode = arrBMOrderDetails[intGridCounter][43];
				sapfunctions.gstrShipmentAmount=arrBMOrderDetails[intGridCounter][3];
				sapfunctions.gstrBrand = arrBMOrderDetails[intGridCounter][45].trim().toUpperCase();
				//Exit if the order status is is not green
				String strSAPOrderNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"VBELN");
				String color =grid_sapguiGridViewCtrl1().getCellValue(intGridCounter, "LIGHT");
				if(! color.equals("@08@")) {
					logTestResult( "Step Description :Verify the status of SAP order: " + strSAPOrderNumber+ "\n Expected Results :Order should be in Green"+ "\n Actual Results :Order: " + strSAPOrderNumber + " status is not green. Order cannot be verified now",false);
					testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				//xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :Verify the status of SAP order: " + strSAPOrderNumber+ "\n Expected Results :Order should be in Green"+ "\n Actual Results :Order: " + strSAPOrderNumber + " status is not green. Order cannot be verified now");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					break;
				}
				logTestResult( "Step Description :Verify the status of SAP order: " + strSAPOrderNumber+ "\n Expected Results :Order should be in Green"+ "\n Actual Results :Order: " + strSAPOrderNumber + " is in Green and ready to be validated",true);


				//Retrieve SAP Order details 
				String strWebExternalOrder = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"BSTKD_E");
				String strWebShipmentNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"BSTKD_M");
				String strPGIPGRNumber =  grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"PGIRNO");
				String strGMAccountingNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"ACC1NO");			
				//Verify STS store number on Purchase Order screen
		
			if( (arrBMOrderDetails[0][50]!= null)) { // && arrBMOrderDetails[0][50].equals("T")) {
				if( arrBMOrderDetails[0][50].equals("T")) {
    			grid_sapguiGridViewCtrl1().setCurrentCellColumn("VBELN");
				grid_sapguiGridViewCtrl1().doubleClick(intGridCounter, "VBELN");
				logTestResult("clicked on Column SAP Order # on row :" + intGridCounter , true);
				//SessionObj.SAPGuiGrid("micclass:=SAPGuiGrid","name:=shell","index:=1").ActivateCell intGridCounter,"SAP Order";
				
				ValidateStoreNumbersOnPurchageOrder(arrBMOrderDetails,strWebShipmentNumber);
				}
					
				//Call ValidateStoreDetailsOnPurchageOrder(strSApOrderNumber,strStoreNumber,strAddress1,strAddress2,strCity,strSate,strZip)
					
			}
		
				//Verify if all the required details are populated for the SAP Order
				blnStatus = sapfunctions.VerifyOrderDetailsInDirectOrdersScreen(intGridCounter, arrBMOrderDetails, strOrderNumber);
				
				//Navigate to SAP Order Overview screen, verify items and retrieve order details
				blnStatus = sapfunctions.verifySAPOrderOverview(strSAPOrderNumber,intGridCounter,arrBMOrderDetails, strWebShipmentNumber, strOrderNumber,arrBMBillingDetails);				

				//Go Back to purchase order screen
				button_btn3().press();
				button_btn3().press();
				strWebExternalOrder = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"BSTKD_E");
				strGMAccountingNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"ACC1NO");
			
				//Verify GM Accounting Document
				if (!strGMAccountingNumber.equals("")) {
	    			grid_sapguiGridViewCtrl1().setCurrentCellColumn("ACC1NO");
					grid_sapguiGridViewCtrl1().doubleClick(intGridCounter, "ACC1NO");
					ValidateGLPosting(strSAPOrderNumber,strWebExternalOrder,strGMAccountingNumber);
					button_btn3().press();
				}
				
				

				//Verify PGI/PGR document
    			grid_sapguiGridViewCtrl1().setCurrentCellColumn("PGIRNO");
				grid_sapguiGridViewCtrl1().doubleClick(intGridCounter, "PGIRNO");
				blnStatus = sapfunctions.ValidatePGI_PGR(strSAPOrderNumber,strWebShipmentNumber,strOrderNumber,strPGIPGRNumber,arrBMOrderDetails);
				button_btn3().press();
				
     			//Verify accounting document
    			grid_sapguiGridViewCtrl1().setCurrentCellColumn("ACC2NO");
				grid_sapguiGridViewCtrl1().doubleClick(intGridCounter, "ACC2NO");
				ValidateAccountPosting(strSAPOrderNumber,strWebExternalOrder,strGMAccountingNumber,arrBMOrderDetails, strWebShipmentNumber);
				button_btn3().press();
				
				//validate Billing details
				blnStatus = false;
    			grid_sapguiGridViewCtrl1().setCurrentCellColumn("BILLNO");
				grid_sapguiGridViewCtrl1().doubleClick(intGridCounter, "BILLNO");
				blnStatus  = validateBillingDetails(strSAPOrderNumber,strWebShipmentNumber, strOrderNumber);
				if(!blnStatus) {
					
					logTestResult("Step Description :Billing Screen Details ,Verify articles are matching between SAP order: " + strSAPOrderNumber + " and corresponding Blue martini web shipment: " + strWebShipmentNumber+ "\n Expected Results :Articles should match between SAP && Blue Martini"+ "\n Actual Results :Failure in verifying Articles between blue martini and SAP",false);
					testStatus=false;
				}
				button_btn3().press();
			} while (blnVerifyOrder);
			}
			catch(Exception e){
				e.printStackTrace();
				continue;
			}
		
		}
		button_btn3().press();
		button_btn3().press();
	}

}
	
	private boolean validateBillingDetails(String strSAPOrderNumber, String strWebShipmentNumber,String strOrderNumber) {
		// TODO Auto-generated method stub
		SAPGuiTableControlTestObject table= table_sapmV60ATCTRL_UEB_FAKT();
		boolean blnStatus  =sapfunctions.verifyItemsOnSAPOrderOverview(strSAPOrderNumber, strWebShipmentNumber, strOrderNumber, table,true);
		return blnStatus;
	}

	public  boolean VerifyOrderDetailsInDirectOrdersScreen(int intGridCounter, String[][] arrBMOrderDetails, String strOrderNumber){
		boolean blnfuncVerifyOrderDetailsInDirectOrdersScreen=false;
		//try {
			//Retrieve SAP Order details number
			
			String strSAPOrderType = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"AUART");
			String strSAPOrderNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"VBELN");
			String strWebExternalOrder = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"BSTKD_E");
			String strWebShipmentNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"BSTKD_M");
			//String strDeliveryNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"Delivery");
			String strPGIAndPGR =  grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"PGIRNO");
			String strGMAccountingNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"ACC1NO");
			String strBillingNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"BILLNO");
			String strAccountingNumber = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"ACC2NO");
			String strSalesOrg = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"VKORG");
			String strDivision = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"SPART");
			String strDistributionChannel  = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"VTWEG");
			String strPaymentCard = grid_sapguiGridViewCtrl1().getCellValue(intGridCounter,"RPLNR");
			//Verify if each required colomn displays a value
			if (strSAPOrderType.isEmpty() || strSAPOrderNumber.isEmpty()  ||  strWebExternalOrder.isEmpty() ||  strWebShipmentNumber.isEmpty() ||  strBillingNumber.isEmpty() ||  strAccountingNumber.isEmpty() ||  strSalesOrg.isEmpty() ||  strDivision.isEmpty() ||  strDistributionChannel.isEmpty()) {
				logTestResult( "Verify Order details in row: " +  Integer.toString(intGridCounter) + " of Direct Orders screen"+ ",SAP Order details should be displayed" + ", One or more columns does not display values in row: " +  Integer.toString(intGridCounter) + ". Values retrieved are...OrdType:" + strSAPOrderType + ", SAP Order:" + strSAPOrderNumber + ", Web External Order:" + strWebExternalOrder + ", Web Shipment#" + 	strWebShipmentNumber + ", Billing:" + strBillingNumber + ", Accounting#"+ strAccountingNumber + ", Sales Org:" + strSalesOrg + ", Division:" + strDivision + ", Dist Channel:" + strDistributionChannel,false);
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
				testStatus=false;
							try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipmentNumber);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Verify Order details in row: " +  Integer.toString(intGridCounter) + " of Direct Orders screen"+ ",SAP Order details should be displayed" + ", One or more columns does not display values in row: " +  Integer.toString(intGridCounter) + ". Values retrieved are...OrdType:" + strSAPOrderType + ", SAP Order:" + strSAPOrderNumber + ", Web External Order:" + strWebExternalOrder + ", Web Shipment#" + 	strWebShipmentNumber + ", Billing:" + strBillingNumber + ", Accounting#"+ strAccountingNumber + ", Sales Org:" + strSalesOrg + ", Division:" + strDivision + ", Dist Channel:" + strDistributionChannel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return blnfuncVerifyOrderDetailsInDirectOrdersScreen;
			}
			
			if (!strWebExternalOrder.trim().equalsIgnoreCase(strOrderNumber)) {
				logTestResult( "Step Description :Verify Web External Order number in row:  " + intGridCounter + "\n Expected Results :Web External Order field should display the input order number:"+ strOrderNumber + "\n Actual Results :Web External Order field is not displaying the input order number: " + strOrderNumber + ", This field is displaying the value: " + strWebExternalOrder,false);
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
			}
			//Verify Payment card is displayed for domestic Orders
			if (arrBMOrderDetails[0][43].equals("US") && strPaymentCard.isEmpty()) {
				logTestResult( "Step Description :Verify payment card is displayed for the SAP order: " + strSAPOrderNumber + "\n Expected Results :Payment card should be displayed"+ "\n Actual Results :Payment card is not displayed for the SAP order:" + strSAPOrderNumber,false);
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
				testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipmentNumber);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :Verify payment card is displayed for the SAP order: " + strSAPOrderNumber + "\n Expected Results :Payment card should be displayed"+ "\n Actual Results :Payment card is not displayed for the SAP order:" + strSAPOrderNumber);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			//Verify correct Order type is displayed in "Ord Type column"
			boolean blnStatus = false;
			blnStatus = verifyOrderTypeInSAP(strSAPOrderNumber,strSAPOrderType,strWebShipmentNumber,arrBMOrderDetails);
			if(!blnStatus) 
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
			
			//Verify Delivery document is displayed
			blnStatus = false;
			blnStatus = verifyDocumentDisplayonDirectOrdersScreen(strSAPOrderNumber,strWebShipmentNumber,strPGIAndPGR, "Delivery" ,arrBMOrderDetails);
			if(!blnStatus) 
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
			
			//Verify PGI/PGR document is displayed
			blnStatus = false;
			blnStatus = verifyDocumentDisplayonDirectOrdersScreen(strSAPOrderNumber,strWebShipmentNumber,strPGIAndPGR, "PGI/PGR" ,arrBMOrderDetails);
			if(!blnStatus) 
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
			
			//Verify GM Accounting document is displayed
			blnStatus = false;
			blnStatus = verifyDocumentDisplayonDirectOrdersScreen(strSAPOrderNumber,strWebShipmentNumber,strPGIAndPGR, "GM Accounting" ,arrBMOrderDetails);
			if(!blnStatus) 
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;

			logTestResult( "Step Description :Verify SAP order:" + strSAPOrderNumber + "\n Expected Results : details displayed on Direct Orders screen"+ "\n Actual Results : Details of SAP Order:" + strSAPOrderNumber + " should be displayed as expected"+"\nDetails of SAP Order:" + strSAPOrderNumber + " are displayed as expected",true);
			blnfuncVerifyOrderDetailsInDirectOrdersScreen = true;
			return blnfuncVerifyOrderDetailsInDirectOrdersScreen; 

	//	} catch (Exception e) {
	//		e.printStackTrace();
	//		return blnfuncVerifyOrderDetailsInDirectOrdersScreen;
	//}
}
	
	public  boolean verifyOrderTypeInSAP(String strSAPOrderNumber, String strSAPOrderType,String strWebShipmentNumber,String[][] arrBMOrderDetails){

		boolean blnAllocationMatch = false;
		String strLyhAllocationCode="";
		String strVariant ;
		String strShippedFrom="" ;
		String strBMShipmentNumber;
		String strOrderType="";
				//Retrieve Brand
				String strBrand = arrBMOrderDetails[0][45].trim();
				if (strBrand.trim().toUpperCase().equals("MADEWELL"))
					strLyhAllocationCode = "LBDM";
				else if (strBrand.trim().toUpperCase().equals("FACTORY"))
					strLyhAllocationCode = "LBDF";
			    else if (strBrand.trim().toUpperCase().equals("JCREW"))
					strLyhAllocationCode = "LBDI";	
				
				
				for (int i = 0; i < arrBMOrderDetails.length; i++)
				{
					strBMShipmentNumber = arrBMOrderDetails[i][41].trim();
					strOrderType = arrBMOrderDetails[i][42].trim();
					if (strWebShipmentNumber.trim().equals(strBMShipmentNumber))
					{
						 strVariant = arrBMOrderDetails[i][6].trim();
						 strShippedFrom = arrBMOrderDetails[i][12].trim();
						if(strShippedFrom.substring(0,1).equals( "0")) 
							strShippedFrom  = "RTL";
												
						switch(strSAPOrderType.toUpperCase().trim())
						{
							case "ZOR":
								if( strShippedFrom.equals(strLyhAllocationCode) && strOrderType.equals("ORD") )
									blnAllocationMatch = true;
								//else if( strOrderType="ORD" && instr(environment("eGiftCard"),strVariant) > 0)
									//blnAllocationMatch = true;
										
							case "ZORE":
								if(strShippedFrom.equals( "RTL") && strOrderType.equals("ORD"))
									blnAllocationMatch = true;	
								
							case "ZRE":	
								if( strShippedFrom.equals( strLyhAllocationCode) && strOrderType.equals("RET")) 
									blnAllocationMatch = true;	
								
							case "ZREE":
								if( strShippedFrom.equals("RTL") && strOrderType.equals("RET") ) 
									blnAllocationMatch = true;	
		
						}
						
						break;
					}
				}
				
				if(!blnAllocationMatch)
				{
					logTestResult("Step Description :Verify correct Order Type is displayed for the SAP Order: " + strSAPOrderNumber +  "\n Expected Results : Correct Order type should be displayed" + "\n Actual Results : Correct Ord Type is NOT displayed for the SAP Order: " + strSAPOrderNumber + ". Order type displayed in SAP: " +strSAPOrderType + ", where as Blue Martini order type is:" + strOrderType + " and it is shipped from:" + strShippedFrom, false);
				testStatus=false;
				try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipmentNumber);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :Verify correct Order Type is displayed for the SAP Order: " + strSAPOrderNumber +  "\n Expected Results : Correct Order type should be displayed" + "\n Actual Results : Correct Ord Type is NOT displayed for the SAP Order: " + strSAPOrderNumber + ". Order type displayed in SAP: " +strSAPOrderType + ", where as Blue Martini order type is:" + strOrderType + " and it is shipped from:" + strShippedFrom);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					return false;
				}	
				else
				{
				logTestResult( "Step Description :Verify correct Order Type is displayed for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results : Correct Order type should be displayed"+ "\n Actual Results : Correct Ord Type is displayed for the SAP Order: " + strSAPOrderNumber + ". Order type displayed in SAP: " + strSAPOrderType + ", where as Blue Martini order type is:" + strOrderType + " and it is shipped from:" + strShippedFrom , true);
				return  true;
				}
				
}	
	
	public  boolean verifyDocumentDisplayonDirectOrdersScreen(String strSAPOrderNumber,String strWebShipmentNumber,String strDocumentNumber, String strDocumentType,String[][] arrBMOrderDetails)
	{
	 boolean blnVerifyDocumentDisplayonDirectOrdersScreen =false;
	 if (strDocumentNumber == "")
	 {
		 for (int i = 0; i < arrBMOrderDetails.length; i++)
		 {
			 String strBMShipmentNumber = arrBMOrderDetails[i][41].trim();
			 String strBMItemName = arrBMOrderDetails[i][6].trim();
			   if(strWebShipmentNumber.trim().equals(strBMShipmentNumber)) 
			   {
				//if !(((instr(environment("eGiftCard"),strBMItemName) > 0) || (instr(environment("ClassicGiftCard"),strBMItemName) > 0) || (instr(environment("MonogramService"),strBMItemName) > 0) || (instr(environment("GiftWrapService"),strBMItemName) > 0)))
				{
					logTestResult( "Step Description :Verify SAP Order:"+ strSAPOrderNumber + " details on Direct Orders screen"+ "\n Expected Results :SAP Order details should be displayed"+ strDocumentType + "\n Actual Results : Number is not displayed for the SAP order:" + strSAPOrderNumber + ", though this order has regular items (no egift card and no monogram) such as " + strBMItemName, false);
					blnVerifyDocumentDisplayonDirectOrdersScreen = false;	
					testStatus=false;
				try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipmentNumber);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :Verify SAP Order:"+ strSAPOrderNumber + " details on Direct Orders screen"+ "\n Expected Results :SAP Order details should be displayed"+ strDocumentType + "\n Actual Results : Number is not displayed for the SAP order:" + strSAPOrderNumber + ", though this order has regular items (no egift card and no monogram) such as " + strBMItemName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					break;
				}
			 }
		 }
	 }

	blnVerifyDocumentDisplayonDirectOrdersScreen = true;
	return blnVerifyDocumentDisplayonDirectOrdersScreen;

}
	
	public void ValidateStoreNumbersOnPurchageOrder(String [][] arrBMOrderDetails,String strWebshipment)
	{
		
	}
	
	
	public void ValidateGLPosting(String strSAPOrderNumber,String strWebExternalOrder,String strGMAccountingNumber)
	{
		String[] arrPostingDetails ;
		int intRowCount=((ITestDataTable) (grid_sapguiGridViewCtrl1_3().getTestData("list"))).getRowCount();
		
		boolean blnAccount;
		boolean blnBisUnit;
		boolean blnProfitCenter;
		
		String strPostingNumber;
		String strPrvPostingNumber;
		String strDescription;
		String strItem;
		String strGridAccount; 
		String strProfitCenter;
		String BusiUnt; 
		String strRecordCenter ;
		String strBisUnit;
		String strPostingType;
		String strPrvDescription = null;
		String strExpProfitCenter = null;
		String [] arrGlPosting;
		
			
		strPrvPostingNumber ="";
		for(int Iterator = 0; Iterator<intRowCount-2;Iterator++ ) {
			blnBisUnit=false;
			blnProfitCenter =  false;
			blnAccount =false;
			
			strDescription="";
			strPostingNumber="";
			strItem="";
			strGridAccount ="";
			strProfitCenter="";
			BusiUnt ="";
			strRecordCenter ="";
			strPostingNumber ="";
			strBisUnit="";
			String strGridAmount="0.00";
			arrPostingDetails = getGLPostingdetailssInSAP(Iterator);
			if(arrPostingDetails.length==0) {
				logTestResult( "Step Description :SAP Order GM Posting Verification - "+ "\n Expected Results :'MM-Env' and Mdse Std Cost postings in GM Accounting documets should contain correct codes "+ "\n Actual Results :Failure in Load Account number + Profict center and Business Area number from master data sheet",false);
				testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", strWebExternalOrder);
				//xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :SAP Order GM Posting Verification - "+ "\n Expected Results :'MM-Env' and Mdse Std Cost postings in GM Accounting documets should contain correct codes "+ "\n Actual Results :Failure in Load Account number + Profict center and Business Area number from master data sheet");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return;
			}
			strDescription =  arrPostingDetails[4];
			strItem= arrPostingDetails[1];
			strGridAccount = arrPostingDetails[3];
			strProfitCenter = arrPostingDetails[7];
			BusiUnt = arrPostingDetails[8];
			strGridAmount = arrPostingDetails[5];
			if(strGridAmount.contains("-"))
				strPostingType = "cr";
			else
				strPostingType = "dr";
			
			if( !strDescription.equals("")) {
				
				//strDescription = strDescription.replace(" ","");
				strRecordCenter= sapfunctions.discGLpostings.get(strDescription);
			
				if(strRecordCenter==null || strRecordCenter.equals("") )
				     {
						logTestResult( "Step Description :GM Posting Document - Retrieve Profit Center and Business Area for: " + strDescription+ "\n Expected Results :Profit Center and Business Area should be retrieved"+ "\n Actual Results :Unable to retrieve Profit Center and Business Area for " + strDescription,false); 
				       testStatus=false;
					   			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", strWebExternalOrder);
				//xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description :GM Posting Document - Retrieve Profit Center and Business Area for: " + strDescription+ "\n Expected Results :Profit Center and Business Area should be retrieved"+ "\n Actual Results :Unable to retrieve Profit Center and Business Area for " + strDescription);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				    }
				else	
				{
				arrGlPosting =   strRecordCenter.split(",");
				strPostingNumber = arrGlPosting [0];
				strPrvPostingNumber = strGridAccount ;
				strPrvDescription = strDescription;
				if( !strGridAccount.equals("")) {
					if(strGridAccount.equals(strPostingNumber)) 
						blnAccount = true;
				}
				else
					blnAccount = true;
				
				if (sapfunctions.gstrDistChannel.contains("internet")) {
						if( strPostingType.equals("cr"))
							strBisUnit = arrGlPosting [1];
						else
							strBisUnit = arrGlPosting [2];						
				}		
						
					else 
					{
						if( strPostingType.equals("cr"))
							strBisUnit = arrGlPosting [3];
						else
							strBisUnit = arrGlPosting [4];					
						
				    }
					
				if(! BusiUnt.equals("")) {
					if( BusiUnt.equals(strBisUnit.trim().replace("BA","") )) 
						blnBisUnit = true;
					
				else
					blnBisUnit = true;			
				}
				if(!strProfitCenter.equals("")) {	
					if (strPostingType.equals("cr")) {
							if ((arrGlPosting[5].trim().equals(""))  && (arrGlPosting[5].equals("PC State"))) 
								strExpProfitCenter = sapfunctions.gstrProfitCenter;
							else if (!(arrGlPosting[5].trim().equals(""))  && (arrGlPosting[5].equals("PC Store")))
								strExpProfitCenter = sapfunctions.gstrBMStoreNumber;
							else if ((arrGlPosting[5].trim().equals(""))  && (arrGlPosting[5].equals("PC Store Gen")))
								strExpProfitCenter = "PC Store Gen";
							
							else if(!(arrGlPosting[5].trim().equals(""))) 
								strExpProfitCenter = arrGlPosting[5].trim();
					}
							
						else {
							if (!(arrGlPosting[6].trim().equals(""))  && (arrGlPosting[6].equals("PC State"))) 
							
								strExpProfitCenter = sapfunctions.gstrProfitCenter;
							else if(!(arrGlPosting[6].trim().equals(""))  && (arrGlPosting[6].equals("PC Store")))
								strExpProfitCenter = sapfunctions.gstrBMStoreNumber;
							else if(!(arrGlPosting[6].trim().equals("")))
								strExpProfitCenter = arrGlPosting[6].trim();
							
						} 
					if (strExpProfitCenter.equals("PC Store Gen")) {
						if( strProfitCenter.substring(1,1).equals("9") &&  strProfitCenter.trim().length()== 3) 
							blnProfitCenter =  true;}
					else if(strExpProfitCenter.equals(strProfitCenter))
							blnProfitCenter =  true;
							
					
					//}	
			}
		 }
			}
			
			else {
					strDescription = strPrvDescription;
					if(! strGridAccount.equals("")) {
						if (strGridAccount.equals(strPrvPostingNumber))
							blnAccount = true;
				    }
					blnProfitCenter=true; 
					blnBisUnit = true;
		     }	
		
			if (blnAccount==true && blnProfitCenter==true && blnBisUnit == true) { 
				logTestResult( "Step Description: GM Posting Document - Verify GL posting for: " + strDescription+"\n Expected Results :Posting should be done properly"+ "\n Actual Results :Prosting are done properly for the follwing order " + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + "\t\t\t GL Posting Number: " + strGMAccountingNumber + "\t\t\t Posting Description: " + strDescription +  "\t\t\tExpected Account Number: " + strPostingNumber +  "\t\t\tPosted Account Number: " + strGridAccount + "\t\t\tExpected Profit Center :" + strExpProfitCenter +  "\t\t\tPosted Profit Center:" + strProfitCenter  + "\t\t\tExpected Business Area :" + strBisUnit +  "\t\t\tPosted Business Area: " + BusiUnt + "\t\t\tOrder Type : " + sapfunctions.gstrOrdType +"\t\t\tPosting type is " + strPostingType,true);
			}
			else
			{
				logTestResult( "Step Description: GM Posting Document - Verify GL posting for: " + strDescription+ "\n Expected Results :Posting should be done properly"+ "\n Actual Results :Prosting are not done properly for " + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + "\t\t\t GL Posting Number: " + strGMAccountingNumber + "\t\t\t Posting Description: " + strDescription + "\t\t\tExpected Account Number: " + strPostingNumber +  "\t\t\tPosted Account Number: " + strGridAccount + "\t\t\tExpected Profit Center :" + strExpProfitCenter +  "\t\t\tPosted Profit Center:" + strProfitCenter  + "\t\t\tExpected Business Area :" + strBisUnit +  "\t\t\tPosted Business Area: " + BusiUnt + "\t\t\tOrder Type : " + sapfunctions.gstrOrdType +"\t\t\tPosting type is " + strPostingType,false);
			    testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", strWebExternalOrder);
				//xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log","Step Description: GM Posting Document - Verify GL posting for: " + strDescription+ "\n Expected Results :Posting should be done properly"+ "\n Actual Results :Prosting are not done properly for " + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + "\t\t\t GL Posting Number: " + strGMAccountingNumber + "\t\t\t Posting Description: " + strDescription + "\t\t\tExpected Account Number: " + strPostingNumber +  "\t\t\tPosted Account Number: " + strGridAccount + "\t\t\tExpected Profit Center :" + strExpProfitCenter +  "\t\t\tPosted Profit Center:" + strProfitCenter  + "\t\t\tExpected Business Area :" + strBisUnit +  "\t\t\tPosted Business Area: " + BusiUnt + "\t\t\tOrder Type : " + sapfunctions.gstrOrdType +"\t\t\tPosting type is " + strPostingType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
				
	       //PostingType	PostingNumber	DebitInternet	DebitPhone	DebitPC	CreditInternet	CreditPhone	CreditPC
		}
		
		
	}
	

	public  String[] getGLPostingdetailssInSAP(int strRowNumber){
		String[] strPostingDetails = new String [10];

		 strPostingDetails[0]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"BUKRS");//COCODE
		 strPostingDetails[1]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"BUZEI"); //lINE ITEM
		 strPostingDetails[2]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"BSCHL"); //Posting Key
		 strPostingDetails[3]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"KTONR");//Account
		 strPostingDetails[4]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"KOBEZ");//Description
		 strPostingDetails[5]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"AZBET");//Amount
		 strPostingDetails[6]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"ZUONR");//Assignment
		 strPostingDetails[7]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"PRCTR");//Profit Center
		 strPostingDetails[8]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"GSBER");//Business Area
		 strPostingDetails[9]=grid_sapguiGridViewCtrl1_3().getCellValue(strRowNumber,"SGTXT");//Text
		
		 return strPostingDetails;
}	
	

	public String GetGMTableBox_Value(int strRowNum, String ColumnName)
	{
		
		ITestDataTable itemTable = (ITestDataTable) (grid_sapguiGridViewCtrl1_3().getTestData("list"));
		//int intRowCount = itemTable.getRowCount();
		String strColumnValue="";
		//int articleCol = 0;
	     for (int col=0; col < itemTable.getColumnCount();++col)
	      {
	    	 if (itemTable.getColumnHeader(col).equals(ColumnName)) {
	    		 strColumnValue =(String) itemTable.getCell(strRowNum,col);
	    		 logTestResult("Capture value for: "+ColumnName+"Captured the value for "+ColumnName+" as: "+strColumnValue,true);
	    	    break;
	    	 }
	 
		 }
	      if (strColumnValue.equals(""))   
	      {
	    	  logTestResult("Capture value for: "+ColumnName+"Failed to Capture as Unable to find the field :"+ColumnName,false);
			  //testStatus=false;
	      }
		 return strColumnValue.trim().toUpperCase();

	}
	
public void ValidateAccountPosting(String strSAPOrderNumber,String strWebExternalOrder,String strGMAccountingNumber,String[][] arrBMOrderDetails,String strWebShipment)
{
	String [] arrPostingDetails; 
	//int intRowCount=SessionObj.SAPGuiGrid("micclass:=SAPGuiGrid").RowCount;
	//int intRowCount=0;
	int intRowCount=((ITestDataTable) (grid_sapguiGridViewCtrl1_3().getTestData("list"))).getRowCount();
	boolean blnAccount;
	boolean blnBisUnit;
	boolean blnProfitCenter;
	String strText;
	String strPostingNumber;
	String strPrvPostingNumber;
	String strDescription;
	String strItem;
	String strGridAccount,strGridAmount,strAssignment; 
	String strProfitCenter;
	String BusiUnt ;
	String strRecordCenter ;
	String strBisUnit;
	boolean blnWebShipment ;
	String []arrPostingAmount ;
	String [] arrGlPosting;
	String strPrvDescription = "";
	String strExpProfitCenter = "";
	arrPostingAmount = getPostingAmounts(strWebShipment,arrBMOrderDetails);
	funVerifyMinimumPostingsinAccounting(arrBMOrderDetails,strWebShipment,arrPostingAmount);
	
	String strOrderType = arrBMOrderDetails[0][42].trim();
	String strBrand = 	arrBMOrderDetails[0][45].trim().toUpperCase();
	strPrvPostingNumber ="";
   for(int Iterator = 0 ;Iterator<intRowCount-2;Iterator++) 
	{
		
		blnBisUnit=false;
		blnProfitCenter =  false;
		blnAccount =false;
		blnWebShipment = false;
		strDescription="";
		strPostingNumber="";
		strItem="";
		strGridAccount ="0.00";
		strProfitCenter="";
		BusiUnt ="";
		strRecordCenter ="";
		strPostingNumber ="";
		strBisUnit="";
		strText="";
		arrPostingDetails = getGLPostingdetailssInSAP(Iterator);
		
		if (arrPostingDetails.length==0) {
			logTestResult( "Step Description :SAP Order Account Posting Verification - "+ "\n Expected Results :Get Account postings from Accounting documets Grid"+ "\n Actual Results :Order: Failure in get Description , Profict center and Business Area number from Accounting document grid , The document details are not opened or some error in getting the details from Grid",false);
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description :SAP Order Account Posting Verification - "+ "\n Expected Results :Get Account postings from Accounting documets Grid"+ "\n Actual Results :Order: Failure in get Description , Profict center and Business Area number from Accounting document grid , The document details are not opened or some error in getting the details from Grid");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		strDescription =  arrPostingDetails[4];
		strItem=  arrPostingDetails[2];
		strGridAccount =  arrPostingDetails[3];
		strGridAmount =  arrPostingDetails[5];
		strProfitCenter =  arrPostingDetails[7];
		strAssignment  =  arrPostingDetails[6];
		BusiUnt =  arrPostingDetails[8];
		strText =  arrPostingDetails[9];
		String strPostingType;
		if (strGridAmount.contains("-"))  {
			strPostingType = "cr";
		}
		else {
			strPostingType = "dr";
		
		}
			
			
		//If (strDescription<>"") or (strOrderType = "RET" And strGridAccount="WEBCUSTINR" and Iterator <>(intRowCount-1)) Then
		if((!strDescription.isEmpty()) ||(strOrderType.equals("RET") && strGridAccount.equals("WEBCUSTINR") && Iterator!=(intRowCount-1) )) {
		if(strOrderType.equals("RET") && strGridAccount.equals("WEBCUSTINR")) {
			strDescription = "WEBCUSTINR";
		}
/*'			If strDescription="Direct Sales Customer" Then
'			
'				strDescription =  "WEBCUST"
'			ElseIf  strDescription="Enterprise Selling Customer"  Then	
'				strDescription =  "WEBCUSTINT"
'			ElseIf strDescription = "WEB 51 Customer" Then	
'				strDescription =  "WEBCUSTINT"
'			End If */
			
			//strDescription = strDescription.replace(" ","");
			
			
			if(strDescription.contains("POS -  MC/Visa") || strDescription.contains("POS-MC/Visa"))  strDescription = "POS -  MC/Visa Clear";
			strRecordCenter= sapfunctions.discGLpostings.get(strDescription);
			if(strRecordCenter==null) { 
				logTestResult( "Step Description :Accounting Document  Posting Verification - " + strDescription+ "\n Expected Results :postings in GM Accounting documets should contain correct codes "+ "\n Actual Results :Order: Failure in Load Account number , Profict center and Business Area number from master data sheet",false);
			    testStatus=false;
				try {
					xlRowno++;
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description :Accounting Document  Posting Verification - " + strDescription+ "\n Expected Results :postings in GM Accounting documets should contain correct codes "+ "\n Actual Results :Order: Failure in Load Account number , Profict center and Business Area number from master data sheet");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				arrGlPosting =  strRecordCenter.split(",");
				strPostingNumber = arrGlPosting [0];
				strPrvPostingNumber = strGridAccount ;
				strPrvDescription = strDescription;
				if(!strGridAccount.equals("")) {
					if(strGridAccount.equals(strPostingNumber))
						blnAccount = true;
				
				}
				else {
					blnAccount = true;			
				}
				if (sapfunctions.gstrDistChannel.contains("internet")) {
					if( strPostingType.equals("cr"))
						strBisUnit = arrGlPosting [1];
					else
						strBisUnit = arrGlPosting [2];						
			    }		
					
				else 
				{
					if( strPostingType.equals("cr"))
						strBisUnit = arrGlPosting [3];
					else
						strBisUnit = arrGlPosting [4];					
					
			    }
				if(! BusiUnt.equals("")) {
					if( BusiUnt.equals(strBisUnit.trim().replace("BA","") )) 
						blnBisUnit = true;
					
				else
					blnBisUnit = true;			
				}
				if(!strProfitCenter.equals("")) {
					
/*'					If Trim(arrGlPosting(3))<>"" Then
'						strExpProfitCenter = Trim(arrGlPosting(3))
'					Else
'						If gstrOrdType="ZORE" Then
'							strExpProfitCenter = gstrBMStoreNumber
'						Else
'							strExpProfitCenter = gstrProfitCenter			
'						End If
'					End If


'					If gstrOrdType="ZORE" and (instr(ucase(environment("StorePCExclusions")),ucase(strDescription)) = 0) Then
'						strExpProfitCenter = gstrBMStoreNumber
'					Else
'					
'						If Trim(arrGlPosting(3))<>"" Then
'							strExpProfitCenter = Trim(arrGlPosting(3))
'						Else
'							strExpProfitCenter = gstrProfitCenter			
'						End If		
'					End If*/

				if (strPostingType.equals("cr")) {
					if (!(arrGlPosting[5].trim().equals(""))  && (arrGlPosting[5].equals("PC State"))) 
						strExpProfitCenter = sapfunctions.gstrProfitCenter;
					else if (!(arrGlPosting[5].trim().equals(""))  && (arrGlPosting[5].equals("PC Store")))
						strExpProfitCenter = sapfunctions.gstrBMStoreNumber;
					else if (!(arrGlPosting[5].trim().equals(""))  && (arrGlPosting[5].equals("PC Store Gen")))
						strExpProfitCenter = "PC Store Gen";
					
					else if(!(arrGlPosting[5].trim().equals(""))) 
						strExpProfitCenter = arrGlPosting[5].trim();
			   }
					
				else {
					if (!(arrGlPosting[6].trim().equals(""))  && (arrGlPosting[6].equals("PC State"))) 
					
						strExpProfitCenter = sapfunctions.gstrProfitCenter;
					else if(!(arrGlPosting[6].trim().equals(""))  && (arrGlPosting[6].equals("PC Store")))
						strExpProfitCenter = sapfunctions.gstrBMStoreNumber;
					else if(!(arrGlPosting[6].trim().equals("")))
						strExpProfitCenter = arrGlPosting[6].trim();
					
				} 
				
				if (strExpProfitCenter.replace("BA","").trim().equals(strProfitCenter)) 
						blnProfitCenter =  true;
					
					else if( strExpProfitCenter.equals("PC Store Gen")) {
						if (strProfitCenter.substring(1,1).equals("9")) 
							blnProfitCenter =  true;
						
					}
					
			  
		      }
				if(!strAssignment.equals("")) { 
						if(strWebShipment.trim().equals("strAssignment")) 
							blnWebShipment = true;
						else
					   blnWebShipment = true;
				}
				
				String strCompareText = "";
				String strCompareTextWithSpace = "";
				if(!strText.equals("")) { 
					if(sapfunctions.gstrCountryCode.trim().toUpperCase().equals("US") || strBrand.equals("FACTORY")) {					
						strCompareText =  strWebExternalOrder;
						if(strDescription.equals("Discounts")) {
							strCompareText =  strWebExternalOrder + "  " + arrBMOrderDetails[0][25];
							strCompareTextWithSpace =  strWebExternalOrder + " " + arrBMOrderDetails[0][25];
						}
						else if(strDescription.equals("EmployeeDiscounts") && (arrBMOrderDetails !=null  || !arrBMOrderDetails[0][48].equals(""))) {
							strCompareText =  strWebExternalOrder+ "  " + arrBMOrderDetails[0][49];
							strCompareTextWithSpace =  strWebExternalOrder + " " + arrBMOrderDetails[0][49];
					   }	
				    }
					else
					{
						String strIntlE4X = arrPostingAmount[3];
						if(strOrderType.equals("RET"))
							strIntlE4X = "RT"+strIntlE4X+"AA";
						
						if(strDescription.equals("WEB51Customer")) {
							strCompareText =  strWebShipment + " " + strIntlE4X;
							strCompareTextWithSpace = strWebShipment + "  " + arrPostingAmount[3] ;
							}
						else if(strDescription.equals("Discounts")) {
							strCompareText =  strWebExternalOrder + " " + strIntlE4X + " " + arrBMOrderDetails[0][25];
							strCompareTextWithSpace =  strWebExternalOrder + "  " + strIntlE4X + "  " + arrBMOrderDetails[0][25]; }
						else if(strDescription.equals("EmployeeDiscounts") && (arrBMOrderDetails !=null  || !arrBMOrderDetails[0][48].equals(""))) {
							strCompareText =  strWebExternalOrder+ "  " + arrBMOrderDetails[0][49];
							strCompareTextWithSpace =  strWebExternalOrder + " " +strIntlE4X  + " " + arrBMOrderDetails[0][49];
					   }
						else if (strDescription.equals("WEBCUSTINR")) {
							strCompareText = strIntlE4X; }
						else {
							strCompareText =  strWebExternalOrder + " " + strIntlE4X;	
							strCompareTextWithSpace =  strWebExternalOrder + "  " + strIntlE4X;								
						}
						
					}
					if(strText.trim().equals(strCompareText.trim()) || strText.trim().equals(strCompareTextWithSpace.trim()) || (sapfunctions.gstrCountryCode.trim().toUpperCase().equals("CA") && strDescription.equals("SalesTaxCollected") && (strText.trim().equals("PST") || strText.trim().equals("GST")))) {
						logTestResult(  "Step Description :Accounting Document - Verify Text Column for: " + strDescription+ "\n Expected Results :Text should match with Blue Martin Order Number"+ "\n Actual Results : Text value matching Web External Order: " +  strWebExternalOrder  + "Expected text: " + strCompareText +  " Actual text :" + strText ,true); 
						}
					else
					{
						logTestResult(  "Step Description :Accounting Document - Verify Text Columnfor: " + strDescription+ "\n Expected Results :Text should match with Blue Martin Order Number"+ "\n Actual Results : Text value not matching for Web External Order: " +  strWebExternalOrder + "Expected text: " + strCompareText +  " Actual text :" + strText,false);							
					    testStatus=false;
							try {
								xlRowno++;
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description :Accounting Document - Verify Text Columnfor: " + strDescription+ "\n Expected Results :Text should match with Blue Martin Order Number"+ "\n Actual Results : Text value not matching for Web External Order: " +  strWebExternalOrder + "Expected text: " + strCompareText +  " Actual text :" + strText);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}			
				}
				
	       }	
		}	
			
			else
			{
			
					strProfitCenter="";
					strBisUnit="";
					strDescription = strPrvDescription;
					if(! strGridAccount.equals("")) {
						if(strGridAccount.equals(strPrvPostingNumber))
							blnAccount = true;
					}
					
					if(strDescription.equals("SalesTaxCollected")) {
						if (strGridAmount.replace("-","").equals(arrPostingAmount[1]))
							logTestResult(  "Step Description :Accounting Document - Verify Sales Tax Collected Amount : "  + "\n Expected Results :Sale tax colleced Amount should match with Blue Martin Sales tax total Amount"+ "\n Actual Results :Sale Tax Collected amount posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Sales Tax Collected amount:= " + arrPostingAmount[1] + "Displayed Amount " +  strGridAmount,true);	
						else
						{
							logTestResult(  "Step Description :Accounting Document - Verify Sales Tax Collected Amount : "  + "\n Expected Results :Sale tax colleced Amount should match with Blue Martin Sales tax total Amount"+ "\n Actual Results :Sale Tax Collected amount not posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Sales Tax Collected amount:= " + arrPostingAmount[1] + "Displayed Amount " +  strGridAmount,false);
							try {
								xlRowno++;
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Order", strSAPOrderNumber);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", strWebExternalOrder);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Sale Tax Collected amount not posted correctly for the follwing order");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					if(strDescription.equals("GrossSales")) {
						if (strGridAmount.replace("-","").equals(arrPostingAmount[0]))
							logTestResult(  "Step Description :Accounting Document - Verify Gross Sale Amount : "  + "\n Expected Results :Gross Sale Amount should match with Blue Martin Amount"+ "\n Actual Results :Gross Sale amount posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Gross sale amount:= " + arrPostingAmount[0] + "Displayed Amount " +  strGridAmount,true);
						else {
						    logTestResult( "Step Description :Accounting Document - Verify Gross Sale Amount : "  + "\n Expected Results :Gross Sale Amount should match with Blue Martin Amount"+ "\n Actual Results :Gross Sale amount not posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Gross sale amount:= " + arrPostingAmount[0] + "Displayed Amount " +  strGridAmount,false);						
						    testStatus=false;
							try {
								xlRowno++;
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description :Accounting Document - Verify Gross Sale Amount : "  + "\n Expected Results :Gross Sale Amount should match with Blue Martin Amount"+ "\n Actual Results :Gross Sale amount not posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Gross sale amount:= " + arrPostingAmount[0] + "Displayed Amount " +  strGridAmount);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					if(strDescription.equals("GiftCardLiability")) {
						if (strGridAmount.replace("-","").equals(arrPostingAmount[4])) {
							logTestResult(  "Step Description :Accounting Document - Verify Gift Card Liability Amount : "  + "\n Expected Results :Gift Card Liability Amount should match with Blue Martin Amount"+ "\n Actual Results :Gift Card Liability amount posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Gift Card Liability amount:= " + arrPostingAmount[4] + "Displayed Amount " +  strGridAmount,true);
						}
						else
						{
							
						   logTestResult(  "Step Description :Accounting Document - Verify Gift Card Liability Amount : "  + "\n Expected Results :Gift Card Liability Amount should match with Blue Martin Amount"+ "\n Actual Results :Gift Card Liability amount not posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Gift Card Liability amount:= " + arrPostingAmount[4] + "Displayed Amount " +  strGridAmount,false);						
						   testStatus=false;
							try {
								xlRowno++;
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
								xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description :Accounting Document - Verify Gift Card Liability Amount : "  + "\n Expected Results :Gift Card Liability Amount should match with Blue Martin Amount"+ "\n Actual Results :Gift Card Liability amount not posted correctly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + " Expected Gift Card Liability amount:= " + arrPostingAmount[4] + "Displayed Amount " +  strGridAmount);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				
					}
					blnProfitCenter=true; 
					blnBisUnit = true;
					blnWebShipment = true;
			}		
			
			if(blnAccount==true && blnProfitCenter==true  && blnBisUnit == true && blnWebShipment == true) {
				logTestResult(  "Step Description :Accounting Document - Verify GL Posting for: " + strDescription + "\n Expected Results :Posting should be done properly"+ "\n Actual Results :Posting are done properly for the follwing order " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + "\t\t\tGL Posting Number: " + strGMAccountingNumber + "\t\t\tPosting Description: " + strDescription +  "\r\n" + "Expected Account Number: " + strPostingNumber +  "\t\t\tPosted Account Number: " + strGridAccount + "\t\t\tExpected Profit Center :" + strExpProfitCenter +  "\t\t\tPosted Profit Center:" + strProfitCenter  + "\t\t\tExpected Business Area :" + strBisUnit +  "\t\t\tPosted Business Area: " + BusiUnt + "\t\t\tExepected Assignment number: " + strWebShipment + "\t\t\tDisplayed Assignment number:" + strAssignment + "\t\t\tOrder Type : " + sapfunctions.gstrOrdType +"\t\t\tPosting type is " + strPostingType,true);
			}			
				else
			{
				logTestResult(  "Step Description :Accounting Document - Verify GL Posting for: " + strDescription + "\n Expected Results :Posting should be done properly"+ "\n Actual Results :Posting are not done properly for " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + "\t\t\tGL Posting Number: " + strGMAccountingNumber + "\t\t\tPosting Description: " + strDescription +  "\r\n" + "Expected Account Number: " + strPostingNumber +  "\t\t\tPosted Account Number: " + strGridAccount + "\t\t\tExpected Profit Center :" + strExpProfitCenter +  "\t\t\tPosted Profit Center:" + strProfitCenter  + "\t\t\tExpected Business Area :" + strBisUnit +  "\t\t\tPosted Business Area: " + BusiUnt +  "\t\t\tExepected Assignment number: " + strWebShipment + "\t\t\tDisplayed Assignment number:" + strAssignment + "\t\t\tOrder Type : " + sapfunctions.gstrOrdType +"\t\t\tPosting type is " + strPostingType,false);
				testStatus=false;
				try {
					xlRowno++;
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Order", strSAPOrderNumber);
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", strWebExternalOrder);
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
					xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description :Accounting Document - Verify GL Posting for: " + strDescription + "\n Expected Results :Posting should be done properly"+ "\n Actual Results :Posting are not done properly for " + "\r\n" + "SAP Order: "+ strSAPOrderNumber + " Web External Order: " +  strWebExternalOrder + "\t\t\tGL Posting Number: " + strGMAccountingNumber + "\t\t\tPosting Description: " + strDescription +  "\r\n" + "Expected Account Number: " + strPostingNumber +  "\t\t\tPosted Account Number: " + strGridAccount + "\t\t\tExpected Profit Center :" + strExpProfitCenter +  "\t\t\tPosted Profit Center:" + strProfitCenter  + "\t\t\tExpected Business Area :" + strBisUnit +  "\t\t\tPosted Business Area: " + BusiUnt +  "\t\t\tExepected Assignment number: " + strWebShipment + "\t\t\tDisplayed Assignment number:" + strAssignment + "\t\t\tOrder Type : " + sapfunctions.gstrOrdType +"\t\t\tPosting type is " + strPostingType);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
				//PostingType	PostingNumber	DebitInternet	DebitPhone	DebitPC	CreditInternet	CreditPhone	CreditPC
		
 }

//}
   
}
	
	
	public boolean isPostingPresent(String strDescription)
	{
     boolean blnPostFound=false;
	int intRowCount=((ITestDataTable) (grid_sapguiGridViewCtrl1_3().getTestData("list"))).getRowCount();
	for(int iterator = 1;iterator<intRowCount-1;iterator++)
	{
				String strGridDescription=grid_sapguiGridViewCtrl1_3().getCellValue(iterator,"KOBEZ"); //description
				if (strGridDescription.contains(strDescription)) {
					
					blnPostFound= true;
					break;
				}
			
	}

	return blnPostFound;
	}

	public void funVerifyMinimumPostingsinAccounting(String[][] arrBMOrderDetails,String strWebShipment,String[] arrPostingAmount)
	{
	//Dim objGrid
	String strDescription;
	String strPostingFound;
	boolean BlnMonoFound=false;
	boolean BlnReturnsFound=false;
	boolean blnGiftBoxFound=false;
	boolean BlnGrossFound=false;

	//Set objGrid =  SessionObj.SAPGuiGrid("micclass:=SAPGuiGrid","type:=GuiShell","Name:=shell")
	
	String strErrorDescription;
	strErrorDescription = "The Following Account posting are not present : " ;
	strPostingFound =  "The Following Account postings are present: ";
	String []arrArrayPosting=new String[4]; 
	

	
	if( arrPostingAmount[6].equals("RET")) {
	
	BlnReturnsFound = isPostingPresent("Returns");
	if( BlnReturnsFound)  {
		  logTestResult( "Step Description: Verify Returns posting in Accounts Document"+"\n Expected Results :Returns posting should be present"+"\n Actual Results :Returns Posting is present for Web shippment: " + strWebShipment,true);
		  		}
		else
		{
			logTestResult( "Step Description: Verify Returns posting in Accounts Document"+"\n Expected Results :Returns posting should be present"+"\n Actual Results :Returns Posting is not present for Web shippment:" + strWebShipment,false);
			testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description: Verify Returns posting in Accounts Document"+"\n Expected Results :Returns posting should be present"+"\n Actual Results :Returns Posting is not present for Web shippment:" + strWebShipment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}	
	
	}
	
	if( arrPostingAmount[6].equals("ORD")  && ( !arrPostingAmount[0].equals("0") &&   !arrPostingAmount[0].equals(""))) {
	
	BlnGrossFound = isPostingPresent("Gross Sales");
	if(BlnGrossFound)  {
		logTestResult( "Step Description: Verify Gross Sales posting in Accounts Document"+"\n Expected Results :Gross Sales posting should be present"+"\n Actual Results :Gross Sales Posting is present for Web shippment: " + strWebShipment,true);
	}
				
		else	{	
			logTestResult( "Step Description: Verify Gross Sales posting in Accounts Document"+"\n Expected Results :Gross Sales posting should be present"+"\n Actual Results :Gross Sales Posting is not present for Web shippment:" + strWebShipment,false);
			testStatus=false;	
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description: Verify Gross Sales posting in Accounts Document"+"\n Expected Results :Gross Sales posting should be present"+"\n Actual Results :Gross Sales Posting is not present for Web shippment:" + strWebShipment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
				
	  }	
	
	}
	
	if( !arrPostingAmount[1].equals("0") &&   !arrPostingAmount[1].equals("")) {
	
	boolean BlnTaxFound = isPostingPresent("Sales Tax Collected");
		if(BlnTaxFound)  {
			logTestResult( "Step Description: Verify Sales Tax posting in Accounts Document"+"\n Expected Results :Sales Tax posting should be present"+"\n Actual Results :Sales Tax Posting is present for Web shippment: " + strWebShipment,true);
		}
		else		{
			logTestResult( "Step Description: Verify Sales Tax posting in Accounts Document"+"\n Expected Results :Sales Tax posting should be present"+"\n Actual Results :Sales Tax Posting is not present for Web shippment:" + strWebShipment,false);
			testStatus=false;
			try {
				//xlFicoOutputWriter.setCellValueInExcel(1, "SAP Order", strSAPOrderNumber);
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Sale Tax Collected amount not posted correctly for the follwing order\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
				
	  }	
	
	}
	

	boolean blnMonogram = sapfunctions.IsMonogramInBlueShipment(arrBMOrderDetails,strWebShipment);
	//intRowCount =  objGrid.rowCount;
	if(blnMonogram) {
			BlnMonoFound = isPostingPresent("Monogramming Expense");
		if(BlnMonoFound ) {
			logTestResult( "Step Description: Verify Monogram posting in Accounts Document"+"\n Expected Results :Monogram posting should be present"+"\n Actual Results :Monogram Posting is present for Web shippment: " + strWebShipment,true);
		}
		else{
			logTestResult( "Step Description: Verify Monogram posting in Accounts Document"+"\n Expected Results :Monogram posting should be present"+"\n Actual Results :Monogram Posting is not present for Web shippment:" + strWebShipment,false);
			testStatus=false;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Monogram Posting is not present for Web shippment for the follwing order\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

							
		}	
	
	}
	
	boolean blnGiftBox = sapfunctions.IsGiftBoxInBlueShipment(arrBMOrderDetails,strWebShipment);
	if(blnGiftBox) {
		blnGiftBoxFound = isPostingPresent("Gift Box Income") ;
		
		if(blnGiftBoxFound)  {
			logTestResult( "Step Description: Verify Gift Box posting in Accounts Document"+"\n Expected Results :Gift Box posting should be present"+"\n Actual Results :Gift Box Posting is present for Web shippment: " + strWebShipment,true);
		}
		else {
			logTestResult( "Step Description: Verify Gift Box posting in Accounts Document"+"\n Expected Results :Gift Box posting should be present"+"\n Actual Results :Gift Box Posting is not present for Web shippment:" + strWebShipment,false);
			testStatus=false;	
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description: Verify Gift Box posting in Accounts Document"+"\n Expected Results :Gift Box posting should be present"+"\n Actual Results :Gift Box Posting is not present for Web shippment:" + strWebShipment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}	
	
	}
	
	String intShippingAmt = arrPostingAmount[2]; 
	if(intShippingAmt!=null) {
		

	if (!intShippingAmt.equals("") &&  Integer.parseInt(intShippingAmt) != 0) {
		boolean blnShippingPost = isPostingPresent("Cust. Deliv. - Incom") ;	
		if (blnShippingPost)  {
			logTestResult( "Step Description: Verify Shiping Charges posting in Accounts Document"+"\n Expected Results :Shiping Charges posting should be present"+"\n Actual Results :Shiping Charges Posting is present for Web shippment: "+ strWebShipment,true);
		}	
		else {
			logTestResult( "Step Description: Verify Shiping Charges posting in Accounts Document"+"\n Expected Results :Shiping Charges posting should be present"+"\n Actual Results :Shiping Charges Posting is not present for Web shippment:" + strWebShipment,false)	;
			try {
				xlRowno++;
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "External Order number", arrBMOrderDetails[0][0]);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Web shipment Number", strWebShipment);
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "SAP Status", "FAIL");
				xlFicoOutputWriter.setCellValueInExcel(xlRowno, "Error Log", "Step Description: Verify Shiping Charges posting in Accounts Document"+"\n Expected Results :Shiping Charges posting should be present"+"\n Actual Results :Shiping Charges Posting is not present for Web shippment:" + strWebShipment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
	
	}
	}
	
	}
	

public String[] getPostingAmounts(String strWebShipment,String [][] arrBMOrderDetails) 
{
	
	String  arrPostingAmounts[] =new String[7];
	
	double intGrossSaleAmt=0;
	double intGrossTaxAmt=0;
	String strExternalNumber="";
	double  intGiftCardLaibility;
	intGiftCardLaibility=0;
	double intMonoService =0;
	double strTotalItemCost = 0;
	double  strItemTotalDiscount =0;
	String intShippingAmt="0";
	
	 String strOrderType =  arrBMOrderDetails[0][42].trim().toUpperCase();
		
	 strExternalNumber=arrBMOrderDetails[0][47];
	for(int Iterator = 0 ;Iterator<arrBMOrderDetails.length;Iterator++) {
		if( strWebShipment.equals(arrBMOrderDetails[Iterator][41])) {
	
			strOrderType = arrBMOrderDetails[Iterator][42].trim().toUpperCase();
		
			if ((arrBMOrderDetails[Iterator][10].equals("V")  && arrBMOrderDetails[Iterator][10].equals("R") && arrBMOrderDetails[Iterator][10].equals("EGV")) && (strOrderType.equals("RET") && arrBMOrderDetails[Iterator][10].equals("S") || arrBMOrderDetails[Iterator][10].equals("RM"))) {
			if (arrBMOrderDetails[Iterator][6].equals(dbReader.getProperty("MonogramService")) &&  !arrBMOrderDetails[Iterator][6].equals(dbReader.getProperty("ClassicGiftCard").split(",")[0]) && !arrBMOrderDetails[Iterator][6].equals(dbReader.getProperty("ClassicGiftCard").split(",")[1])) {
				intGrossSaleAmt = 	(intGrossSaleAmt) +	Double.parseDouble(arrBMOrderDetails[Iterator][7]);
			}	
			
			if (arrBMOrderDetails[Iterator][6].equals(dbReader.getProperty("ClassicGiftCard").split(",")[0]) || arrBMOrderDetails[Iterator][6].equals(dbReader.getProperty("ClassicGiftCard").split(",")[1])) {
				intGiftCardLaibility= intGiftCardLaibility +	Double.parseDouble(arrBMOrderDetails[Iterator][7]);
			}
			
			if (arrBMOrderDetails[Iterator][7].equals(dbReader.getProperty("MonogramService"))) {
				intMonoService = intMonoService  +	Double.parseDouble(arrBMOrderDetails[Iterator][7]);
			}
			intGrossTaxAmt = 	(intGrossTaxAmt )+	Double.parseDouble(arrBMOrderDetails[Iterator][8]);
			intShippingAmt=arrBMOrderDetails[Iterator][44];

			
			String strItemDiscount = arrBMOrderDetails[Iterator][50];
			if(strItemDiscount!=null || !strItemDiscount.isEmpty() ) {
				if( Double.parseDouble(arrBMOrderDetails[Iterator][7]) != 0) {
					Double dblItemDiscount = Double.parseDouble(arrBMOrderDetails[Iterator][50]);
					strItemTotalDiscount = strItemTotalDiscount + dblItemDiscount;	
				}				
			}
			
			}
		}
		
	strTotalItemCost = strTotalItemCost + Double.parseDouble(arrBMOrderDetails[Iterator][7]);
	}
	
	//Add Discount Amount to Gross Sales
	String  strDiscountAmoumt = arrBMOrderDetails[0][23];
	if (strDiscountAmoumt!=null || strDiscountAmoumt.isEmpty()) {
		double dblDiscountAmoumt = Double.parseDouble(strDiscountAmoumt);
		double strShipmentDiscountAmount =  Math.round((intGrossSaleAmt/strTotalItemCost) * dblDiscountAmoumt);  
		intGrossSaleAmt = intGrossSaleAmt + strShipmentDiscountAmount;
	}
	
	//Add Employee discount to Gross Sales
	String strEmployeeDiscount = arrBMOrderDetails[0][48];
	if ((strEmployeeDiscount!=null))
	{
	if ((strEmployeeDiscount!=null) || strEmployeeDiscount.isEmpty()) {
		double dblEmployeeDiscount = Double.parseDouble(strEmployeeDiscount.trim());
		double strEmpDiscountAmount = Math.round((intGrossSaleAmt/strTotalItemCost) * dblEmployeeDiscount);
		intGrossSaleAmt = intGrossSaleAmt + strEmpDiscountAmount;
	}
	}
		
	//Add Item Level discounts
	intGrossSaleAmt = intGrossSaleAmt + strItemTotalDiscount;
	
	arrPostingAmounts[0] = String.valueOf(intGrossSaleAmt); 
	arrPostingAmounts[1] = String.valueOf(intGrossTaxAmt); 
	arrPostingAmounts[2] = intShippingAmt;
	arrPostingAmounts[3] = strExternalNumber;
	arrPostingAmounts[5] = String.valueOf(intMonoService);
	arrPostingAmounts[6] = strOrderType;
    return  arrPostingAmounts;
	
}	
	
}

