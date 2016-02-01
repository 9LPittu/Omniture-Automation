@Account
Feature: Account Page

  Background:
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Check box is enabled
    And Hits sign in button
    And User is in My Account page

  Scenario: Validating sign in page and login with valid credential, sign out
    When User clicks on SIGN OUT link in My Account Page
    Then Verify user is in homepage
    And User is signed out
    And Verify BAG header link is displayed

  Scenario: Successful sign in and account section validation
    When Verifies page displays My Account title
    Then Validates link MY DETAILS is displayed in My Account Page
    And Validates link EMAIL PREFERENCES is displayed in My Account Page
    And Validates link CATALOG PREFERENCES is displayed in My Account Page
    And Validates link PAYMENT METHODS is displayed in My Account Page
    And Validates link GIFT CARD BALANCE is displayed in My Account Page
    And Validates link ADDRESS BOOK is displayed in My Account Page
    And Validates link ORDER HISTORY is displayed in My Account Page

  Scenario: Validate My Account link is present
    When Clicks on JCrew Logo
    And Verify user is in homepage
    And User clicks on hamburger menu
    Then My Account link is present
    And User clicks on My Account link
    Then User is in My Account page

  Scenario: account page link validation
    When User clicks on MY DETAILS link in My Account Page
    Then User should be in account_detail.jsp menu link page
    When User clicks on EMAIL PREFERENCES link in My Account Page
    Then User should be in email_preferences.jsp menu link page
    When User clicks on CATALOG PREFERENCES link in My Account Page
    Then User should be in catalog_preferences.jsp menu link page
    When User clicks on PAYMENT METHODS link in My Account Page
    Then User should be in payment_info.jsp menu link page
    When User clicks on GIFT CARD BALANCE link in My Account Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And User presses back button
    When User clicks on ADDRESS BOOK link in My Account Page
    Then User should be in address_book.jsp menu link page
    When User clicks on ORDER HISTORY link in My Account Page
    Then User should be in reg_user_order_history.jsp menu link page
    When User clicks on WISHLIST link in My Account Page
    Then User should be in /wishlist menu link page
