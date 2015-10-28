@Home
Feature: Home Page

  Scenario: Home page is functional
    Given User is on homepage
    Then JCrew Logo is present
    And Stores Link is present
    And Hamburger menu is present
    When User clicks on hamburger menu
    Then Hamburger Menu Women Link is present
    And Hamburger Menu Men Link is present
    And Hamburger Menu Boys Link is present
    And Hamburger Menu Girls Link is present
    And Hamburger Menu Wedding Link is present
    And Hamburger Menu sale Link is present
    And Hamburger Menu Blog Link is present