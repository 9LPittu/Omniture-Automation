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
	
  Scenario: Select Single Refinement and verify Product count    
  When User refines using any of this options
      | Category 	|
      | Color    	|
      | Price    	|
      | New in Sale	|
  Then Verify selected refinement is displayed in header
  And Verify item count matches selected refinement
  
  When User selects a second option from previously selected filter
  And Verify item count matches selected refinement
    
  
  Scenario: Verify Multiple Refinement Filters
  And Verify that filter options contains this list
      | Shop for 	|
      | Category 	|
      | Size     	|
      | Color    	|
      | Price    	|
      | New in Sale	|
      
  When User refines using any of this options
      | Category  |
  Then Verify selected refinement is displayed in header
  And Verify that search result number is greater than 0
      
  When User refines using any of this options
      | Size	|
  Then Verify selected refinement is displayed in header  
  And Verify that search result number is greater than 0  
      
  When User refines using any of this options
      | Color	|
  Then Verify selected refinement is displayed in header 
  And Verify that search result number is greater than 0
    
  When User refines using any of this options
      | Price	|
  Then Verify selected refinement is displayed in header
  And Verify that search result number is greater than 0      
      
  When User refines using any of this options
      | New in Sale |                
  Then Verify selected refinement is displayed in header
  And Verify that search result number is greater than 0
 
  	


	
	