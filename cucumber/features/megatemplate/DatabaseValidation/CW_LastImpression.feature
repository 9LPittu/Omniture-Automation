@CW
Feature: Cart and Wishlist -  Products with last impression less than 7 days

Scenario: Cart and Wishlist_Validate Products with last impression less than 7 days should not be retrieved in the feed
   	When user executes feed validation feedCartWishlistLastImprDateQuery sql in DB
   And user verify feed validation and log the results for the query feedCartWishlistLastImprDateQuery
  
