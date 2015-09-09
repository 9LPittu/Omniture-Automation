@Search
Feature: Search Regression Suite

  Scenario: Search Refine Single Select
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    Then Clicks on gender selector
    Then User is in gender refine array page
    Then Refine button is clicked
    And Click on Category refinement
    Then Validate View All option is selected under Category refinement
    Then Select collection option from Category refinement
    And Verify collection option is displayed as selected for Category refinement
    Then Click on Category: refinement
    And Validate View All option is NOT selected under Category: refinement