@PDPSizeAndFit
Feature: Sale Landing functionality

  Background:
    Given User goes to homepage
    And User clicks on sale link from top nav

  Scenario: Verify Sale Landing Page
    Then Verify sale landing page is displayed
    And Verify url is /r/login