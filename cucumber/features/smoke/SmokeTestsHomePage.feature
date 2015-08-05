@Home
Feature: Smoke Tests Home Page
  Background:
    Given User is on homepage

  Scenario: JCrew Logo
    Then JCrew Logo is present

  Scenario: Department Links
    Then Department Links are present

  Scenario: Stores Link
    Then Stores Link is present

  Scenario: Hamburger Menu
    Then Hamburger menu is present

  Scenario: Hamburger Menu Links
    Given User clicks on hamburger menu
    Then Hamburger Menu Links are present