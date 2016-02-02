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
  Scenario: Validate breadcrumbs display and functionality on category/category plus pages
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    And Breadcrumb should display J.Crew/Women
    And Clicks on Women Breadcrumb
    Then User is in gender landing page
    And User presses back button
    When Clicks on J.Crew Breadcrumb
    And Verify user is in homepage







