@SaleRefinement @HighLevel
Feature: Sale Array - Refinement

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User clicks on sale link from top nav
    Then Verify sale landing page is displayed
    
    When User Selects random sale category from the list
    |women|
	|men|
	|girls|
	|boys|
	Then Verify Sale array page is displayed
	
  Scenario: Verify Sale Filters
  And Verify that filter options contains this list
      | Category 	|
      | Size     	|
      | Color    	|
      | Price    	|
      | New in Sale	|

  	


	
	