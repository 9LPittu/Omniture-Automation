@BreadCrumbs  @HighLevel
Feature: Global Header: Breadcrumbs

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up

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
   #US15452_TC003
   #Scenario: 'Sale' in top nav should direct to Sale Landing Page
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
    |Girls |SKIRTS|J.Crew/Girls|
    |Boys  |SHIRTS          |J.Crew/Boys |

    #US9724_TC06--context chooser not available in the application at this time
   #US9724_TC07
   #Scenario: Validate breadcrumbs display and functionality on Size charts (from footer link) page
   #Moved to RegressionFooter.feature

     #US9724_TC08
     #Merged: Scenario: Validate Sidecar url when user is on PDP from Search array page
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
     And Deletes browser cookies

    #US9724_TC10, #US9724_TC11
#  Scenario: Validate breadcrumbs functionality and display on sign in/ register for email page
#    And Goes to sign in page
#    And Clicks on create new account
#    And JCrew Logo is present
#    And Clicks on JCrew Logo
#    And Verify user is in homepage

     #US9724_TC12
     #US9724_TC14
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
    And page url should contain isFromSearch
    And Breadcrumb should display J.crew/Search Results
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    And Clicks on Search Results Breadcrumb
    And User is in search results page


      #US9724_TC13
      #US9724_TC15
      #Merged: Scenario: Validate Sidecar url when user is on PDP from Sale array page
  Scenario: Validate breadcrumbs display and functionality on the sale category array page
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User clicks on sale department women
    Then User is in Sale results page
    And Breadcrumb should display J.crew/Sale
    And Selects any product from product grid list
    And User is in product detail page
    And page url should contain isFromSale
    And Breadcrumb should display J.crew/Sale/women
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    And Clicks on Sale Breadcrumb
    And User is in sale landing page
    And User presses back button
    And Clicks on women Breadcrumb
    Then User is in Sale results page

     #US9724_TC16
    #Scenario: Validate no breadcrumbs are displayed pages

    #Social responsibility page

    #Moved to RegressionFooter.feature

    #Our story Page
    #Moved to RegressionFooter.feature

    #Investor Relations page
    #Moved to RegressionFooter.feature

    #The J.Crew Gift Card page
    #Moved to RegressionFooter.feature

    # The J.Crew Credit Card page
    #Moved to RegressionFooter.feature

     #privacy policy page
    #Moved to RegressionFooter.feature

    #contact us page

    #Moved to RegressionFooter.feature

    #Order status Page
    #Moved to RegressionFooter.feature

    #terms of use page
    #Moved to RegressionFooter.feature

    #Request a style guide page
    #Moved to RegressionFooter.feature


    # US9724_TC16 (cont)   Store Locator page
    #moved to HeaderAndFooter3Footer.feature
    #Scenario: Validate no breadcrumbs are displayed on store locator page


    #US9724_TC16 (cont)
    #Moved to RegressionAccountGuest.feature