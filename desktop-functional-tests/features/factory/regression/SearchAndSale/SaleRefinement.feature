@SaleRefinement @HighLevel
Feature: Sale Array - Refinement

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User clicks on clearance link from top nav
    Then Verify sale landing page is displayed
    
    When User Selects random sale category from the list
    |women	|
	|men	|
	|girls	|
	|boys	|
	Then Verify Sale array page is displayed
	
  Scenario: Select Single Refinement and verify Product count 
	And Verify that filter options contains this list
	| Shop for 		|
	| Category 		|
	| Size     		|
	| Color    		|
	| Price    		|
		  
	When User refines using any of this options
	| Category 		|
	| Color    		|
	| Price    		|
	Then Verify selected refinement is displayed in header
	And Verify item count matches selected refinement
		  
	When User selects a second option from previously selected filter
	And Verify item count matches selected refinement
    
  
  Scenario: Select Multiple Refinements and Clear Filters
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
	
	When User clears Category refinements
	Then Verify Category filter is cleared
	
	When User clears Size refinements
	Then Verify Size filter is cleared
	
	When User clears Color refinements
	Then Verify Color filter is cleared
	
	When User clears Price refinements
	Then Verify Price filter is cleared
	
	When User clears New in Sale refinements
	Then Verify New in Sale filter is cleared
	