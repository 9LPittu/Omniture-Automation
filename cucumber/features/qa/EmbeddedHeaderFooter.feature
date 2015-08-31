@EmbeddedHeaderFooter
Feature: Embedded Header Footer Scenarios

  Scenario: Home Page header footer links
    Given User is on homepage
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify BAG header link is displayed
    And Verify MENU, SEARCH, STORES, BAG header links order is valid, ignore SIGN IN, MY ACCOUNT
    And Verify LET US HELP YOU footer link is displayed
    And Verify YOUR ORDERS footer link is displayed
    And Verify THE J.CREW STYLE GUIDE footer link is displayed
    And Verify ABOUT J.CREW footer link is displayed
    And Verify OUR STORES footer link is displayed
    And Verify OUR CARDS footer link is displayed
    And Verify OUR BRANDS footer link is displayed
    And Verify LET US HELP YOU, YOUR ORDERS, THE J.CREW STYLE GUIDE, ABOUT J.CREW, OUR STORES, OUR CARDS, OUR BRANDS footer links order is valid, ignore GET TO KNOW US
    Then User clicks on hamburger menu
    And Hamburger Menu WOMEN Link is present
    Then Closes hamburger menu
    And User presses search button
    And Search drawer is open
    Then User clicks on stores link
    And User is in stores link
    And User is on external page https://stores.jcrew.com/
    Then User presses back button
    And Verify user is in homepage
    Then User clicks on item bag
    And User should be in shopping bag page
    And User is on /checkout2/shoppingbag.jsp?sidecar=true page