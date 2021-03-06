@Footer8 @HighLevel
Feature: Footer - From my Account page

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page

  Scenario: Verify Let Us Help You links in footer are functional from my Account page
    When User clicks on Order Status footer link under Let Us Help You
    Then Verify user is navigated to url help/order_status.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Shipping & Handling footer link under Let Us Help You
    Then Verify user is navigated to url /help/shipping_handling.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Returns & Exchanges footer link under Let Us Help You
    Then Verify user is navigated to url /help/returns_exchanges.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Need Some Help? footer link under Let Us Help You
    Then Verify user is navigated to url /footie/contactus.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Request A Style Guide footer link under Let Us Help You
    Then Verify user is navigated to url /help/request_catalog.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on The J.Crew Gift Card footer link under Let Us Help You
    Then Verify user is navigated to url /help/gift_card.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Exclusive Offers & Promotions footer link under Let Us Help You
    Then Verify user is navigated to url /promo.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Size Charts footer link under Let Us Help You
    Then Verify user is navigated to url /r/size-charts-module on external page


  Scenario: Verify About J.Crew links in footer are functional from my Account page
    When User clicks on Our Story footer link under About J.Crew
    Then Verify user is navigated to url aboutus/jcrew.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Careers footer link under About J.Crew
    Then External https://jobs.jcrew.com/ page is opened in a different tab
    Then Verify user is in My Account main page

    When User clicks on Social Responsibility footer link under About J.Crew
    Then Verify user is navigated to url flatpages/social_responsibility.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Investor Relations footer link under About J.Crew
    Then Verify user is navigated to url investors.jcrew.com/phoenix.zhtml?c=135311&p=irol-irhome&ver=jc on external page


  Scenario: Verify our stores and our brands links in footer are functional from my Account page
    When User clicks on J.Crew Factory footer link under Our Brands
    Then Verify user is navigated to url https://factory.jcrew.com/?srcCode=JCFooter on external page

    When User clicks on Madewell footer link under Our Brands
    Then Verify user is navigated to url https://www.madewell.com/index.jsp?srcCode=JCFooter on external page


    When User clicks on Store Locator footer link under Our Stores
    Then Verify user is navigated to url https://stores.jcrew.com/ on same page


  Scenario: Verify jcrew credit card links in footer are functional from my Account page
    When User clicks on Manage Your Card footer link under The J.Crew Credit Card
    Then Verify user is navigated to url https://d.comenity.net/jcrew/ on external page

    When User clicks on Apply Today & Get 15% Off* footer link under The J.Crew Credit Card
    Then Verify user is navigated to url /help/credit_card.jsp?sidecar=true on same page

#    When User presses browser back button
#    Then Verify user is in homepage
#    When User clicks on Get $25 For Every $500 You Spend** footer link under The J.Crew Credit Card
#    Then Verify user is navigated to url /help/credit_card.jsp?sidecar=true on same page

  Scenario: Verify social links in footer are functional from my Account page
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

  Scenario: Verify popular search links in footer are functional from my Account page
    When User clicks on Cardigans footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Blazers footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Men's Sweaters footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Business Casual For Women footer link under Popular Searches
    Then Verify user is in category array page
    When User presses browser back button
    Then Verify user is in My Account main page

    When User clicks on Men's Suits footer link under Popular Searches
    Then Verify user is in category array page