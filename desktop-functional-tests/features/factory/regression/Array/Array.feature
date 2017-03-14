@Array
Feature: Array Page

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Category Array  - Display and Refinement
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page
    
    And Verify refine dropdown displayed in array page
    And Verify refine dropdown text is All sweaters

    When User expands refine dropdown
    And Verify refine dropdown is open
    And Verify refine options matches available lists
