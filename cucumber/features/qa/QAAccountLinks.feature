@Account
Feature: Account Page

  Background:
    Given User is on homepage

  Scenario: Validate All Menu Links in Account Page
    Given Goes to sign in page
    And User provides login information
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account page
    And User clicks on MY DETAILS link in My Account Page
    And User should be in account_detail.jsp menu link page
    And User clicks on EMAIL PREFERENCES link in My Account Page
    And User should be in email_preferences.jsp menu link page
    And User clicks on CATALOG PREFERENCES link in My Account Page
    And User should be in catalog_preferences.jsp menu link page
    And User clicks on PAYMENT METHODS link in My Account Page
    And User should be in payment_info.jsp menu link page
    And User clicks on GIFT CARD BALANCE link in My Account Page
    And User should be in checkout/giftcard_balance1.jsp menu link page
    And User presses back button
    And User clicks on ADDRESS BOOK link in My Account Page
    And User should be in address_book.jsp menu link page
    And User clicks on ORDER HISTORY link in My Account Page
    And User should be in reg_user_order_history.jsp menu link page
    And User clicks on WISHLIST link in My Account Page
    And User should be in /wishlist menu link page
