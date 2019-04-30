@s_omnitureJcrew
Feature: Omniture Verification

  
  Scenario: Verify Omniture on Home page 
  	Given User goes to homepage
    And User closes email capture
    Then Verify omniture variables have values 
  
  Scenario: Verify Omniture on Array page
  	Given User goes to homepage
    And User closes email capture            
    When User hovers on a random category and subcategory from list
      |Women|sweaters|
    Then Verify user is in category array page
    And Verify omniture variables have values
    
    Scenario: Verify Omniture on PDP page 
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |Men|shirts|
    Then Verify user is in category array page
    When User selects first product from product array
    And Verify product detail page is displayed
    And Verify omniture variables have values
   
  Scenario: Verify Omniture variables for Checkout / Shopping bag page - Registered user
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form and signs in
    And This script cleans bag for current user    
    And User goes to homepage
    When User hovers on a random category and subcategory from list
      |Women|sweaters|
    When User selects first product from product array
    And Verify product detail page is displayed    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    When User clicks in bag
	  And Verify omniture variables have values

 Scenario: Verify Omniture variables for Checkout / Shopping bag page - Guest User
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |Women|sweaters|
    When User selects first product from product array
    And Verify product detail page is displayed    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    When User clicks in bag
	  And Verify omniture variables have values

	Scenario: Verify Omniture variables for Search array page
    Given User goes to homepage
    And User closes email capture
		When User searches for the item Shirts
  	And Verify omniture variables have values
  
  Scenario: Verify Omniture variables for Sale category
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |sale|Shop All|
   	Then Verify user is in category array page
    And Verify omniture variables have values
  
  Scenario: Verify Omniture variables for Crewcuts
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |new|for Girls|
   	Then Verify user is in category array page
   	 When User selects first product from product array
    And Verify product detail page is displayed
    And Verify omniture variables have values
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  #Scenario: Verify Omniture variables for review page
    #Given User goes to homepage
    #And User closes email capture
    #When User clicks on sign in using header
    #And User fills form and signs in
    #And This script cleans bag for current user    
    #And User goes to homepage
    #When User hovers on a random category and subcategory from list
      #|Women|sweaters|
    #When User hovers on a random category from list
    #	|Women|
   #And User selects a subcategory
    #And User selects sweaters subcategory array
    #When User selects first product from product array
    #And Verify product detail page is displayed    
    #When User selects random color
    #And User selects random size        
    #And User adds product to bag
    #When User clicks in bag
    #And User clicks in CHECK OUT NOW button    
    #Then Verify user is in review page
    #And Verify checkout breadcrumb is REVIEW
    #And Verify omniture variables have values	

         
   
  #Scenario: Verify Omniture on Women Category page
  #	Given User goes to homepage
    #And User closes email capture            
    #When User hovers on a random category and subcategory from list
      #|Women|Sweaters|
    #Then Verify user is in category array page
    #And Verify omniture variables have values
    
  #Scenario: Verify Omniture on Men Category page
  #	Given User goes to homepage
    #And User closes email capture
    #When User hovers on a random category and subcategory from list
      #|men|slim shop|
    #Then Verify user is in category array page
    #And Verify omniture variables have values
    #
  #Scenario: Verify Omniture on Girls Category page
    #Given User goes to homepage
    #And User closes email capture
    #When User hovers on a random category and subcategory from list
      #|girls|dresses|
    #Then Verify user is in category array page
    #And Verify omniture variables have values
    #
  #Scenario: Verify Omniture on Boys Category page
  #	Given User goes to homepage
    #And User closes email capture
    #When User hovers on a random category and subcategory from list
      #|boys|sweaters|
    #Then Verify user is in category array page
    #And Verify omniture variables have values
#
 #Scenario: Verify Omniture on PDP page 
    #Given User goes to homepage
    #And User closes email capture
    #When User hovers on a random category and subcategory from list
      #|girls|dresses|
    #Then Verify user is in category array page
    #When User selects first product from product array
    #And Verify product detail page is displayed
    #And Verify omniture variables have values
