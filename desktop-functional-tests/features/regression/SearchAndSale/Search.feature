@Search @HighLevel
Feature: Search Functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Verify Search Functionality
  	Then Verify search drawer is closed
    
    When User searches for a random search term
    Then User is in search results page
    And Verify that search result number is greater than 0
   	And Verify search drawer is open
	
	When User closes search drawer
	Then Verify search drawer is closed
    
    When User selects random product from array
    Then Verify product detail page is displayed

  Scenario: Single result search term displays PDP
    When User searches for a single result search term
    Then Verify product detail page is displayed
    
  Scenario: Multiple result Search term displays array page 
    When User searches for a multi result search term
    Then User is in search results page
    And Verify that search result number is greater than 1
    
    When User selects random product from array
    Then Verify product detail page is displayed
    
  Scenario: Gender Selectors on Search Array
    When User searches specific term dresses
    Then User is in search results page
   	And Verify following gender selectors are displayed
	|women|
	|men|
	|girls|
	|boys|
	
	When User clicks on random gender selector
	Then Verify gender filter displays selected gender
	
	When User removes gender selector
	Then User is in search results page
   	And Verify following gender selectors are displayed
	|women|
	|men|
	|girls|
	|boys|
	
	When User clicks on random gender selector
	Then Verify gender filter displays selected gender

    	