@Footer3 @HighLevel
Feature: Footer - From Category Array page3

  Background:
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in category array page
  
  Scenario: Verify social links in footer are functional from category array page
    When User clicks on facebook social link
    Then Verify user is navigated to url https://www.facebook.com/jcrew on external page

    When User clicks on twitter social link
    Then Verify user is navigated to url https://twitter.com/jcrew on external page

    When User clicks on tumblr social link
    Then Verify user is navigated to url http://jcrew.tumblr.com/ on external page

    When User clicks on pinterest social link
    Then Verify user is navigated to url https://www.pinterest.com/jcrew/ on external page

    When User clicks on instagram social link
    Then Verify user is navigated to url https://www.instagram.com/jcrew/ on external page

    When User clicks on plus.google social link
    Then Verify user is navigated to url https://plus.google.com/+JCrew on external page

    When User clicks on youtube social link
    Then Verify user is navigated to url https://www.youtube.com/user/jcrewinsider on external page

    When User clicks on spotify social link
    Then Verify user is navigated to url https://open.spotify.com/user/jcrew on external page

  Scenario: Verify popular search links in footer are functional from category array page
    When User clicks on Cardigans footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Blazers footer link under Popular Searches
    Then Verify user is in category array page
    
    When User clicks on Men's Sweaters footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Business Casual For Women footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Men's Suits footer link under Popular Searches
    Then Verify user is in category array page