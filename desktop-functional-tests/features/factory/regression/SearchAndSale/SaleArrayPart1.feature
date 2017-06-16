@SaleArray1
Feature: Sale Array - Pagination

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User clicks on crew clearance link from top nav
    Then Verify sale landing page is displayed
    
    When User Selects random sale category from the list
    |women|
	|men|
	|girls|
	|boys|
	Then Verify Sale array page is displayed
	And Verify that search result number is greater than 0
	
  Scenario: Verify header pagination on Array page
  	And Verify header pagination is displayed on array page
  	And Verify page 1 is selected in header pagination
  	And Verify Previous pagination arrow is displayed on header pagination
  	And Verify Next pagination arrow is displayed on header pagination
  	And Verify Previous pagination arrow in header is in disabled state
  	And Verify Next pagination arrow in header is in active state
  	
  	When User clicks on Next pagination arrow from header
  	Then Verify selected page number increases by 1
  	
  	When User clicks on Previous pagination arrow from header
  	Then Verify selected page number decreases by 1
  	
  	When User selects random page number from header pagination dropdown
  	And Verify content changes when page number is changed
  	
  Scenario: Verify footer pagination on Array page
  	And Verify footer pagination is displayed on array page
  	And Verify page 1 is selected in footer pagination
  	And Verify Previous pagination arrow is displayed on footer pagination
  	And Verify Next pagination arrow is displayed on footer pagination
  	And Verify Previous pagination arrow in footer is in disabled state
  	And Verify Next pagination arrow in footer is in active state
  	
  	When User clicks on Next pagination link from footer
  	Then Verify selected page number increases by 1
  	
  	When User clicks on Previous pagination link from footer
  	Then Verify selected page number decreases by 1
  	
  	When User selects random page number from footer pagination dropdown
  	And Verify content changes when page number is changed