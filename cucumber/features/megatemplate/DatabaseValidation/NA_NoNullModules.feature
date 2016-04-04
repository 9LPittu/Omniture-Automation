@NA
Feature: Newly Added - No Null values in the feed

Scenario: Newly Added_Validate that all the users are getting the Newly added Module and no Null values for any fields
   	When user executes feed validation FeedNewlyAddedNoNullFieldsQuery sql in DB
   And user verify feed validation and log the results for the query FeedNewlyAddedNoNullFieldsQuery
	
			