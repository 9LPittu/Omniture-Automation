@HeaderAndFooter
Feature: Embedded Header and Footer Validations

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up
    Then click on change link from footer
	And User is on context chooser page
	Given user selects top10country at random from context chooser page
    Then user should land on country specific home page
    #And click on 'START SHOPPING' button on country specific home page
    

#  Scenario: Account Registration Header Links
#    When Goes to sign in page
#    And Clicks on create new account
#    Then Verify embedded headers links
#    Then Verify embedded footer is visible and functional
#    And user should see selected country in the footer

  Scenario: Forgot Password Page Header Links
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    Then Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer

    #Merged with Category and PDP Page header links
    #Scenario: PDP Page header links
    #Scenario: Category Page header links
  Scenario: Category and PDP Page header links
    When User goes to /c/womens_category/sweaters page
    And Verify embedded headers links
    And user should see selected country in the footer
    And Selects the first product from product grid list
    And User is in product detail page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer

    #Merged with Multiple Pages During Checkout Header Links
    #Scenario: Order Confirmation Page Header Links
    #Scenario: PDP Page header links
    #Scenario: Shipping Address Page Header Links
    #Scenario: Shipping And Gift Options Page Header Links
    #Scenario: Shopping Bag Page header footer links
    #Scenario: Review Page Header Links
 @HeaderAndFooter2
  Scenario: Multiple Pages During Checkout Header Links
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Verify proper currency symbol is displayed on product grid list
    And Selects the first product from product grid list
    And User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    Then User should be in shopping bag page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Clicks on checkout
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Selects to checkout as guest
    #When user fills shipping address
    When user fills selcted country shipping address
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And Clicks on place your order
    And User should be in order confirmation page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    
 