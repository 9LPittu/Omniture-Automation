@EmbeddedHeaderFooterCategoryPage
  
Feature: Embedded Header Footer Category

  Scenario: Category Page header footer links
    Given User is on homepage
    Then User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on KNITS & TEES subcategory from Women Category
    And User clicks on hamburger menu
    And User clicks on back link
    And Closes hamburger menu
    Then Verify embedded header and footer are visible and functional