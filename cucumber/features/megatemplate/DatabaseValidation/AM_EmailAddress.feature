@AM
Feature: All Modules - Email Adderss should not be NULL

Scenario: All Modules - Validate Email Adderss is not NULL across all modules
   	When user executes feed validation feedNewlyAddedEmailAdderss sql in DB
   And user verify feed validation and log the results for the query feedNewlyAddedEmailAdderss
	
			