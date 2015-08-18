@Category
Feature: Regression Tests Category Feature
  Scenario: Check Category Header Title Not Present
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Category header should not be present