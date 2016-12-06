@s.pageName
Feature: Verify Omniture variables

  Scenario: Verify s.pageName value on Home, Category pages and PDP
    Given User is on homepage with clean session
    And User closes email capture
    Then Verify omniture variables have values

	When User hovers on a random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array
	And Verify omniture variables have values
	
	When User selects random product from product array
    Then Verify product detail page is displayed
    And Verify omniture variables have values

  Scenario: Verify s.pageName value on Sale and Search pages
	Given User is on homepage with clean session
    And User closes email capture
    
    When User clicks on sale link from top nav
    Then Verify sale landing page is displayed
    And Verify omniture variables have values
    
    When User Selects random sale category from the list
    |women|
	|men|
	|girls|
	|boys|
	Then Verify Sale array page is displayed
	And Verify omniture variables have values

  Scenario: Verify s.pageName value on Landing pages
    Given User is on homepage with clean session
    And User closes email capture
    
    When User clicks on random link from top nav
    And Verify omniture variables have values

  Scenario: Verify s.pageName value on Feature pages and shoppable tray
    Given User is on homepage with clean session
    And User closes email capture
	
	When User selects a random feature page from list
	|Women|Looks We Love| 
	|Girls|Looks We Love| 
	|Boys|Looks We Love|	
	Then Verify omniture variables have values
    
    When User selects a random shop this look
    Then Verify omniture variables have values	

  Scenario Outline: Verify s.pageName value on Category page and PDP with direct navigation
    Given User navigates to <page name> with clean session
    And User closes email capture
    And Verify omniture variables have values

    Examples:
      |page name|
      |category page|
      |pdp          |