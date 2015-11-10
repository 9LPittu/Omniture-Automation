@EmbeddedHeaderFooterCategoryPage
  
Feature: Embedded Header Footer Category

  Scenario: Category Page header footer links
    Given User goes to /c/womens_category/sweaters page
    Then Verify embedded header and footer are visible and functional