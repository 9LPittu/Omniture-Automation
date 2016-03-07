@EmbeddedHeader
Feature: Embedded Header Validations

  Background:
    Given User is on homepage

  Scenario: Account Registration Header Links
    When Goes to sign in page
    And Clicks on create new account
    Then Verify embedded headers links

  Scenario: Forgot Password Page Header Links
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    Then Verify embedded headers links

    #Merged with Category and PDP Page header links
    #Scenario: PDP Page header links
    #Scenario: Category Page header links
  Scenario: Category and PDP Page header links
    When User goes to /c/womens_category/sweaters page
    And Verify embedded headers links
    And Selects the first product from product grid list
    And User is in product detail page
    And Verify embedded headers links

    #Merged with Multiple Pages During Checkout Header Links
    #Scenario: Order Confirmation Page Header Links
    #Scenario: PDP Page header links
    #Scenario: Shipping Address Page Header Links
    #Scenario: Shipping And Gift Options Page Header Links
    #Scenario: Shopping Bag Page header footer links
    #Scenario: Review Page Header Links
  @wip
  Scenario: Multiple Pages During Checkout Header Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    Then User should be in shopping bag page
    And Verify embedded headers links
    And Clicks on checkout
    And Verify embedded headers links
    And Selects to checkout as guest
    And Verify embedded headers links
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Verify embedded headers links
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    And Verify embedded headers links
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Clicks on place your order
    And User should be in order confirmation page
    And Verify embedded headers links
