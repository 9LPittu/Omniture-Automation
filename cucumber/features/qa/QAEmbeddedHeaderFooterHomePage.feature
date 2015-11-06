@EmbeddedHeaderFooterHomePage
Feature: Embedded Header Footer Homepage

  Scenario: Home Page header footer links
    Given User is on homepage
    Then Verify user is in homepage
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify header bag icon is displayed
    And Verify MENU, SEARCH, STORES header links including bag order is valid, ignore SIGN IN, MY ACCOUNT
    Then User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    Then Closes hamburger menu
    And User presses search button
    And Search drawer is open
    Then User clicks on stores link
    And Verify user is on stores page
    Then User goes to homepage
    Then User clicks on item bag
    And User should be in shopping bag page
