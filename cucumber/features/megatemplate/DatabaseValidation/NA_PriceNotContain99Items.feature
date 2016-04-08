@NA
Feature: Newly Added -  Prices of the items does not end in .99

Scenario: Newly Added_Validate the prices of the items does not end in .99 are in the feeds
   	When user executes feed validation feedNewlyAddedItemValueEndwith99Query sql in DB
   And user verify feed validation and log the results for the query feedNewlyAddedItemValueEndwith99Query
	
			