@ShoppingBag @HighLevel
Feature: Checkout - Add Classic Gift Card to Bag

  Scenario: Checkout - Add Classic Gift Card to Bag
    Given User goes to homepage
    And User closes email capture
	Then Verify The J.Crew Gift Card link is displayed under Let Us Help You in footer
    
    When User clicks on The J.Crew Gift Card footer link under Let Us Help You
    Then Verify J.Crew Gift Cards page is displayed
    