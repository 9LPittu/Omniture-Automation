@Home
Feature: Home Page

  Background:
    Given User is on homepage

  Scenario: Home page is functional
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

  Scenario: Verification of Footer section in the page
    Then Verify Contact Us header from footer is visible in homepage
    And Contact Us section twitter icon is displayed
    And Contact Us section phone icon is displayed
    And Contact Us section vps icon is displayed
    Then Verify Let Us Help You footer link is displayed
    And user should see "Let Us Help You" content grouping in collapsed mode
    And Verify Our Cards footer link is displayed
    And user should see "Our Cards" content grouping in collapsed mode
    And Verify Our Stores footer link is displayed
    And user should see "Our Stores" content grouping in collapsed mode
    And Verify Our Brands footer link is displayed
    And user should see "Our Brands" content grouping in collapsed mode
    And Verify About J.Crew footer link is displayed
    And user should see "About J.Crew" content grouping in collapsed mode
    And Verify LIKE BEING FIRST? footer header legend is displayed
    And Verify email field is displayed
    And Verify Get To Know Us footer link is displayed
    And Verify facebook icon is displayed under Get To Know Us section
    And Verify twitter icon is displayed under Get To Know Us section
    And Verify tumblr icon is displayed under Get To Know Us section
    And Verify pinterest icon is displayed under Get To Know Us section
    And Verify instagram icon is displayed under Get To Know Us section
    And Verify google icon is displayed under Get To Know Us section
    And Verify youtube icon is displayed under Get To Know Us section
