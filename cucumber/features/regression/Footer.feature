#@Footer
Feature: Footer Tests
  Scenario: Verify Footer Tests
    Given User is on homepage
    Then Verify LET US HELP YOU footer link is displayed
    And Verify YOUR ORDERS footer link is displayed
    And Verify THE J.CREW STYLE GUIDE footer link is displayed
    And Verify ABOUT J.CREW footer link is displayed
    And Verify OUR STORES footer link is displayed
    And Verify OUR CARDS footer link is displayed
    And Verify OUR BRANDS footer link is displayed
    And Verify LIKE BEING FIRST footer header legend is displayed
    And Verify LET US HELP YOU, YOUR ORDERS, THE J.CREW STYLE GUIDE, ABOUT J.CREW, OUR STORES, OUR CARDS, OUR BRANDS footer links order is valid, ignore GET TO KNOW US
    Then Click on footer link LET US HELP YOU
    And Verify 800 562 0258 footer sub text is displayed for LET US HELP YOU footer link
    And Click on sublink our size charts from LET US HELP YOU footer link
    And Verify user is on size charts page
    Then User presses back button
    Then  Click on footer link LET US HELP YOU
    And Click on sublink very personal stylist from LET US HELP YOU footer link
    And Verify user is on personal stylist page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link YOUR ORDERS
    And Click on sublink order status from YOUR ORDERS footer link
    And Verify user is on order status page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link YOUR ORDERS
    And Click on sublink shipping & handling from YOUR ORDERS footer link
    And Verify user is on shipping & handling page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link YOUR ORDERS
    And Click on sublink returns & exchanges from YOUR ORDERS footer link
    And Verify user is on returns & exchanges page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link THE J.CREW STYLE GUIDE
    And Click on sublink request one from THE J.CREW STYLE GUIDE footer link
    And Verify user is on request a catalog page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link THE J.CREW STYLE GUIDE
    And Click on sublink browse one from THE J.CREW STYLE GUIDE footer link
    And Verify user is on browse our style guide page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link ABOUT J.CREW
    And Click on sublink our story from ABOUT J.CREW footer link
    And Verify user is on about us page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link ABOUT J.CREW
    And Click on sublink careers from ABOUT J.CREW footer link
    And Verify user is on careers page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link ABOUT J.CREW
    And Click on sublink social responsibility from ABOUT J.CREW footer link
    And Verify user is on social responsibility page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link OUR STORES
    And  Click on sublink store locator from OUR STORES footer link
    And Verify user is on help store locator page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link OUR CARDS
    And Click on sublink the j.crew credit card from OUR CARDS footer link
    And Verify user is on the j.crew credit card page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link OUR CARDS
    And Click on sublink the j.crew gift card from OUR CARDS footer link
    And Verify user is on the j.crew gift card page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link OUR BRANDS
    And Click on sublink j.crew factory from OUR BRANDS footer link
    And Verify user is on the j.crew factory page
    Then User presses back button
    And Verify user is in homepage
    Then Click on footer link OUR BRANDS
    And Click on sublink madewell from OUR BRANDS footer link
    And Verify user is on the madewell page
