@Account @HighLevel
Feature: Account details Page validations

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Goes to sign in page

  Scenario: Validate error messages and update details in my details form
    When Enter random first name as First Name in create account section
    And Enter random last name as Last Name in create account section
    And Enter random email as Email in create account section
    And Enter random password as Password in create account section
    And United States is selected as default value
    And User clicks on create an account button
    Then Verify user is in homepage

    When click on MY ACCOUNT from header
    And user clicks on "My Details" from My Account dropdown
    Then Verify birth field is enabled
    And Verify 'Add your birthday and we'll send you something special on your big day!' copy displayed

    When Update first name with invalid data
    Then Verify 'Please enter first name.' error message displayed for first name field

    When Update last name with invalid data
    Then Verify 'Please enter last name.' error message displayed for last name field

    When Update email with invalid data
    And Verify 'Please enter a valid email address.' error message displayed for email field

    When Select March as Month from date
    Then Verify 'Please enter Day.' error message displayed for Birth field

    When Select Month as Month from date
    And Select 01 as day from date
    Then Verify 'Please enter Month.' error message displayed for Birth field

    When User scrolls up the page
    And click on MY ACCOUNT from header
    And user clicks on "My Details" from My Account dropdown
    Then Verify Embedded header is displayed
    And Verify embedded footer is visible and functional

    When Update first name with valid data
    And Update last name with valid data
    And Update email with valid data
    And Select March as Month from date
    And Select 01 as day from date
    And Click on Change Password in my details form
    And Enter old and new password details
    And click on save button
    Then verify confirmation message displayed
    And Verify birth field is disabled
    And Verify 'Better than cake: make sure you are signed up for emails to get a special gift on your big day!' copy displayed

  Scenario Outline: Validate drop down in My details form is functional for domestic users

    When User provides <userCategory> category login information
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account home page
    And User should be in /account/home.jsp menu link page

    When User clicks on MY DETAILS link in My Account Page
    Then My Details form should display
    And User should be in /r/account/details menu link page

    When User selects Home from my details dropdown
    Then User should be in /account/home.jsp menu link page

    When User presses back button
    And User selects Email Preferences from my details dropdown
    Then User should be in email_preferences.jsp menu link page

    When User presses back button
    And User selects Catalog Preferences from my details dropdown
    Then User should be in catalog_preferences.jsp menu link page

    When User presses back button
    And User selects Payment Methods from my details dropdown
    Then User should be in payment_info.jsp menu link page

    When User presses back button
    And User selects Gift Card Balance from my details dropdown
    Then User should be in checkout/giftcard_balance1.jsp menu link page

    When User presses back button
    And User selects Address Book from my details dropdown
    Then User should be in address_book.jsp menu link page

    When User presses back button
    And User selects Order History from my details dropdown
    Then User should be in reg_user_order_history.jsp menu link page

    When User presses back button
    And User selects My Wishlist from my details dropdown
    Then User should be in /wishlist menu link page

    When User presses back button
    And User presses back button
    And User navigates to my detail form
    Then Verify J.Crew Card Rewards Status reward link for <userCategory> user in My details dropdown

    When User selects J.Crew Card Rewards Status from my details dropdown
    Then User should be in /r/account/jccc-rewards menu link page

    When User presses back button
    And User navigates to my detail form
    And User selects Sign Out from my details dropdown
    Then Verify user is in homepage

    Examples:
      |userCategory |
      |loyalty  |
      |noLoyalty|
