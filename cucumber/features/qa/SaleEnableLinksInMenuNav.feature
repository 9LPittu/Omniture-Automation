@SaleEnablelinks
Feature: Sale Enable Links in Menu Nav

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects SALE Category from hamburger menu


  Scenario: New in Sale link functional validation
    And User clicks on NEW IN SALE subcategory from sale Category
    And User is in sale results page
    And User is in search results page