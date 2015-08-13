@Search
Feature: Smoke Tests Search Page

  Background:
    Given User is on homepage

  Scenario: Search results should be displayed
    Given User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Search results are displayed
    And Gender selectors are displayed
    And Clicks on gender selector
    Then User is in gender refine array page
    And Refine button is displayed
