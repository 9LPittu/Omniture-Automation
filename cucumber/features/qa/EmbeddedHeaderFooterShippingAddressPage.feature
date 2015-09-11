@EmbeddedHeaderFooterShippingAddressPage
Feature: Embedded Header Footer Shipping Address

  Scenario: Shipping Address Page Header Footer Links
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on KNITS & TEES subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    Then Verify embedded header and footer are visible and functional