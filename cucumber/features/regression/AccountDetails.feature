@AccountDev @HighLevel
Feature: My Account Page

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Goes to sign in page

  Scenario: Validate existing user is able to set birthday if not provided


  @wip
  Scenario: Validate My Detail drop down is functional
    When User provides noLoyalty login information
    And Check box is enabled
    And Hits sign in button

    Then User is in My Account home page
    And User should be in /account/home.jsp menu link page

    When User clicks on MY DETAILS link in My Account Page
    Then My Details form should display
    And User should be in /r/account/details menu link page

    When User selects Home from my details dropdown
    And User should be in /account/home.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Email Preferences from my details dropdown
    Then User should be in email_preferences.jsp menu link page

    When User presses back button
    Then My Details form should display


    When User selects Catalog Preferences from my details dropdown
    Then User should be in catalog_preferences.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Payment Methods from my details dropdown
    Then User should be in payment_info.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Gift Card Balance from my details dropdown
    Then User should be in checkout/giftcard_balance1.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Address Book from my details dropdown
    Then User should be in address_book.jsp menu link page
    And User presses back button

    When User selects Order History from my details dropdown
    Then User should be in reg_user_order_history.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects My Wishlist from my details dropdown
    Then User should be in /wishlist menu link page
    And User presses back button

    When User presses back button
    Then My Details form should display

    When User selects Sign Out from my details dropdown
    Then Verify user is in homepage


  @wip
  Scenario Outline: Validate fields in my details form
    And User provides <userType> login information
    And Check box is enabled
    And Hits sign in button
    And User is in My Account home page
    And User should be in /account/home.jsp menu link page
    When User clicks on MY DETAILS link in My Account Page
    And My Details form should display
    And User should be in /r/account/details menu link page

    Then Verify first name in my details form

    And Verify last name in my details form

    And Verify email in my details form
    And Verify Birth date is editable if not set in My details form
    And Verify country in my details form
    And Click on Change Password in my details form

    And Verify Old password in my details form
    And Verify New password in my details form
    And Verify re-enter password in my details form

    And Verify save button in my details form

    Examples:
      | userType  |
      | loyalty   |
      | noLoyalty |