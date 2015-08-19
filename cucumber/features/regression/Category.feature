@Category
Feature: Regression Tests Category Feature

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu

  Scenario: Check Category Header Title Not Present
    Given User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Category header should not be present

  Scenario: Check category header image
    Given User clicks on SWEATERS subcategory from Women Category
    Then User should be in sweaters page for women
    And An image should be displayed for SWEATERS

  Scenario: Check Product Tiles
    Given User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Verifies Carrie cami product is displayed
    And Verifies Carrie cami product list price is $98.00
    And Verifies Carrie cami is available in 7 colors
    And An image is displayed for Carrie cami product


  Scenario: Check Product Tiles for Sale Variations
    Given User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Verifies Grosgrain ribbon top product is displayed
    And Verifies Grosgrain ribbon top product price was $98.00
    And Verifies Grosgrain ribbon top product sale price is now $29.99
    And Verifies Grosgrain ribbon top is available in 4 colors
    And An image is displayed for Grosgrain ribbon top product