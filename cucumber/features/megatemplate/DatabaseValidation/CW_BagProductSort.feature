@CW
Feature: Cart and Wishlist -  Sort order of Bag URLs based on Low Inventory, Back in Stock, New in Sale, Date added and Price

Scenario: Cart and Wishlist_Validate Sort order of Bag URLs based on Low Inventory, Back in Stock, New in Sale, Date added and Price
   	When user executes feed validation feedCartWishlistBagSortingQuery sql in DB
   And user verify feed validation and log the results for the query feedCartWishlistBagSortingQuery
  
