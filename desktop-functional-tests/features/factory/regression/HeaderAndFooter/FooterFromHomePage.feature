@Footer5
Feature: Footer From home page - Let Us Help You and About Factory

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Verify Let Us Help You links in footer are functional from home page
    When User clicks on Order Status footer link under Let Us Help You
    Then Verify user is navigated to url help/order_status.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Shipping & Handling footer link under Let Us Help You
    Then Verify user is navigated to url /help/shipping_handling.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Returns & Exchanges footer link under Let Us Help You
    Then Verify user is navigated to url /help/returns_exchanges.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on International Orders footer link under Let Us Help You
    Then Verify user is navigated to url /help/international_orders.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Need Some Help footer link under Let Us Help You
    Then Verify user is navigated to url help/contact_us.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on The J.Crew Gift Card footer link under Let Us Help You
    Then Verify user is navigated to url /help/gift_card.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

  Scenario: Verify About Factory links in footer are functional from home page
    When User clicks on About Us footer link under About Factory
    Then Verify user is navigated to url /help/about_jcrewfactory.jsp?sidecar=true on same page
    When User presses browser back button
    Then Verify user is in homepage

    When User clicks on Careers footer link under About Factory
    Then Verify user is navigated to url https://jobs.jcrew.com/ on same page
    When User presses browser back button
    Then Verify user is in homepage