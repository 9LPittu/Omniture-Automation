package Sap_login;

//import com.jcrew.pojo.ShippingMethod;

//import com.jcrew.pojo.Product;
//import com.jcrew.utils.StateHolder;
//import com.jcrew.utils.TestDataReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

//import com.rational.test.ft.script.*;
//import  com.rational.test.ft.script.RationalTestScript;

public class BMOrderDetails extends SapValidationHelper{

  //  StateHolder stateHolder = StateHolder.getInstance();
    //TestDataReader dataReader = TestDataReader.getTestDataReader();
    //private Logger logger = LoggerFactory.getLogger(BMOrderDetails.class);
    //final PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
    String [] arrInputCompareFailedOrders;

    public BMOrderDetails() {

 
    }

	public String[][] getBMOrderDetails(String strOrderNumber, String carrierCode){
		try {
			//String schema = dbReader.getProperty("schema");
			//String dbQuery = dbReader.getProperty("order.query");
			//dbQuery= "select * from MWINT3_STORE.ORDER_LINE ODETAIL";
			//dbQuery = dbQuery.replaceAll("schema",schema);
			//dbQuery = dbQuery.replaceAll("ordNum",ordNum);
			//dbQuery = dbQuery.replaceAll("carriername",carrier);

			//String strEnvironment = null;
			String strRetrievalType = null;
			String strEnvironment = dbReader.getProperty("BMEnvironment");
			String dbQuery =Create_Sql_Query_For_BM_OrderDetail_Retrieval(strEnvironment,strOrderNumber,"OrderLine","");
			logTestResult("Create Query to retrieve order:" + dbQuery ,true);

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
			ResultSet rs = stmt.executeQuery(dbQuery);
			String[][] arrBMOrderDetails = null;
			rs.last();
		    int rowNumb = rs.getRow();
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnS = rsmd.getColumnCount();

		    rs.beforeFirst();
		    arrBMOrderDetails= new String[rowNumb][columnS];
		    
			/*if (rs.next()){
				logTestResult( "Step Description :Retrieve details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :Order details should be retrieved from BM database"+ "\n Actual Results :Order: " + strOrderNumber + " details does not exist in Blue Martini database. Please verify",false);
		
			}
			else {
			logTestResult( "Step Description :Retrieve details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :Order details should be retrieved from BM database"+ "\n Actual Results :Order: " + strOrderNumber + " details are retrieved from Blue Martini database",true);
			}*/

		    int i=0;
		    while(rs.next() && i<rowNumb && rowNumb<100)
		    {
		        for(int j=0;j<columnS;j++)
		        {
		        	arrBMOrderDetails[i][j] = rs.getString(j+1);
		        }
		        i++;
		    }
		    System.out.println(arrBMOrderDetails.length);
		    logTestResult( "Step Description :Retrieve details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :Order details should be retrieved from BM database"+ "\n Actual Results :Order: " + strOrderNumber + " details are retrieved from Blue Martini database",true);
			databaseReader.closeConnection(conn);
			return arrBMOrderDetails;
		} catch (Exception e) {
			logTestResult( "Step Description :Retrieve details for the Order: " + strOrderNumber + " from Blue Martini"+ "\n Expected Results :Order details should be retrieved from BM database"+ "\n Actual Results :Order: " + strOrderNumber + " details does not exist in Blue Martini database. Please verify",false);
			//logger.error("Unable to run query for retrieving Start and End date the shipping method {}", carrierCode);
			return null;
		}
	}
	
	public String Create_Sql_Query_For_BM_OrderDetail_Retrieval(String  strEnvironment,String strOrderNumber, String strRetrievalType, String WebShipmentNumber)
	{
		
	if( strEnvironment == "" || strOrderNumber == "" ) {
		logTestResult( "Create Query to retrieve order:" + strOrderNumber + " details from BM database"+ "Query should be created"+ "Either order number or Environment is blanlk. Order Number: " + strOrderNumber + ", Environment: " + strEnvironment, false);
		return "";
	}
	
	String strSchema = null;
	if (strEnvironment.trim().toUpperCase().equals("STEEL"))  {
		if (strOrderNumber.trim().substring(0, 1).equals( "3"))
			strSchema = "JCINT3";
		else if(strOrderNumber.trim().substring(0, 1).equals( "5") )
			strSchema = "MWINT2"	;
	}
	else if(strEnvironment.trim().toUpperCase().equals("PLATINUM"))   {
		if (strOrderNumber.trim().substring(0, 1).equals( "3" ))
			strSchema = "JCINT";
		else if(strOrderNumber.trim().substring(0, 1).equals( "5") )
			strSchema = "MWINT3";	
	}
	else
	{
	  logTestResult("Create Query to retrieve order:" + strOrderNumber + " details from BM database"+ "Query should be created"+ "BM Query can be created only for Titanium and Platinum environments. But, the environment provided here is :" + strEnvironment,false);
		return "";
	}
	String strBlueQry = null;
	if( strRetrievalType.equals("OrderLine" ))
	{
		strBlueQry = dbReader.getProperty("order.query");
	    strBlueQry = strBlueQry.replaceAll("schema",strSchema);
	    strBlueQry = strBlueQry.replaceAll("ordNum",strOrderNumber);
	}
	else if(strRetrievalType.equals("OrderLineRET" ))
		strBlueQry = dbReader.getProperty("OrderLineRET.query");		
	
	
	else if( strRetrievalType.equals("Billing")) {
		strBlueQry = dbReader.getProperty("order.billing"); 
	    strBlueQry = strBlueQry.replaceAll("schema",strSchema);
	    strBlueQry = strBlueQry.replaceAll("ordNum",strOrderNumber);
	}
	else if(strRetrievalType.equals( "Article.webshipment")) {

		strBlueQry = dbReader.getProperty("article.webshipment.query");
		strBlueQry = strBlueQry.replaceAll("schema",strSchema);
		strBlueQry = strBlueQry.replaceAll("ordNum",strOrderNumber);
		strBlueQry = strBlueQry.replaceAll("WebShipmentNumber",WebShipmentNumber);

			
   }
	else if (strRetrievalType.equals("Article"  )) {
		strBlueQry = dbReader.getProperty("article.query");
		strBlueQry = strBlueQry.replaceAll("schema",strSchema);
		strBlueQry = strBlueQry.replaceAll("ordNum",strOrderNumber);
		}
	else if (strRetrievalType.equals("ArticleWithStatus"  ))
		strBlueQry = dbReader.getProperty("ArticleWithStatus.query");
	else if(strRetrievalType.equals( "InternationalShippingAddress"))
		strBlueQry = dbReader.getProperty("InternationalShippingAddress.query");
	else if(strRetrievalType.equals("PaypalAuth")) 
	{
		strBlueQry = dbReader.getProperty("PaypalAuth.query");
	    strBlueQry = strBlueQry.replaceAll("schema",strSchema);
	    strBlueQry = strBlueQry.replaceAll("ordNum",strOrderNumber);
	}
	  return strBlueQry;

	}
	
	public boolean func_Compare_OrderInput_BlueMartini(String strOrderNumber, String strCountry, String strProductCode,String strProductColor,String  strProductSize,String strProductQuantity,String strPromoCode,String strShippingMethod, String strPayment1,String strPayment2,String strMonogram, String strGiftWrap, String[][] arrBMOrderDetails, String[][] arrBMBillingDetails)
	{
	boolean func_Compare_OrderInput_BlueMartini=false;
	if(strOrderNumber.equals("") || strProductCode.equals("")) {
		String strErrMsg = "Unable to retrieve order details from input sheet. order number/product code is blank. Order Number:" + strOrderNumber + ", Product Code:" + strProductCode + ", Product Color:" + strProductColor + ", Product Size:" + strProductSize;
		logTestResult( "Step Description :Compare order details from input sheet against order details from blue martini"+ "\n Expected Results :Order details should match"+ "\n Actual Results :"+strErrMsg,false);
		func_Compare_OrderInput_BlueMartini=false;
		func_add_order_to_distribution_list(strOrderNumber,strErrMsg);
		return func_Compare_OrderInput_BlueMartini;
	}
	
	
	//Retrieve variant and allocation details for all the available products
	String[][] arrVariantAndAllocation = func_retrieve_variant_and_allocation(strOrderNumber,strProductCode,strProductColor,strProductSize,strProductQuantity);
	if(!(arrVariantAndAllocation.length==0)) {
		logTestResult( "Step Description :Retrieve variant and allocation details from Item Master sheet"+"\n Expected Results :variant and allocation should be retrieved"+ "\n Actual Results :Unable to retrieve variant and allocation details from Item Master sheet for the order:" + strOrderNumber,false);
		func_Compare_OrderInput_BlueMartini = false;
		return func_Compare_OrderInput_BlueMartini;
	}
	
	//Compare Number of items and corresponding allocation
	boolean blnStatus = func_VerifyItemsAndAllocation(arrVariantAndAllocation,arrBMOrderDetails);
	if(!blnStatus) {
		logTestResult( "Step Description :Verify item Allocation and Quantity are matching between Order input sheet and Blue Martini for the order:" + strOrderNumber+ "\n Expected Results :Allocation and Quantity should match between Order input sheet and Blue Martini"+"\n Actual Results :Allocation and Quantity are not matching between Order input sheet and Blue Martini for the order:" + strOrderNumber,false);
		func_Compare_OrderInput_BlueMartini = false;
		return func_Compare_OrderInput_BlueMartini;
	}
	
	
	//Compare Promo code, shipping method, monogram and Gift Wrap
	blnStatus = func_VerifyShippingAndOtherOptions(strOrderNumber, strPromoCode, strShippingMethod, strMonogram, strGiftWrap, arrBMOrderDetails);
	if(!blnStatus) {
		logTestResult( "Step Description :Verify item promo code, shipping method, monogram and gift wrap details are matching between order input sheet and blue martini"+ "\n Expected Results :Details should match between order input sheet and blue martini"+"\n Actual Results :There are one or more mismatches between order input sheet and blue martini database",false);
		func_Compare_OrderInput_BlueMartini = false;
		return func_Compare_OrderInput_BlueMartini;
	}
	
	
	//Compare Payment details
	//blnStatus = func_VerifyPayment(strOrderNumber,strCountry, strPayment1, strPayment2, arrBMBillingDetails);
	if(!blnStatus) {
		func_Compare_OrderInput_BlueMartini = false;
		return func_Compare_OrderInput_BlueMartini;
	}
	return blnStatus;
	

	
}


public void func_add_order_to_distribution_list(String strOrderNumber, String strErrMsg)
{

	try {
	if(arrInputCompareFailedOrders[0].length()==0) 
		arrInputCompareFailedOrders= new String[1];
	else
	{
		int intArrayLength = arrInputCompareFailedOrders.length-1;
		arrInputCompareFailedOrders=new String[intArrayLength+1];
	}
	
	int intLength = arrInputCompareFailedOrders.length-1;
	arrInputCompareFailedOrders[intLength] = strOrderNumber + "|" + strErrMsg;
	}
	catch(Exception e){
		}

	
}

	public String[][] func_retrieve_variant_and_allocation(String strOrderNumber,String strProductCode,String strProductColor,String strProductSize,String strProductQuantity)
	{
	//Dim arrItemDetails()
		String[] ArrProductCodes;
		String[] ArrProductColor = null;
		String[] ArrProductSize = null;
		String[] ArrProductQuantity = null;
	if(!strProductCode .equals("")) 
		ArrProductCodes = strProductCode.split(":");
	else {
		logTestResult( "Step Description :Retrieve Product codes from test input sheet"+ "\n Expected Results :Product codes should be retrieved"+"\n Actual Results :Unable to retrieve product codes from test input sheet",false);
		return null;
	}
	
	if(ArrProductCodes[0].trim().length() != 5 ){
		if(!strProductColor.equals(""))  
			ArrProductColor = strProductColor.split(":");
		else
		{
			logTestResult( "Step Description :Retrieve Products color from test input sheet"+"\n Expected Results :Products color should be retrieved"+ "\n Actual Results :Unable to retrieve products color from test input sheet",false);
			return null;
		}
		
		
		if(!strProductSize.equals(""))
			ArrProductSize = strProductSize.split(":");
		else
		{
			logTestResult( "Step Description :Retrieve Products size from test input sheet"+ "\n Expected Results :Products size should be retrieved"+ "\n Actual Results :Unable to retrieve products size from test input sheet",false);
			return null;
		}
	}
	
	if(!strProductQuantity.equals(""))
		ArrProductQuantity = strProductQuantity.split(":");
	
	
	int intItemCount = ArrProductCodes.length;
	String[][] arrItemDetails = new String[intItemCount][2];
	for( int intCounter =  0; intCounter<ArrProductCodes.length;intCounter++) {
		String strProduct = ArrProductCodes[intCounter].trim();
		String strColor = null,strSize = null;
		int intQuantity;
		if(ArrProductQuantity.length>0 && intCounter <= ArrProductQuantity.length)
			intQuantity = Integer.parseInt(ArrProductQuantity[intCounter]);
		else
			intQuantity =1;
		Map<String, Object> rsItemDetails ;
		if(strProduct.trim().length()<= 5) {
			strColor = ArrProductColor[intCounter];
			strSize = ArrProductSize[intCounter];
			
			String strFilter = "ProductCode='" + strProduct + "' and ProductSize='" + strSize + "' and ProductColor='" + strColor + "'";
			 rsItemDetails =  getItemDetailsFromSheet(strFilter);
 
		}
        else
        	 rsItemDetails =  getItemDetailsFromSheet("Variant='" + strProduct + "'");
    		//rsItemDetails =  gf_getDataFromSheet(Environment("eComItemMasterSheet")+E2E_ITEMS", "Variant='" + strProduct + "'");
    	
    	if(rsItemDetails.size() < 1) {
    		logTestResult( "Step Description :Retrieve variant and allocation details from Item Master sheet"+"\n Expected Results :variant and allocation should be retrieved"+ "\n Actual Results :Unable to retrieve product details for the ProductCode=" + strProduct + " and ProductSize=" + strSize + " and ProductColor=" + strColor,false);
    		return null;
    	}
    	String strVariant = (String) rsItemDetails.get("Variant");
    	String strAllocation = (String)rsItemDetails.get("Expected Allocation");
         
    	
    	arrItemDetails[intCounter][0] = strVariant;
    	arrItemDetails[intCounter][1] = strAllocation;
    	arrItemDetails[intCounter][2] = String.valueOf(intQuantity);
    	

	}

return arrItemDetails;

}
	
	public Map<String, Object> getItemDetailsFromSheet(String strFilter)
	{
		//String strState;
		String strSheetName;

	//String strFilter = "ProductCode='" + strProduct + "' and ProductSize='" + strSize + "' and ProductColor='" + strColor + "'";
	String strTestPath = dbReader.getProperty("eComItemMasterSheet");


		strSheetName= "E2E_ITEMS" ;
			

		ExcelUtils testDataReader = null;
		try {
				testDataReader = new ExcelUtils(strTestPath, strSheetName, strFilter);
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		Map<String, Object> rsTestData = null;
		
		
		int j = testDataReader.getSearchTextFirstRowNum();
		//int maxRowCount = testDataReader.getSearchTextLastRowNum();
		
		//while(j<=maxRowCount){
			try{
				rsTestData = testDataReader.getDataFromExcel(j);
				
			}
			catch (IOException e) {
				e.printStackTrace();
				j++;
			}	
			
		//}
		
		return rsTestData;
	}

public boolean func_VerifyItemsAndAllocation(String[][] arrVariantAndAllocation, String[][] arrBMOrderDetails)
{	
	//Retrieve Brand from Blue Martini order details array
	boolean func_VerifyItemsAndAllocation=false;
	String strLyhAllocationCode = null;
	String strBrand = arrBMOrderDetails[0][45].trim();
	if (strBrand.trim().toUpperCase().equals("MADEWELL"))
		strLyhAllocationCode = "LBDM";
	else if (strBrand.trim().toUpperCase().equals("FACTORY"))
		strLyhAllocationCode = "LBDF";
	else if (strBrand.trim().toUpperCase().equals("JCREW"))
	    strLyhAllocationCode = "LBDI";
	
	for (int i = 0; i < arrVariantAndAllocation.length; i++) {
		String strInputVariant = arrVariantAndAllocation[i][0].trim().toUpperCase();
		String strInputAllocation = arrVariantAndAllocation[i][1].trim().toUpperCase();
		String strInputQuantity = arrVariantAndAllocation[i][2].trim().toUpperCase();
		
		int intInstances = 0;
		for (int j = 0; i < arrBMOrderDetails.length; j++) {
			String strBMItem = arrBMOrderDetails[j][6].trim().toUpperCase();
			String strAllocation = arrBMOrderDetails[j][11].trim().toUpperCase();
			String strBMFulfilmentLocation = arrBMOrderDetails[j][6].trim().toUpperCase();
			
			if(!strBMFulfilmentLocation .equals(""))
				strAllocation = strBMFulfilmentLocation;
		
			
			if(strAllocation.equals(strLyhAllocationCode)||strAllocation.equals("LDC")||strAllocation.equals("LYN"))
				strAllocation = "LYH";
			else 
				if( strAllocation.substring(0,1).equals("0")) 
				strAllocation = "RTL";
	
			
			if(strInputVariant.equals(strBMItem)) {
				intInstances = intInstances +	1;
				if(! strInputAllocation.equals(strAllocation)) {	
					logTestResult( "Step Description :Verify Allocation for the variant: " + strInputVariant + " in Blue Martini"+ "\n Expected Results :Item should be allocated to: " + strInputAllocation+ "\n Actual Results :Item is allocated to : " + strAllocation,false);
					return false;
						
				}
			}		
		}
		
		//Verify Quantity of the variant
		if(Integer.parseInt(strInputQuantity)== intInstances) {
			logTestResult("Step Description :Verify Quantity and Allocation for the item: " + strInputVariant+ "\n Expected Results :Quantity and Allocation should match between Order Input Sheet and Blue Martini"+ "\n Actual Results :Quantity and allocation are matching for the item: " + strInputVariant + " +Quantity: " + strInputQuantity + " +Allocation: " + strInputAllocation,true);
			func_VerifyItemsAndAllocation = true;
		}
		else
		{
			logTestResult( "Step Description :Verify Quantity for the item: " + strInputVariant+ "Quantity should match between Order Input Sheet and Blue Martini"+ "\n Actual Results :Quantity is not matching for the item: " + strInputVariant + " +Expected Quantity: " + strInputQuantity + " + Actual Quantity: " + intInstances,false);
			func_VerifyItemsAndAllocation = false;
		}
		
	}
	return func_VerifyItemsAndAllocation;
	
}


public boolean func_VerifyShippingAndOtherOptions(String strOrderNumber, String strPromoCode, String strShippingMethod, String strMonogram, String strGiftWrap,String[][] arrBMOrderDetails)
{
	String strCountryCode = arrBMOrderDetails[0][43];
	boolean func_ShippingPaymentAndOtherOptions;
	
	//Verify Promo code
	if(!strPromoCode.equals("")) {
		String[] arrPromoCode = strPromoCode.split(":");
		for (int intCounter = 0; intCounter < arrPromoCode.length; intCounter++) {
			boolean blnPromo= false;
			strPromoCode = arrPromoCode[intCounter].trim().toUpperCase();
			for (int i = 0; i < arrBMOrderDetails.length; i++) {
				String strBMPromo1 = arrBMOrderDetails[i][25].trim().toUpperCase();
				String strBMPromo2 = arrBMOrderDetails[i][40].trim().toUpperCase();
				if(strPromoCode.equals(strBMPromo1) ||strPromoCode.equals(strBMPromo2)){
					blnPromo = true;
					break;
				}
			}
			
			if(!blnPromo){
				logTestResult( "Step Description :Verify Promo code: " + strPromoCode + " is displayed in Blue Martini for the order: " + strOrderNumber+ "Promo code should be displayed"+ "Promo code: " + strPromoCode + " is NOT displayed in Blue Martini for the order: " + strOrderNumber,false);
				func_ShippingPaymentAndOtherOptions= false;
				return func_ShippingPaymentAndOtherOptions;
			}
		
		}
	}
	
	//Verify shipping method
	String strAllShipMethods = "";
	if(!strShippingMethod .equals("")) {
		boolean blnShipMethod =false;
		strShippingMethod = strShippingMethod.trim().toUpperCase();
		for (int i = 0; i < arrBMOrderDetails.length; i++) {
			String strBMShipMethod,strBMShipMethodNoSpace = null;
			if(strCountryCode.equals("US")) 	
				strBMShipMethod = arrBMOrderDetails[i][21].trim().toUpperCase();
			else
			{
				strBMShipMethod = arrBMOrderDetails[i][38].trim().toUpperCase() + " (" + arrBMOrderDetails[i][39].trim().toUpperCase() + " )";
				strBMShipMethodNoSpace = arrBMOrderDetails[i][38].trim().toUpperCase() + " (" + arrBMOrderDetails[i][39].trim().toUpperCase() + ")";
			}			
			if(strShippingMethod.equals(strBMShipMethod)||strShippingMethod.equals(strBMShipMethodNoSpace)) {
				blnShipMethod = true;
				break;
			}
			else
				if(strAllShipMethods.contains(strBMShipMethod))
					strAllShipMethods = strAllShipMethods + strBMShipMethod + "+";	

		}
		
		if(!blnShipMethod) 
			if(strAllShipMethods .equals("")) {
				strAllShipMethods = strAllShipMethods.substring(0,strAllShipMethods.length()-1);
			
			logTestResult( "Step Description :Verify Shipping method: " + strShippingMethod + " is displayed in Blue Martini for the order: " + strOrderNumber+ "\n Expected Results :\n Expected Results :Shipping method should be displayed"+ "\n Actual Results :Shipping method: " + strShippingMethod + " is NOT displayed in Blue Martini for the order: " + strOrderNumber + ". Shipping methods displayed on the order are:" + strAllShipMethods,false);
			func_ShippingPaymentAndOtherOptions= false;
			return func_ShippingPaymentAndOtherOptions;
		}
	}
	
	
	//verify monogram
	if(strMonogram.toUpperCase().contains("YES")) {
		boolean blnMonogram =false;
		for (int i = 0; i < arrBMOrderDetails.length; i++) {
			String strBMMonogram = arrBMOrderDetails[i][28].trim().toUpperCase();	
			if(!strBMMonogram .equals("") && !strBMMonogram.trim().toUpperCase() .equals("NULL")) {
				blnMonogram = true;
				break;
			}
		}
		
		if(!blnMonogram ) {
			logTestResult( "Step Description :Verify Monogram is displayed in Blue Martini for the order: " + strOrderNumber+ "\n Expected Results :Monogram should be displayed"+ "\n Actual Results :Monogram is NOT displayed in Blue Martini for the order: " + strOrderNumber,false);
			func_ShippingPaymentAndOtherOptions= false;
			return func_ShippingPaymentAndOtherOptions;
		}
	}

	
	
	//verify gift box
	if(strGiftWrap.toUpperCase().contains("YES")) {
		boolean blnGiftWrap= false;
		for (int i = 0; i < arrBMOrderDetails.length; i++) {
			String strBMGiftWrap = arrBMOrderDetails[i][29].trim().toUpperCase();	
			if(!strBMGiftWrap .equals("") && !strBMGiftWrap.trim().toUpperCase().equals("NULL")) {
				blnGiftWrap = true;
				break;
			}
		}
		
		if(! blnGiftWrap) {
			logTestResult( "Step Description :Verify GiftWrap is displayed in Blue Martini for the order: " + strOrderNumber+ "\n Expected Results :GiftWrap should be displayed"+ "\n Actual Results :GiftWrap is NOT displayed in Blue Martini for the order: " + strOrderNumber,false);
			func_ShippingPaymentAndOtherOptions= false;
			return func_ShippingPaymentAndOtherOptions;
		}
	}
	
	logTestResult( "Step Description :Verify promo code+ Shipping method+ Monogram and Giftwrap from Input sheet are matching with corresponding details from Blue Martini"+ "\n Expected Results :Details should match"+ "\n Actual Results :Promo code+ Shipping method+ Monogram and Giftwrap options are matching between Input sheet and blue martini",true); 
	return func_ShippingPaymentAndOtherOptions = true;

}


public void func_VerifyPayment(String strOrderNumber,String strCountry,String strPayment1,String  strPayment2,String [][]arrBMBillingDetails)
{
}
	
	
	

}