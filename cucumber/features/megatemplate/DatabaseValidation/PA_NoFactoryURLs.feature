@PA
Feature: Popular in Your Area - No Factory URLs
   
Scenario:  Popular in Your Area_Validate that there are no Factory URLs' for the products being sent
	When user executes feed validation feedPopularInYourAreaNoFactoryURLQuery sql in DB
	And user verify feed validation and log the results for the query feedPopularInYourAreaNoFactoryURLQuery
	
					