@NewAccount
Feature: Create New Account in US

  Scenario: Create a new account in US and checkout
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User get create account form
    And Selected country matches the current country context
    And Verify form does not contain international email option message

    When first name field is filled with new data
    And last name field is filled with new data
    And email field is filled with new data
    And password field is filled with new data
    And User selects country from US group
    And User clicks Create An Account button
    Then Verify user is in homepage

    When User goes to My Details using header
    When User clicks on Home link in Account detail Page

    When User clicks on Payment Methods link in My Account Page
    Then Verify user is in Payment Methods page
    And User has 0 payment methods

    When User adds new payment method
    Then User has 1 payment methods

    When User opens menu
    And User goes to home using menu drawer
    And User adds to bag a random product using a main category
    And User clicks in bag
    And User clicks check out button
    And In Shipping Address Page, user clicks continue
    And User selects random shipping method and continue
    And In Payment page, user clicks continue
    And In Review page, user fills cvv and places order
    Then User gets an order confirmation number
