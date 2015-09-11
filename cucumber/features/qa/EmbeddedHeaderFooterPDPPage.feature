@EmbeddedHeaderFooterPDPPage
  
Feature: Embedded Header Footer PDP

  Scenario: PDP Page header footer links
    Given User is on homepage
    Then User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on KNITS & TEES subcategory from Women Category
    Then Selects the first product from product grid list
    Then Verify embedded header and footer are visible and functional