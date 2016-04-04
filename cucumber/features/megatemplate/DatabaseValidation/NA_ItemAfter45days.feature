@NA
Feature: Newly Added -  Items after 45 days should not be there in the feeds

Scenario: Newly Added_Validate newly added items after 45 days should not be there in the feeds
   	When user executes feed validation feedNewlyAddedItem45DaysQuery sql in DB
   And user verify feed validation and log the results for the query feedNewlyAddedItem45DaysQuery
	
			