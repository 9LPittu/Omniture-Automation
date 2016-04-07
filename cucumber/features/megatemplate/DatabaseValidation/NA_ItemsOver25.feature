@NA
Feature: Newly Added - Items Over $25

Scenario: Newly Added_Validate feeds should contains items over $25 only
   	When user executes feed validation feedNewlyAddedItemOver25Query sql in DB
   And user verify feed validation and log the results for the query feedNewlyAddedItemOver25Query
	
			