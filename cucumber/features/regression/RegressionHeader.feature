@Header
Feature: Header Tests

  Background:
    Given User is on homepage

  Scenario: Verify Header Links
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify BAG header link is displayed

  Scenario: Hamburger Menu
    Then User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    Then Closes hamburger menu

  Scenario: Search
    And User presses search button
    And Search drawer is open

  Scenario: Stores
    Then User clicks on stores link
    And User is on external page https://stores.jcrew.com/

  Scenario: Bag
    Then User clicks on item bag
    And User is on /checkout2/shoppingbag.jsp?sidecar=true page

