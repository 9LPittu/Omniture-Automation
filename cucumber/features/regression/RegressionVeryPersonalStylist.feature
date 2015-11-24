@VeryPersonalStylist
Feature: Very Personal Stylist Product Feature

  Background:
    Given User is on homepage

  Scenario: Very Personal Stylist Product Validation
    When User presses search button
    And Enters A1632 to the search field
    Then User is in product detail page
    And Size selector is not displayed
    And Color selector is not displayed
    And Quantity selector is not displayed
    And Add to bag button is not displayed
    And Wishlist button is not displayed
    And Verify headline message for VPS product is 'Since this is a special, limited-edition item with a small quantity available, our Very Personal Stylists are on hand to help you purchase yours.'
    And Verify body message for VPS product is 'Call 800 261 7422 or email erica@jcrew.com to order.'