@Account
Feature: Account Feature QA Suite

  Background:
    Given User is on homepage

  Scenario Outline: sign in page should be verified and signed in
    Given Goes to sign in page
    When User enters <Username> as email
    And User enters <Password> as password
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account page
    And User clicks on SIGN OUT link in My Account Page
    Then Verify user is in homepage
    And User is signed out
    And Verify BAG header link is displayed

    Examples:
      | Username          | Password |
      | test@example.org  | test1234 |
      | test2@example.org | test1234 |


  Scenario Outline: validating user sign in state after unchecking keep me signed in
    And Goes to sign in page
    When User enters <Username> as email
    And User enters <Password> as password
    And User disables check box
    And Hits sign in button
    Then User is in My Account page
    And User bag is cleared
    And User goes to homepage
    And Clicks on JCrew Logo
    And User clicks on hamburger menu
    And My Account link is present
    And Selects Women Category from hamburger menu
    And User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    And User clicks on hamburger menu
    And User clicks on back link
    And My Account link is present
    And User clicks on My Account link
    And User is in My Account page

    Examples:
      | Username          | Password |
      | test@example.org  | test1234 |
      | test2@example.org | test1234 |
