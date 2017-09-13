@International-Part1A @HighLevel
Feature: International Country Context - Part 1A

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario Outline: International context validation on all My Account related pages
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    When User clicks on sign in using header
    And User fills user data and signs in
    Then Verify user is in My Account main page

    And Verify J.Crew Card Rewards Status reward link for <userCategory> user in My account page

    When User clicks on My Details link in My Account Page
    Then User should be in r/account/details menu link page
    And Verify selected country is in footer

    When User clicks on Home link in Account detail Page

    When User clicks on Email Preferences link in My Account Page
    Then User should be in account/email_preferences.jsp menu link page
    And Verify selected country is in footer

    When User clicks on Catalog Preferences link in My Account Page
    And Verify selected country is in footer

    When User clicks on Gift Card Balance link in My Account Page
    And Verify selected country is in footer

    When User clicks on Payment Methods link in My Account Page
    Then User should be in account/payment_info.jsp menu link page
    And Verify selected country is in footer

    When User clicks on Address Book link in My Account Page
    Then User should be in account/address_book.jsp menu link page
    And Verify selected country is in footer

    When User clicks on Order History link in My Account Page
    Then User should be in account/order-history menu link page
    And Verify selected country is in footer

    When User clicks on My Wishlist link in My Account Page
    Then User should be in /wishlist menu link page
    And Verify selected country is in footer

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |


  Scenario Outline: Forgot Password Page context validation
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    When User clicks on sign in using header
    And Clicks on forgot password link
    And Verify user is in forgot password page
    And Verify selected country is in footer

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |