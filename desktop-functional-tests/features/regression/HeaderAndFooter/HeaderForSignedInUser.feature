@Header @HighLevel
Feature: Header verification on all pages for registered user

  Background:
    Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page

  Scenario: Verify Header links are functional from home page
    When User clicks on sign in using header
    Then Verify user is navigated to url /r/login on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on search using header
    And Verify search edit box is exposed
    When User clicks on search using header
    And Verify search edit box is hidden

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page

  Scenario: Verify Header links are functional from Sale landing page
    When User clicks on sale link from top nav
    Then Verify sale landing page is displayed

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/login on same page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks on search using header
    And Verify search edit box is exposed
    When User clicks on search using header
    And Verify search edit box is hidden

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page

  Scenario: Verify Header links are functional from Gender landing pages
    When User clicks on random link from top nav
    Then Verify gender landing page is displayed

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/login on same page
    When User presses browser back button
    Then Verify gender landing page is displayed

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify gender landing page is displayed

    When User clicks on search using header
    And Verify search edit box is exposed
    When User clicks on search using header

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page

  Scenario: Verify Header links are functional from Array page
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in category array page

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/login on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on search using header
    And Verify search edit box is exposed
    When User clicks on search using header

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page



  Scenario: development

    Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on sign in using header
    Then Verify user is navigated to url /r/login on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks in bag
    Then Verify user is in shopping bag page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on search using header
    And Verify search edit box is exposed
    When User clicks on search using header

    When User clicks on stores using header
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page