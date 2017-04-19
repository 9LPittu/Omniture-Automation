@Search2 @HighLevel
Feature: Search Functionality 2

  Background:
    Given User goes to homepage
    And User closes email capture
  
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

    	