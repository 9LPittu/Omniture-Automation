Feature: Smoke Tests
  Background:
    Given User is on homepage

  Scenario: JCrew Logo
    Then JCrew Logo is present

  Scenario: Department Links
    Then Department Links are present