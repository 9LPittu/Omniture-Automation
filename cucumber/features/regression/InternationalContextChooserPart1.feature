@Context_Part1 @HighLevel
Feature: International Country Context - Part 1

  Background:
    Given User is on homepage with clean session

    #US9479_TC01, US9479_TC02, US9479_TC04
  #US9479_TC03 -  not automating because the test case is about validating context chooser page with mockup
  #Validate context chooser flag is displayed on all the sidecar pages in the footer
  Scenario: Context chooser flag should be displayed and functional in the footer section.
    Then user should see Ship To section in footer
    And verify country name is displayed in the ship to section of footer
    And verify change link is displayed in the ship to section of footer
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    And Handle the Email Capture pop up
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And UNITED STATES & CANADA region drawer is displayed
    And ASIA PACIFIC region drawer is displayed
    And EUROPE region drawer is displayed
    And LATIN AMERICA & THE CARIBBEAN region drawer is displayed
    And MIDDLE EAST & AFRICA region drawer is displayed
    And user should see all regional drawers closed by default
    And expand each regional drawer and verify the countries displayed and only one drawer should be opened
      |UNITED STATES & CANADA|
      |ASIA PACIFIC|
      |EUROPE|
      |LATIN AMERICA & THE CARIBBEAN|
      |MIDDLE EAST & AFRICA|

    And click on "terms of use" link from terms section on the context chooser page
    And User is on internal /footer/termsofuse.jsp?sidecar=true page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And User presses back button
    And click on "privacy policy" link from terms section on the context chooser page
    And User is on internal /help/privacy_policy.jsp?sidecar=true page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And User presses back button
    And click on "SEE ALL FAQ & HELP" button from FAQ section on the context chooser page
    And User is on internal /help/international_orders.jsp?sidecar=true page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And User presses back button
    And click on "borderfree.com" link from FAQ section on the context chooser page
    And external https://www.borderfree.com/#/ page is opened in a different tab

  Scenario Outline: International context validation on all My Account related pages
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    And Handle the Email Capture pop up
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    When Goes to sign in page
    And User provides login information
    And Check box is enabled
    And Hits sign in button
    And User is in My Account home page
    And user should see selected country in the footer

    And Verify J.Crew Card Rewards Status reward link for International user in My account page

    When User clicks on MY DETAILS link in My Account Page
    And User should be in /r/account/details menu link page
    And user should see selected country in the footer
    And User presses back button
    When User clicks on EMAIL PREFERENCES link in My Account Page
    Then User should be in email_preferences.jsp menu link page
    And user should see selected country in the footer
    When User clicks on CATALOG PREFERENCES link in My Account Page
    Then User should be in catalog_preferences.jsp menu link page
    And user should see selected country in the footer
    When User clicks on PAYMENT METHODS link in My Account Page
    Then User should be in payment_info.jsp menu link page
    And user should see selected country in the footer
    When User clicks on GIFT CARD BALANCE link in My Account Page
    Then User should be in checkout/giftcard_balance1.jsp menu link page
    And user should see selected country in the footer
    When User clicks on ADDRESS BOOK link in My Account Page
    Then User should be in address_book.jsp menu link page
    And user should see selected country in the footer
    When User clicks on ORDER HISTORY link in My Account Page
    Then User should be in reg_user_order_history.jsp menu link page
    And user should see selected country in the footer
    When User clicks on WISHLIST link in My Account Page
    Then User should be in /wishlist menu link page
    And user should see selected country in the footer

    Examples:
      |country_group |
      |PRICEBOOK     |
      |NONPRICEBOOK  |