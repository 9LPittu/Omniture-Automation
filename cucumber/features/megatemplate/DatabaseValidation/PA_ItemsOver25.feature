@PA
Feature: Popular in Your Area -  Items over $25 
   
Scenario: Popular in Your Area_Validate feeds should contains items over $25
   	When user executes feed validation feedPopularItemValueOver25Query sql in DB
   And user verify feed validation and log the results for the query feedPopularItemValueOver25Query
 