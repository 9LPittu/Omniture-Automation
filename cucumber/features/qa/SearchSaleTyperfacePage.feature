@Typerface
Feature: Search Sale Typerface Design Updates Page

  Background:
    Given User is on homepage
    And User presses search button


  Scenario Outline:
    And Enters <search term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Search results are displayed
    And Gender selectors are displayed

    Examples:
      |search term|
      |skirts  |
      |shoes      |