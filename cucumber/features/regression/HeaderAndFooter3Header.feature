@HeaderAndFooter
Feature: Header And Footer Verification In Homepage

  Background:
    Given User is on homepage

  Scenario: Verify Header Links
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify BAG header link is displayed
    And Verify MENU, SEARCH, STORES header links including bag order is valid, ignore SIGN IN, MY ACCOUNT
    Then Verify embedded headers are visible and functional
    Then Verify embedded footer is visible and functional

  Scenario: Hamburger Menu
    Then User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    Then Closes hamburger menu

  #Removed, we had a copy in RegressionHomePage.feature
  #Scenario: Search

  Scenario: Stores
    Then User clicks on stores link
    And User is on external https://stores.jcrew.com/ page
    Then Verify user is on stores page

  Scenario: Bag
    Then User clicks on item bag
    And User is on internal /checkout2/shoppingbag.jsp?sidecar=true page

