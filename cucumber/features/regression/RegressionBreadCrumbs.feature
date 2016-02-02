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
    Then User is on homepage

   Examples:
    |gender|
    |Women |
    |Men   |
    |Boys  |
    |Girls |

   #US9724_TC03
  Scenario: Validate breadcrumbs display on  Sale Landing Page
    And User clicks on sale link from top nav
    And User is in sale landing page
    Then Breadcrumb should display J.Crew