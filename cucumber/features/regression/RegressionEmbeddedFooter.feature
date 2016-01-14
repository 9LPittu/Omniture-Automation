@EmbeddedFooter
Feature: Embedded Footer Validations

  Background:
    Given User is on homepage

  Scenario: Account Registration Footer Links
    When Goes to sign in page
    And Clicks on create new account
    Then Verify embedded footer is visible and functional

  Scenario: Category Page Footer links
    When User goes to /c/womens_category/sweaters page
    Then Verify embedded footer is visible and functional

  Scenario: Forgot Password Page Footer Links
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    Then Verify embedded footer is visible and functional

  Scenario: Billing Page Footer Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
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
    Then Verify embedded footer is visible and functional

  Scenario: Order Confirmation Page Footer Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
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
    And Clicks on place your order
    And User should be in order confirmation page
    Then Verify embedded footer is visible and functional

  Scenario: PDP Page Footer links
    When User goes to /c/womens_category/sweaters page
    Then Selects the first product from product grid list
    Then Verify embedded footer is visible and functional

  Scenario: Review Page Footer Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
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
    Then Verify embedded footer is visible and functional

  Scenario: Shipping Address Page Footer Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    Then Verify embedded footer is visible and functional

  Scenario: Shipping And Gift Options Page Footer Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
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
    Then Verify embedded footer is visible and functional

  Scenario: Shopping Bag Page Footer links
    When User clicks on item bag
    Then User should be in shopping bag page
    Then Verify embedded footer is visible and functional
