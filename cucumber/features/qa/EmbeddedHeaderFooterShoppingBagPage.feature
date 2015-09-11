@EmbeddedHeaderFooterShoppingBagPage
  
Feature: Embedded Header Footer Shopping Bag

  Scenario: Shopping Bag Page header footer links
    Given User is on homepage
    Then User clicks on item bag
    And User should be in shopping bag page
    Then Verify embedded header and footer are visible and functional