@TR
Feature: Top Rated - Items Over $25

   
Scenario: Top Rated_Validate feeds should contains items over $25
   	When user executes feed validation feedTopRatedValueOver25Query sql in DB
   And user verify feed validation and log the results for the query feedTopRatedValueOver25Query