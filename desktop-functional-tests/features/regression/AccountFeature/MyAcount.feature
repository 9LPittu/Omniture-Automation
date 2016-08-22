@AccountDev
Feature: My Account page validations

  Background:
    Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  Scenario: verify left nav links in my account pages are functional
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on My Details link in My Account Page
    Then Verify user is in account details page
    And User should be in r/account/details menu link page

    When User clicks on Home link in Account detail Page

    When User clicks on Email Preferences link in My Account Page
    Then User should be in account/email_preferences.jsp menu link page

    When User clicks on Catalog Preferences link in My Account Page
    Then User should be in account/catalog_preferences.jsp menu link page

    When User clicks on Payment Methods link in My Account Page
    Then User should be in account/payment_info.jsp menu link page

    When User clicks on Gift Card Balance link in My Account Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And User presses browser back button

    When User clicks on Address Book link in My Account Page
    Then User should be in account/address_book.jsp menu link page


    When User clicks on Order History link in My Account Page
    Then User should be in account/reg_user_order_history.jsp menu link page


    When User clicks on My Wishlist link in My Account Page
    Then User should be in /wishlist menu link page
    And User presses browser back button


    When User clicks on Sign Out link in My Account Page


