@SearchRefinement @HighLevel
Feature: Search Functionality - Part 1

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User searches specific term tops
    Then User is in search results page
    
    When User clicks on random gender selector
    Then User is in search results page

  Scenario: Verify Refinement filters on Search Array
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
	| New in Sale	|
	Then Verify selected refinement is displayed in header
	And Verify item count matches selected refinement
		  
	When User selects a second option from previously selected filter
	And Verify item count matches selected refinement
	
  Scenario: Verify Sort Functionality on Search Array
  	And Verify that filter options contains this list
	| Sort by 		|

	And Verify Sort by filter contains following options
	| Relevance			|
	| Price: Low to High|
	| Price: High to Low|  		
	And Verify selected option on Sort by filter is Relevance
	
	When User clicks on Price: Low to High option from Sort by filter
	And Verify selected option on Sort by filter is Price: Low to High
	And Verify items in Search array are sorted by Price: Low to High
	
	When User clicks on Price: High to Low option from Sort by filter
	And Verify selected option on Sort by filter is Price: High to Low
	And Verify items in Search array are sorted by Price: High to Low
	
	
	
	
	