@Search
Feature: Search Regression Suite

  Background:
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    Then Clicks on gender selector
    Then User is in gender refine array page
    Then Refine button is clicked

  Scenario: Search Refine Single Select
    Given Click on Category refinement
    Then Validate View All option is selected under Category refinement
    Then Select collection single option from Category refinement
    And Verify collection value is displayed next to Category refinement
    Then Click on Category refinement
    And Validate View All option is NOT selected under Category refinement

  Scenario: Search Refine multi select, single select
    Given Click on Size refinement
    Then Validate View All option is selected under Size refinement
    Then Select xx-small multiple option from Size refinement
    And Validate View All option is NOT selected under Size refinement
    And Verify xx-small value is displayed next to Size refinement
    Then Select x-small multiple option from Size refinement
    And Verify 2 selected value is displayed next to Size refinement
    And Verify Size refinement drawer remains open

  Scenario: Search Refine multi select, multiple selections
    Given Click on Size refinement
    Then Validate View All option is selected under Size refinement
    Then Select xx-small multiple option from Size refinement
    And Validate View All option is NOT selected under Size refinement
    And Verify xx-small value is displayed next to Size refinement
    Then Select x-small multiple option from Size refinement
    Then Click on Size refinement close drawer icon
    And Verify 2 selected value is displayed next to Size refinement

  Scenario: Search Refine Breadcrumbs and results
    Given Click on Size refinement
    Then Validate View All option is selected under Size refinement
    Then Select xx-small multiple option from Size refinement
    And Validate View All option is NOT selected under Size refinement
    And Verify xx-small value is displayed next to Size refinement
    Then Select x-small multiple option from Size refinement
    Then Click on Size refinement close drawer icon
    And Verify 2 selected value is displayed next to Size refinement
    Then Click on done button for refinement filter menu
    Then Verify number of results is less than 100
    And Verify xx-small option breadcrumb is created
    And Verify x-small option breadcrumb is created

