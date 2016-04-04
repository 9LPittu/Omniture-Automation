@NA
Feature: Newly Added - Item must have been added to site in last 30 days

Scenario: Newly Added_Validate the item must have been added to site in last 30 days
   	When user executes feed validation feedNewlyAddedItem30DaysQuery sql in DB
   And user verify feed validation and log the results for the query feedNewlyAddedItem30DaysQuery
	
			