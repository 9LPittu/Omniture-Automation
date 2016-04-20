@BR
Feature: Browse By - No Duplicates in the Feed for same Account

Scenario:  Browse By_Validate there are no duplicate product URLs in the feed for the same account
	When user executes feed validation browseproductDupforAccountQuery1 sql in DB
	And user verify feed validation and log the results for the query browseproductDupforAccountQuery1
	
Scenario:  Browse By_Validate there are no duplicate image URLs in the feed for the same account
	When user executes feed validation browseproductDupforAccountQuery2 sql in DB
	And user verify feed validation and log the results for the query browseproductDupforAccountQuery2
		
Scenario:  Browse By_Validate there are no duplicate product names in the feed for the same account
	When user executes feed validation browseproductDupforAccountQuery3 sql in DB
	And user verify feed validation and log the results for the query browseproductDupforAccountQuery3		
				