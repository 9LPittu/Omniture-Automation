@e2e @e2e-nonexpressuser
Feature: Non Express User E2E order placing

 Scenario: Non Express User order placing E2E scenario
	Given Test data is read from excel file "JC_E2E_NonExpressUser_Testdata_Sheet.xls"	
 	 	
 		And User goes to homepage
  	And User closes email capture
  	
  	When User clicks on sign in using header
  	And User enters login credentials  	
  	And This script cleans bag for current user
  	And User signs out using header
#  	
  	When User selects country as per testdata
  	
  	When User clicks on sign in using header
  	And User enters login credentials
  	
  	When User adds the products to bag as per testdata
  	And User adds gift cards to bag as per testdata
  	
  	And User clicks in bag
  	
  	Then Verify shopping bag is displayed
  	And Verify products added matches with products in bag
  	And Verify gift cards added matches with gift cards in bag
  	
  	And Apply promos, if required. If applied, verify promos are applied successfully
  	
  	When User clicks in CHECK OUT NOW button
  #	And User checks out as guest
    
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
  	When I opened email in browser
 	And I validate Order Number In Email
 	Then Verify user is in mail confirmation page
 	
 	
  	Scenario: Non Express User Forgot Password scenario
	And User goes to homepage
  	And User closes email capture
  	When User clicks on sign in using header
  	And Enter Email credentials
  	And Click on Forgot Password
  	When I opened email in browser
 	Then Verify_user_is_in_ForgotPassword_mail_Confirmation_Page
  	
  	
  	Scenario: Non Express User Update Password scenario
	And User goes to homepage
  	And User closes email capture
  	When User clicks on sign in using header  	
  	And User enters credentials
  	When User goes to My Details from header
  	Then Verify user is in Account Details page
  	When User Reset new password
  	Then Verify Update Password Success Message
  	When I opened email in browser
 	Then Verify_user_is_in_UpdatePassword_mail_Confirmation_Page

Scenario: Non Express User Update User ID scenario
	And User goes to homepage
  	And User closes email capture
  	When User clicks on sign in using header
  	And User enters credentials
  	When User goes to My Details from header
  	Then Verify user is in Account Details page
    When User Update User ID
    Then Verify Updated UserID Success Message
  	When I opened email in browser
 	Then Verify_user_is_in_Update UserID_mail_Confirmation_Page