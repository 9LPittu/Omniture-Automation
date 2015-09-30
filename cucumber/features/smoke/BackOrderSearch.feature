@BackOrderSearch
Feature: Smoke Tests Search Page

  Scenario: Search functionality
    Given User is on homepage
    And User presses search button
    And Enter backorder item from database to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Search results are displayed
   


