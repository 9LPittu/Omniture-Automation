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

  Scenario Outline: Verify that category links are displayed on top nav in the gender landing page
    When User clicks on <gender> link from top nav
    Then Verify that top nav options contains view all
    And Verify that top nav contains less or equal to 8 options

    When User clicks on view all link from top nav
    Then Verify menu drawer is displayed
    And Verify menu drawer title is <gender>

    Examples:
      | gender |
      | women  |
      | men    |
      | girls  |
      | boys   |