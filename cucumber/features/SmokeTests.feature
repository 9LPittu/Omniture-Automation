Feature: Smoke Tests
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

  Scenario: Category Link
    Given User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And Selects Shirts and Tops from Women Category in hamburger menu
    Then User is in shirts and tops for women page