@Search
Feature: Smoke Tests Search Page

  Scenario: Search functionality
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Search results are displayed
    And Gender selectors are displayed
    When Clicks on gender selector
    Then User is in gender refine array page
    And Refine button is displayed
    When Refine button is clicked
    Then Category, Size, Brand, Color, Price filter refinements should appear
    And Relevance, Price: Low to High, Price: High to Low sort by options should appear
    And Relevance sort by option should be selected


