@Account
Feature: Account Feature

  Background:
    Given User is on homepage

  Scenario: Validating sign in page and login with valid credential, sign out
    And Goes to sign in page
    And User provides login information
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account page
    And User clicks on SIGN OUT link in My Account Page
    Then Verify user is in homepage
    And User is signed out
    And Verify BAG header link is displayed

  Scenario: Successful sign in and account section validation
    Given Goes to sign in page
    When User provides login information
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account page
    And Verifies page displays My Account title
    And Validates link MY DETAILS is displayed in My Account Page
    And Validates link EMAIL PREFERENCES is displayed in My Account Page
    And Validates link CATALOG PREFERENCES is displayed in My Account Page
    And Validates link PAYMENT METHODS is displayed in My Account Page
    And Validates link GIFT CARD BALANCE is displayed in My Account Page
    And Validates link ADDRESS BOOK is displayed in My Account Page
    And Validates link ORDER HISTORY is displayed in My Account Page
    And Clicks on J.Crew Breadcrumb
    Then Verify user is in homepage
    Then User clicks on hamburger menu
    And My Account link is present
    And User clicks on My Account link
    Then User is in My Account page

  Scenario: account page link validation
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

  Scenario: Error message validation for incorrect user (both error)
    Given Goes to sign in page
    And Login page is loaded
    When User enters invalid as email
    And Changes focus to password field
    Then An error message saying Please enter a valid email address. should appear
    And Sign in button should be deactivated
    Then User enters test@example.org as email
    And An error message saying Whoops, that's not right... Please try your password again. should appear
    And Sign in button should be deactivated

  # TODO: incomplete; script need to be updated for validation
  Scenario: Create New Account
    Given Goes to sign in page
    And Clicks on create new account
    And Fills required account data in create account page
    And Clicks on create new account in create account page
    Then Verify user is in homepage
