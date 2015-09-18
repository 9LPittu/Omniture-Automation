@Home
Feature: Smoke Tests Home Page
  Background:
    Given User is on homepage

  Scenario: JCrew Logo
    Then JCrew Logo is present

#  Scenario: Department Links
#    Then Verify WOMEN Department Link is present
#    And Verify MEN Department Link is present
#    And Verify BOYS Department Link is present
#    And Verify GIRLS Department Link is present

  Scenario: Stores Link
    Then Stores Link is present

  Scenario: Hamburger Menu
    Then Hamburger menu is present

  Scenario: Hamburger Menu Links
    Given User clicks on hamburger menu
    Then Hamburger Menu WOMEN Link is present
    And Hamburger Menu MEN Link is present
    And Hamburger Menu BOYS Link is present
    And Hamburger Menu GIRLS Link is present
    And Hamburger Menu WEDDING Link is present
    And Hamburger Menu SALE Link is present
    And Hamburger Menu BLOG Link is present