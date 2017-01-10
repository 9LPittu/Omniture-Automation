@Footer7 @HighLevel
Feature: Footer - From Sale landing page2

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sale link from top nav
    Then Verify sale landing page is displayed

    Scenario: Verify jcrew credit card links in footer are functional from sale landing page
    When User clicks on Manage Your Card footer link under The J.Crew Credit Card
    Then Verify user is navigated to url https://d.comenity.net/jcrew/ on external page

    When User clicks on Apply Today & Get 15% Off* footer link under The J.Crew Credit Card
    Then Verify user is navigated to url /help/credit_card.jsp?sidecar=true on same page

#    When User presses browser back button
#    Then Verify user is in homepage
#    When User clicks on Get $25 For Every $500 You Spend** footer link under The J.Crew Credit Card
#    Then Verify user is navigated to url /help/credit_card.jsp?sidecar=true on same page

  Scenario: Verify social links in footer are functional from sale landing page
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
    Then Verify user is navigated to url https://play.spotify.com/user/jcrew on external page

  Scenario: Verify popular search links in footer are functional from sale landing page
    When User clicks on Cardigans footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks on Blazers footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks on Men's Sweaters footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks on Business Casual For Women footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks on Men's Chinos footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify sale landing page is displayed

    When User clicks on Men's Suits footer link under Popular Searches
    Then Verify user is in category array page