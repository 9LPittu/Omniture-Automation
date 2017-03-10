@Footer @HighLevel
Feature: Footer - From Category Array page

  Background:
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in category array page

  Scenario: Verify Let Us Help You links in footer are functional from category array page
    When User clicks on Order Status footer link under Let Us Help You
    Then Verify user is navigated to url help/order_status.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Shipping & Handling footer link under Let Us Help You
    Then Verify user is navigated to url /help/shipping_handling.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Returns & Exchanges footer link under Let Us Help You
    Then Verify user is navigated to url /help/returns_exchanges.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Need Some Help? footer link under Let Us Help You
    Then Verify user is navigated to url /footie/contactus.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Request A Style Guide footer link under Let Us Help You
    Then Verify user is navigated to url /help/request_catalog.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on The J.Crew Gift Card footer link under Let Us Help You
    Then Verify user is navigated to url /help/gift_card.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Exclusive Offers & Promotions footer link under Let Us Help You
    Then Verify user is navigated to url /promo.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Size Charts footer link under Let Us Help You
    Then Verify user is navigated to url /r/size-charts-module on external page


  Scenario: Verify About J.Crew links in footer are functional from category array page
    When User clicks on Our Story footer link under About J.Crew
    Then Verify user is navigated to url aboutus/jcrew.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Careers footer link under About J.Crew
    Then Verify user is navigated to url https://jobs.jcrew.com/ on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Social Responsibility footer link under About J.Crew
    Then Verify user is navigated to url flatpages/social_responsibility.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in category array page

    When User clicks on Investor Relations footer link under About J.Crew
    Then Verify user is navigated to url investors.jcrew.com/phoenix.zhtml?c=135311&p=irol-irhome&ver=jc on external page