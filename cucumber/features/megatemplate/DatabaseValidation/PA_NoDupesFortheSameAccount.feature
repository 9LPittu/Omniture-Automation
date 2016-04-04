@PA
Feature: Popular in Your Area - No Duplicates for the Same Account 


Scenario:  Popular in Your Area_Validate there are no duplicate product URLs in the feed for the same account
	When user executes feed validation feedPopularInYourAreaDupforAccountQuery1 sql in DB
	And user verify feed validation and log the results for the query feedPopularInYourAreaDupforAccountQuery1
	
Scenario:  Popular in Your Area_Validate there are no duplicate image URLs in the feed for the same account
	When user executes feed validation feedPopularInYourAreaDupforAccountQuery2 sql in DB
	And user verify feed validation and log the results for the query feedPopularInYourAreaDupforAccountQuery2
		
Scenario:  Popular in Your Area_Validate there are no duplicate product names in the feed for the same account
	When user executes feed validation feedPopularInYourAreaDupforAccountQuery3 sql in DB
	And user verify feed validation and log the results for the query feedPopularInYourAreaDupforAccountQuery3				

	
