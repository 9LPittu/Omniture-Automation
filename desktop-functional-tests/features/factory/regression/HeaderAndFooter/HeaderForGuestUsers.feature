@Header
Feature: Header verification for guest user

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Verify Header links are functional from home page
    When User clicks on sign in using header
    Then Verify user is navigated to url /r/account/details on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify user is in homepage
    And Verify search drawer is closed

    When User clicks on search using header
    And Verify search drawer is open

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.factory.jcrew.com/ on same page

  Scenario: Verify Header links are functional from Sale landing page
    When User clicks on crew clearance link from top nav
    Then Verify sale landing page is displayed

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/account/details on same page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify sale landing page is displayed
    And Verify search drawer is closed

    When User clicks on search using header
    And Verify search drawer is open

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.factory.jcrew.com/ on same page

  Scenario: Verify Header links are functional from Gender landing pages
    When User clicks on random link from top nav
    Then Verify gender landing page is displayed

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/account/details on same page
    When User presses browser back button
    Then Verify gender landing page is displayed

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify gender landing page is displayed
    And Verify search drawer is closed

    When User clicks on search using header
    And Verify search drawer is open

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.factory.jcrew.com/ on same page

  Scenario: Verify Header links are functional from Array page
    When User hovers on a random category and subcategory from list
      | women | sweaters         |
      | men   | t-shirts & polos |
      | girls | dresses & skirts |
    Then Verify user is in category array page

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/account/details on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify user is in category array page
    And Verify search drawer is closed

    When User clicks on search using header
    And Verify search drawer is open

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.factory.jcrew.com/ on same page
