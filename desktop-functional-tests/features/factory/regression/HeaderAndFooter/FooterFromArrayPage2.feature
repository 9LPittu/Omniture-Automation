@Footer2
Feature: Footer From Category Array page

  Background:
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      | women | sweaters         |
      | men   | t-shirts & polos |
      | girls | dresses & skirts |
    Then Verify user is in category array page

  Scenario: Verify Our Brands links in footer are functional from category array page
    When User clicks on J.Crew footer link under Our Brands
    Then Verify user is navigated to url https://www.jcrew.com/?srcCode=FCFooter on external page

    When User clicks on Madewell footer link under Our Brands
    Then Verify user is navigated to url https://www.madewell.com/?srcCode=FCFooter on external page

    When User clicks on J.Crew Mercantile footer link under Our Brands
    Then Verify user is navigated to url /help/mercantile.jsp?sidecar=true on same page

  Scenario: Verify jcrew credit card links in footer are functional from category array page
    When User clicks on Manage Your Card footer link under The J.Crew Credit Card
    Then Verify user is navigated to url https://d.comenity.net/jcrew/ on external page

    When User clicks on Apply Today & Get 15% Off* footer link under The J.Crew Credit Card
    Then Verify user is navigated to url /help/credit_card.jsp?sidecar=true on same page

  Scenario: Verify social links in footer are functional from category array page
    When User clicks on facebook social link
    Then Verify user is navigated to url https://www.facebook.com/jcrewfactory on external page

    When User presses browser back button
    And User clicks on plus.google social link
    Then Verify user is navigated to url https://plus.google.com/+JCrewFactory on external page

  Scenario: Verify popular search links in footer are functional from category array page
    When User clicks on Chambray Shirts footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Casual Dresses footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Chino Shorts footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Polos footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Cardigans footer link under Popular Searches
    Then Verify user is in category array page

    When User clicks on Work Dresses footer link under Popular Searches
    Then Verify user is in category array page