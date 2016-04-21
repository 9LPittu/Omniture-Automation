@Context
Feature:

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up
    Then click on change link from footer
	And User is on context chooser page
	Given user selects top10country at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer

  Scenario: international context validation on all My Account related pages
    When Goes to sign in page
    And User provides login information
    And Check box is enabled
    And User is in My Account page
    And user should see selected country in the footer
    When User clicks on MY DETAILS link in My Account Page
    Then User should be in account_detail.jsp menu link page
    And user should see selected country in the footer
    When User clicks on EMAIL PREFERENCES link in My Account Page
    Then User should be in email_preferences.jsp menu link page
    And user should see selected country in the footer
    When User clicks on CATALOG PREFERENCES link in My Account Page
    Then User should be in catalog_preferences.jsp menu link page
    And user should see selected country in the footer
    When User clicks on PAYMENT METHODS link in My Account Page
    Then User should be in payment_info.jsp menu link page
    And user should see selected country in the footer
    When User clicks on GIFT CARD BALANCE link in My Account Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And user should see selected country in the footer
    And User presses back button
    When User clicks on ADDRESS BOOK link in My Account Page
    Then User should be in address_book.jsp menu link page
    And user should see selected country in the footer
    When User clicks on ORDER HISTORY link in My Account Page
    Then User should be in reg_user_order_history.jsp menu link page
    And user should see selected country in the footer
    When User clicks on WISHLIST link in My Account Page
    Then User should be in /wishlist menu link page
    And user should see selected country in the footer

  Scenario: Forgot Password Page context validtaion
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    And user should see selected country in the footer


  Scenario: Category and PDP Page context validation
    When User goes to /c/womens_category/sweaters page
    And Verify embedded headers links
    And user should see selected country in the footer
    And Selects the first product from product grid list
    And User is in product detail page
    And user should see selected country in the footer

  Scenario Outline: international context validation on Department/Gender Landing Pages
    And User clicks on <gender> link from top nav
    And user should see selected country in the footer
    And user should see country code in the url for international countries

    Examples:
      |gender|
      |Women |
      |Men   |
      |Boys  |
      |Girls |


  Scenario: Multiple Pages During Checkout Context and Currency validations
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	And Verify proper currency symbol is displayed on product grid list
	And user should see selected country in the footer
	And user should see country code in the url for international countries
	And user selects any item from array page, select any color and size
    And User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And user should see selected country in the footer
    And user should see country code in the url for international countries
    And Add to cart button is pressed
    And User clicks on item bag
    Then User should be in shopping bag page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on item section on Checkout page    
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Clicks edit button on item bag page
	And User is in product detail page
	And Verify proper currency symbol is displayed on PDP page
	Then Update Bag button is pressed
	Then User should be in shopping bag page
    And Clicks on checkout
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Selects to checkout as guest
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    When user fills selcted country shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Verify embedded headers links
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And Uses default value for shipping method
    #And Uses default value for gifts option
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

  Scenario: context and Local currency validation on sale page
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And user should see selected country in the footer
    And User clicks on WOMEN subcategory from Sales
    And Verify proper currency symbol is displayed on product grid list
    And user should see selected country in the footer
    And Selects any product from product grid list
    And User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    Then Verify embedded footer is visible and functional

  Scenario: Context and currency is displayed on search page
    And User presses search button
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And user should see selected country in the footer
    And Verify proper currency symbol is displayed on product grid list
    When Selects the first product from product grid list
    Then User is in product detail page
    And Verify proper currency symbol is displayed on PDP page

  #multipdp, shoppable tray
  Scenario: international context and currency validation for shoppable tray page
    And User clicks on hamburger menu
    And User selects random tray from available categories
      |Women|THIS MONTH'S FEATURES|looks we love |
      |Men  |THIS MONTH'S FEATURES|1 Suit, 5 Ways|
      |Girls|THIS MONTH'S FEATURES|Looks We Love |
      |Boys |THIS MONTH'S FEATURES|Looks We Love |
    And Verify proper currency symbol is displayed on PDP page
    And user should see country code in the url for international countries