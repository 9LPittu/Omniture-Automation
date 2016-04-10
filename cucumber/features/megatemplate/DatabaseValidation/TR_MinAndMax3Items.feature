@TR
Feature: Top Rated - Minimum and Maximum 3 items per account

Scenario: Top Rated_Validate feed Have maximum and minimum 3 item for account in the Feed
   	When user executes feed validation feedTopratedminimumQuery sql in DB
   And user verify feed validation and log the results for the query feedTopratedminimumQuery
  
