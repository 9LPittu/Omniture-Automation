@NA
Feature: Newly Added -  No Factory URLs

Scenario: Newly Added_Validate that there are no factory URLs in the feed
   	When user executes feed validation feedNewlyAddedNoFactoryURLQuery sql in DB
   And user verify feed validation and log the results for the query feedNewlyAddedNoFactoryURLQuery
	
			