@AM
Feature: All Modules - Verify Only Deliverable emails exist
		
Scenario:  All Modules_Validate only deliverable  emails exist in all modules
	When user executes feed validation AMdeliverableemails sql in DB
	And user verify feed validation and log the results for the query AMdeliverableemails		
	
			