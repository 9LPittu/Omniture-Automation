@Home
Feature: Homepage

  Background:
    Given User is on homepage

  Scenario: Contact Us Page Header Footer Links
    Then Contact Us header from footer is visible

  Scenario: Home Page header Links
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify header bag icon is displayed
    And Verify MENU, SEARCH, STORES header links including bag order is valid, ignore SIGN IN, MY ACCOUNT

  Scenario: Menu is functional
    When User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    Then Closes hamburger menu

  Scenario: Search is functional
    When User presses search button
    Then Search drawer is open
    When User clicks on stores link
    Then Verify user is on stores page

  Scenario: Bag is functional
    When User clicks on item bag
    Then User should be in shopping bag page
