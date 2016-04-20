@TR
Feature: Top Rated - No Factory URLs been sent 
   
Scenario:  Top Rated_Validate that there are no Factory URLs' for the products being sent
	When user executes feed validation feedTopRatedNoFactoryURLQuery sql in DB
	And user verify feed validation and log the results for the query feedTopRatedNoFactoryURLQuery
	
					