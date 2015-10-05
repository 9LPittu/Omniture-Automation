@Home
Feature: Home Page

  Scenario: Home page is functional
    Given User is on homepage
    Then JCrew Logo is present
    And Stores Link is present
    And Hamburger menu is present
    When User clicks on hamburger menu
    Then Hamburger Menu WOMEN Link is present
    And Hamburger Menu MEN Link is present
    And Hamburger Menu BOYS Link is present
    And Hamburger Menu GIRLS Link is present
    And Hamburger Menu WEDDING Link is present
    And Hamburger Menu SALE Link is present
    And Hamburger Menu BLOG Link is present