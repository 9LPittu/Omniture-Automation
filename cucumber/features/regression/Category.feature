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
    And Verifies Carrie cami product price is $98.00
    And Verifies Carrie cami colors available are 7
    And An image is displayed for Carrie cami product