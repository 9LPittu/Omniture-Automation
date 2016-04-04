@TR
Feature: Top Rated - No Duplicates in the Feed for same Account


Scenario:  Top Rated_Validate there are no duplicate product URL in the feed for the same account
	When user executes feed validation feedTopratedDupforAccountQuery1 sql in DB
	And user verify feed validation and log the results for the query feedTopratedDupforAccountQuery1
	
Scenario:  Top Rated_Validate there are no duplicate image URL in the feed for the same account
	When user executes feed validation feedTopratedDupforAccountQuery2 sql in DB
	And user verify feed validation and log the results for the query feedTopratedDupforAccountQuery2
		
Scenario:  Top Rated_Validate there are no duplicate product name in the feed for the same account
	When user executes feed validation feedTopratedDupforAccountQuery3 sql in DB
	And user verify feed validation and log the results for the query feedTopratedDupforAccountQuery3				