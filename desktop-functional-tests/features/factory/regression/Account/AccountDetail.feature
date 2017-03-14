@Account2
Feature: Account details Page validations

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  @HighLevel
  Scenario: Verify account left nav links from details page for domestic user
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on My Details link in My Account Page
    Then Verify user is in account details page
    And User should be in account/account_detail.jsp menu link page

    When User clicks on Email Preferences link in Account detail Page
    Then User should be in account/email_preferences.jsp menu link page
    And User presses browser back button

    When User clicks on Payment Methods link in Account detail Page
    Then User should be in account/payment_info.jsp menu link page
    And User presses browser back button

    When User clicks on Gift Card Balance link in Account detail Page
    Then Verify user is navigated to url checkout/giftcard_balance1.jsp on external page   

    When User clicks on Address Book link in Account detail Page
    Then User should be in account/address_book.jsp menu link page
    And User presses browser back button

    When User clicks on Order History link in Account detail Page
    Then User should be in account/reg_user_order_history.jsp menu link page
    And User presses browser back button

    When User clicks on My Wishlist link in Account detail Page
    Then User should be in /wishlist menu link page
    And User presses browser back button

    And User clicks on Sign Out link in Account detail Page