@Account @HighLevel
Feature: Account details Page validations

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Goes to sign in page

  Scenario: Validate error messages and update details in my details form
    And Enter random first name as First Name in create account section
    And Enter random last name as Last Name in create account section
    And Enter random email as Email in create account section
    And Enter random password as Password in create account section
    And selects any country from the country list

    And User clicks on create an account button
    And Verify user is in homepage

    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown

    And Verify birth field is enabled
    And Verify 'Add your birthday and we'll send you something special on your big day!' copy displayed

    And Update first name with invalid data
    And Verify 'Please enter first name.' error message displayed for first name field
    And Update last name with invalid data
    And Verify 'Please enter last name.' error message displayed for last name field
    And Update email with invalid data
    And Verify 'Please enter a valid email address.' error message displayed for email field

    And Select March as Month from date
    And Verify 'Please enter Day.' error message displayed for Birth field
    And Select Month as Month from date

    And Select 01 as day from date
    And Verify 'Please enter Month.' error message displayed for Birth field
    And User scrolls up the page
    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown
    And Verify Embedded header is displayed

    And Verify embedded footer is visible and functional

    And Update first name with valid data
    And Update last name with valid data
    And Update email with valid data

    And Select March as Month from date
    And Select 01 as day from date
    And Click on Change Password in my details form
    And Enter old and new password details
    And click on save button
    And verify confirmation message displayed

    And Verify birth field is disabled
    And Verify 'Better than cake: make sure you are signed up for emails to get a special gift on your big day!' copy displayed


  Scenario Outline: Validate drop down is functional in My details form
    When User provides <userType> login information
    And Check box is enabled
    And Hits sign in button

    Then User is in My Account home page
    And User should be in /account/home.jsp menu link page

    When User clicks on MY DETAILS link in My Account Page
    Then My Details form should display
    And User should be in /r/account/details menu link page

    When User selects Home from my details dropdown
    And User should be in /account/home.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects My Details from my details dropdown
    Then My Details form should display
    And User should be in /r/account/details menu link page

    When User selects Email Preferences from my details dropdown
    Then User should be in email_preferences.jsp menu link page

    When User presses back button
    Then My Details form should display


    When User selects Catalog Preferences from my details dropdown
    Then User should be in catalog_preferences.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Payment Methods from my details dropdown
    Then User should be in payment_info.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Gift Card Balance from my details dropdown
    Then User should be in checkout/giftcard_balance1.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects Address Book from my details dropdown
    Then User should be in address_book.jsp menu link page
    And User presses back button

    When User selects Order History from my details dropdown
    Then User should be in reg_user_order_history.jsp menu link page

    When User presses back button
    Then My Details form should display

    When User selects My Wishlist from my details dropdown
    Then User should be in /wishlist menu link page
    And User presses back button

    When User presses back button
    Then My Details form should display

    And validate J.Crew Card Rewards Status option is <isRewards>

    When User selects Sign Out from my details dropdown
    Then Verify user is in homepage

    Examples:
    |userType |isRewards    |
    |noLoyalty|notVisible   |
    |loyalty  |visible      |