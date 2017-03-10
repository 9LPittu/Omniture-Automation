@Header3 @HighLevel
Feature: Header verification for registered user3

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page
    When User clicks Factory logo
    Then Verify user is in homepage

  Scenario: Verify Header links are functional from Gender landing pages
    When User clicks on random link from top nav
    Then Verify gender landing page is displayed

    When User hovers on My Account
    Then Verify My Account drop down is displayed

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify gender landing page is displayed
    And Verify search drawer is closed

    When User clicks on search using header
    And Verify search drawer is open

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.factory.jcrew.com/ on same page

 