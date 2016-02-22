@BreadCrumbs
Feature: Global Header: Breadcrumbs

  Background:
    Given User is on homepage

  #US9724_TC01
  Scenario: Validate J crew logo displayed on Home page
    And JCrew Logo is present

  #US9724_TC02
  Scenario Outline: Validate J crew logo display and functionality on Department/Gender Landing Pages
    And User clicks on <gender> link from top nav
    And JCrew Logo is present
    And Clicks on JCrew Logo
    And Verify user is in homepage

   Examples:
    |gender|
    |Women |
    |Men   |
    |Boys  |
    |Girls |

   #US9724_TC03
  Scenario: Validate breadcrumbs display and functionality on Sale Landing Page
    And User clicks on sale link from top nav
    And User is in sale landing page
    Then Breadcrumb should display J.Crew
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage

    #US9724_TC04
  Scenario: Validate breadcrumbs display and functionality on Features pages
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on THIS MONTH'S FEATURES subcategory from Women Category
    And User clicks on looks we love from featured this month
    And JCrew Logo is present
    And Clicks on JCrew Logo
    And Verify user is in homepage


   #US9724_TC05
  Scenario Outline: Validate breadcrumbs display and functionality on category/category plus pages
    And User clicks on hamburger menu
    And Selects <gender> Category from hamburger menu
    When User clicks on <category> subcategory from <gender> Category
    And Breadcrumb should display <breadcrumb_text>
    And Clicks on <gender> Breadcrumb
    Then User is in <gender> gender landing page
    And User presses back button
    When Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    Examples:
    |gender|category|breadcrumb_text|
    |Women |SWEATERS| J.Crew/Women  |
    |Men   |CASUAL SHIRTS|J.Crew/Men|
    |Girls |COLLECTIBLE TEES|J.Crew/Girls|
    |Boys  |SHIRTS          |J.Crew/Boys |

    #US9724_TC06--context chooser not available in the application at this time
   #US9724_TC07
  Scenario: Validate breadcrumbs display and functionality on Size charts (from footer link) page
     Then Click on footer link Let Us Help You
     And Click on sublink Size Charts from Let Us Help You footer link
     And User is on internal /r/size-charts page
     Then Breadcrumb should display J.Crew
     And Clicks on J.Crew Breadcrumb
     And Verify user is in homepage

     #US9724_TC08
  Scenario: Validate breadcrumbs display and functionality on PDP page
     And User clicks on hamburger menu
     And Selects Women Category from hamburger menu
     When User clicks on SWEATERS subcategory from Women Category
     And Selects any product from product grid list
     Then User is in product detail page
     Then Breadcrumb should display J.Crew/Women/Sweaters
     When Clicks on sweaters Breadcrumb
     Then User should be in subcategory page
     And User presses back button
     When Clicks on Women Breadcrumb
     Then User is in Women gender landing page
     And User presses back button
     When Clicks on J.Crew Breadcrumb
     And Verify user is in homepage

    #US9724_TC09 multi pdp not available?
  #will implement later
  #Scenario: Validate breadcrumbs display and functionality on multi PDP page

    #US9724_TC10, #US9724_TC11
  Scenario: Validate breadcrumbs functionality and display on sign in/ register for email page
    And Goes to sign in page
    Then Breadcrumb should display J.Crew
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    And Clicks on create new account
    And JCrew Logo is present
    And Clicks on JCrew Logo
    And Verify user is in homepage

     #US9724_TC12
  Scenario: Validate breadcrumbs display and functionality on the search array page
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    Then Breadcrumb should display J.Crew
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Selects any product from product grid list
    And User is in product detail page
    And Breadcrumb should display J.crew/Search Results
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    And Clicks on Search Results Breadcrumb
    And User is in search results page


      #US9724_TC13
  Scenario: Validate breadcrumbs display and functionality on the sale category array page
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User clicks on sale department women
    Then User is in Sale results page
    And Breadcrumb should display J.crew/Sale
    And Selects any product from product grid list
    And User is in product detail page
    And Breadcrumb should display J.crew/Sale/women
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    And Clicks on Sale Breadcrumb
    And User is in sale landing page
    And User presses back button
    And Clicks on women Breadcrumb
    Then User is in Sale results page

    #US9724_TC14
  Scenario: Validate Sidecar url when user is on PDP from Search array page
    And User presses search button
    When Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Selects any product from product grid list
    And User is in product detail page
    And page url should contain isFromSearch


   #US9724_TC15
  Scenario:Validate Sidecar url when user is on PDP from Sale array page
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User is in sale landing page
    And User clicks on sale department women
    And User is in Sale results page
    And Selects any product from product grid list
    And User is in product detail page
    And page url should contain isFromSale


     #US9724_TC16
  Scenario: Validate no breadcrumbs are displayed pages

    #Social responsibility page
    And Click on footer link About J.Crew
    And Click on sublink Social Responsibility from About J.Crew footer link
    And Verify user is on social responsibility page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #Our story Page
    Then Click on footer link About J.Crew
    And Click on sublink Our Story from About J.Crew footer link
    And Verify user is on about us page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #Investor Relations page
    Then Click on footer link About J.Crew
    And Click on sublink Investor Relations from About J.Crew footer link
    And User is on external http://investors.jcrew.com page
    #the page is not showing embedded header
    #And Verify J crew breadcrumb is not displayed
    #And Verify Embedded header is displayed
    And User presses back button

    #The J.Crew Gift Card page
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Gift Card from Our Cards footer link
    And Verify user is on the j.crew gift card page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    # The J.Crew Credit Card page
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Credit Card from Our Cards footer link
    And Verify user is on the j.crew credit card page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #Store Locator page
    When Click on footer link Our Stores
    And Store Locator sublink is displayed
    And Click on sublink Store Locator from Our Stores footer link
    And Verify user is on help store locator page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

     #privacy policy page
    And click on "PRIVACY POLICY" in the legal links section of footer
    And User is on internal /help/privacy_policy.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #contact us page
    Then Click on footer link Let Us Help You
    And Click on sublink Contact Us from Let Us Help You footer link
    And User is on internal /footie/contactus.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #Order status Page

    When Click on footer link Let Us Help You
    And Click on sublink Order Status from Let Us Help You footer link
    Then Verify user is on order status page
    And User is on internal /help/order_status.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #terms of use page

    And click on "TERMS OF USE" in the legal links section of footer
    And User is on internal /footer/termsofuse.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    #Request a style guide page

    Then Click on footer link Let Us Help You
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    And Verify user is on request a catalog page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button


    #US9724_TC16 (cont)
  Scenario: Validate no breadcrumbs are displayed o Account related pages
    #Wishlist Page
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    When User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    Then User should be in wishlist page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    When User clicks on ADDRESS BOOK link in My Account Page
    And User should be in address_book.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    And User clicks on CATALOG PREFERENCES link in My Account Page
    And User should be in catalog_preferences.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    And User clicks on EMAIL PREFERENCES link in My Account Page
    And User should be in email_preferences.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    And User clicks on MY DETAILS link in My Account Page
    And User should be in account_detail.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    And User clicks on ORDER HISTORY link in My Account Page
    And User should be in reg_user_order_history.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

    And User clicks on PAYMENT METHODS link in My Account Page
    And User should be in payment_info.jsp menu link page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button











