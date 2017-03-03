@e2e @e2e-guestuser-7
Feature: Guest User 7 E2E order placing

 Scenario: Guest User 7 order placing E2E scenario
	Given Test data is read from excel file "JC_E2E_GuestUser_Testdata_Sheet_7.xls"	
 	
 	And User goes to homepage
  	And User closes email capture
  	
  	When User selects country as per testdata
  	
  	When User adds the products to bag as per testdata
  	And User adds gift cards to bag as per testdata
  	
  	And User clicks in bag
  	
  	Then Verify shopping bag is displayed
  	And Verify products added matches with products in bag
  	And Verify gift cards added matches with gift cards in bag
  	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User clicks in CHECK OUT NOW button
  	And User checks out as guest
    
    Then Verify Shipping Page is displayed
  	
  	When User enters Shipping Addresses as per testdata 	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to Shipping and Gift Options page
  	Then Verify Shipping And Gift Options page is displayed
  	
  	When User selects Shipping Methods as per testdata
  	And User select Gift Options as per testdata, if required
  	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to Payment Method page
  	Then Verify Billing page is displayed
  	
  	When User selects/enters Payment Methods as per testdata
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User continues to review page
  	Then Verify user is in review page
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User clicks on PLACE MY ORDER
  	Then Verify user is in order confirmation page
  	When User closes the Bizrate Popup
  	Then Verify user gets a confirmation number