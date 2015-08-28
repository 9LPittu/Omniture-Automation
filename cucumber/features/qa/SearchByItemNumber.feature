@SearchByItem
Feature: Smoke Tests Search By Item Number Page

  Background:
    Given User is on homepage
    
 Scenario: validating search results page display by item number
    And User presses search button
    And Enters C3409 to the search field
    And Clicks on search button for input field
    Then User is in search results page 
  #  And search array with available colors displayed
    And Enters 21421 to the search field
    And Clicks on search button for input field
    Then User is in search results page
    #And Search array with price variations displayed
    #And Wait a little 
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    
 Scenario: single product result goes to pdp directly
    And User presses search button
    And Enters B8752 to the search field
    And Clicks on search button for input field
    And User is in product detail page
    And Product details are displayed
    And User presses search button
    And Enters 08796 to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Search array with price variations displayed
    And User presses search button
    And Enters 12245 to the search field
    And Clicks on search button for input field
    And User is in product detail page
    And Product details are displayed