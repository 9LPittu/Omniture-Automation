@EmbeddedHeaderFooterReviewPage
Feature: Embedded Header Footer Review


  Scenario: Review Page Header Footer Links
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
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Validates billing section is present in review page
    Then Verify embedded header and footer are visible and functional for checkout pages