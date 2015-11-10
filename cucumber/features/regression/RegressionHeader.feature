@Header
Feature: Header Tests
  Scenario: Verify Header Links
    Given User is on homepage
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify BAG header link is displayed
    Then User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    Then Closes hamburger menu
    And User presses search button
    And Search drawer is open
    Then User clicks on stores link
    And User is on external page https://stores.jcrew.com/
    Then User goes to homepage
    And Verify user is in homepage
    Then User clicks on item bag
    And User is on /checkout2/shoppingbag.jsp?sidecar=true page

