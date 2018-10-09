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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;
public class SAPApplicationFunctions extends SapValidationHelper {
	private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
	public Map<String, String> discGLpostings = new HashMap<>();
	
	boolean gblnConsolidatePlanShipment;
	String gstrDistributeCode ="";
	String gstrDistChannel ="";
	String gstrOrgCode ="";
	String gstrOrgCodeName ="";
	String gstrProfitCenter="";
	String gstrShipmentAmount="";
	String gstrCountryCode="";
	String gstrBMStoreNumber="";
	String gstrStoreNumber="";
	String gstrOrdType = "";
	String gstrBrand ="";
	boolean testStatus=true;
    public SAPApplicationFunctions() {

    	 
    }
    
    public boolean VerifyWebShipmentInDirectOrdersScreen(int intRowCount, String[][] arrBMOrderDetails, String strOrderNumber)
    {

    boolean VerifyWebShipmentInDirectOrdersScreen;
	Map<String, String> dictSAPWebShipment = new HashMap<>();
	Map<String, String> dictBMWebShipment = new HashMap<>();
	//Retrieve all the web shipment numbers from SAP into a dictionary object
	for(int rownum= 0;rownum< intRowCount-1;rownum++) {
		String strSAPWebShipment = grid_sapguiGridViewCtrl1().getCellValue(rownum,"BSTKD_M");
		dictSAPWebShipment.put(strSAPWebShipment,strSAPWebShipment);
	}
	
	//Retrieve all the web shipment numbers from Blue Martini into a dictionary object
	for(int i = 0 ;i<arrBMOrderDetails.length;i++) {
		String strBMWebShipment = arrBMOrderDetails[i][41].trim().toUpperCase();
		String strItemStatus = arrBMOrderDetails[i][10].trim().toUpperCase();
		String strOrderType = arrBMOrderDetails[i][42].trim().toUpperCase();
		if ((strOrderType.equals("ORD")) && (strItemStatus.equals("V") || strItemStatus.equals("T") || strItemStatus.equals("EGV") || strItemStatus.equals("R")) || ((strOrderType.equals("RET")) && (strItemStatus.equals("S") || strItemStatus.equals("RM")))) {
			if(!dictBMWebShipment.containsKey(strBMWebShipment)) 
				dictBMWebShipment.put(strBMWebShipment,strBMWebShipment);
			
		}
	}
		
	//Verify all web shipment numbers are same between SAP and Blue Martini
	if( dictSAPWebShipment.size() == dictBMWebShipment.size())  {
		Iterator<Map.Entry<String, String>> arrBMKeys = dictBMWebShipment.entrySet().iterator();
		    while(arrBMKeys.hasNext()) {
		    	Map.Entry<String, String> mentry = arrBMKeys.next();
				String strBMKey = (String) mentry.getKey();
				if(!dictSAPWebShipment.containsKey(strBMKey)) {
					logTestResult("Step Description :Verify Web shipment numbers match between Blue Martini and SAP"+ "\n Expected Results :Web shipment numbers should match"+ "\n Actual Results :Web shipment numbers are not matching between Blue Martini and SAP for the order:" + strOrderNumber + ". Example: " + strBMKey + " exists in Blue matrini, but does not exist in SAP",false);
					VerifyWebShipmentInDirectOrdersScreen = false;
					testStatus=false;
					return false;
				}
			}
	}
	else 
	{
		String strBMKeys = "";
        Iterator<Map.Entry<String, String>> arrBMKeys = dictBMWebShipment.entrySet().iterator();
        while(arrBMKeys.hasNext()) {
           Map.Entry<String, String> mentry = arrBMKeys.next();
           strBMKeys = strBMKeys + (String) mentry.getKey() + ","	;

        }
        
		String strSAPKeys = "";
        Iterator<Map.Entry<String, String>> arrSAPKeys = dictSAPWebShipment.entrySet().iterator();
        while(arrSAPKeys.hasNext()) {
           Map.Entry<String, String> mentry = arrSAPKeys.next();
           strSAPKeys = strSAPKeys + (String) mentry.getKey() + ","	;

        }
		
	
		if(strBMKeys.equals("") || strSAPKeys.equals( "")) {
			logTestResult("Step Description :Retrieve web shipment numbers from Blue Martini and SAP"+ "\n Expected Results :Web shipment numbers should be retrieved"+ "\n Actual Results :Web shipment numbwers from either Blue Martini or SAP is blank. Web shipment numbers from Blue Martini:" + strBMKeys + ", We shipment numbers from SAP:" + strSAPKeys, false);
			VerifyWebShipmentInDirectOrdersScreen = false;
			testStatus=false;
			return false;
		}
		
		strBMKeys =  strBMKeys.substring(0, strBMKeys.length()-1);
		strSAPKeys = strSAPKeys.substring(0, strSAPKeys.length()-1);	
		
		logTestResult("Step Description :Verify Web shipment numbers match between Blue Martini and SAP"+ "\n Expected Results :Web shipment numbers should match"+ "\n Actual Results :Web shipment numbers are not matching between Blue Martini and SAP for the order:" + strOrderNumber +". Web shipment numbers in Blue Martini:" + strBMKeys + ". Web shipment numbers in SAP:" + strSAPKeys,false);
		VerifyWebShipmentInDirectOrdersScreen = false;
		testStatus=false;
		return false;
	}
	
	VerifyWebShipmentInDirectOrdersScreen = true;
	logTestResult("Step Description :Verify Web shipment numbers match between Blue Martini and SAP"+ "\n Expected Results :Web shipment numbers should match"+ "\n Actual Results :Web shipment numbers are matching between Blue Martini and SAP",true);
	return VerifyWebShipmentInDirectOrdersScreen;
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
				logTestResult( "Step Description :Verify Order details in row: " +  Integer.toString(intGridCounter) + " of Direct Orders screen"+ "\n Expected Results :SAP Order details should be displayed" + "\n Actual Results : One or more columns does not display values in row: " +  Integer.toString(intGridCounter) + ". Values retrieved are...OrdType:" + strSAPOrderType + ", SAP Order:" + strSAPOrderNumber + ", Web External Order:" + strWebExternalOrder + ", Web Shipment#" + 	strWebShipmentNumber + ", Billing:" + strBillingNumber + ", Accounting#"+ strAccountingNumber + ", Sales Org:" + strSalesOrg + ", Division:" + strDivision + ", Dist Channel:" + strDistributionChannel,false);
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
				testStatus=false;
				return blnfuncVerifyOrderDetailsInDirectOrdersScreen;
			}
			
			if (!strWebExternalOrder.trim().equalsIgnoreCase(strOrderNumber)) {
				logTestResult( "Step Description :Verify Web External Order number in row:  " + intGridCounter + "\n Expected Results :Web External Order field should display the input order number:"+ strOrderNumber + "\n Actual Results :Web External Order field is not displaying the input order number: " + strOrderNumber + ", This field is displaying the value: " + strWebExternalOrder,false);
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
				testStatus=false;
			}
			//Verify Payment card is displayed for domestic Orders
			if (arrBMOrderDetails[0][43].equals("US") && strPaymentCard.isEmpty()) {
				logTestResult( "Step Description :Verify payment card is displayed for the SAP order: " + strSAPOrderNumber + "\n Expected Results :Payment card should be displayed"+ "\n Actual Results :Payment card is not displayed for the SAP order:" + strSAPOrderNumber,false);
				blnfuncVerifyOrderDetailsInDirectOrdersScreen = false;
				testStatus=false;
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

			logTestResult( "Step Description :Verify SAP order:" + strSAPOrderNumber + " details displayed on Direct Orders screen"+ "\n Expected Results : Details of SAP Order:" + strSAPOrderNumber + " should be displayed as expected"+"\n Actual Results :Details of SAP Order:" + strSAPOrderNumber + " are displayed as expected",true);
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
					return false;
				}	
				else
				{
				logTestResult( "logTestResult(Verify correct Order Type is displayed for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results : Correct Order type should be displayed"+ "\n Actual Results : Correct Ord Type is displayed for the SAP Order: " + strSAPOrderNumber + ". Order type displayed in SAP: " + strSAPOrderType + ", where as Blue Martini order type is:" + strOrderType + " and it is shipped from:" + strShippedFrom , true);
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
					break;
				}
			 }
		 }
	 }
		
	blnVerifyDocumentDisplayonDirectOrdersScreen = true;
	return blnVerifyDocumentDisplayonDirectOrdersScreen;

}
   
    public boolean verifySAPOrderOverview(String strSAPOrderNumber,int intRowNumber,String [][] arrBMOrderDetails, String strWebShipmentNumber, String strOrderNumber, String[][] arrBMBillingDetails) 
    {
	//On Error Resume next
	//Err.Clear
    System.out.println("verifySAPOrderOverview");
	boolean verifySAPOrderOverview=false;
	//Retrieve Order Type into the global variable gstrOrdType
	gstrOrdType = grid_sapguiGridViewCtrl1().getCellValue(intRowNumber,"AUART");
	//populate pre-requisite data
	
	//Click on SAP Order && navigate to SAP Order Overview screen
	//SessionObj.SAPGuiGrid("micclass:=SAPGuiGrid","name:=shell","index:="&intIndex).ActivateCell intRowNumber,"SAP Order"
	grid_sapguiGridViewCtrl1().setCurrentCellColumn("VBELN");
	grid_sapguiGridViewCtrl1().doubleClick(intRowNumber, "VBELN");
	logTestResult("clicked on Column SAP Order # on row :" + intRowNumber , true);
	PopulatePreReqData(strOrderNumber,arrBMOrderDetails,strWebShipmentNumber);
	
	//Verify the items are displayed as expected on SAP Order Overview screen
	boolean blnStatus = false;
	SAPGuiTableControlTestObject table= table_allItems2();
	blnStatus  = verifyItemsOnSAPOrderOverview(strSAPOrderNumber,strWebShipmentNumber, strOrderNumber,table,false);
	if(!blnStatus) {
		
		logTestResult("Step Description :Verify articles are matching between SAP order: " + strSAPOrderNumber + " and corresponding Blue martini web shipment: " + strWebShipmentNumber+ "\n Expected Results :Articles should match between SAP && Blue Martini"+ "\n Actual Results :Failure in verifying Articles between blue martini and SAP",false);
		verifySAPOrderOverview = false;
		testStatus=false;
	}
	
	//Verify if correct shipping address is displayed
	blnStatus = false;
	blnStatus = verifyShippingAddress(strSAPOrderNumber,arrBMOrderDetails,strWebShipmentNumber, strOrderNumber);
	if(!blnStatus) {
		logTestResult("Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+"\n Actual Results :Failure in 'Ship- To Party' address verification",false);
		verifySAPOrderOverview = false;
		testStatus=false;
	}
	
	//Verify if correct shipping address in partners tab  is displayed
	blnStatus = false;
	button_displayDocHeaderDetails().click();
	blnStatus = verifyShippingAddressInPartnersTab(strSAPOrderNumber,arrBMOrderDetails,strWebShipmentNumber, strOrderNumber);
	if(!blnStatus) {
		logTestResult("Step Description :SAP Order Overview Document Partners Tab - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+"\n Actual Results :Failure in 'Ship- To Party' address verification",false);
		verifySAPOrderOverview = false;
		testStatus=false;
	}
	
//'	'Verify if correct credit card is displayed
	blnStatus = false;
	blnStatus = VerifyCreditCard_SAPOrderScreen(strSAPOrderNumber, strOrderNumber, arrBMBillingDetails, arrBMOrderDetails,"","",false);
	if(!blnStatus) {
		logTestResult( "SAP Order Overview Document - Verify payment type && corresponding auth number between SAP && Blue Martini for the SAP Order: " + strSAPOrderNumber+ "Payment type && auth number should match"+"Payment type && auth number are not matching between blue martini && SAP",false);
		verifySAPOrderOverview = false;
		testStatus=false;
	}
	
	//Verify Br&& && order type
	blnStatus = false;
	blnStatus = verifyBrandAndOrderType(strSAPOrderNumber, strOrderNumber, arrBMOrderDetails);
	if(!blnStatus) {
		logTestResult("Step Description :SAP Order Overview Document - Verify sales area code and corresponding description"+ "\n Expected Results : Correct sales are code should be displayed" + "\n Actual Results :Either sales area code or corresponding description is wrong.Please verify", false);
		verifySAPOrderOverview = false;	
		testStatus=false;
	}
	
	
	//Verify Site Value
	blnStatus = false;
	blnStatus = verifySiteValue(strSAPOrderNumber, strWebShipmentNumber, arrBMOrderDetails);
	if(!blnStatus) {
		logTestResult("Step Description :SAP Order Overview Document - Verify Site value"+ "\n Expected Results :Site value should be displayed based on the shipping location from Blue Martini"+"\n Actual Results :Incorrect Site value is displayed",false);
		verifySAPOrderOverview = false;	
		testStatus=false;
	}
	
	//Verify AuthAmt Value
	blnStatus = false;
	button_displayDocHeaderDetails().click();
	blnStatus = VerifyAuthAmtInPaymentsandConditionScreen(strSAPOrderNumber, strOrderNumber, arrBMBillingDetails, arrBMOrderDetails);
	if(!blnStatus) {
		logTestResult("Step Description :SAP Order Overview Document - VerifyAuthAmtInPaymentsandConditionScreen"+ "\n Expected Results :Auth value should be displayed  from Blue Martini"+"\n Actual Results :Incorrect auth value is displayed",false);
		verifySAPOrderOverview = false;	
		testStatus=false;
	}
	
	
	verifySAPOrderOverview=true;
	return verifySAPOrderOverview;
	}
    
    
    public boolean verifyItemsOnSAPOrderOverview(String strSAPOrderNumber,String strWebShipmentNumber,String strOrderNumber,SAPGuiTableControlTestObject table, boolean blnScreen)
    {
    	boolean verifyItemsOnSAPOrderOverview =false;
    	System.out.println("verifyItemsOnSAPOrderOverview");
    //Retrieve article details from SAP
    String[][]arrSAPArticles = funcRetrieveArticlesFromSAP(strSAPOrderNumber,table);

    if (arrSAPArticles==null ||arrSAPArticles.length==0){
    logTestResult("Step Description :Retrieve all articles && coreresponding quantity from SAP Order details screen"+ "\n Expected Results :All the articles should be retrieved"+ "\n Actual Results :Unable to retrieve articles && coreresponding quantity from SAP Order details screen",false);
    testStatus=false;
	return false;
    }

    //Retrieve article details from Blue Martini
    String[][]arrBMArticles = funcRetrieveArticlesFromBlueMartini(strSAPOrderNumber, strWebShipmentNumber, strOrderNumber);
    if (arrBMArticles==null ||arrBMArticles.length==0){
    	logTestResult("Step Description :Retrieve all articles && coreresponding quantity from Blue Martini"+ "\n Expected Results :All the articles should be retrieved"+ "\n Actual Results :Unable to retrieve articles && coreresponding quantity from Blue Martini",false);
        testStatus=false;
		return false;
        }


    //billingscreen
    //boolean blnScreen =true;
    int sapArticlesLen=0;
    int bmArticlesLen=0;
    //if(blnScreen) sapArticlesLen++;
    
    //Replace Monogram && Gift Card BM SKUs with SAP numbers
    for( int counter = 0;counter <arrBMArticles.length; counter++) {
    	if(arrBMArticles[counter][0].equals("99101566087"))
    	{
    		arrBMArticles[counter][0] = "000000000999999504";
    		if(blnScreen) sapArticlesLen++;
    	}
    	else if (arrBMArticles[counter][0].equals("99101566083")) {
    			arrBMArticles[counter][0] = "000000000999999500";
    			if(blnScreen) sapArticlesLen++;
    	}
    	else if (arrBMArticles[counter][0].equals("99101566093")) {
    			arrBMArticles[counter][0] = "000000000999999510";	
    			if(blnScreen) sapArticlesLen++;
    	}
    	else if (arrBMArticles[counter][0].equals("99101566091")) {
    			arrBMArticles[counter][0] = "000000000999999508";
    			if(blnScreen) sapArticlesLen++;
    	}
    	else if (arrBMArticles[counter][0].equals("99101566092" )) {
    			arrBMArticles[counter][0] = "000000000999999520";	
    			if(blnScreen) sapArticlesLen++;
    	}
    	
    }

    sapArticlesLen = sapArticlesLen + arrSAPArticles.length;
    String strBMArticleQty = null;
    //Compare articles between Blue Martini && SAP Order Details screen
    	//if(arrSAPArticles.length == arrBMArticles.length) {
         if(sapArticlesLen == arrBMArticles.length) {
    		for(int i = 0 ;i<arrSAPArticles.length;i++) {
    			String strSAPArticle = arrSAPArticles[i][0];
    			String  strSAPArticleQty = arrSAPArticles[i][1];
    			
    			boolean blnArticleFound = false;
    			boolean blnQtyMatch = false;
    			for(int j = 0; j<arrBMArticles.length;j++) {
    				String strBMArticle = arrBMArticles[j][0];
    				strBMArticleQty = arrBMArticles[j][1];
    				if(blnScreen) {if(strBMArticle.substring(0, 8).equals("00000000"))  continue;   }
    				if(strSAPArticle.trim().toUpperCase().equals( strBMArticle.trim().toUpperCase()) ) {
    					blnArticleFound = true;
    					if(blnArticleFound) {
    						if(strSAPArticleQty.equals(strBMArticleQty))
    							blnQtyMatch = true	;
    						
    						break;
    					}
    				}
    			}
    	
    			if( blnArticleFound &&  (!blnQtyMatch)) {
    				logTestResult("Step Description :Verify articles are matching between SAP order: " + strSAPOrderNumber + " && corresponding Blue martini web shipment: " + strWebShipmentNumber+ "\n Expected Results :Articles should match between SAP && Blue Martini"+"\n Actual Results :Quantity is not matching for the atticle:" + strSAPArticle + ". Quantity in SAP is:" + strSAPArticleQty + ", Quantity in Blue Martini is:" + strBMArticleQty ,false); 
    				verifyItemsOnSAPOrderOverview = false;
    				return false;
    			}
    			else if (!blnArticleFound) {
    			  logTestResult( "Step Description :Verify articles are matching between SAP order: " + strSAPOrderNumber + " && corresponding Blue martini web shipment: " + strWebShipmentNumber+ "\n Expected Results :Articles should match between SAP && Blue Martini"+ "\n Actual Results :Atticle:" + strSAPArticle + " is available in SAP order:" +  strSAPOrderNumber + " but not available in corresponding web shipment:" + strWebShipmentNumber + " in blue martini",true);
    			    verifyItemsOnSAPOrderOverview = false;
    			    return false;	
    			}
    		}	
    		logTestResult( "Step Description :Verify articles are matching between SAP order: " + strSAPOrderNumber + " && corresponding Blue martini web shipment: " + strWebShipmentNumber+ "\n Expected Results :Articles should match between SAP && Blue Martini"+ "\n Actual Results :Atticles are matching between SAP order:" + strSAPOrderNumber + " && corresponding Blue Martini web shipment:" + strWebShipmentNumber,true);	
    		verifyItemsOnSAPOrderOverview = true;
    	}
    	else{
    	logTestResult( "Step Description :Verify articles are matching between SAP order: " + strSAPOrderNumber + " && corresponding Blue martini web shipment: " + strWebShipmentNumber+ "\n Expected Results :Articles should match between SAP && Blue Martini"+ "\n Actual Results :Atticles are not matching between SAP order:" + strSAPOrderNumber + " && corresponding Blue Martini web shipment:" + strWebShipmentNumber,false);
    	verifyItemsOnSAPOrderOverview = false;
		testStatus=false;
    	return false;
    	}
     return verifyItemsOnSAPOrderOverview;
   }

    public String[][] funcRetrieveArticlesFromSAP(String strSAPOrderNumber,SAPGuiTableControlTestObject table)
    {
    	String[][] arrSAPArticles;

    	System.out.println("funcRetrieveArticlesFromSAP");
        //Retrieve all articles from SAP into a hashmap object

     	Map<String, Integer> dictSAPArticles = new HashMap<>();

    	ITestDataTable itemTable = (ITestDataTable) (table.getTestData("list"));
    	int intRowCount = itemTable.getRowCount();
    	int articleCol = 0;
	     for (int col=0; col < itemTable.getColumnCount();++col)
	      {
	    	 if (itemTable.getColumnHeader(col).equals("Article")) {
	    		 articleCol = col;
	    	    break;
	    	 }
	          // Print out values of cells at (row,col) coordinates
	          System.out.println ( itemTable.getColumnHeader(col) + ":" );
		 }
    	
    	int i = 0;
    	String strArticle;
    	do {
    		
    		strArticle =(String) itemTable.getCell(i,articleCol);
    		System.out.println ( strArticle + ":" );
    		if( !strArticle.equals("")) {
    			if(!dictSAPArticles.containsKey(strArticle)) 
    				dictSAPArticles.put( strArticle,1);
    			else {	
    				int intCount = (dictSAPArticles.get(strArticle))	+ 1;	
    				dictSAPArticles.remove(strArticle);
    				dictSAPArticles.put(strArticle,intCount);
    			   }
    			
    		}

    		i = i+ 1;
    	} while(i<=intRowCount-1 && !strArticle.equals(""));

    	arrSAPArticles= new String[(dictSAPArticles.size())][2];
    	
    	i=0;
    	//Create a 2D array from the dictionary
        //Set set = dictSAPArticles.entrySet();
       // Iterator iterator = set.iterator();
        Iterator<Map.Entry<String, Integer>> entries = dictSAPArticles.entrySet().iterator();
        while(entries.hasNext()) {
           Map.Entry<String, Integer> mentry = entries.next();
   		   arrSAPArticles[i][0] = (String) mentry.getKey();
   		   arrSAPArticles[i][1] =  mentry.getValue().toString();	
   		   i= i + 1;
        }
    	
    return  arrSAPArticles;
    	
    }


    public String[][] funcRetrieveArticlesFromBlueMartini(String strSAPOrderNumber, String strWebShipmentNumber, String strOrderNumber) 
    {
   // On Error Resume Next
    String strEnvironment;
    String[][] arrArticleDetails = null;
    System.out.println("funcRetrieveArticlesFromBlueMartini");
    	//Retrieve blue martini environment for database connection
    	strEnvironment = dbReader.getProperty("BMEnvironment");
    	
   
    	//Create sql query for order retrieval from Blue Martini
		//Get Bluemartini order details from the database.
		BMOrderDetails bmOrderDetails = null;
		bmOrderDetails = new BMOrderDetails();	
		String strRetrievalType ="";
		if(!gblnConsolidatePlanShipment)
				strRetrievalType = "Article.webshipment";
    	else 
    		strRetrievalType = "Article";
		
    	String strSqlQuery = bmOrderDetails.Create_Sql_Query_For_BM_OrderDetail_Retrieval(strEnvironment, strOrderNumber, strRetrievalType, strWebShipmentNumber);
    	logTestResult("Create Query RetrieveArticlesr:" + strSqlQuery ,true);
    	try {
        //Execute SQL query
		//Establish DB connection
		DatabaseReader databaseReader = DatabaseReader.getDatabaseReader();
		Connection conn = databaseReader.getConnectionToDatabase();
		if (conn != null) {
			//logger.info("DB connection is successful...");
		}
		//Statement stmt = databaseReader.createTheStatement(conn);
		Statement stmt = conn.createStatement(
			    ResultSet.TYPE_SCROLL_INSENSITIVE,
			    ResultSet.CONCUR_READ_ONLY
			);

		//Retrieve values from database
		ResultSet objDB_RS = stmt.executeQuery(strSqlQuery);

    	
    	if (!objDB_RS.next())
    	{
    		logTestResult("Step Description :Retrieve articles && quantity for the Order: " + strOrderNumber + ", with web shipment:" + strWebShipmentNumber + " from Blue Martini"+ "\n Expected Results :Article details should be retrieved from BM database"+"\n Actual Results :Order: " + strOrderNumber + " with web shipment:" + strWebShipmentNumber + " details does not exist in Blue Martini database. Please verify",false);
    		testStatus=false;
			return arrArticleDetails;
    	}
    	else
    		logTestResult("Step Description :Retrieve articles && quantity for the Order: " + strOrderNumber + ", with web shipment:" + strWebShipmentNumber + " from Blue Martini"+ "\n Expected Results :Article details should be retrieved from BM database"+ "\n Actual Results :Order: " + strOrderNumber + " with web shipment:" + strWebShipmentNumber + " details are retrieved from Blue Martini database",true);
    	
    	
	
    	
    	//Create a 2D array
    	objDB_RS.last();
	    int intItemLineCount = objDB_RS.getRow();
    
    	arrArticleDetails= new String[intItemLineCount][4];
    	objDB_RS.beforeFirst();
    	int i = 1;
    	while(objDB_RS.next()) {
    		arrArticleDetails[i-1][0] = objDB_RS.getString(1);
    		arrArticleDetails[i-1][1] = objDB_RS.getString(2);
    		arrArticleDetails[i-1][2] = objDB_RS.getString(3);
    		arrArticleDetails[i-1][3] = objDB_RS.getString(4);
    		//arrArticleDetails[i-1][4] = objDB_RS.getString(4);
    		i = i + 1;
    	}
    	
    return  arrArticleDetails;
    	}
    	 catch (Exception e) {
 			//logger.error("Unable to run query for retrieving Start and End date the shipping method {}", carrierCode);
 			return null;
 		}

    }
    
   public boolean verifyShippingAddress(String strSAPOrderNumber,String[][] arrBMOrderDetails,String strWebShipmentNumber, String strOrderNumber)
    {
    	boolean VerifyShippingAddress = false;
    	String[] arrInternationalAddress =null;

       String strOrderType = arrBMOrderDetails[0][42];
       if(strOrderType.trim().toUpperCase().equals("ORD")) {
    	//Retrieve shipping address
    	String strSAPShippingAddress = (String) text_kuwevtxtpa().getProperty("Text");
    	strSAPShippingAddress =strSAPShippingAddress.substring(strSAPShippingAddress.indexOf("/")+1, strSAPShippingAddress.length()).trim().toUpperCase();
    	
    	//Retrieve shipping address for domestic orders
    	String strCountry = (arrBMOrderDetails[0][43]).trim().toUpperCase();
    	String strBrand = arrBMOrderDetails[0][45].trim().toUpperCase();
    	if(strCountry.equals( "US" ) || strBrand.equals( "FACTORY")) {
    		String strBMAddress = "";
    		for(int i = 0 ;i<arrBMOrderDetails.length;i++) {
    			String strPlanShipment = 	arrBMOrderDetails[i][41];
    			if(strPlanShipment.equals(strWebShipmentNumber)) {
    				strBMAddress = arrBMOrderDetails[i][13] + " / " + arrBMOrderDetails[i][16] + " " + arrBMOrderDetails[i][17] + " " + arrBMOrderDetails[i][20];
    				break;
    			}		
    		}
    		
    		if(strBMAddress.equals( "")) {
    			logTestResult( "Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :Unable to retrieve address from Blue Martini for the order:" + strOrderNumber + ", and the plan shipment:" + strWebShipmentNumber,false);
    			VerifyShippingAddress = false;
    			return VerifyShippingAddress ;
    		}
    		else
    			strBMAddress = strBMAddress.trim().toUpperCase();
    		
    		
    		//Compare Address
    		if(strSAPShippingAddress.equals(strBMAddress)) {
    			logTestResult( "Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :'Ship- To Party' address on SAP Order overview screen is matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address:" + strSAPShippingAddress,true);
    			VerifyShippingAddress = true;
    			return VerifyShippingAddress;
    		}
    		else
    		{
    			logTestResult("Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :'Ship- To Party' address on SAP Order overview screen is not matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address in SAP:" + strSAPShippingAddress + ", Address from Blue Martini:" +  strBMAddress,false); 
    			VerifyShippingAddress = false;
				testStatus=false;
    			return VerifyShippingAddress;
    		}
    	}
    	else {
    		arrInternationalAddress = RetrieveInternationalAddressFromBlueMartini(strOrderNumber);
    		
    		if(arrInternationalAddress.length==0) {
    			logTestResult( "Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+"\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :Unable to retrieve address from Blue Martini for the order:" + strOrderNumber + ", && the plan shipment:" + strWebShipmentNumber,false);	
    			VerifyShippingAddress = false;
    			return VerifyShippingAddress;
    		}
    		String strAddressLine1 = arrInternationalAddress[0].trim().toUpperCase();
    		String strAddressCity = arrInternationalAddress[3].trim().toUpperCase();
    		if(!strSAPShippingAddress.contains(strAddressLine1) || !strSAPShippingAddress.contains(strAddressCity) ) {
    			logTestResult( "Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :'Ship- To Party' address on SAP Order overview screen is not matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address in SAP:" + strSAPShippingAddress + ", Address line 1 from Blue Martini:" +  strAddressLine1 + ", Address City from Blue Martini:" + strAddressCity,false); 
    			VerifyShippingAddress = false;
				testStatus=false;
    			return VerifyShippingAddress;
    		}
    		else
    		{
    		logTestResult( "Step Description :SAP Order Overview Document - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+"\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+"\n Actual Results :'Ship- To Party' address on SAP Order overview screen is matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address:" + strSAPShippingAddress,true);		
    		VerifyShippingAddress = true;
    		return VerifyShippingAddress;
    		}
    	
    	}
    }

    VerifyShippingAddress = true;
    return VerifyShippingAddress;

}

    public String[] RetrieveInternationalAddressFromBlueMartini(String strOrderNumber) 
    {
    	
    	try {
    	String strEnvironment;
    	String[] RetrieveInternationalAddressFromBlueMartini;

    	//Retrieve blue martini environment for database connection
    	strEnvironment = dbReader.getProperty("BMEnvironment");	
   	 
     	//Create sql query for order retrieval from Blue Martini
 		//Get Bluemartini order details from the database.
 		BMOrderDetails bmOrderDetails = null;
 		bmOrderDetails = new BMOrderDetails();	
     	String strRetrievalType = "InternationalShippingAddress";
     	String strSqlQuery = bmOrderDetails.Create_Sql_Query_For_BM_OrderDetail_Retrieval(strEnvironment, strOrderNumber, strRetrievalType, "");
       	if(strSqlQuery.equals( "") || strSqlQuery.isEmpty()) {
    		logTestResult("Step Description :Retrieve shipping address for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :shipping address should be retrieved from BM database"+ "\n Actual Results :shipping address query is blank. Hence, unable to retrieve order details from BM Database",false);
    		testStatus=false;
			RetrieveInternationalAddressFromBlueMartini = null;
    		return 	RetrieveInternationalAddressFromBlueMartini;
       	}
         //Execute SQL query
 		//Establish DB connection
 		DatabaseReader databaseReader = DatabaseReader.getDatabaseReader();
 		Connection conn = databaseReader.getConnectionToDatabase();
 		if (conn != null) {
 			//logger.info("DB connection is successful...");
 		}
 		//Statement stmt = databaseReader.createTheStatement(conn);
 		Statement stmt = conn.createStatement(
 			    ResultSet.TYPE_SCROLL_INSENSITIVE,
 			    ResultSet.CONCUR_READ_ONLY
 			);

 		//Retrieve values from database
 		ResultSet objDB_RS = stmt.executeQuery(strSqlQuery);
    	
 	  	if (!objDB_RS.next()) {
    		logTestResult(  "Step Description :Retrieve shipping address for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :Shipping address should be retrieved from BM database"+ "\n Actual Results :Unable to retrieve shipping address of the Order: " + strOrderNumber + " from Blue Martini database. Please verify",false);
    		RetrieveInternationalAddressFromBlueMartini = null;
			testStatus=false;
    		return RetrieveInternationalAddressFromBlueMartini;
 	  	}
    	else
    	  logTestResult("Step Description :Retrieve shipping address for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :Shipping address should be retrieved from BM database"+ "\n Actual Results :Shipping address is retrieved for the Order: " + strOrderNumber + " from Blue Martini database",true);
    	
    	
    	//Create a  array
    	objDB_RS.first();
    	String strAddressLine1 = objDB_RS.getString(0);
    	String strAddressLine2 = objDB_RS.getString(1);
    	String strAddressLine3 = objDB_RS.getString(2);
    	String strAddressCity = objDB_RS.getString(3);
    	String strAddressStateCode = objDB_RS.getString(4);
    	String strAddressStateName = objDB_RS.getString(5);
    	String strAddressCountry = objDB_RS.getString(6);
    	String strAddressPinCode = objDB_RS.getString(7);
    
    	String []arrShippingAddres= new String[8];
    	objDB_RS.beforeFirst();
    	int i = 1;
    	if(objDB_RS.next()) {
    		arrShippingAddres[0] = strAddressLine1;
    		arrShippingAddres[1] = strAddressLine2;
    		arrShippingAddres[2] = strAddressLine3;
    		arrShippingAddres[3] = strAddressCity;
    		arrShippingAddres[4] = strAddressStateCode;
    		arrShippingAddres[5] = strAddressStateName;
    		arrShippingAddres[6] = strAddressCountry;
    		arrShippingAddres[7] = strAddressPinCode;
    	}
    	 	
    RetrieveInternationalAddressFromBlueMartini = arrShippingAddres;
    return RetrieveInternationalAddressFromBlueMartini;
    	}
    	
    	 catch (Exception e) {
 			//logger.error("Unable to run query for retrieving Start and End date the shipping method {}", carrierCode);
 			return null;
 		}
    
    }
    
public boolean verifyBrandAndOrderType(String strSAPOrderNumber, String strOrderNumber, String[][] arrBMOrderDetails)
{
	//Retrieve sales area code details 
	String strSalesAreaCode = (String) text_vbakvkorg().getProperty("Text");
	String strOrderTypeCode = (String) text_vbakvtweg().getProperty("Text");
	String strCompanyCode = (String) text_vbakspart().getProperty("Text");
	String strOrderTypeDescription = (String) text_rV45ATXT_VTRBER().getProperty("Text");
	
	
	//Retrieve br&& && order type from Blue Martini
	String strBrand = arrBMOrderDetails[0][45].trim().toUpperCase();
	String strOrderType = arrBMOrderDetails[0][22].trim().toUpperCase();
	//String strOrderCatrgory = arrBMOrderDetails[0][42].trim().toUpperCase();
	
//	'Define order type as "WEB" for return orders
//	If strOrderCatrgory = "RET") {
//		strOrderType = "WEB"	
//	End If
	
	String strExpSalesAreaCode = null;
	String strExpOrderTypeCode = null;
	String strExpCompanyCode = null;
	String strExpOrderTypeDescription = null;
	boolean VerifyBrandAndOrderType;
	
	if(strOrderType.equals( "WEB" )|| strOrderType.equals( "RTL")) {
			switch( strBrand) {
				case "JCREW" :
					strExpSalesAreaCode = "S200";
					strExpOrderTypeCode	= "I0";
					strExpCompanyCode = "JC";
					strExpOrderTypeDescription = "J.CREW SALES ORG, INTERNET, J.CREW DIVISION";	
					break;
				
				case "FACTORY":
					strExpSalesAreaCode = "S200";
					strExpOrderTypeCode	= "I4";
					strExpCompanyCode = "JC";
					strExpOrderTypeDescription = "J.CREW SALES ORG, INTERNET FACTORY, J.CREW DIVISION";
					break;
				
				case "MADEWELL":
					strExpSalesAreaCode = "S200";
					strExpOrderTypeCode	= "I6";
					strExpCompanyCode = "JC";
					strExpOrderTypeDescription = "J.CREW SALES ORG, INTERNET " + (char)34 + "M" + (char)34 + ", J.CREW DIVISION";
					break;
				
				default:
					logTestResult(  "Step Description :SAP Order Overview Document - Verify Sales area codes and corresponding description is correct"+ "\n Expected Results :Correct Sales area codes should be displayed"+ "\n Actual Results :Unable to verify sales area codes for the brand:" + strBrand ,false);		
				    VerifyBrandAndOrderType = false;
					testStatus=false;
					return VerifyBrandAndOrderType;
			}
	}			
	else if(strOrderType.equals("CALL") || strOrderType.equals("CON")) {
			switch(strBrand) {
				case "JCREW":
					strExpSalesAreaCode = "S200";
					strExpOrderTypeCode	= "M1";
					strExpCompanyCode = "JC";
					strExpOrderTypeDescription = "J.CREW SALES ORG, MAIL ORDER, J.CREW DIVISION";
					break;
				
				case "FACTORY":
					strExpSalesAreaCode = "S200";
					strExpOrderTypeCode	= "I4";
					strExpCompanyCode = "JC";
					strExpOrderTypeDescription = "J.CREW SALES ORG, INTERNET FACTORY, J.CREW DIVISION";
					break;
				
				
				case "MADEWELL":
					strExpSalesAreaCode = "S200";
					strExpOrderTypeCode	= "M6";
					strExpCompanyCode = "JC";
					strExpOrderTypeDescription = "J.CREW SALES ORG, MAIL ORDER " + (char)34 + "M" + (char)34 + ", J.CREW DIVISION";
					break;
				
				default:
					logTestResult(  "Step Description :SAP Order Overview Document - Verify Sales area codes and corresponding description is correct"+ "\n Expected Results :Correct Sales area codes should be displayed"+ "\n Actual Results :Unable to verify sales area codes for the brand" + strBrand,false);		
					 VerifyBrandAndOrderType = false;
					 testStatus=false;
					return VerifyBrandAndOrderType;				
			}
	}
	else
	{
	    logTestResult(  "SAP Order Overview Document - Verify Sales area codes and corresponding description is correct"+ "\n Expected Results :Correct Sales area codes should be displayed"+ "\n Actual Results :Automation does not have expected sales areac codes for the order type:" + strOrderType + ". Please verify this manually.",true);
	    VerifyBrandAndOrderType = true;
		//return VerifyBrandAndOrderType;
	}
	//Compare sales area codes
	if(strSalesAreaCode.equalsIgnoreCase(strExpSalesAreaCode) && strOrderTypeCode.equalsIgnoreCase(strExpOrderTypeCode) && strCompanyCode.equalsIgnoreCase(strExpCompanyCode))
	{
		if(strOrderTypeDescription.equalsIgnoreCase(strExpOrderTypeDescription)) {
			logTestResult( "Step Description :SAP Order Overview Document - Verify Sales area codes and corresponding description is correct"+ "\n Expected Results :Sales area codes should be displayed as:" + strExpSalesAreaCode + ", " + strExpOrderTypeCode + ", " + strExpCompanyCode + ". Sales area description should be displayed as:" +strExpOrderTypeDescription+ "\n Actual Results :Sales area codes are displayed as:" + strExpSalesAreaCode + ", " + strExpOrderTypeCode + ", " + strExpCompanyCode + ". Sales area description is displayed as:" + strExpOrderTypeDescription,true); 
			VerifyBrandAndOrderType = true;
			return VerifyBrandAndOrderType;
			}
		else {
			logTestResult("Step Description :SAP Order Overview Document - Verify Sales area codes and corresponding description is correct"+ "\n Expected Results :Sales area codes should be displayed as:" + strExpSalesAreaCode + ", " + strExpOrderTypeCode + ", " + strExpCompanyCode + ". Sales area description should be displayed as:" + strExpOrderTypeDescription+ "\n Actual Results :Correct Sales area codes are displayed, but incorrect sales area description. Expected description is:" + strExpOrderTypeDescription + ", actual description is:" + strOrderTypeDescription,false);
		    VerifyBrandAndOrderType = false;
			return VerifyBrandAndOrderType;
		    }
	}
	else 
	{
		logTestResult("Step Description :SAP Order Overview Document - Veify Sales area codes"+ "\n Expected Results :Sales area codes should be displayed as:" + strExpSalesAreaCode + ", " + strExpOrderTypeCode + ", " + strExpCompanyCode + "Incorrect Sales area codes are displayed. Expected sales area codes are:" + strExpSalesAreaCode + ", " + strExpOrderTypeCode + ", " + strExpCompanyCode + "\n Actual Results : Actual salea area codea are:" + strSalesAreaCode + ", " + strOrderTypeCode + ", " + strCompanyCode,false);
	    VerifyBrandAndOrderType = false;
		testStatus=false;
		return VerifyBrandAndOrderType;
	  }
	//return VerifyBrandAndOrderType;

}


public boolean verifySiteValue(String strSAPOrderNumber, String strWebShipmentNumber, String[][] arrBMOrderDetails)
{

	boolean VerifySiteValue;	
	//Retrieve expected Site Value
	String strOrderType = arrBMOrderDetails[0][42].trim().toUpperCase();
	String strBrand = arrBMOrderDetails[0][45].trim().toUpperCase();
	
	String strShippedFrom = " ";
	String strSAPOrderType = " ";
	for(int i = 0 ;i<arrBMOrderDetails.length;i++) {
		if(arrBMOrderDetails[0][41].equals( strWebShipmentNumber)) {
			strShippedFrom = (arrBMOrderDetails[0][12]).trim();
		}	
	}
	
	//String gstrBMStoreNumber = null;
	if(strOrderType.equals("ORD") && strShippedFrom.substring(0, 1).equals( "0")) {
		strShippedFrom = strShippedFrom.substring(1, 4);	
		strSAPOrderType = "ZORE";
	}
	else if(strOrderType.equals("ORD") && strShippedFrom.substring(0, 1).equals( "L")) {
		switch(strBrand) {
			case "JCREW":
				strShippedFrom = "LBDI";
				break;
			case "FACTORY":
				strShippedFrom = "LBDF"	;
				break;
			case "MADEWELL":
				strShippedFrom = "LBDM"	;
				break;
	   }
	}
	
	//Verify Actual Site Value
	ITestDataTable itemTable = (ITestDataTable) (table_allItems2().getTestData("list"));
	int intRowCount = itemTable.getRowCount();
	int i = 0;
	String strArticle = GetTableBox_Value(i, "Article");
	while (i<intRowCount-2 && strArticle!="") {
		String strSite = GetTableBox_Value(i, "Site");
		if(!strSite.trim().equals(strShippedFrom)) {
			logTestResult(  "Step Description :SAP Order Overview Document - Verify Site value "+ "\n Expected Results :Site value should be displayed as:" + strShippedFrom+ "\n Actual Results :Incorrect Site value is displayed. Expected Site value is:" + strShippedFrom + ". Actual Site Value is:" + strSite,false);
			VerifySiteValue = false;
			return VerifySiteValue;
		}

		i = i+ 1;
		strArticle = GetTableBox_Value(i, "Article");
	}
	
	
	//Verify Site Value in ItemDetails tab
	if(strSAPOrderType.equals("ZORE")) {
		//Navigate to ItemDetail tab
		//Retrieve Site Value
		pageTab_itemDetail().click();
		String strSiteValue = (String)text_vbapwerks().getProperty("Text");
		if(!strSiteValue.equals(strShippedFrom)) {
			logTestResult("Step Description :SAP Order Overview Document - Verify Site value in Item Details tab"+ "\n Expected Results :Site value should be displayed as:" + strShippedFrom +"\n Actual Results :Incorrect Site value is displayed on Item Detail tab. Expected Site value is:" + strShippedFrom + "+ Actual Site Value is:" + strSiteValue,false);
			VerifySiteValue = false;
			return VerifySiteValue;
		}
	}

	logTestResult( "Step Description :SAP Order Overview Document - Verify Site value"+ "\n Expected Results :Site value should be displayed as:" + strShippedFrom+ "\n Actual Results :Site value is displayed as:" + strShippedFrom, true); 
	VerifySiteValue = true;
	return VerifySiteValue;
		
}

public String GetTableBox_Value(int strRowNum, String ColumnName)
{
	
	ITestDataTable itemTable = (ITestDataTable) (table_allItems2().getTestData("list"));
	//int intRowCount = itemTable.getRowCount();
	String strColumnValue="";
	//int articleCol = 0;
     for (int col=0; col < itemTable.getColumnCount();++col)
      {
    	 if (itemTable.getColumnHeader(col).equals(ColumnName)) {
    		 strColumnValue =(String) itemTable.getCell(strRowNum,col);
    		 logTestResult("Step Description :Capture value for: "+ColumnName+"\n Expected Results :Captured the value for "+ColumnName+" as: \n Actual Results :"+strColumnValue,true);
    	    break;
    	 }
 
	 }
      if (strColumnValue.equals(""))   
      {
    	  logInfo("Step Description :Capture value for: "+ColumnName+"Failed to Capture as Unable to find the field :"+ColumnName);
      }
	 return strColumnValue.trim().toUpperCase();

}



public Map<String, String> PopulatePreReqData(String strOrderNumber, String[][] arrBMOrderDetails, String strWebShipmentNumber)
{
	//String strOrdType;



//'Retrieve Brand and shipped From details from BM
String strBrandName = arrBMOrderDetails[0][45];

for(int i = 0 ;i<arrBMOrderDetails.length;i++) {
	String strPlanShipment = 	arrBMOrderDetails[i][41];
	if(strPlanShipment.equals(strWebShipmentNumber)) {
		gstrBMStoreNumber = arrBMOrderDetails[i][12].trim().toUpperCase();
		break;
	}		
}
if(! gstrBMStoreNumber.equals("")) {
	for(int counter = 0 ;counter<=2;counter++) {
		if(gstrBMStoreNumber.substring(0,1).equals("0" ))
			gstrBMStoreNumber = gstrBMStoreNumber.substring(1);
		else
			break;	
	
			}
}


Map<String, String> discGLpostings = new HashMap<>();
gstrDistributeCode =  (String) text_vbakvtweg().getProperty("Text");
gstrDistChannel =  GetDistributionChannel(gstrDistributeCode);
gstrOrgCode =  (String) text_vbakvkorg().getProperty("Text");
gstrOrgCodeName = gFunGetOrganization(gstrOrgCode);
gstrStoreNumber=GetTableBox_Value(1, "Site");
gstrProfitCenter = gfunGetStateProfitCenter (gstrDistChannel,strOrderNumber,strBrandName);
 discGLpostings = RetrieveGridpostingData(gstrOrdType);
//PopulatePreReqData = discGLpostings
logTestResult( "Step Description :Retrieve order header details for the SAP Order"+"\n Expected Results :Order geader details dhould be retrieved"+ "\n Actual Results :Order header details are retrieved",true);
return discGLpostings;

}


public String GetDistributionChannel(String strCode)
{
	String lDistributionChannel="";
  switch(strCode) {
  
  case "F4":case "F5":case "I4":case "F6":case"R4":case"W5":
	  gstrDistChannel = "factoryinternet";
        lDistributionChannel = "factoryinternet";
		break;
  case "I0":case"I5":case"R3":case"R5":case"W3":
	  gstrDistChannel = "jcrewinternet";
        lDistributionChannel = "jcrewinternet";
		break;
  case "I6":case "R6":case"W6":
	  gstrDistChannel = "madewellinternet";
        lDistributionChannel = "madewellinternet";
		break;
  case "M1":case"M5":
	   gstrDistChannel = "jcrewmobile";
        lDistributionChannel = "jcrewmobile";
		break;
  case "M4":
       gstrDistChannel = "factoryrmobile";
        lDistributionChannel = "factoryrmobile";
		break;
  case "M6":
	    gstrDistChannel = "madewellmobile";
		lDistributionChannel = "madewellmobile";
		break;
  }
  return lDistributionChannel;
}


public String  gFunGetOrganization(String strCode)
{
	String org="";
switch(strCode) {
    case "CA01":
    	org = "J.Crew Canada";
		break;
	case "FR01":
		org = "J.Crew France";
		break;
	case "HK01":
		org = "J.Crew Hong Kong";
			break;
	case "S200":
		org = "J.Crew Sales Org";
		  break;
	case "UK01":
		org = "J.Crew UK";
		break;		
}
return org;

}


public String gfunGetStateProfitCenter(String strDisChannel,String strOrderNumber,String strBrandName)
{
	//String strState;
	String strSheetName;
	String gfunGetStateProfitCenter = "";
String strStateCode = gFunGetStateCodeFromOrderPage(strOrderNumber);
String strTestPath = dbReader.getProperty("eComMasterDataSheet");

if(gstrCountryCode.toUpperCase().equals("US") ||  gstrCountryCode.toUpperCase().equals("CA"))
	strSheetName=strBrandName + "Domestic_ProfitCenter";
else
	strSheetName=strBrandName + "Intl_ProfitCenter" ;
		

	ExcelUtils testDataReader = null;
	try {
			testDataReader = new ExcelUtils(strTestPath, strSheetName, strStateCode);
	
	} catch (IOException e) {
		e.printStackTrace();
	}	
	
	Map<String, Object> rsTestData = null;
	
	
	int j = testDataReader.getSearchTextFirstRowNum();
	//int maxRowCount = testDataReader.getSearchTextLastRowNum();
	
	//while(j<=maxRowCount){
		try{
			rsTestData = testDataReader.getDataFromExcel(j);
			if(strDisChannel.contains("internet")) 
			   gfunGetStateProfitCenter=(String)rsTestData.get("InterNet");
					else
			   gfunGetStateProfitCenter=(String)rsTestData.get("Mobile");
		
			j++;
		}
		catch (IOException e) {
			e.printStackTrace();
			j++;
		}	
		
	//}
	
	return gfunGetStateProfitCenter;
}

public String gFunGetStateCodeFromOrderPage(String strOrderNumber) 
{
	//On Error Resume Next
	String gFunGetStateCodeFromOrderPage="";
	if(gstrCountryCode.toUpperCase().equals("US")  || gstrBrand.equalsIgnoreCase("FACTORY" )) {
		
		String windowText= (String) window_displayStdOrderDirect10().getProperty("Text");
		if(windowText.contains("Display Std Order") || windowText.contains("Display Returns")) {
			String strShippedto = (String) text_kuwevtxtpa().getProperty("Text");
			String strShippeto1=  strShippedto.split("/")[2];//Split(strShippedto ,"/")(2) 
			int intNumber=strShippeto1.split(" ").length-1; //Ubound(Split(strShippeto1," "))
			
			String strStateCode="";
			
			if(gstrCountryCode.toUpperCase().equals("US"))
				strStateCode=strShippeto1.split(" ")[intNumber-1];
			else if(strShippeto1.split(" ")[intNumber-1].trim().length() == 3 )
				strStateCode=strShippeto1.split(" ")[intNumber-2];	
			else
				strStateCode=strShippeto1.split(" ")[intNumber-1];				
			
				
			gFunGetStateCodeFromOrderPage = strStateCode;
		}
		else 
			gFunGetStateCodeFromOrderPage ="";
		
	}
	else if(gstrCountryCode.toUpperCase().equals("CA")  || gstrBrand.equalsIgnoreCase("FACTORY" )) {
		String [] arrIntShippingAdress= RetrieveInternationalAddressFromBlueMartini(strOrderNumber);
		gFunGetStateCodeFromOrderPage = arrIntShippingAdress[4];
	}

	else
		gFunGetStateCodeFromOrderPage = gstrCountryCode.toUpperCase();
		
  return gFunGetStateCodeFromOrderPage;
		
}

public Map<String, String> RetrieveGridpostingData(String strOrderType)
{
	Map<String, String> RetrieveGridpostingData =null;
	String strTestPath = dbReader.getProperty("eComMasterDataSheet");
    String strFilesheet = null;
	if(gstrDistChannel.contains("jcrew"))
		strFilesheet="Jcrew";
	else if(gstrDistChannel.contains("factory"))	
		strFilesheet="Factory";
	else if(gstrDistChannel.contains("madewell"))
		strFilesheet="Madewell";
	
	String strSheetName=strFilesheet + "_" + strOrderType;
	String strkey,strItem; 
	discGLpostings.clear();
	
	
	ExcelUtils testDataReader = null;
	try {
			testDataReader = new ExcelUtils(strTestPath, strSheetName, "");
	
	} catch (IOException e) {
		e.printStackTrace();
	}	
	

	
	Map<String, Object> rsTestData = null;
	
	
	int j = testDataReader.getSearchTextFirstRowNum();
	int maxRowCount = testDataReader.getSearchTextLastRowNum();
	
	while(j<=maxRowCount){
		try{
			rsTestData = testDataReader.getDataFromExcel(j);
			strkey= (String)rsTestData.get("PostingType");
			strItem=(String)rsTestData.get("PostingNumber") + ","+(String)rsTestData.get("InternetCr") + ","+(String)rsTestData.get("InternetDr") + ","+(String)rsTestData.get("MobileCr") + ","+(String)rsTestData.get("MobileDr") + ","+(String)rsTestData.get("PCCr") + ","+(String)rsTestData.get("PCDr") ;
			discGLpostings.put(strkey, strItem);
			j++;
		}
		catch (IOException e) {
			e.printStackTrace();
			j++;
		}	
		
	}

	if(j==0) {
	 logTestResult("Step Description :Retrieve Profit center, Account number and BA from master data sheet"+"\n Expected Results :Should load data in dictionary object"+"\n Actual Results :Unable to load data in Dictionary object Error occured " ,false);
	  RetrieveGridpostingData =null;
	  testStatus=false;
	}
		
	RetrieveGridpostingData = discGLpostings;
	return RetrieveGridpostingData;
	
  }

public boolean IsMonogramInBlueShipment(String[][] arrBmOrder, String strWebShipment) 
{
	boolean blnMonogram =  false;

for(int Iterator = 0;Iterator<arrBmOrder.length;Iterator++) {
	if(arrBmOrder[Iterator][41].equals(strWebShipment))  {
		if(arrBmOrder[Iterator][28]==null) break;
		if(!arrBmOrder[Iterator][28].equals("") && !arrBmOrder[Iterator][42].equals("RET")) {
			blnMonogram = true;
			break;
	}
		
	}
	
}

return  blnMonogram;

}

public boolean IsGiftBoxInBlueShipment(String[][] arrBmOrder, String strWebShipment) 
{
	boolean blnGiftBox =  false;

	for(int Iterator = 0;Iterator<arrBmOrder.length;Iterator++) {
	if(arrBmOrder[Iterator][41].equals(strWebShipment))  {
		if(arrBmOrder[Iterator][29]==null) break;
		if(!arrBmOrder[Iterator][29].equals("") && !arrBmOrder[Iterator][42].equals("RET")) {
			blnGiftBox = true;
			break;
		}
		
	}
	
	}

return blnGiftBox;

}


public String[][] funcRetrieveBillingDetailsFromBlueMartini(String strOrderNumber) 
{
// On Error Resume Next
String strEnvironment;
String[][] arrBillingDetails = null;
System.out.println("funcRetrieveArticlesFromBlueMartini");
	//Retrieve blue martini environment for database connection
	strEnvironment = dbReader.getProperty("BMEnvironment");
	

	//Create sql query for order retrieval from Blue Martini
	//Get Bluemartini order details from the database.
	BMOrderDetails bmOrderDetails = null;
	bmOrderDetails = new BMOrderDetails();	
	String strRetrievalType = "Billing";
	String strSqlQuery = bmOrderDetails.Create_Sql_Query_For_BM_OrderDetail_Retrieval(strEnvironment, strOrderNumber, strRetrievalType, "");
	logTestResult("Create Query to billing details:" + strSqlQuery ,true);
	try {
    //Execute SQL query
	//Establish DB connection
	DatabaseReader databaseReader = DatabaseReader.getDatabaseReader();
	Connection conn = databaseReader.getConnectionToDatabase();
	if (conn != null) {
		//logger.info("DB connection is successful...");
	}
	//Statement stmt = databaseReader.createTheStatement(conn);
	Statement stmt = conn.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY
		);

	//Retrieve values from database
	ResultSet objDB_RS = stmt.executeQuery(strSqlQuery);

	
	if (!objDB_RS.next())
	{
		logTestResult("Step Description :Retrieve billing details for the Order: " + strOrderNumber +  " from Blue Martini"+ "\n Expected Results :billing details should be retrieved from BM database"+"\n Actual Results :Order: " + strOrderNumber +  " details does not exist in Blue Martini database. Please verify",false);
		testStatus=false;
		return arrBillingDetails;
	}
	else
		logTestResult("Step Description :Retrieve articles && quantity for the Order: " + strOrderNumber +  " from Blue Martini"+ "\n Expected Results :Billing details should be retrieved from BM database"+ "\n Actual Results :Order: " + strOrderNumber +  " Billing details are retrieved from Blue Martini database",true);
	
	

	
	//Create a 2D array
	objDB_RS.last();
    int intItemLineCount = objDB_RS.getRow();

	arrBillingDetails= new String[intItemLineCount][4];
	objDB_RS.beforeFirst();
	int i = 1;
	while(objDB_RS.next()) {
		arrBillingDetails[i-1][0] = objDB_RS.getString(1);
		arrBillingDetails[i-1][1] = objDB_RS.getString(2);
		arrBillingDetails[i-1][2] = objDB_RS.getString(3);
		arrBillingDetails[i-1][3] = objDB_RS.getString(4);
		//arrArticleDetails[i-1][4] = objDB_RS.getString(4);
		i = i + 1;
	}
	
return  arrBillingDetails;
	}
	 catch (Exception e) {
			//logger.error("Unable to run query for retrieving Start and End date the shipping method {}", carrierCode);
			return null;
		}

}


public boolean VerifyCreditCard_SAPOrderScreen(String strSAPOrderNumber, String strOrderNumber, String[][] arrBMBillingDetails, String[][] arrBMOrderDetails,String strSAPCreditCard,String strSAPTokenNumber,boolean blnPaymentCardsTab)
{
//String strSAPCreditCard;
//String strSAPTokenNumber;
boolean VerifyCreditCard_SAPOrderScreen=false;

//String strOrderType = arrBMOrderDetails[0][42].trim();
String strCountryCode = arrBMOrderDetails[0][43].trim();
String strBrand = 	arrBMOrderDetails[0][45].trim().toUpperCase();

//Retrieve payment and corresponding token number from SAP
if(!blnPaymentCardsTab) {
 strSAPCreditCard = (String)text_ccdataccins().getProperty("Text");//SessionObj.SAPGuiEdit("name:=CCDATA-CCINS","attachedtext:=Payment card").getroproperty("value");
 strSAPTokenNumber = (String) text_ccdataccnum().getProperty("Text");//SessionObj.SAPGuiEdit("name:=CCDATA-CCNUM","attachedtext:=Payment card").getroproperty("value");
}

if(strCountryCode.equals( "US" ) || strBrand.equals( "FACTORY")) {
	//Retrieve Payment methods from Blue Martini
	boolean blnPayVerification = false;
	for (int i = 0; i < arrBMBillingDetails.length; i++) {
		String strPayment = arrBMBillingDetails[i][2].trim().toUpperCase();
		if(strPayment.equals( "CCD") || strPayment.equals(  "PPL") || strPayment.equals( "ACT")) {
			blnPayVerification = true;
			break;	
	  }
	}
	

	if(blnPayVerification) {
		if (strSAPCreditCard.isEmpty() || strSAPCreditCard.equals("") ||  strSAPTokenNumber.isEmpty() || strSAPTokenNumber.equals("")) {
		logTestResult( "Step Description: SAP Order Overview Document - Verify Credit card type and token number for the SAP order:" + strSAPOrderNumber+ "\n Expected Results :Credit card type and token number should match with the details from Blue Martini"+ "\n Actual Results :Unable to retrieve Credit Card type or corresponding token number. Following are the details retrieved. Credit Card type:" + strSAPCreditCard + ", token number:" + strSAPTokenNumber,false);
			VerifyCreditCard_SAPOrderScreen = false;
			testStatus=false;
			return 	VerifyCreditCard_SAPOrderScreen;	
	    }
		
		//'Make sure that the payment from SAP matches credit Card from Blue Martini
		if (strSAPCreditCard.equals("PPAL")) {
			//Retrieve Paypal auth number from Blue Martini database
			String [] arrBMPaypalDetails = RetrievePaypalPaymentAuth(strOrderNumber);
			
			if(arrBMPaypalDetails.length == 0 || arrBMPaypalDetails.equals(null)) {
				logTestResult( "Step Description: Retrieve paypal auth details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :paypal auth details should be retrieved from BM database"+ "\n Actual Results :No paypal auth details are retrieved for the order:" + strOrderNumber,false);		
				VerifyCreditCard_SAPOrderScreen = false;
				testStatus=false;
				return 	VerifyCreditCard_SAPOrderScreen;
			}
			
			for (int i = 0; i < arrBMPaypalDetails.length; i++) {
				String strBMAuthNumber = arrBMPaypalDetails[i].trim().toUpperCase();
				
				if(strBMAuthNumber.equals(strSAPTokenNumber)) {
					//blnMatch = true;
					logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and corresponding auth number should match between Blue Martini and SAP"+ "\n Actual Results :Payment type: PPAL and corresponding auth number:" + strSAPTokenNumber + " are matching with Blue Martini for the order:" + strOrderNumber,true);
					VerifyCreditCard_SAPOrderScreen = true;
					return 	VerifyCreditCard_SAPOrderScreen;
				}
			}
			
			//Report error if auth not found in Blue Martini
			logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and corresponding auth number should match between Blue Martini and SAP"+ "\n Actual Results :Payment type: PPAL and corresponding auth number:" + strSAPTokenNumber + " are not available in Blue Martini for the order:" + strOrderNumber,false);
			VerifyCreditCard_SAPOrderScreen = false;
			testStatus=false;
			return 	VerifyCreditCard_SAPOrderScreen;
		}
		else if( strSAPCreditCard.equals("ZGCR")) {
				for (int i = 0; i < arrBMBillingDetails.length; i++) {
				 String strBMPayType = arrBMBillingDetails[i][2].trim().toUpperCase();
				 if(strBMPayType.equals("ACT")) {
					String strBMAuthNumber = arrBMBillingDetails[i][3].trim().toUpperCase();
					
					if(strBMAuthNumber.equals(strSAPTokenNumber)) {
						logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and corresponding auth number should match between Blue Martini and SAP"+ "\n Actual Results :Payment type: ZGCR (gift card) and corresponding auth number:" + strSAPTokenNumber + " are matching with Blue Martini for the order:" + strOrderNumber,true);
						VerifyCreditCard_SAPOrderScreen = true;
						break;
					}
				 }
				}//for
			
			//Report error if auth not found in Blue Martini
				logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and corresponding auth number should match between Blue Martini and SAP"+ "\n Actual Results :Payment type: ZGCR (gift card) and corresponding auth number:" + strSAPTokenNumber + " are not available in Blue Martini for the order:" + strOrderNumber,false);
			VerifyCreditCard_SAPOrderScreen = false;
			testStatus=false;
			return 	VerifyCreditCard_SAPOrderScreen;				
		}
		else {
			for (int i = 0; i < arrBMBillingDetails.length; i++) {
				if(arrBMBillingDetails[i][0]==null || arrBMBillingDetails[i][1]==null)continue;
				String strBMCardType =  arrBMBillingDetails[i][0].trim().toUpperCase();
				if(strBMCardType.equals("JCCC")) {
					strBMCardType = "JCRW"; }
				else if(strBMCardType.equals("JCB")) {
					strBMCardType = "ZJCB";	
				}
				String strBMTokenNumber = 	 arrBMBillingDetails[i][1].trim().toUpperCase();
				
				if(strBMCardType.equals(strSAPCreditCard) && strBMTokenNumber.equals( strSAPTokenNumber)) {
					logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and corresponding auth number should match between Blue Martini and SAP"+ "\n Actual Results :Payment type:" + strSAPCreditCard + " and corresponding auth number:" + strSAPTokenNumber + " are matching with Blue Martini for the order:" + strOrderNumber,true);
					VerifyCreditCard_SAPOrderScreen = true;
					return 	VerifyCreditCard_SAPOrderScreen;
				}
			}
			
			//Report error if auth not found in Blue Martini
			logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and corresponding auth number should match between Blue Martini and SAP"+ "\n Actual Results :Payment type:" + strSAPCreditCard + " and corresponding auth number:" + strSAPTokenNumber + " are not available in Blue Martini for the order:" + strOrderNumber,false);
			VerifyCreditCard_SAPOrderScreen = false;
			testStatus=false;
			return 	VerifyCreditCard_SAPOrderScreen;
		}
	
}
else {
	if (strSAPCreditCard.equals("") && strSAPTokenNumber.equals( "")) {
		logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and auth number should not be displayed for International orders"+"\n Actual Results :Payment type and auth number fields are blank.",true);
		VerifyCreditCard_SAPOrderScreen = true;
		return 	VerifyCreditCard_SAPOrderScreen;
	}
	else {
		logTestResult( "Step Description: SAP Order Overview Document - Verify payment type and corresponding auth number between SAP and Blue Martini for the SAP Order: " + strSAPOrderNumber+ "\n Expected Results :Payment type and auth number should not be displayed for International orders"+"\n Actual Results :Payment type and auth number fields are not blank. Payment Type:" + strSAPCreditCard + ", Auth number:" + strSAPTokenNumber,false);
		VerifyCreditCard_SAPOrderScreen =false;
		testStatus=false;
		return 	VerifyCreditCard_SAPOrderScreen;		
	}
 }


}
return VerifyCreditCard_SAPOrderScreen;

}

public String[] RetrievePaypalPaymentAuth(String strOrderNumber)
{
	try {
    	String strEnvironment;
    	String[] RetrievePaypalPaymentAuth;

    	//Retrieve blue martini environment for database connection
    	strEnvironment = dbReader.getProperty("BMEnvironment");	
   	 
     	//Create sql query for order retrieval from Blue Martini
 		//Get Bluemartini order details from the database.
 		BMOrderDetails bmOrderDetails = null;
 		bmOrderDetails = new BMOrderDetails();	
     	String strRetrievalType = "PaypalAuth";
     	String strSqlQuery = bmOrderDetails.Create_Sql_Query_For_BM_OrderDetail_Retrieval(strEnvironment, strOrderNumber, strRetrievalType, "");
       	if(strSqlQuery.equals( "") || strSqlQuery.isEmpty()) {
    		logTestResult("Step Description :Retrieve shipping address for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :shipping address should be retrieved from BM database"+ "\n Actual Results :shipping address query is blank. Hence, unable to retrieve order details from BM Database",false);
    		RetrievePaypalPaymentAuth = null;
			testStatus=false;
    		return 	RetrievePaypalPaymentAuth;
       	}
         //Execute SQL query
 		//Establish DB connection
 		DatabaseReader databaseReader = DatabaseReader.getDatabaseReader();
 		Connection conn = databaseReader.getConnectionToDatabase();
 		if (conn != null) {
 			//logger.info("DB connection is successful...");
 		}
 		//Statement stmt = databaseReader.createTheStatement(conn);
 		Statement stmt = conn.createStatement(
 			    ResultSet.TYPE_SCROLL_INSENSITIVE,
 			    ResultSet.CONCUR_READ_ONLY
 			);

 		//Retrieve values from database
 		ResultSet objDB_RS = stmt.executeQuery(strSqlQuery);
    	
 	  	if (!objDB_RS.next()) {
    		logTestResult(  "Step Description :Retrieve paypal auth details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :paypal auth details should be retrieved from BM database"+ "\n Actual Results :Unable to retrieve paypal auth details of the Order: " + strOrderNumber + " from Blue Martini database. Please verify",false);
    		RetrievePaypalPaymentAuth = null;
			testStatus=false;
    		return RetrievePaypalPaymentAuth;
 	  	}
    	else
    	  logTestResult("Step Description :Retrieve paypal auth details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :paypal auth details should be retrieved from BM database"+ "\n Actual Results :paypal auth details is retrieved for the Order: " + strOrderNumber + " from Blue Martini database",true);
    	
    	
    	//Create a  array
    	objDB_RS.last();
    	int rowCount = objDB_RS.getRow();
    	
    	String []arrBMPaypalAuth= new String[rowCount];
    	objDB_RS.beforeFirst();
    	int i = 0;
    	while(objDB_RS.next()) {
    		arrBMPaypalAuth[i] = objDB_RS.getString(1);
    		i++;
    		
    	}
    	 	
    	RetrievePaypalPaymentAuth = arrBMPaypalAuth;
    	return RetrievePaypalPaymentAuth;
    	}
    	
    	 catch (Exception e) {
 			//logger.error("Unable to run query for retrieving Start and End date the shipping method {}", carrierCode);
 			return null;
 		}


}

public boolean ValidatePGI_PGR(String strSAPOrderNumber, String strWebShipmentNumber, String strOrderNumber, String strPGIPGRNumber, String[][] arrBMOrderDetails)
{
	/*Property Props[] = new Property[2];
	// property and value
	Props[0] = new Property("Name", "MSEG-ZEILE");
	Props[1] = new Property(".class", "GuiTextField");
	TestObject Obj[] = getRootTestObject().find(atDescendant(Props));
	System.out.println (Obj.length);*/
	boolean ValidatePGI_PGR;
	//Retrieve article details from Blue Martini
	String[][] arrBMArticles = funcRetrieveArticlesFromBlueMartini(strSAPOrderNumber,strWebShipmentNumber, strOrderNumber);
	if (arrBMArticles==null ||arrBMArticles.length==0){
		logTestResult("Step Description :Retrieve all articles and coreresponding quantity from Blue Martini"+"\n Expected Results :All the articles should be retrieved"+ "\n Actual Results :Unable to retrieve articles and coreresponding quantity from Blue Martini",false);
		ValidatePGI_PGR = false;
		testStatus=false;
		return 	ValidatePGI_PGR;
	}

	
	//Retrieve article details from PGI/PGR document
	String[][] arrSAPArticles = RetrieveArticlesFromPGI_PGR(strSAPOrderNumber, strPGIPGRNumber);
	if (arrSAPArticles==null ||arrSAPArticles.length==0){
		logTestResult("Step Description :Retrieve all articles and coreresponding allocation and movement from PGI/PGR document"+"\n Expected Results :Article details should be retrieved"+ "\n Actual Results :Unable to retrieve article details from PGI/PGR document",false);
		ValidatePGI_PGR = false;
		testStatus=false;
		return 	ValidatePGI_PGR;
	}
	
	
	//Retrieve Brand and order type from Blue Martini order details array
	String strBrand = arrBMOrderDetails[0][45].trim();
	String strOrderType = arrBMOrderDetails[0][42].trim();
	
	//Define allocation Type
	String strLyhAllocationCode = null;
	if (strBrand.trim().toUpperCase().equals("MADEWELL"))
		strLyhAllocationCode = "LBDM";
	else if (strBrand.trim().toUpperCase().equals("FACTORY"))
		strLyhAllocationCode = "LBDF";
	else if (strBrand.trim().toUpperCase().equals("JCREW"))
		strLyhAllocationCode = "LBDI";	
	
	//Make sure that allocation and Movement displayed correct on PGI/PGR document
	for( int i = 0;i <arrBMArticles.length; i++) {
		boolean blnRewpeat = false;
		do {
			blnRewpeat = false;
			String strBMArticle = arrBMArticles[i][0].trim().toUpperCase();
			if( strBMArticle.equals("99101566087") || strBMArticle.equals("99101566083"))
				break;
			String strBMArticleQty = arrBMArticles[i][1];
			String strBMAllocation = arrBMArticles[i][2].trim().toUpperCase();
			
			//Transform allocation value
			if( strBMAllocation.equals("LYH") || strBMAllocation.equals("LYN") || strBMAllocation.equals("LDC"))
				strBMAllocation = strLyhAllocationCode;
			else if( strBMAllocation.substring(0,1).equals("0")) {
				if( strOrderType .equals("RET"))
					strBMAllocation = strLyhAllocationCode;		
				else
					for( int counter = 0;counter <=3; counter++) {
						if(strBMAllocation.substring(0,1).equals("0")) 
							strBMAllocation = strBMAllocation.substring(counter+1,4-counter);
						else
							break;	
						
					}
							
			}
			
			//transform Status code
			String strBMStatus;
			strOrderType = arrBMArticles[0][3].trim().toUpperCase();
			if( strOrderType.equals("RET"))
				strBMStatus = "657";
			else
				strBMStatus = "601";
			
			
			int strQtyFound = 0;
			for(int j = 0 ;j<arrSAPArticles.length;j++) {
				String strSAPArtile = arrSAPArticles[j][0].trim().toUpperCase();
				String strSAPAllocation = arrSAPArticles[j][1].trim().toUpperCase();
				String strSAPStatus = arrSAPArticles[j][2];
				String strSAPArticleQty = arrSAPArticles[j][3];
				
				if( strSAPArtile.equals(strBMArticle) && strBMAllocation.equals(strSAPAllocation) && strBMStatus.equals(strSAPStatus) && strSAPArticleQty.equals("1")) 
					strQtyFound = strQtyFound + 1;	
				
			}
			
			if( Integer.parseInt(strBMArticleQty) != strQtyFound) {
				logTestResult("Step Description :PGI/PGR document - Compare all articles, coreresponding Quantity,allocation and movement type between Blue Martini and PGI/PGR document: " + strPGIPGRNumber +"\n Expected Results :Details should match between Blue Martini and PGI/PGR document"+ "\n Actual Results :Article name, coreresponding Quantity,allocation and movement type are matching between Blue Martini and PGI/PGR document. For example, BM Article name:" + strBMArticle  + ", allocation:"+ strBMAllocation + ", BM Status:" + strBMStatus + ", BM Quantity:" + strBMArticleQty + "; Quantity displayed on SAP screen for this article is:" + strQtyFound,false);  
				ValidatePGI_PGR = false;
				testStatus=false;
				return 	ValidatePGI_PGR;
			}
		} while(blnRewpeat==true);
	}
	
	logTestResult("Step Description :PGI/PGR document - Compare all articles, coreresponding Quantity,allocation and movement type between Blue Martini and PGI/PGR document: " + strPGIPGRNumber +"\n Expected Results :Details should match between Blue Martini and PGI/PGR document"+ "\n Actual Results :Article name, coreresponding Quantity,allocation and movement type are matching between Blue Martini and PGI/PGR document",true);
	
	
	//Make sure that Minus (-) symbol is displayed for regular orders and Plus(+) symbol is displayed for Return Orders
	boolean	blnStatus = VerifyMovementSymbolFromPGI_PGR(strSAPOrderNumber, strPGIPGRNumber, strOrderType);
	if( ! blnStatus) 
	logTestResult("Step Description :PGI/PGR document - Verify Movement sumbol in the document:" + strPGIPGRNumber+"\n Expected Results :Correct movement symbol is displayed on PGI/PGR document"+ "\n Actual Results :Correct movement symbol is not displayed",false);
	
	
	ValidatePGI_PGR = true;
	return 	ValidatePGI_PGR;

}


public String[][] RetrieveArticlesFromPGI_PGR(String strSAPOrderNumber, String strPGIPGRNumber)
{
	String[][] arrSAPArticles = null;
	
	//Make sure that the user is in PGI/PGR document screen
	String strSessionText = (String) window_displayArticleDocument4().getProperty("Text");
	if(!strSessionText.toUpperCase().contains("DISPLAY ARTICLE DOCUMENT " + strPGIPGRNumber.trim()))  {
		logTestResult("Step Description :Retrieve all articles, corresponding allocation and Movement type from PGI/PGR document"+"\n Expected Results :Details should be retrieved"+ "\n Actual Results :User is not on PGI/PGR document screen",false);	
		return 	null;	
	}
	
    DomainTestObject domains[] = getDomains();
    for  (int k =0; k < domains.length; k ++)
    {
           DomainTestObject domain = domains[k];
           String name = (String)domain.getName();
           System.out.println(name);
           if (name.compareTo("SAP") == 0)
           {
                 TestObject[] sapApps = domains[k].getTopObjects();

                 Property Props0[] = new Property[3];
                 Props0[0] = new Property(".class", "GuiUserArea");
                 Props0[1] = new Property("Id", "/usr");
                 Props0[2] = new Property("Name", "usr");

                 TestObject Obj[] = sapApps[0].find(atChild(Props0));
                 System.out.println(Obj.length);

	//_items().getTestData(testDataType)
	Property Props[] = new Property[2];
	Props[0] = new Property("Name", "MSEG-MATNR");
	Props[1] = new Property(".class", "GuiCTextField");
	TestObject Obj1[] = Obj[0].find(atChild(Props));
	int intCount = Obj1.length;
	//Retrieve number of articles on the PGI/PGR
	//Set oArticle = SessionObj.Object.FindAllByName("MSEG-MATNR","SAPGuiEdit") 
	
	Property Props1[] = new Property[2];
	Props1[0] = new Property("Name", "MSEG-WERKS");
	Props1[1] = new Property(".class", "GuiCTextField");
			
	Property Props2[] = new Property[2];
	Props2[0] = new Property("Name", "MSEG-BWART");
	Props2[1] = new Property(".class", "GuiCTextField");
			
	Property Props3[] = new Property[2];
	Props3[0] = new Property("Name", "MSEG-ERFMG");
	Props3[1] = new Property(".class", "GuiTextField");
	
	Property Props4[] = new Property[2];
	Props4[0] = new Property("Name", "MSEG-ZEILE");
	Props4[1] = new Property(".class", "GuiTextField");
			
	
	if( intCount==0) {
		logTestResult("Step Description :Retrieve all articles, corresponding allocation and Movement type from PGI/PGR document"+"\n Expected Results :Details should be retrieved"+ "\n Actual Results :Unable to retrieve articles detaiuls from PGI/PGR document",false);
		testStatus=false;
		return 	null;	
	}
	
	if( intCount < 7 ) 
		arrSAPArticles=new String[intCount][4];
	else  {
		//Call sapsendKey_Enter("CTRL_PAGEDOWN")
		window_sap5().sendVKey(SAPTopLevelTestObject.VKEY_CTRL_PAGEDOWN);
		TestObject Obj2[] = Obj[0].find(atChild(Props));
		int intRowCount =  Obj2.length;
		intCount = 7;
		for( int counter = 0;counter <=intRowCount-1; counter++) {
			int intRowValue = (int) Obj2[counter].getProperty("Text");
			if( intRowValue > intCount )
				intCount = intRowValue;
			
		}
		arrSAPArticles=new String[intCount][4];
		window_sap5().sendVKey(SAPTopLevelTestObject.VKEY_CTRL_PAGEUP);
	}
		//Retrieve article details into an array
		boolean blnMatchFlag = false;
		for( int i = 1;i <=intCount; i++) {
			if( (i != 1) && ((i-1) % 7 == 0)) {
				window_sap5().sendVKey(SAPTopLevelTestObject.VKEY_CTRL_PAGEDOWN);
				blnMatchFlag = true;		
			}
			if( ! blnMatchFlag ){
				arrSAPArticles[i-1][0]=  (String) Obj[0].find(atChild(Props))[i-1].getProperty("Text");
				arrSAPArticles[i-1][1] = (String) Obj[0].find(atChild(Props1))[i-1].getProperty("Text");
				arrSAPArticles[i-1][2] = (String) Obj[0].find(atChild(Props2))[i-1].getProperty("Text");
				arrSAPArticles[i-1][3] = (String) Obj[0].find(atChild(Props3))[i-1].getProperty("Text");
				}
			else {
				for( int counter = 0;counter <=6; counter++) {
					int intRow = (int) getRootTestObject().find(atDescendant(Props4))[i-1].getProperty("Text");
					if( i == intRow) {
						arrSAPArticles[i-1][0]=  (String) Obj[0].find(atChild(Props))[i-1].getProperty("Text");
						arrSAPArticles[i-1][1] = (String) Obj[0].find(atChild(Props1))[i-1].getProperty("Text");
						arrSAPArticles[i-1][2] = (String) Obj[0].find(atChild(Props2))[i-1].getProperty("Text");
						arrSAPArticles[i-1][3] = (String) Obj[0].find(atChild(Props3))[i-1].getProperty("Text");					
					}
					
				}
			}
		}
		//
           }
    }
    unregisterAll();
    return 	arrSAPArticles;
   
	
}



public boolean VerifyMovementSymbolFromPGI_PGR(String strSAPOrderNumber, String strPGIPGRNumber, String strOrderType)
{
	boolean VerifyMovementSymbolFromPGI_PGR = false;
	//Retrieve number of articles on the PGI/PGR
	/*Property Props[] = new Property[2];
	Props[0] = new Property("Name", "MSEG-MATNR");
	Props[1] = new Property(".class", "GuiCTextField");
	TestObject oArticle[] = getRootTestObject().find(atDescendant(Props));*/
    DomainTestObject domains[] = getDomains();
    for  (int k =0; k < domains.length; k ++)
    {
           DomainTestObject domain = domains[k];
           String name = (String)domain.getName();
           System.out.println(name);
           if (name.compareTo("SAP") == 0)
           {
                 TestObject[] sapApps = domains[k].getTopObjects();

                 Property Props0[] = new Property[3];
                 Props0[0] = new Property(".class", "GuiUserArea");
                 Props0[1] = new Property("Id", "/usr");
                 Props0[2] = new Property("Name", "usr");

                 TestObject Obj[] = sapApps[0].find(atChild(Props0));
                 System.out.println(Obj.length);
	int intCount = Obj.length;
	
	if(  intCount== 0) {
		logTestResult("Step Description :PGI/PGR document - Retrieve all articles, corresponding allocation and Movement type"+"\n Expected Results :Details should be retrieved"+ "\n Actual Results :Unable to retrieve articles detaiuls from PGI/PGR document",false);
		VerifyMovementSymbolFromPGI_PGR = false;
		testStatus=false;
		return VerifyMovementSymbolFromPGI_PGR;	
	}
	String strExpectedMovementSymbol = null;
	if( strOrderType.equals("ORD"))
		strExpectedMovementSymbol = "-";
	else if( strOrderType.equals("RET"))
		strExpectedMovementSymbol = "+";

	Property Props1[] = new Property[1];
	Props1[0] = new Property("Name", "DM07M-VZBEW");
	String strMovementSymbol = null;
	//Retrieve article details into an array
	for( int i = 0;i <intCount; i++) {
		strMovementSymbol = (String) Obj[0].find(atChild(Props1))[i].getProperty("Text");
		if( ! strMovementSymbol.trim().equals(strExpectedMovementSymbol)) {
			logTestResult("Step Description :PGI/PGR document - Verify Movement sumbol in the document:" + strPGIPGRNumber+"\n Expected Results :Movement symbol should be displayed as:" + strExpectedMovementSymbol+ "\n Actual Results :Movement symbol is displayed as:" + strMovementSymbol + ", Expected movement symbol is:" + strExpectedMovementSymbol + " for the order type:" + strOrderType,false);
			VerifyMovementSymbolFromPGI_PGR = false;
			return VerifyMovementSymbolFromPGI_PGR;
		}
	}

	logTestResult("Step Description :PGI/PGR document - Verify Movement sumbol in the document:" + strPGIPGRNumber+"\n Expected Results :Movement symbol should be displayed as:" + strExpectedMovementSymbol+ "\n Actual Results :Movement symbol is displayed as:" + strMovementSymbol,true);
	VerifyMovementSymbolFromPGI_PGR = true;
           }
    }


    unregisterAll();

	return VerifyMovementSymbolFromPGI_PGR;


}
 
public boolean VerifyAuthAmtInPaymentsandConditionScreen(String strSAPOrderNumber, String strOrderNumber, String[][] arrBMBillingDetails, String[][] arrBMOrderDetails)
{
	boolean VerifyAuthAmt=true;
	pageTab_paymentCards().select();

	boolean blnStatus= VerifyCreditCard_SAPOrderScreenPaymentTab(strSAPOrderNumber, strOrderNumber, arrBMBillingDetails, arrBMOrderDetails);
	
	//Verify Actual Auth Value
	ITestDataTable itemTable = (ITestDataTable) (table_paymentCards().getTestData("list"));
	int intRowCount = itemTable.getRowCount();
	int i = 0;
	
	double dblMaxAmt = 0.0;
	double dblAuthAmt=0.0;
	while (i<intRowCount-1) {
	
	     for (int col=0; col < itemTable.getColumnCount();++col)
	      {
	    	 if (itemTable.getColumnHeader(col).equals("Maximum amount")) {
	    		 String strMaxAmt = itemTable.getCell(i,col).toString();
	    		 if( !strMaxAmt.equals(""))
	    		    dblMaxAmt = dblMaxAmt+ Double.parseDouble(strMaxAmt);
	    		// logTestResult("Step Description :Capture value for: "+ColumnName+"\n Expected Results :Captured the value for "+ColumnName+" as: \n Actual Results :"+strColumnValue,true);
	    	   // break;
	    	 }
	    	 if (itemTable.getColumnHeader(col).equals("Authorized amt")) {
	    		 String strAuthAmt = itemTable.getCell(i,col).toString();
	    		 if( !strAuthAmt.equals(""))
	    		    dblAuthAmt = dblAuthAmt+  Double.parseDouble(strAuthAmt);
	    		// logTestResult("Step Description :Capture value for: "+ColumnName+"\n Expected Results :Captured the value for "+ColumnName+" as: \n Actual Results :"+strColumnValue,true);
	    	   // break;
	    	 }
	 
		 }
			i = i+ 1;
	}
	
	if(dblMaxAmt!=dblAuthAmt) {
		logTestResult(  "Step Description :SAP Order Overview Document - Verify Auth value "+ "\n Expected Results :Auth value should be displayed as:" + dblMaxAmt+ "\n Actual Results :Incorrect Auth value is displayed. Expected Auth value is:" + dblMaxAmt + ". Actual Auth Value is:" + dblAuthAmt,false);
		VerifyAuthAmt = false;
		testStatus=false;
		return VerifyAuthAmt;
	}
	else
		logTestResult(  "Step Description :SAP Order Overview Document - Verify Auth value "+ "\n Expected Results :Auth value should be displayed as:" + dblMaxAmt+ "\n Actual Results :Correct Auth value is displayed. Expected Auth value is:" + dblMaxAmt + ". Actual Auth Value is:" + dblAuthAmt,true);
	sleep(1);
	

	pageTab_conditions2().click();

	//Verify Actual Amount Value in Conditions screen
	String flag = "not found";
    int j =0;
    Double paymentTotal=0.0;
    do 
    {
     table_pricingElements().setVerticalScrollBar(atPosition(j+1));
     ITestDataTable itemTable2 = (ITestDataTable) (table_pricingElements().getTestData("list"));
	//window_sap5().sendVKey(SAPTopLevelTestObject.VKEY_PAGEDOWN);
	intRowCount = itemTable2.getRowCount();
	i = 0;
	 System.out.println(intRowCount);
	
	while (i<intRowCount-1) {
	
	     for (int col=0; col < itemTable2.getColumnCount();++col)
	      {
	    	 //System.out.println(itemTable2.getColumnHeader(col));
	    	 if (itemTable2.getColumnHeader(col).equals("Name")) {
	    		 System.out.println(itemTable2.getCell(i,col).toString());
	    		 String strPaymentTotal = itemTable2.getCell(i,col).toString();
	    		 if(strPaymentTotal.trim().equals("Payment Total")) {
	    			 System.out.println(strPaymentTotal+ itemTable2.getColumnHeader(col+1));
	    			 paymentTotal =  Double.parseDouble(itemTable2.getCell(i,col+5).toString());
	    			 flag = "found";
	    			 break;
	    		 }

	    	 }
	 
		 }
	     i = i+ 1;
		//strMaxAmt1 = GetTableBox_Value(i, "Maximum amount");
	}
    j++;
    }while(flag.equals("not found"));

	if(paymentTotal!=dblAuthAmt) {
		logTestResult(  "Step Description :SAP Order Overview Document - Condition Screen Verify Amount value "+ "\n Expected Results :Auth value should be displayed as:" + dblMaxAmt+ "\n Actual Results :correct payment total value is displayed. Expected Amount value is:" + dblMaxAmt + ". Actual Amount Value is:" + paymentTotal,false);
		VerifyAuthAmt = false;
		testStatus=false;
		return VerifyAuthAmt;
	}
	else
		logTestResult(  "Step Description :SAP Order Overview Document - Condition Screen Verify Amount value "+ "\n Expected Results :Auth value should be displayed as:" + dblMaxAmt+ "\n Actual Results :correct payment total value is displayed. Expected Amount value is:" + dblMaxAmt + ". Actual Amount Value is:" + paymentTotal,true);
return true;
}

public boolean verifyShippingAddressInPartnersTab(String strSAPOrderNumber,String[][] arrBMOrderDetails,String strWebShipmentNumber, String strOrderNumber)
{
	boolean VerifyShippingAddress = false;
	String[] arrInternationalAddress =null;

   String strOrderType = arrBMOrderDetails[0][42];
   if(strOrderType.trim().toUpperCase().equals("ORD")) {
	//Retrieve shipping address
	   pageTab_partners().select();
	String strSAPShippingAddress = GetShippingAddressInPartnersTab_Value(3, "Street")+" / " + GetShippingAddressInPartnersTab_Value(3, "Cty") + " " + GetShippingAddressInPartnersTab_Value(3, "Postal code");
	//strSAPShippingAddress =strSAPShippingAddress.substring(strSAPShippingAddress.indexOf("/")+1, strSAPShippingAddress.length()).trim().toUpperCase();
	button_btn3().press();
	//Retrieve shipping address for domestic orders
	String strCountry = (arrBMOrderDetails[0][43]).trim().toUpperCase();
	String strBrand = arrBMOrderDetails[0][45].trim().toUpperCase();
	if(strCountry.equals( "US" ) || strBrand.equals( "FACTORY")) {
		String strBMAddress = "";
		for(int i = 0 ;i<arrBMOrderDetails.length;i++) {
			String strPlanShipment = 	arrBMOrderDetails[i][41];
			if(strPlanShipment.equals(strWebShipmentNumber)) {
				strBMAddress = arrBMOrderDetails[i][13] + " / " + arrBMOrderDetails[i][16] + " " +  arrBMOrderDetails[i][20];
				break;
			}		
		}
		
		if(strBMAddress.equals( "")) {
			logTestResult( "Step Description :SAP Order Overview Document PartnersTab - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :Unable to retrieve address from Blue Martini for the order:" + strOrderNumber + ", and the plan shipment:" + strWebShipmentNumber,false);
			VerifyShippingAddress = false;
			testStatus=false;
			return VerifyShippingAddress ;
		}
		else
			strBMAddress = strBMAddress.trim().toUpperCase();
		
		
		//Compare Address
		if(strSAPShippingAddress.equals(strBMAddress)) {
			logTestResult( "Step Description :SAP Order Overview Document PartnersTab - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :'Ship- To Party' address on SAP Order overview screen is matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address:" + strSAPShippingAddress,true);
			VerifyShippingAddress = true;
			return VerifyShippingAddress;
		}
		else
		{
			logTestResult("Step Description :SAP Order Overview Document PartnersTab - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :'Ship- To Party' address on SAP Order overview screen is not matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address in SAP:" + strSAPShippingAddress + ", Address from Blue Martini:" +  strBMAddress,false); 
			VerifyShippingAddress = false;
			testStatus=false;
			return VerifyShippingAddress;
		}
	}
	else {
		arrInternationalAddress = RetrieveInternationalAddressFromBlueMartini(strOrderNumber);
		
		if(arrInternationalAddress.length==0) {
			logTestResult( "Step Description :SAP Order Overview Document PartnersTab - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+"\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :Unable to retrieve address from Blue Martini for the order:" + strOrderNumber + ", && the plan shipment:" + strWebShipmentNumber,false);	
			VerifyShippingAddress = false;
			return VerifyShippingAddress;
		}
		String strAddressLine1 = arrInternationalAddress[0].trim().toUpperCase();
		String strAddressCity = arrInternationalAddress[3].trim().toUpperCase();
		if(!strSAPShippingAddress.contains(strAddressLine1) || !strSAPShippingAddress.contains(strAddressCity) ) {
			logTestResult( "Step Description :SAP Order Overview Document PartnersTab- Verify 'Ship- To Party' address is matching with the address from Blue Martini"+ "\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+ "\n Actual Results :'Ship- To Party' address on SAP Order overview screen is not matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address in SAP:" + strSAPShippingAddress + ", Address line 1 from Blue Martini:" +  strAddressLine1 + ", Address City from Blue Martini:" + strAddressCity,false); 
			VerifyShippingAddress = false;
			testStatus=false;
			return VerifyShippingAddress;
		}
		else
		{
		logTestResult( "Step Description :SAP Order Overview Document PartnersTab - Verify 'Ship- To Party' address is matching with the address from Blue Martini"+"\n Expected Results :'Ship- To Party' address on SAP Order overview screen should match with corresponding address from Blue Martini"+"\n Actual Results :'Ship- To Party' address on SAP Order overview screen is matching with corresponding address from Blue Martini. Order Number:" +strOrderNumber+ ", Web shipment number:" +strWebShipmentNumber + ", Address:" + strSAPShippingAddress,true);		
		VerifyShippingAddress = true;
		return VerifyShippingAddress;
		}
	
	}
}

VerifyShippingAddress = true;
return VerifyShippingAddress;

}
public String GetShippingAddressInPartnersTab_Value(int strRowNum, String ColumnName)
{
	SAPGuiTableControlTestObject test = partnertable_sap() ;
	ITestDataTable itemTable = (ITestDataTable) (test.getTestData("list"));
	//int intRowCount = itemTable.getRowCount();
	String strColumnValue="";
	//int articleCol = 0;
     for (int col=0; col < itemTable.getColumnCount();++col)
      {
    	 if (itemTable.getColumnHeader(col).equals(ColumnName)) {
    		 strColumnValue =(String) itemTable.getCell(strRowNum,col);
    		 logTestResult("Step Description :Capture value for: "+ColumnName+"\n Expected Results :Captured the value for "+ColumnName+" as: \n Actual Results :"+strColumnValue,true);
    	    break;
    	 }
 
	 }
      if (strColumnValue.equals(""))   
      {
    	  logTestResult("Step Description :Capture value for: "+ColumnName+"Failed to Capture as Unable to find the field :"+ColumnName,false);
      }
	 return strColumnValue.trim().toUpperCase();

}

public String GetSAPGuiTable_Value(int strRowNum, String ColumnName,SAPGuiTableControlTestObject test)
{
	
	ITestDataTable itemTable = (ITestDataTable) (test.getTestData("list"));
	//int intRowCount = itemTable.getRowCount();
	String strColumnValue="";
	//int articleCol = 0;
     for (int col=0; col < itemTable.getColumnCount();++col)
      {
    	 if (itemTable.getColumnHeader(col).equals(ColumnName)) {
    		 strColumnValue =(String) itemTable.getCell(strRowNum,col);
    		 logTestResult("Step Description :Capture value for: "+ColumnName+"\n Expected Results :Captured the value for "+ColumnName+" as: \n Actual Results :"+strColumnValue,true);
    	    break;
    	 }
 
	 } 
      if (strColumnValue.equals(""))   
      {
    	  logInfo("Step Description :Capture value for: "+ColumnName+"Failed to Capture as Unable to find the field :"+ColumnName);
      }
	 return strColumnValue.trim().toUpperCase();

}

public boolean VerifyCreditCard_SAPOrderScreenPaymentTab(String strSAPOrderNumber, String strOrderNumber, String[][] arrBMBillingDetails, String[][] arrBMOrderDetails)
{
	boolean blnStatus=false;
	SAPGuiTableControlTestObject table = table_paymentCards();
	ITestDataTable itemTable = (ITestDataTable) (table_paymentCards().getTestData("list"));
	int intRowCount = itemTable.getRowCount();
	for (int i=1; i < intRowCount-1;++i)
	{
		String strSAPCreditCard= GetSAPGuiTable_Value(i,"Type",table);
		String strSAPTokenNumber= GetSAPGuiTable_Value(i,"Card number",table);
		if(strSAPCreditCard.isEmpty()) break;
		blnStatus= VerifyCreditCard_SAPOrderScreen(strSAPOrderNumber, strOrderNumber, arrBMBillingDetails, arrBMOrderDetails,strSAPCreditCard,strSAPTokenNumber,true);
		if(!blnStatus) {
			logTestResult( "SAP Order Overview Document - PaymentTab -Verify payment type && corresponding auth number between SAP && Blue Martini for the SAP Order: " + strSAPOrderNumber+ "Payment type && auth number should match"+"Payment type && auth number are not matching between blue martini && SAP",false);
             testStatus=false;
		}
	}
	return blnStatus;
}
    
}
