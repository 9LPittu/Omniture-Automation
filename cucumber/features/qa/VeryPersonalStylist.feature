@VeryPersonalStylist
Feature: Very Personal Stylist Product Feature
  
  Scenario Outline:
    Given User goes to <VPS item pdp> page
    Then Size selector is not displayed
    And Color selector is not displayed
    And Quantity selector is not displayed
    And Add to bag button is not displayed
    And Wishlist button is not displayed
    And Verifies product list price is <listed price>
    Then Verify headline message for VPS product is 'Since this is a special, limited-edition item with a small quantity available, our Very Personal Stylists are on hand to help you purchase yours.'
    Then Verify body message for VPS product is 'Call 800 261 7422 or email erica@jcrew.com to order.'

    Examples:
    |VPS item pdp|listed price|
    |/p/womens_category/jewelry/jewelryshop/garden-party-statement-earrings/C8678|$98.00|
