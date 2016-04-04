@CW
Feature: Cart and Wishlist - No Duplicates in the Feed for same Account

   
Scenario:  Cart and Wishlist_Validate there are no duplicate product URLs in the feed for the same account
	When user executes feed validation feedCartWishlistDupforAccountQuery1 sql in DB
	And user verify feed validation and log the results for the query feedCartWishlistDupforAccountQuery1
	
Scenario: Cart and Wishlist_ Validate there are no duplicate image URLs in the feed for the same account
	When user executes feed validation feedCartWishlistDupforAccountQuery2 sql in DB
	And user verify feed validation and log the results for the query feedCartWishlistDupforAccountQuery2