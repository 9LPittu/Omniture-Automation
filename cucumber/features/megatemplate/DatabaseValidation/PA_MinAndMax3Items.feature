@PA
Feature: Popular in Your Area - Minimum and Maximum 3 items per account

Scenario: Popular in Your Area_Validate feed Have maximum and minimum 3 item for account in the Feed
   	When user executes feed validation feedPopularinYourAreaQuery sql in DB
   And user verify feed validation and log the results for the query feedPopularinYourAreaQuery
	
			