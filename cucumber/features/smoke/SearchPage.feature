@Search
Feature: Smoke Tests Search Page

  Background:
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Search results are displayed
    And Gender selectors are displayed
    And Clicks on gender selector
    Then User is in gender refine array page

  Scenario: Search results should be displayed
    And Refine button is displayed

  Scenario: Search refine modal display
    Given Refine button is clicked
    Then Category, Size, Brand, Color, Price filter refinements should appear
    And Relevance, Price: Low to High, Price: High to Low sort by options should appear
    And Relevance sort by option should be selected
