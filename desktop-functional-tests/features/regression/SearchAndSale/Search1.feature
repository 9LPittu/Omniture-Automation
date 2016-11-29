@Search1 @HighLevel
Feature: Search Functionality - Part 1

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Verify Search Functionality
    When User searches for a random search term
    Then User is in search results page
    And Verify that search result number is greater than 0
   	And Search drawer is open
   	And Search drawer displays search term
    
    When User selects random product from array
    Then Verify product detail page is displayed
    
    
    
    