@EmbeddedHeaderFooterPDPPage
  
Feature: Embedded Header Footer PDP

  Scenario: PDP Page header footer links
    Given User goes to /c/womens_category/sweaters page
    Then Selects the first product from product grid list
    Then Verify embedded header and footer are visible and functional