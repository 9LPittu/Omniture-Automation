@SignIn

  Feature: Sign In

    Scenario: User is able to sign in
      Given User goes to homepage
       When User clicks on sign in using header
        And User fills user data
        And User hovers in My Account dropdown
       Then Dropdown should welcome user by first name
       When User goes to homepage
       Then Dropdown contains these elements
            |Welcome message|
            |My Details     |
            |Wishlist       |
            |Order History  |
            |Sign Out       |
       When User go to My Details using header
        And Visits My Details using My Account
       Then User information should match My Details page
       When User go to Wishlist using header
       Then User is in wishlist page
       When User go to Order History using Header
       Then User ir in Order History page
       When User signs out using header
       Then User will be in hompage
        And Header will contain Sign In
