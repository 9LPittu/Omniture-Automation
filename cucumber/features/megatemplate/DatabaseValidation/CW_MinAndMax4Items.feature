@CW
Feature: Cart and Wishlist - Minimum and Maximum 4 items Cart and Wishlist put together

Scenario: Cart and Wishlist_Validate the feed, the minimum item count should be 0 and Maximum 4, both cart and the Wishlist put together
   	When user executes feed validation feedCartWishlistmaxmumQuery sql in DB
   And user verify feed validation and log the results for the query feedCartWishlistmaxmumQuery
  
