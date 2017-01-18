@e2e-expressuser
Feature: Express User E2E order placing

 Scenario: Express User order placing E2E scenario
	Given Test data is read from excel file "JC_E2E_ExpressUser_Testdata_Sheet.xls"	
 	
 	And User goes to homepage
  	And User closes email capture
  	
  	When User clicks on sign in using header
  	And User enters login credentials  	
  	And This script cleans bag for current user
  	And User signs out using header
  	
  	When User selects country as per testdata
  	When User clicks on sign in using header
  	And User enters login credentials
  	
  	When User adds the products to bag as per testdata
  	
  	And User clicks in bag
  	
  	Then Verify shopping bag is displayed
  	And Verify products added matches with products in bag
  	
  	And Apply promos, if required. If applied, verify promos are applied successfully  	
  	
  	When User clicks on CHECK OUT NOW button or Express Paypal button
  	And User completes Paypal transaction, if required  	
  	Then Verify user is in review page
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	And User enters security code as per payment method, if required
  	
  	When User clicks on PLACE MY ORDER
  	Then Verify user is in order confirmation page
  	When User closes the Bizrate Popup
  	Then Verify user gets a confirmation number