@Account
Feature: My Account page validations

  Background:
    Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  Scenario: International context validation on all My Account related pages
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    When User clicks on sign in using header
    And User fills user data and signs in
    Then Verify user is in My Account main page
    When User clicks on MY DETAILS link in My Account Page
    Then User should be in account/account_detail.jsp? menu link page
    And Verify selected country is in footer
    When User clicks on EMAIL PREFERENCES link in My Account Page
    Then User should be in account/email_preferences.jsp menu link page
    And Verify selected country is in footer
    When User clicks on CATALOG PREFERENCES link in My Account Page
    And Verify selected country is in footer
    When User clicks on GIFT CARD BALANCE link in My Account Page
    And Verify selected country is in footer
    When User clicks on PAYMENT METHODS link in My Account Page
    Then User should be in account/payment_info.jsp menu link page
    And Verify selected country is in footer
    When User clicks on ADDRESS BOOK link in My Account Page
    Then User should be in account/address_book.jsp menu link page
    And Verify selected country is in footer
    When User clicks on ORDER HISTORY link in My Account Page
    Then User should be in account/reg_user_order_history.jsp menu link page
    And Verify selected country is in footer
    When User clicks on WISHLIST link in My Account Page
    Then User should be in /wishlist menu link page
    And Verify selected country is in footer

