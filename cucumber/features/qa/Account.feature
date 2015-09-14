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

    Examples:
    |Username|Password|
    |test@example.org|test1234|
    |test2@example.org|test1234|
   # |test@test.net    |no      |

    
  Scenario: validating user sign in state after unchecking keep me signed in
    And Goes to sign in page
    When User enters test@example.org as email
    And User enters test1234 as password
    And User disables check box
    And Hits sign in button
    Then User is in My Account page
    And User goes to homepage
    And User clicks on hamburger menu
    And My Account link is present
    And Selects WOMEN Category from hamburger menu
    And Selects Shirts and Tops from Women Category in hamburger menu
    And User should be in shirtsandtops page for women
    And User clicks on hamburger menu
    And User clicks on back link
    And My Account link is present
   
    