@E2E-RegisteredUser
Feature: Registered User E2E order placing

 Scenario: Registered User order placing E2E scenario
	Given Test data is read from excel file "E2E_RegisteredUser_Testdata_Sheet.xls"	
 	
 	And User goes to homepage
  	And User closes email capture
  	
  	When User selects country as per testdata
  	Then User should land on country specific home page
  	
  	When User clicks on sign in using header
  	And User enters login credentials
  	
  	And This script cleans bag for current user
  	
  	When User adds the products to bag as per testdata
  	
  	When User clicks in bag
  	Then Verify products added matches with products in bag
  	
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
  	And Verify that title is Order Complete