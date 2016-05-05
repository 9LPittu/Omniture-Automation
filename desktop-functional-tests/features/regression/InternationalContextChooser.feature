@International
 Feature: International Country Context

   Background:
     Given User goes to homepage
     And User closes email capture

   Scenario: Context chooser page validation
     Then User should see Ship To section in footer
     And Verify country name is displayed in the ship to section of footer
     And Verify change link is displayed in the ship to section of footer
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     And UNITED STATES & CANADA region is displayed
     And ASIA PACIFIC region is displayed
     And EUROPE region is displayed
     And LATIN AMERICA & THE CARIBBEAN region is displayed
     And MIDDLE EAST & AFRICA region is displayed
     And Verify countries displayed correctly under each region
       |UNITED STATES & CANADA|
       |ASIA PACIFIC|
       |EUROPE|
       |LATIN AMERICA & THE CARIBBEAN|
       |MIDDLE EAST & AFRICA|
     And Click on "terms of use" link from terms section on the context chooser page
     And User is on internal /footer/termsofuse.jsp?sidecar=true page
     And User presses browser back button
     And Click on "privacy policy" link from terms section on the context chooser page
     And User is on internal /help/privacy_policy.jsp?sidecar=true page
     And User presses browser back button
     And Click on "SEE ALL FAQ & HELP" button from FAQ section on the context chooser page
     And User is on internal /help/international_orders.jsp?sidecar=true page
     And User presses browser back button
     And Click on "borderfree.com" link from FAQ section on the context chooser page
     And External http://www.pitneybowes.com/us/borderfree-is-now-part-of-pitney-bowes.html page is opened in a different tab


   Scenario Outline: International context validation on all My Account related pages
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     Given User selects <country_group> at random from context chooser page
     Then User should land on country specific home page
     And User should see selected country in the footer
     When User clicks on sign in using header
     And User fills user data and signs in
     Then Verify user is in My Account main page
     And User should see selected country in the footer
     When User clicks on MY DETAILS link in My Account Page
     Then User should be in account_detail.jsp menu link page
     And User should see selected country in the footer
     When User clicks on EMAIL PREFERENCES link in My Account Page
     Then User should be in email_preferences.jsp menu link page
     And User should see selected country in the footer
     #need to handle this validation for countrie other than us
#     When User clicks on CATALOG PREFERENCES link in My Account Page
#     Then User should be in catalog_preferences.jsp menu link page
     And User should see selected country in the footer
     When User clicks on PAYMENT METHODS link in My Account Page
     Then User should be in payment_info.jsp menu link page
     And User should see selected country in the footer
     #need to handle this validation for countrie other than us
#     When User clicks on GIFT CARD BALANCE link in My Account Page
#     Then User should be in checkout/giftcard_balance1.jsp menu link page
#     And User should see selected country in the footer
     When User clicks on ADDRESS BOOK link in My Account Page
     Then User should be in address_book.jsp menu link page
     And User should see selected country in the footer
     When User clicks on ORDER HISTORY link in My Account Page
     Then User should be in reg_user_order_history.jsp menu link page
     And User should see selected country in the footer
     When User clicks on WISHLIST link in My Account Page
     Then User should be in /wishlist menu link page
     And User should see selected country in the footer


     Examples:
       |country_group |
       |PRICEBOOK     |
       |NON-PRICEBOOK  |


   Scenario Outline: Forgot Password Page context validation
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     Given User selects <country_group> at random from context chooser page
     Then User should land on country specific home page
     And User should see selected country in the footer
     When User clicks on sign in using header
     And Clicks on forgot password link
     And Verify user is in forgot password page
     And User should see selected country in the footer

     Examples:
       |country_group |
       |PRICEBOOK     |
       |NON-PRICEBOOK  |

   Scenario Outline: International context validation on gender landing pages and sale landing page
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     Given User selects <country_group> at random from context chooser page
     Then User should land on country specific home page
     And User should see selected country in the footer
     And User clicks on <topnav> link from top nav
     And User should see selected country in the footer
     And User should see country code in the url for international countries

     Examples:
       |country_group |topnav|
       |PRICEBOOK     |Women |
       |NON-PRICEBOOK  |Women |
       |PRICEBOOK     |Men |
       |NON-PRICEBOOK  |Men |
       |PRICEBOOK     |Girls |
       |NON-PRICEBOOK  |Girls |
       |PRICEBOOK     |Boys |
       |NON-PRICEBOOK  |Boys |
       |PRICEBOOK     |sale |
       |NON-PRICEBOOK  |sale |

   Scenario Outline: Multiple Pages During Checkout Context validations
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     Given User selects <country_group> at random from context chooser page
     Then User should land on country specific home page
     And User should see selected country in the footer
     And User navigates to a subcategory from main category
     And Verify context in the array page
     And User should see selected country in the footer
     And User selects random product from array
     And User is in product detail page
     And User should see selected country in the footer
     And Verify context in the product detail page
     And User adds selected product to bag
     And User clicks in bag
     And User is in shopping bag page
     And Verify that shopping bag has expected context
     And User clicks check out button
     And User should see selected country in the footer
     And User selects guest check out
     And User should see selected country in the footer
     And Guest user fills shipping address and continue
     And User should see selected country in the footer
     And User selects random shipping method and continue
     And User should see selected country in the footer
     And User fills payment method and continue
     And User should see selected country in the footer
     And User reviews and places order
     And User should see selected country in the footer
     Then User gets an order confirmation number
     And User should see selected country in the footer

    Examples:
      |country_group|
     # |PRICEBOOK|
     |NON-PRICEBOOK|
      
   Scenario Outline: context validation on sale landing page from Hamburger menu
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     Given User selects <country_group> at random from context chooser page
     Then User should land on country specific home page
     And User should see selected country in the footer
     And User opens menu
     And User selects sale category from menu
     And User should see selected country in the footer
     And User should see country code in the url for international countries

    Examples:
      |country_group|
      |PRICEBOOK|
      |NON-PRICEBOOK|
      
   Scenario Outline: Context is displayed on search page
     Then Click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     Given User selects <country_group> at random from context chooser page
     Then User should land on country specific home page
     And User should see selected country in the footer
     And User searches for a random search term
     Then User is in search results page
     And User should see selected country in the footer
     And User should see country code in the url for international countries

    Examples:
      |country_group|
      |PRICEBOOK|
      |NON-PRICEBOOK|
      
   	#multipdp, shoppable tray
    # shoppable tray page for desktop is not ready.Not running the below
#  Scenario Outline: international context validation for shoppable tray page
#    Then Click on change link from footer
#    And User is on context chooser page
#    And User is on internal /r/context-chooser page
#    Given User selects <country_group> at random from context chooser page
#    Then User should land on country specific home page
#    And User should see selected country in the footer
#    And User opens menu
#    And User selects random tray from available categories
#      |Women|THIS MONTH'S FEATURES|looks we love |
#      |Men  |THIS MONTH'S FEATURES|1 Suit, 5 Ways|
#      |Girls|THIS MONTH'S FEATURES|Looks We Love |
#      |Boys |THIS MONTH'S FEATURES|Looks We Love |
#    And User should see country code in the url for international countries
#
#    Examples:
#    |country_group|
#    |PRICEBOOK|
#    |NON-PRICEBOOK|