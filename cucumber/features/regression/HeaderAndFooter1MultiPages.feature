@HeaderAndFooter @HighLevel
Feature: Embedded Header and Footer Validations with header changes

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up

  Scenario: Login Page Header Links
    When Goes to sign in page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional

  Scenario: Forgot Password Page Header Links
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional

    #Merged with Category and PDP Page header links
    #Scenario: PDP Page header links
    #Scenario: Category Page header links
  Scenario: Category and PDP Page header links
    When User goes to /c/womens_category/sweaters page
    Then Verify embedded headers links is visible
    And Selects the first product from product grid list
    And User is in product detail page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional

    #Merged with Multiple Pages During Checkout Header Links
    #Scenario: Order Confirmation Page Header Links
    #Scenario: PDP Page header links
    #Scenario: Shipping Address Page Header Links
    #Scenario: Shipping And Gift Options Page Header Links
    #Scenario: Shopping Bag Page header footer links
    #Scenario: Review Page Header Links
  Scenario: Multiple Pages During Checkout Header Links
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory
    And user selects any item from array page, select any color and size
    And User is in product detail page
    And Add to cart button is pressed
    And User clicks on item bag
    Then User should be in shopping bag page
    Then Verify embedded headers links is visible    
    Then Verify embedded footer is visible and functional
    And Clicks on checkout
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And Selects to checkout as guest
    And Fills shipping address
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Clicks on place your order
    And User should be in order confirmation page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional