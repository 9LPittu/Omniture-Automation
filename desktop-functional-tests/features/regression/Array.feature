@Array
Feature: Array page functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: User is able to use refine in array
    When User opens menu
    And User selects women category from menu
    And User selects sweaters subcategory array
    Then Verify refine dropdown text is VIEW ALL
    And Verify refine options matches available lists
    And Verify category contains items count

    When User selects a random refinement option
    Then Verify refinement option was selected
