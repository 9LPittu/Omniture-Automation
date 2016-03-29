@NewAccount

  Feature: Create New Account

    Scenario: Guest user is able to create an account
      Given User goes to homepage
      When User clicks on sign in using header
      Then User get create account form
      When User clicks Create An Account
      Then Fields will get error messages
          |first name|
          |last name |
          |email     |
          |password  |

