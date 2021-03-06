@Account
Feature: Account details Page validations 2

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  Scenario: Validate error messages and update details in my details form
    When User get create account form
    And first name field is filled with new data
    And last name field is filled with new data
    And email field is filled with new data
    And password field is filled with new data
    And User selects US country
    And User clicks Create An Account button
    Then Verify user is in homepage

    #When User goes to My Details from header
    When User clicks in My Account
    Then Verify user is in account details page
    And Verify birth field is enabled

#    And Verify 'Add your birthday and we'll send you something special on your big day!' copy displayed

    And User update first name with invalid data
    And click on save button
    Then Verify 'please enter first name' error message displayed for first name field

    When User update last name with invalid data
    And click on save button
    Then Verify 'please enter last name' error message displayed for last name field

    When User update email with invalid data
    And click on save button
    And Verify 'Please enter a valid email address.' error message displayed for email field
    
    When User clicks in My Account
    Then Verify user is in account details page

    When User update first name with valid data
    And User update last name with valid data
    And User update email with valid data
    And User update re-enter email with valid data
    And Select March as Month from date
    And Select 01 as day from date
    And Click on Change Password in my details form
    And Enter old and new password details
    And click on save button
    Then verify confirmation message displayed
    And Verify birth field is disabled
    
#    And Verify 'Better than cake: make sure you are signed up for emails to get a special gift on your big day!' copy displayed