@Footer
Feature: Header and Footer - Let us help you section

  Background:
    Given User is on homepage with clean session
    And User closes email capture

  Scenario: Verify footer links displayed under Let Us Help You section are functional from home page

    When User clicks on Order Status link under Let Us Help You in footer
    Then Verify user is navigated to url help/order_status.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Shipping & Handling link under Let Us Help You in footer
    Then Verify user is navigated to url /help/shipping_handling.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Returns & Exchanges link under Let Us Help You in footer
    Then Verify user is navigated to url /help/returns_exchanges.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Need Some Help? link under Let Us Help You in footer
    Then Verify user is navigated to url /footie/contactus.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Request A Style Guide link under Let Us Help You in footer
    Then Verify user is navigated to url /help/request_catalog.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on The J.Crew Gift Card link under Let Us Help You in footer
    Then Verify user is navigated to url /help/gift_card.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Exclusive Offers & Promotions link under Let Us Help You in footer
    Then Verify user is navigated to url /promo.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

#    When Verify Size Charts link is displayed under Let Us Help You in footer


  Scenario: developmentInprogress

    When User clicks on Our Story link under About J.Crew in footer
    Then Verify user is navigated to url aboutus/jcrew.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Careers link under About J.Crew in footer
    Then Verify user is navigated to url https://jobs.jcrew.com/ on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Social Responsibility link under About J.Crew in footer
    Then Verify user is navigated to url flatpages/social_responsibility.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage
   #Investor Relations