@PA
Feature: Popular in Your Area - Gender prefernces for Kids 

Scenario: Popular in Your Area_Validate if kids's gender score is 1 all the items should displayed for boys/girls in the feeds
   	When user executes feed validation feedPopularinYourAreaGenderPrefQuery sql in DB
   And user verify feed validation and log the results for the query feedPopularinYourAreaGenderPrefQuery
 