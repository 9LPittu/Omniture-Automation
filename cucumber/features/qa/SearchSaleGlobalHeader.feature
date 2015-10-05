@SearchSaleGlobalHeader
Feature: Smoke Tests Search Sale Global Header Page

  Background:
    Given User is on homepage
    And User presses search button
   # And Enters dresses to the search field
   # And Clicks on search button for input field
   # Then User is in search results page
    
  Scenario Outline: validation of search drawer stays open by default
    And Enters <search term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Gender selectors are displayed
    And User scrolls down the page
    And User scrolls up the page
    And Search drawer is open
    And User clicks on search close icon
    When User presses search button
    And Search drawer is open
    And User is in search results page
    And Dresses is populated
    When Enters <edited search term> to the search field
    And Hits enter in search field
   Then Verifies <corresponding> product is displayed

    Examples:
    |search term|edited search term|corresponding|
    |dresses    |yellow dresses    | Nadia dress in silk chiffon|