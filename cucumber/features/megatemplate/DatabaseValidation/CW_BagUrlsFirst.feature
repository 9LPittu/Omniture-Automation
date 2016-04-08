@CW
Feature: Cart and Wishlist -  Bag URLs displayed before Wishlist URLs

Scenario: Cart and Wishlist_Validate Bag URLs are displayed first and then wishlist URLs for any user
   	When user executes feed validation feedCartWishlistBagFirstQuery sql in DB
   And user verify feed validation and log the results for the query feedCartWishlistBagFirstQuery
  
