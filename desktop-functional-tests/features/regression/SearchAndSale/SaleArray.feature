@SaleArray @HighLevel
Feature: Sale Landing functionality

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
	And Verify that search result number is greater than 0
	
  Scenario: Verify header paginationon on Array page
  	And Verify pagination is displayed on array page
  	And Verify page 1 is selected
  	And Verify Previous pagination arrow is displayed on array page
  	And Verify Next pagination arrow is displayed on array page
  	And Verify Previous pagination arrow is in disabled state
  	And Verify Next pagination arrow is in active state
  	
  	When User clicks on Next pagination arrow
  	Then Verify selected page number increases by 1
  	
  	When User clicks on Previous pagination arrow
  	Then Verify selected page number decreases by 1
  	
  	When User selects random page number from pagination dropdown
  	And Verify content changes when page number is changed
  	
  	
  	
  	When User selects random page 
#  	And left pagination text Previous is displayed above footer
#    And left pagination text Previous is in disabled state
#    And right pagination text Next is displayed above footer
#    And right pagination text Next is in active state
#    And right pagination text should be Next
#    And right pagination text Next should be in active state
#    And pagination dropdown should display current page number
#    And select random page number from pagination dropdown
#    And user should be displayed with correct page when page number is changed
  	
    

#  Scenario Outline: Verify Gender Selectors are functional on New In Sale page
#	When User selects new in sale dept from sales
#	Then Verify Sale array page is displayed
#	When User clicks on <gender> gender selector
#	Then Verify Sale array page is displayed
#	And Verify gender filter displays <gender>
#	And Verify that search result number is greater than 0
#
#	Examples:
#	|gender|
#	|women|
#	|men|
#	|girls|
#	|boys|

	
	