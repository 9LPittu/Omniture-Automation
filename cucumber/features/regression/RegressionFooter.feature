@Footer
Feature: Footer Tests

  Background:
    Given User is on homepage

  Scenario: Verify Footer Tests
    Then Verify Let Us Help You footer link is displayed
    And Verify Our Cards footer link is displayed
    And Verify Our Stores footer link is displayed
    And Verify Our Brands footer link is displayed
    And Verify About J.Crew footer link is displayed
    And Verify LIKE BEING FIRST? footer header legend is displayed

  Scenario: Size Charts
    When Click on footer link Let Us Help You
    And Click on sublink Size Charts from Let Us Help You footer link
    Then Verify user is on size charts page

  Scenario: Very Personal Stylist
    When Click on footer link Let Us Help You
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    And Verify user is on personal stylist page

  Scenario: Order Status
    When Click on footer link Let Us Help You
    And Click on sublink Order Status from Let Us Help You footer link
    Then Verify user is on order status page

  Scenario: Shipping & Handling
    Then Click on footer link Let Us Help You
    And Click on sublink Shipping & Handling from Let Us Help You footer link
    And Verify user is on shipping & handling page

   Scenario: Returns And Exchanges
    Then Click on footer link Let Us Help You
    And Click on sublink Returns & Exchanges from Let Us Help You footer link
    And Verify user is on returns & exchanges page

  Scenario: Request A Style Guide
    Then Click on footer link Let Us Help You
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    And Verify user is on request a catalog page

   Scenario: Our Story
    Then Click on footer link About J.Crew
    And Click on sublink Our Story from About J.Crew footer link
    And Verify user is on about us page

  Scenario: Careers
    And Verify user is in homepage
    Then Click on footer link About J.Crew
    And Click on sublink Careers from About J.Crew footer link
    And Verify user is on careers page

  Scenario: Social Responsibility
    Then Click on footer link About J.Crew
    And Click on sublink Social Responsibility from About J.Crew footer link
    And Verify user is on social responsibility page

  Scenario: Store Locator
    Then Click on footer link Our Stores
    And  Click on sublink Store Locator from Our Stores footer link
    And Verify user is on help store locator page

   Scenario: Credit Card
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Credit Card from Our Cards footer link
    And Verify user is on the j.crew credit card page

  Scenario: Gift Card
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Gift Card from Our Cards footer link
    And Verify user is on the j.crew gift card page

  Scenario: J.Crew Factory
    Then Click on footer link Our Brands
    And Click on sublink J.Crew Factory from Our Brands footer link
    And Verify user is on the j.crew factory page

  Scenario: Madewell
    Then Click on footer link Our Brands
    And Click on sublink Madewell from Our Brands footer link
    And Verify user is on the madewell page
