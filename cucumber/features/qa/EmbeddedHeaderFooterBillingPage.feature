@EmbeddedHeaderFooterBillingPage
Feature: Embedded Header Footer Billing

  Scenario: Billing Page Header Footer Links
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    Then Verify embedded header and footer are visible and functional