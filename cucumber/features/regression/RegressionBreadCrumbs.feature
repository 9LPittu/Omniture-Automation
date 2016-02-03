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
    And User clicks on looks we love
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
     And User is on /r/size-charts page
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
  Scenario: Validate breadcrumbs display and functionality on multi PDP page

    #US9724_TC10, #US9724_TC11
  Scenario: Validate breadcrumbs functionality anddisplay on sign in/ register for email page
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





