@Account
Feature: My Account page validations

  Background:
    Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  Scenario: verify left links in my account pages
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on MY DETAILS link in My Account Page
    Then Verify user is in account details page
    And User should be in account/account_detail.jsp? menu link page


    When User clicks on EMAIL PREFERENCES link in My Account Page
    Then User should be in account/email_preferences.jsp menu link page


    When User clicks on CATALOG PREFERENCES link in My Account Page

    When User clicks on GIFT CARD BALANCE link in My Account Page

    When User clicks on PAYMENT METHODS link in My Account Page
    Then User should be in account/payment_info.jsp menu link page


    When User clicks on ADDRESS BOOK link in My Account Page
    Then User should be in account/address_book.jsp menu link page


    When User clicks on ORDER HISTORY link in My Account Page
    Then User should be in account/reg_user_order_history.jsp menu link page


    When User clicks on WISHLIST link in My Account Page
    Then User should be in /wishlist menu link page


