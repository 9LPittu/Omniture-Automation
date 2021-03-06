@Header5
Feature: Header verification for registered from Array page

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page
    When User clicks JCrew logo
    Then Verify user is in homepage

  Scenario: Verify Header links are functional from Array page
    When User hovers on a random category and subcategory from list
      | women | sweaters         |
      | men   | t-shirts & polos |
      | girls | dresses & skirts |
    Then Verify user is in category array page

    #When User hovers on My Account
    Then Verify My Account is displayed

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify user is in category array page
    And Verify search drawer is closed

    When User clicks on search using header
    And Verify search drawer is open

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.factory.jcrew.com/ on same page