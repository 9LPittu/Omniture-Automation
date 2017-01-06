@e2e-registereduser
Feature: Registered User E2E order placing

 Scenario: Registered User order placing E2E scenario
	Given Test data is read from excel file "JC_E2E_RegisteredUser_Testdata_Sheet.xls"	
 	
 	And User goes to homepage
  	And User closes email capture
  	
  	When User clicks on sign in using header
  	And User enters login credentials  	
  	And This script cleans bag for current user
  	And User signs out using header
  	
  	When User selects country as per testdata
  	And User clicks on sign in using header
  	And User enters login credentials
  	
  	When User adds the products to bag as per testdata
  	And User adds gift cards to bag as per testdata
  	
  	And User clicks in bag
  	Then Verify products added matches with products in bag
  	And Verify gift cards added matches with gift cards in bag
  	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User clicks in CHECK OUT NOW button
  	
  	And Navigate to Shipping Address page, if user is on Review page  	
  	Then Verify select shipping address page is displayed
  	
  	When User selects Shipping Addresses as per testdata  		  	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to Shipping and Gift Options page
  	Then Verify Shipping And Gift Options page is displayed
  	
  	When User selects Shipping Methods as per testdata  	
  	#And Select Gift Receipt as per testdata, if required
  	#And Select Gift Wrapping as per testdata, if required
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to Payment Method page
  	Then Verify Billing page is displayed
  	
  	When User selects Payment Methods as per testdata  	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to review page
  	Then Verify user is in review page
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	And User enters security code as per payment method, if required
  	
  	When User clicks on PLACE MY ORDER
  	Then Verify user is in order confirmation page
  	Then Verify user gets a confirmation number