@Email
Feature: Handling email capture pop up

  Background:
    Given User is on homepage

  Scenario:  Email capture option should be displayed on fresh session.
    And Close the email pop up
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page
    And Email pop up is not displayed
    And User clicks on hamburger menu
    And User presses back button
    And Selects sale Category from hamburger menu
    And Email pop up is not displayed
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Email pop up is not displayed
