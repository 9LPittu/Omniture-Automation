Feature: Account Feature

  Background:
    Given User is on homepage

  Scenario: Sign In
    Given Goes to sign in page
    When User enters test@example.org as email
    And User enters test1234 as password
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