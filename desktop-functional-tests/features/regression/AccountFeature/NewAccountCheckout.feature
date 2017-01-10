@NewAccount3 @HighLevel
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
    And User clicks on Home link in Account detail Page

    And User goes to Payment Methods using My Account menu
    Then Verify account Billing payment page is displayed

    And User has 0 payment methods

    When User adds new payment method
    Then User has 1 payment methods

    When User clicks JCrew logo
    Then Verify user is in homepage
    
    And User adds to bag a random product using a main category
    And User clicks in bag
    And User clicks check out button
    And In Shipping Address Page, user clicks continue
    And User selects a random shipping method and continues
    And In Payment page, user clicks continue
    
    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
