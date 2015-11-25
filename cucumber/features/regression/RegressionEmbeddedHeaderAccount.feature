@EmbeddedHeader
Feature: Embedded Header Validations

  Background:
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User is in My Account page

  Scenario: Account Landing Page Header Links
    Then Verify embedded headers are visible and functional

  Scenario: Account Address Book Page Header Links
    When User clicks on ADDRESS BOOK link in My Account Page
    And User should be in address_book.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Catalog Preferences Page Header Links
    And User clicks on CATALOG PREFERENCES link in My Account Page
    And User should be in catalog_preferences.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Email Preferences Page Header Links
    And User clicks on EMAIL PREFERENCES link in My Account Page
    And User should be in email_preferences.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account My Details Page Header Links
    And User clicks on MY DETAILS link in My Account Page
    And User should be in account_detail.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Order Detail Page Header Links
    And User clicks on ORDER HISTORY link in My Account Page
    And User should be in reg_user_order_history.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Account Payment Methods Page Header Links
    And User clicks on PAYMENT METHODS link in My Account Page
    And User should be in payment_info.jsp menu link page
    Then Verify embedded headers are visible and functional

  Scenario: Wishlist Page Header Footer Links
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    Then Verify embedded headers are visible and functional
