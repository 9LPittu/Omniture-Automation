@Footer
Feature: Header and Footer - Let us help you section

  Background:
    Given User is on homepage with clean session
    And User closes email capture

  Scenario: Verify footer links displayed under Let Us Help You section are functional from home page

    When Verify Order Status link is displayed under Let Us Help You in footer
    When Verify Shipping & Handling link is displayed under Let Us Help You in footer
    When Verify Returns & Exchanges link is displayed under Let Us Help You in footer
    When Verify Need Some Help? link is displayed under Let Us Help You in footer
    When Verify Request A Style Guide link is displayed under Let Us Help You in footer
    When Verify The J.Crew Gift Card link is displayed under Let Us Help You in footer
    When Verify Size Charts link is displayed under Let Us Help You in footer
    When Verify Exclusive Offers & Promotions link is displayed under Let Us Help You in footer

  Scenario: developmentInprogress
    When Verify xx is displayed

 #   And User presses browser back button
 #   Then Verify user is in homepage



