@PA
Feature: Popular in Your Area - Items does not end with .99 


Scenario: Popular in Your Area_Validate the prices of the items does not end in .99 are in the feeds
   	When user executes feed validation feedPopularInYourAreaItemValueEndwith99Query sql in DB
   And user verify feed validation and log the results for the query feedPopularInYourAreaItemValueEndwith99Query         
