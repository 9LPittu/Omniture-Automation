@NewAccount @HighLevel

Feature: Create New Account International

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User get create account form
    And Selected country matches the current country context
    And Verify form does not contain international email option message

  Scenario: Validate error messages in new account form
    When User clicks Create An Account button
    Then Fields will get error messages, ignore case
      | first name | Please enter first name.            |
      | last name  | Please enter last name.             |
      | email      | Please enter a valid email address. |
      | password   | Please enter password.              |
    When email field is filled with invalid data "invalid"
    Then Fields will get error messages, ignore case
      | email | Please enter a valid email address. |
    When email field is filled with new data
    Then Error message disappears from field email
    When password field is filled with less than 6 characters
    Then Fields will get error messages, ignore case
      | password | Please enter password. |
    When password field is filled with new data
    Then Error message disappears from field password
    When first name field is filled with new data
    Then Error message disappears from field first name
    When last name field is filled with new data
    Then Error message disappears from field last name
    When User selects HK country
    Then Verify form contains international email option message
    When User selects other country
    Then Verify form contains international email option message
    When User selects US country
    Then Verify form does not contain international email option message

  