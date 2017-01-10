@NewAccount2 @HighLevel

Feature: Create New Account International

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User get create account form
    And Selected country matches the current country context
    And Verify form does not contain international email option message
  
  Scenario Outline: Create account in international context
    When first name field is filled with new data
    And last name field is filled with new data
    And email field is filled with new data
    And password field is filled with new data
    And User selects country from <country_group> group
    And User clicks Create An Account button
    Then Verify user is in homepage
    When User goes to My Details using header
    Then Verify user is in account details page
    Then User information should match My Details page

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

  Scenario: Create an existing account shows error message
    When first name field is filled with new data
    And last name field is filled with new data
    And email field is filled with known data
    And password field is filled with new data
    And User selects random country
    And User clicks Create An Account button
    Then Fields will get error messages, ignore case
      | email | This email id is already registered with us. Please sign-in. |