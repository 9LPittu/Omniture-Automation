@EmbeddedHeader
Feature: Embedded Header Validations

  Scenario: Account Address Book Page Header Links
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on ADDRESS BOOK link in My Account Page
    And User should be in address_book.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Catalog Preferences Page Header Links
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on CATALOG PREFERENCES link in My Account Page
    And User should be in catalog_preferences.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Email Preferences Page Header Links
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on EMAIL PREFERENCES link in My Account Page
    And User should be in email_preferences.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Landing Page Header Links
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    Then User is in My Account page
    Then Verify embedded headers are visible and functional

  Scenario: Account My Details Page Header Links
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on MY DETAILS link in My Account Page
    And User should be in account_detail.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Order Detail Page Header Links
    Given User is on homepage
    And Goes to sign in page
    When User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on ORDER HISTORY link in My Account Page
    And User should be in reg_user_order_history.jsp menu link page
    # TODO: Review since order table seems to not be there...
    #Then User selects an order listed for review
    #And User should be in reg_user_order_detail.jsp menu link page
    #Then Verify embedded header and footer are visible and functional

  Scenario: Account Order History Page Header Links
    Given User is on homepage
    And Goes to sign in page
    When User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on ORDER HISTORY link in My Account Page
    And User should be in reg_user_order_history.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Payment Methods Page Header Links
    Given User is on homepage
    And Goes to sign in page
    When User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on PAYMENT METHODS link in My Account Page
    And User should be in payment_info.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Registration Header Links
    Given User is on homepage
    And Goes to sign in page
    And Clicks on create new account
    Then Verify embedded headers are visible and functional

  Scenario: Billing Page Header Links
    Given User is on homepage
    And User clicks on hamburger menu
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
    Then Verify embedded headers are visible and functional

  Scenario: Category Page header links
    Given User goes to /c/womens_category/sweaters page
    Then Verify embedded headers are visible and functional

  Scenario: Contact Us Page Header Footer Links
    Given User is on homepage
    Then Contact Us header from footer is visible

  Scenario: Forgot Password Page Header Links
    Given User is on homepage
    And Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    Then Verify embedded headers are visible and functional

  Scenario: Home Page header Links
    Given User is on homepage
    Then Verify user is in homepage
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify header bag icon is displayed
    And Verify MENU, SEARCH, STORES header links including bag order is valid, ignore SIGN IN, MY ACCOUNT
    Then User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    Then Closes hamburger menu
    And User presses search button
    And Search drawer is open
    Then User clicks on stores link
    And Verify user is on stores page
    Then User goes to homepage
    Then User clicks on item bag
    And User should be in shopping bag page


  Scenario: Order Confirmation Page Header Links
    Given User is on homepage
    And User clicks on hamburger menu
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
    Then Verify embedded headers are visible and functional

  Scenario: PDP Page header links
    Given User goes to /c/womens_category/sweaters page
    Then Selects the first product from product grid list
    Then Verify embedded headers are visible and functional

  Scenario: Review Page Header Links
    Given User is on homepage
    And User clicks on hamburger menu
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
    Then Verify embedded headers are visible and functional

  Scenario: Shipping Address Page Header Links
    Given User is on homepage
    And User clicks on hamburger menu
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
    Then Verify embedded headers are visible and functional

  Scenario: Shipping And Gift Options Page Header Links
    Given User is on homepage
    And User clicks on hamburger menu
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
    Then Verify embedded headers are visible and functional

  Scenario: Shopping Bag Page header footer links
    Given User is on homepage
    Then User clicks on item bag
    And User should be in shopping bag page
    Then Verify embedded headers are visible and functional


  Scenario: Wishlist Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    Then Verify embedded headers are visible and functional
