@FactoryLinkInHeader
Feature: Factory link validation in header

   Background:
	 Given User goes to homepage
     And User closes email capture
   
   Scenario: Factory link validation on home page
     And factory link should be displayed in the header
     
   Scenario: Factory link validation on sign in/login page
   	  When User clicks on sign in using header
   	  And factory link should be displayed in the header

   Scenario: Factory link validation on My Account pages
   	 When User clicks on sign in using header
     And User fills user data and signs in
     Then Verify user is in My Account main page
     And factory link should be displayed in the header     
     When User goes to MY DETAILS page using My Account left nav
     And factory link should be displayed in the header
     When User goes to EMAIL PREFERENCES page using My Account left nav
     And factory link should be displayed in the header
     When User goes to CATALOG PREFERENCES page using My Account left nav
     And factory link should be displayed in the header
     When User goes to PAYMENT METHODS page using My Account left nav
     And factory link should be displayed in the header
     When User goes to ADDRESS BOOK using My Account left nav
     And factory link should be displayed in the header
     When User goes to ORDER HISTORY using My Account left nav
     And factory link should be displayed in the header
     When User goes to WISHLIST using My Account left nav
     And factory link should be displayed in the header
    
   Scenario: Factory link validation on shopping bag and checkout pages
   	 When User adds to bag a random product using a main category
   	 And factory link should be displayed in the header
     And User clicks in bag
     And factory link should NOT be displayed in the header
     And User clicks check out button
     And factory link should be displayed in the header
     And User selects guest check out
     And factory link should NOT be displayed in the header
     And Guest user fills shipping address and continue
     And factory link should NOT be displayed in the header
     And User selects random shipping method and continue
     And factory link should NOT be displayed in the header
     And User fills payment method and continue
     And factory link should NOT be displayed in the header
     And User reviews and places order
     And factory link should NOT be displayed in the header
     Then User gets an order confirmation number
   
   Scenario: Source code validation for for factory.jcrew.com page
   	 When user clicks on 'Looking for Factory?' link from header
   	 Then user should see a new tab opened with url as https://factory.jcrew.com/?srcCode=JCNav
   	 Then source code should have https://factory.jcrew.com/?srcCode=JCNav	 