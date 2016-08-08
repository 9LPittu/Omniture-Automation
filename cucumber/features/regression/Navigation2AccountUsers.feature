@AccountDev @HighLevel
Feature: My Account Page

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up
    And Goes to sign in page

    @wip
  Scenario: Verify My Account page and sign out
    When Clicks on JCrew Logo
    And Verify user is in homepage
    And User clicks on hamburger menu
    Then My Account link is present
    And User clicks on My Account link
    Then User is in My Account page
    When User clicks on SIGN OUT link in My Account Page
    Then Verify user is in homepage
    And User is signed out
    And Verify BAG header link is displayed
    And Deletes browser cookies

  #US9724_TC16: Validate no breadcrumbs are displayed on Account related pages
  Scenario: Verify account page links
    And User provides loyalty login information
    And Check box is enabled
    And Hits sign in button
    And User is in My Account page
    When Verifies page displays My Account title
    When User clicks on MY DETAILS link in My Account Page
    And User should be in /r/account/details menu link page
    And Verify Embedded header is displayed
    And User presses back button
    When User clicks on EMAIL PREFERENCES link in My Account Page
    Then User should be in email_preferences.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    When User clicks on CATALOG PREFERENCES link in My Account Page
    Then User should be in catalog_preferences.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    When User clicks on PAYMENT METHODS link in My Account Page
    Then User should be in payment_info.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    When User clicks on GIFT CARD BALANCE link in My Account Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And User presses back button
    When User clicks on ADDRESS BOOK link in My Account Page
    Then User should be in address_book.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    When User clicks on ORDER HISTORY link in My Account Page
    Then User should be in reg_user_order_history.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    When User clicks on WISHLIST link in My Account Page
    Then User should be in /wishlist menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed

    And User presses back button
    And User presses back button

