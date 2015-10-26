@EmbeddedHeaderFooterPDPPage
  
Feature: Embedded Header Footer PDP

  Scenario: PDP Page header footer links
    Given User is on homepage
    Then User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    Then Selects the first product from product grid list
    And User clicks on hamburger menu
    And User clicks on back link
    And Closes hamburger menu
    Then Verify embedded header and footer are visible and functional