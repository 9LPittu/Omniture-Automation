@ShoppingBag @HighLevel
Feature: Checkout - Add Classic Gift Card to Bag

  Scenario: Checkout - Add Classic Gift Card to Bag
    Given User goes to homepage
    And User closes email capture
	Then Verify The J.Crew Gift Card link is displayed under Let Us Help You in footer
    
    When User clicks on The J.Crew Gift Card footer link under Let Us Help You
    Then Verify J.Crew Gift Cards page is displayed  
    
    When User selects "classic card" as card type
    
    And User chooses gift amount of any value    
    And User enters Dad & Mom as Sender Name    
    And User enters any name as Recipient Name    
    And User enters any email as Recipient Email Address    
    And User enters Auto Gift Message for Line 1 as gift message in Line 1
    And User enters Auto Gift Message for Line 2 as gift message in Line 2
    And User clicks Add to Bag on gift cards page        
    
    When User clicks in bag
    Then Verify gift card details in shopping bag page    