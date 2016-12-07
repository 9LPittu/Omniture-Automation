@E2E-RegisteredUser
Feature: Registered User E2E scenario

 Scenario: Registered User E2E scenario
	Given test data is read from excel file "E2E_RegisteredUser_Testdata_Sheet.xls"
 	
 	And User goes to homepage
  	And User closes email capture
  	
  	When User selects country as per testdata
  	
  	When User clicks on sign in using header
  	And User enters login credentials
  	
  	And This script cleans bag for current user
  	
  	When User adds the products to bag as per testdata
  	
  	When User clicks in bag
  	Then Verify products added matches with products in bag
  	
  	And apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User clicks in CHECK OUT NOW button
  	And Navigate to Shipping Address page, if required
  	Then Verify select shipping address page is displayed
  	
  	When User selects Shipping Address as per testdata  	
  	And apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to Shipping and Gift Options page
  	Then Verify Shipping And Gift Options page is displayed
  	
  	When User selects Shipping Method as per testdata
  	And Select Gift Receipt as per testdata, if required
  	And Select Gift Wrapping as per testdata, if required
  	And apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to Payment Method page
  	Then Verify Billing page is displayed
  	
  	When User selects Payment Method as per testdata
  	And apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to review page
  	Then Verify user is in review page
  	And apply promos, if required. If applied, verify promos are applied successfully
  	
  	And User enters security code as per payment method, if required
  	
  	When User clicks on PLACE MY ORDER
  	Then Verify user is in order confirmation page
  	And Verify that title is Order Complete