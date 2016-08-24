@AccountDev
Feature: My Account page validations

  Background:
    Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  Scenario: verify left nav links in my account pages are functional for domestic user
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on My Details link in My Account Page
    Then Verify user is in account details page
    And User should be in r/account/details menu link page

    When User clicks on Home link in Account detail Page

    When User clicks on Email Preferences link in My Account Page
    Then User should be in account/email_preferences.jsp menu link page

    When User clicks on Catalog Preferences link in My Account Page
    Then User should be in account/catalog_preferences.jsp menu link page

    When User clicks on Payment Methods link in My Account Page
    Then User should be in account/payment_info.jsp menu link page

    When User clicks on Gift Card Balance link in My Account Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And User presses browser back button

    When User clicks on Address Book link in My Account Page
    Then User should be in account/address_book.jsp menu link page


    When User clicks on Order History link in My Account Page
    Then User should be in account/reg_user_order_history.jsp menu link page


    When User clicks on My Wishlist link in My Account Page
    Then User should be in /wishlist menu link page
    And User presses browser back button


    When User clicks on Sign Out link in My Account Page


  Scenario Outline: Validate rewards link is visible only for loyalty user in my account pages

    When User provides <userCategory> category login information
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account home page
    And User should be in /account/home.jsp menu link page

    When User clicks on MY DETAILS link in My Account Page
    Then My Details form should display
    And User should be in /r/account/details menu link page

    Then Verify J.Crew Card Rewards Status reward link for <userCategory> user in My details dropdown

    When User selects J.Crew Card Rewards Status from my details dropdown
    Then User should be in /r/account/jccc-rewards menu link page

    Examples:
      |userCategory |
      |loyalty  |
      |noLoyalty|