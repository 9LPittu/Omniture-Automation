@Account @HighLevel
Feature: My Account Page

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

    And Verify birthdate is enabled
    And Update first name to invalid and verify 'Please enter first name.' error message
    And Update last name to invalid and verify 'Please enter last name.' error message
    And Update email to invalid and verify 'Please enter a valid email address.' error message

    And Select March as Month from date
    And Verify 'Please enter Day.' error message should display for birth field
    And Select Month as Month from date

    And Select 01 as day from date
    And Verify 'Please enter Month.' error message should display for birth field
    And User scrolls up the page
    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown
    And Verify Embedded header is displayed

    And Verify embedded footer is visible and functional

    And Update first name in my details form
    And Update last name in my details form
    And Update email in my details form

    And Select March as Month from date
    And Select 01 as day from date

    And click on save button
    And verify confirmation message displayed
    And Verify birthdate is disabled



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