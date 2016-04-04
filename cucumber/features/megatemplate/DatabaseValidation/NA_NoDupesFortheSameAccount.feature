@NA
Feature: Newly Added -  No Duplicates for the Same Account 
	
Scenario:  Newly Added_Validate there are no duplicate product URLs in the feed for the same account
	When user executes feed validation feedNewlyAddedDupforAccountQuery1 sql in DB
	And user verify feed validation and log the results for the query feedNewlyAddedDupforAccountQuery1
	
Scenario:  Newly Added_Validate there are no duplicate image URLs in the feed for the same account
	When user executes feed validation feedNewlyAddedDupforAccountQuery2 sql in DB
	And user verify feed validation and log the results for the query feedNewlyAddedDupforAccountQuery2
		
Scenario:  Newly Added_Validate there are no duplicate product names in the feed for the same account
	When user executes feed validation feedNewlyAddedDupforAccountQuery3 sql in DB
	And user verify feed validation and log the results for the query feedNewlyAddedDupforAccountQuery3				