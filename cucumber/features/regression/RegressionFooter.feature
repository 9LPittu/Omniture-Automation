@Footer
Feature: Footer Tests

  Background:
    Given User is on homepage

   #tc-01 and tc-02
  Scenario: Verification of Footer section in the page
    Then Contact Us header from footer is visible
    And twitter icon is displayed
    And phone icon is displayed
    And vps icon is displayed
    Then Verify Let Us Help You footer link is displayed
    And Verify Our Cards footer link is displayed
    And Verify Our Stores footer link is displayed
    And Verify Our Brands footer link is displayed
    And Verify About J.Crew footer link is displayed
    And Verify LIKE BEING FIRST? footer header legend is displayed
    And Verify email field is displayed
    And Verify Get To Know Us footer link is displayed
    And Verify facebook icon is displayed under Get To Know Us section
    And Verify twitter icon is displayed under Get To Know Us section
    And Verify tumblr icon is displayed under Get To Know Us section
    And Verify pinterest icon is displayed under Get To Know Us section
    And Verify instagram icon is displayed under Get To Know Us section
    And Verify google icon is displayed under Get To Know Us section
    And Verify youtube icon is displayed under Get To Know Us section

    # tc-03 goes here--And Verify Content Grouping Order is valid
   #tc-04 starts below
  Scenario: Verification of Let Us Help You links display
    When Click on footer link Let Us Help You
    And Accordion should be expanded
    And Order Status sublink is displayed
    And Shipping & Handling sublink is displayed
    And Returns & Exchanges sublink is displayed
    And International Orders sublink is displayed
    And Size Charts sublink is displayed
    And Contact Us sublink is displayed
    And Request A Style Guide sublink is displayed

  Scenario: Verification of Let Us Help You Order Status link functionality
    When Click on footer link Let Us Help You
    And Click on sublink Order Status from Let Us Help You footer link
    Then Verify user is on order status page
    And User is on /help/order_status.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Shipping & Handling link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Shipping & Handling from Let Us Help You footer link
    And Verify user is on shipping & handling page
    And User is on /help/shipping_handling.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Returns And Exchanges link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Returns & Exchanges from Let Us Help You footer link
    And Verify user is on returns & exchanges page
    And User is on /help/returns_exchanges.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You International Orders link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink International Orders from Let Us Help You footer link
    And User is on /help/international_orders.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Size Charts link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Size Charts from Let Us Help You footer link
    And User is on /r/size-charts page

  Scenario: Verification of Let Us Help You Contact Us link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Contact Us from Let Us Help You footer link
    And User is on /footie/contactus.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Request A Style Guide link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    And Verify user is on request a catalog page

  #tc-05
  Scenario: Verification of Our Cards links display
    When Click on footer link Our Cards
    And Accordion should be expanded
    And The J.Crew Credit Card sublink is displayed
    And The J.Crew Gift Card sublink is displayed

  Scenario: Verification of Our Cards The J.Crew Credit Card link functionality
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Credit Card from Our Cards footer link
    And Verify user is on the j.crew credit card page
    And User is on /help/credit_card.jsp?sidecar=true page

  Scenario: Verification of Our Cards The J.Crew Gift Card link functionality
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Gift Card from Our Cards footer link
    And Verify user is on the j.crew gift card page
    And User is on /help/gift_card.jsp?sidecar=true page

  #tc-06
  Scenario: Verification of Our Stores links display and functional
    When Click on footer link Our Stores
    #And Accordion should be expanded
    And Store Locator sublink is displayed
    And Click on sublink Store Locator from Our Stores footer link
    And Verify user is on help store locator page
    And User is on /help/store_locator.jsp?sidecar=true page

    #tc-07
  Scenario: Verification of About J.Crew links display
    When Click on footer link About J.Crew
  #And Accordion should be expanded
    And Our Story sublink is displayed
    And Careers sublink is displayed
    And Social Responsibility sublink is displayed
    And Investor Relations sublink is displayed

  Scenario: Verification of Our Story About J.Crew link functionality
    Then Click on footer link About J.Crew
    And Click on sublink Our Story from About J.Crew footer link
    And Verify user is on about us page
    And User is on /aboutus/jcrew.jsp?sidecar=true page

  Scenario: Verification of About J.Crew Careers link functionality
    And Verify user is in homepage
    Then Click on footer link About J.Crew
    And Click on sublink Careers from About J.Crew footer link
    And Verify user is on careers page
    And User is on external page https://jobs.jcrew.com/?sidecar=true

  Scenario: Verification of About J.Crew Social Responsibility link functionality
    Then Click on footer link About J.Crew
    And Click on sublink Social Responsibility from About J.Crew footer link
    And Verify user is on social responsibility page
    And User is on /flatpages/social_responsibility.jsp?sidecar=true page

  Scenario: Verification of  About J.Crew Investor Relations link functionality
    Then Click on footer link About J.Crew
    And Click on sublink Investor Relations from About J.Crew footer link
    And User is on external page http://investors.jcrew.com

    #tc-08
  Scenario: Verification of Our Brands links display
    When Click on footer link Our Brands
    #And Accordion should be expanded
    And J.Crew Factory sublink is displayed
    And Madewell sublink is displayed

  Scenario: Verifying Our Brands J.Crew Factory link funtionality
    Then Click on footer link Our Brands
    And Click on sublink J.Crew Factory from Our Brands footer link
    And Verify user is on the j.crew factory page
    And User is on external page https://factory.jcrew.com/index.jsp?srcCode=JCFooter

  Scenario: Verifying Our Brands Madewell link funtionality
    Then Click on footer link Our Brands
    And Click on sublink Madewell from Our Brands footer link
    And Verify user is on the madewell page
    And User is on external page https://www.madewell.com/index.jsp?srcCode=JCFooter






































  
