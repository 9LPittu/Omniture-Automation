@SearchSaleGlobalHeader
Feature: Smoke Tests Search Sale Global Header Page

  Background:
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    
  Scenario: validation of search drawer stays open by default
    And Gender selectors are displayed
    And User scrolls down the page
    And User scrolls up the page
    And Search drawer is open
    And User clicks on search close icon
    When User presses search button
    And Search drawer is open
    And User is in search results page
    And Dresses is populated
    When Enters yellow dresses to the search field
    And Hits enter in search field
   Then Verifies Nadia dress in silk chiffon product is displayed