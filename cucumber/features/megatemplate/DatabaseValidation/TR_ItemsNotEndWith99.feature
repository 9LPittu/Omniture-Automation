@TR
Feature: Top Rated - Items does not end with .99 


Scenario: Top Rated_Validate the prices of the items does not end in .99 are in the feeds
   	When user executes feed validation feedTopRatedItemValueEndwith99Query sql in DB
   And user verify feed validation and log the results for the query feedTopRatedItemValueEndwith99Query
   