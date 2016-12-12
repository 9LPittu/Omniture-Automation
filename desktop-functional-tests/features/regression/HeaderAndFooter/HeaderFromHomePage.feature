@Header @HighLevel
  Feature: Header - From home page

    Background:
      Given User is on homepage with clean session
      And User closes email capture

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


     Scenario: development
       When User clicks on search using header
       And Verify search edit box is exposed
       When User clicks on search using header
       And Verify search edit box is hidden

       When User clicks on stores using header
       Then Verify user is navigated to url https://stores.jcrew.com/ on same page