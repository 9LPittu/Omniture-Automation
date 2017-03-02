@Footer2 @HighLevel
Feature: Footer - From Category Array page2

  Background:
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in category array page

  
  Scenario: Verify our stores and our brands links in footer are functional from category array page
    When User clicks on J.Crew Factory footer link under Our Brands
    Then Verify user is navigated to url https://factory.jcrew.com/?srcCode=JCFooter on external page

    When User clicks on Madewell footer link under Our Brands
    Then Verify user is navigated to url https://www.madewell.com/index.jsp?srcCode=JCFooter on external page


    When User clicks on Store Locator footer link under Our Stores
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page


  Scenario: Verify jcrew credit card links in footer are functional from category array page
    When User clicks on Manage Your Card footer link under The J.Crew Credit Card
    Then Verify user is navigated to url https://d.comenity.net/jcrew/ on external page

    When User clicks on Apply Today & Get 15% Off* footer link under The J.Crew Credit Card
    Then Verify user is navigated to url /help/credit_card.jsp?sidecar=true on same page
