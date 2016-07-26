@Home
Feature: Home Page

  Background:
    Given User is on homepage

  Scenario: Verification of Header and Footer section in the Home page
    Then JCrew Logo is present
    And Verify GlobalPromo
    And Sign In Link is present
    And Menu icon is present
    When User clicks on hamburger menu
    Then Hamburger Menu Women Link is present
    And Hamburger Menu Men Link is present
    And Hamburger Menu Boys Link is present
    And Hamburger Menu Girls Link is present
    And Hamburger Menu Wedding Link is present
    And Hamburger Menu sale Link is present
    And Hamburger Menu Blog Link is present
    And Closes hamburger menu
    Then Verify Contact Us header from footer is visible in homepage
    And Contact Us section twitter icon is displayed
    And Contact Us section phone icon is displayed
    And Contact Us section vps icon is displayed
    Then Verify Let Us Help You footer link is displayed
    And Verify Our Cards footer link is displayed
    And Verify Our Stores footer link is displayed
    And Verify Our Brands footer link is displayed
    And Verify About J.Crew footer link is displayed
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
    And user should see legal links section in the footer
    And user should see "TERMS OF USE" in the legal links section of footer
    And user should see "PRIVACY POLICY" in the legal links section of footer
    And user should see "2016 J.Crew" in the legal links section of footer
    And "2016 J.Crew" should not be displayed as a link
