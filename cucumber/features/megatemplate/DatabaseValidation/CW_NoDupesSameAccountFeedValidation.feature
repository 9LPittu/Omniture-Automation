@CW
Feature: Cart and Wishlist - No Duplicates SKUs for same Account

   
Scenario:  Cart and Wishlist_Validate there are no duplicate SKU (product URL and Size combination) in the feed for the same account
	When user executes feed validation feedCartWishlistDupProductQuery sql in DB
	And user verify feed validation and log the results for the query feedCartWishlistDupProductQuery