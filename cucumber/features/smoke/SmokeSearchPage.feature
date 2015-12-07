@Search
Feature: Smoke Tests Search Page

  Background:
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Search results are displayed
    And Gender selectors are displayed

  Scenario: Search functionality
    When Clicks on gender selector
    And User is in gender refine array page
    Then Refine button is displayed
    And Refine button is clicked
    And Category, Size, Brand, Color, Price filter refinements should appear
    And Relevance, Price: Low to High, Price: High to Low sort by options should appear
    And Relevance sort by option should be selected