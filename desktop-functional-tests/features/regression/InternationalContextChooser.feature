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
     When User clicks on CATALOG PREFERENCES link in My Account Page
     Then User should be in catalog_preferences.jsp menu link page
     And User should see selected country in the footer
     When User clicks on PAYMENT METHODS link in My Account Page
     Then User should be in payment_info.jsp menu link page
     And User should see selected country in the footer
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


   Scenario Outline: Forgot Password Page context validtaion
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
       |NONPRICEBOOK  |

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
       |NONPRICEBOOK  |Women |
       |PRICEBOOK     |Men |
       |NONPRICEBOOK  |Men |
       |PRICEBOOK     |Girls |
       |NONPRICEBOOK  |Girls |
       |PRICEBOOK     |Boys |
       |NONPRICEBOOK  |Boys |
       |PRICEBOOK     |sale |
       |NONPRICEBOOK  |sale |

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
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
     And user should see country code in the url for international countries
     And Clicks edit button on item bag page
	 And User is in product detail page
     Then Update Bag button is pressed
	 Then User should be in shopping bag page
     And Clicks on checkout
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
     And Selects to checkout as guest
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
     When user fills selected country shipping address
     And Presses continue button on shipping address
     And Verifies is in shipping method page
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
     And Uses default value for shipping method
     #And Uses default value for gifts option
     And Clicks continue button on shipping method page
     And Verify user is in billing page
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
     And Fills required payment data in billing page
     And Submits payment data in billing page
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
     And Clicks on place your order
     And User should be in order confirmation page
     And Verify embedded headers links
     Then Verify embedded footer is visible and functional
     And user should see selected country in the footer
    Examples:
      |country_group|
      |PRICEBOOK|
      |NONPRICEBOOK|