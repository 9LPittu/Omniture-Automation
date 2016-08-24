@Context_Part1 @HighLevel
Feature: International Country Context - Part 1

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

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

  Scenario Outline: Forgot Password Page context validtaion
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    And user should see selected country in the footer

    Examples:
      |country_group |
      |PRICEBOOK     |
      |NONPRICEBOOK  |
      
  Scenario Outline: Multiple Pages During Checkout Context validations
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And user should see selected country in the footer
	And user should see country code in the url for international countries
	And user selects any item from array page, select any color and size
    And User is in product detail page
    And user should see selected country in the footer
    And user should see country code in the url for international countries
    And Add to cart button is pressed
    And User clicks on item bag
    Then User should be in shopping bag page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Clicks edit button on item bag page
	And User is in product detail page
    Then Update Bag button is pressed
	Then User should be in shopping bag page
    And Clicks on checkout
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Selects to checkout as guest
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    When user fills selected country shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Uses default value for shipping method
    #And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Fills required payment data in billing page
    And Submits payment data in billing page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Clicks on place your order
    And User should be in order confirmation page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer

    Examples:
      |country_group|
      |PRICEBOOK|
      |NONPRICEBOOK|

  Scenario Outline: Validate drop down in My details form is functional for international users

    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And Goes to sign in page
    And User provides login information
    And Check box is enabled
    And Hits sign in button

    When User is in My Account home page
    And User should be in /account/home.jsp menu link page

    When User clicks on MY DETAILS link in My Account Page
    Then My Details form should display
    And User should be in /r/account/details menu link page

    When User selects Home from my details dropdown
    Then User should be in /account/home.jsp menu link page

    And User presses back button

    When User selects Email Preferences from my details dropdown
    Then User should be in email_preferences.jsp menu link page

    And User presses back button

    When User selects Catalog Preferences from my details dropdown
    Then User should be in catalog_preferences.jsp menu link page

    And User navigates to my detail form

    When User selects Payment Methods from my details dropdown
    Then User should be in payment_info.jsp menu link page

    And User presses back button

    When User selects Gift Card Balance from my details dropdown
    Then User should be in checkout/giftcard_balance1.jsp menu link page

    And User navigates to my detail form


    When User selects Address Book from my details dropdown
    Then User should be in address_book.jsp menu link page

    And User presses back button

    When User selects Order History from my details dropdown
    Then User should be in reg_user_order_history.jsp menu link page

    And User presses back button

    When User selects My Wishlist from my details dropdown
    Then User should be in /wishlist menu link page
    And User presses back button

    And User presses back button
    And User navigates to my detail form


    And Verify J.Crew Card Rewards Status reward link for International user in My details dropdown

    When User selects Sign Out from my details dropdown
    Then Verify user is in homepage

    Examples:
      |country_group |
      |PRICEBOOK     |
      |NONPRICEBOOK  |