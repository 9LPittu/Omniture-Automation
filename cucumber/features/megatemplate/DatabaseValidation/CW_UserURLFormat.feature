@CW
Feature: Cart and Wishlist -  URL format validation

Scenario: Cart and Wishlist_Validate cart URLs start with "https://www.jcrew.com/bag" and wishklist URLs start with "https://www.jcrew.com/wishlist"
   	When user executes feed validation feedCartWishlistURLFormatQuery sql in DB
   And user verify feed validation and log the results for the query feedCartWishlistURLFormatQuery
  
