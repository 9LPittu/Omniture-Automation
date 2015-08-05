@Search
Feature: Smoke Tests Search Page

  Background:
    Given User is on homepage

  Scenario: Search results should be displayed
    Given User presses search button
    And Enters dresses to the search field
    And Hits enter in search field
    Then User is in search results page
    And Search results are displayed
