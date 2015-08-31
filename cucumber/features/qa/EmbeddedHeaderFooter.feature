Feature: Embedded Header Footer Scenarios

  Scenario: Home Page header footer links
    Given User is on homepage
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify BAG header link is displayed
    And Verify MENU, SEARCH, STORES, BAG order is valid, ignore SIGN IN, MY ACCOUNT