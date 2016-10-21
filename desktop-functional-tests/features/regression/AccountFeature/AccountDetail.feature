@Account
Feature: Account details Page validations

  Background:
    Given User is on homepage with clean session
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

    When User goes to My Details using header
    Then Verify user is in account details page
    And Verify birth field is enabled

#    And Verify 'Add your birthday and we'll send you something special on your big day!' copy displayed

    And User update first name with invalid data
    Then Verify 'Please enter first name.' error message displayed for first name field

    When User update last name with invalid data
    Then Verify 'Please enter last name.' error message displayed for last name field

    When User update email with invalid data
    And Verify 'Please enter a valid email address.' error message displayed for email field

    When Select March as Month from date
    Then Verify 'Please enter Day.' error message displayed for Birth field

    When Select Month as Month from date
    And Select 01 as day from date
    Then Verify 'Please enter Month.' error message displayed for Birth field


    When User goes to My Details using header
    Then Verify user is in account details page

    When User update first name with valid data
    And User update last name with valid data
    And User update email with valid data
    And Select March as Month from date
    And Select 01 as day from date
    And Click on Change Password in my details form
    And Enter old and new password details
    And click on save button
    Then verify confirmation message displayed
    And Verify birth field is disabled
#    And Verify 'Better than cake: make sure you are signed up for emails to get a special gift on your big day!' copy displayed


  Scenario: verify account left nav links from details page for domestic user
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on My Details link in My Account Page
    Then Verify user is in account details page
    And User should be in r/account/details menu link page

    When User clicks on Email Preferences link in Account detail Page
    Then User should be in account/email_preferences.jsp menu link page
    And User presses browser back button

    When User clicks on Catalog Preferences link in Account detail Page
    Then User should be in account/catalog_preferences.jsp menu link page
    And User presses browser back button

    When User clicks on Payment Methods link in Account detail Page
    Then User should be in account/payment_info.jsp menu link page
    And User presses browser back button

    When User clicks on Gift Card Balance link in Account detail Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And User presses browser back button

    When User clicks on Address Book link in Account detail Page
    Then User should be in account/address_book.jsp menu link page
    And User presses browser back button

    When User clicks on Order History link in Account detail Page
    Then User should be in account/reg_user_order_history.jsp menu link page
    And User presses browser back button

    When User clicks on My Wishlist link in Account detail Page
    Then User should be in /wishlist menu link page
    And User presses browser back button
    And User presses browser back button

	Then User clicks on My Details link in My Account Page
    And User clicks on Sign Out link in Account detail Page


  Scenario Outline: Validate rewards link is visible only for JCCC associated users in account detail page

    When User fills <userCategory> category data and signs in
    Then Verify user is in My Account main page

    When User clicks on My Details link in My Account Page
    Then Verify user is in account details page
    And User should be in r/account/details menu link page
    And Verify J.Crew Card Rewards Status reward link for <userCategory> user in account detail page

    When User clicks on J.Crew Card Rewards Status reward link from Account detail Page
    Then User should be in /r/account/jccc-rewards menu link page

    Examples:
      |userCategory |
      |loyalty  |
      |noLoyalty|