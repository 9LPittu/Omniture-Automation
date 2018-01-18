@Search
Feature: Smoke Search Functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Verify Search Functionality
  	Then Verify search drawer is closed

    When User searches specific term dresses
    And User closes email capture
    Then User is in search results page
    And Verify that search result number is greater than 0
    
    When User selects random product from array
    Then Verify product detail page is displayed
  
    When User searches for a single result search term
    And User selects first product from product array
    Then Verify product detail page is displayed

    # Commenting out because team says that is not important to see where is going
#    When User searches for a multi result search term
#    Then User is in search results page
#    And Verify that search result number is greater than 1
    
#    When User selects random product from array
#    Then Verify product detail page is displayed
    