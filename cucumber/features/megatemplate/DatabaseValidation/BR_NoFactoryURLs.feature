@BR
Feature: Browse By - No Factory URLs been sent 
   
Scenario:  Browse By_Validate that there are no Factory URLs' for the products being sent
	When user executes feed validation feedBrowseProductNoFactoryURLQuery sql in DB
	And user verify feed validation and log the results for the query feedBrowseProductNoFactoryURLQuery
	
					