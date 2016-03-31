@NewAccount

  Feature: Create New Account Form

    Background:
      Given User goes to homepage
      When User clicks on sign in using header
      Then User get create account form
      And Selected country is United States

    Scenario: Validate error messages in new account form
      When User clicks Create An Account button
      Then Fields will get error messages, ignore case
        |first name|Please enter first name.|
        |last name |Please enter last name. |
        |email     |Please enter a valid email address.|
        |password  |Please enter password.             |
      When email field is filled with "invalid"
      Then Fields will get error messages, ignore case
        |email     |Please enter a valid email address.|
      When email field is filled with new data
      Then Error message disappears from field email
      When password field is filled with less than 6 characters
      Then Fields will get error messages, ignore case
        |password  |Please enter password.             |
      When password field is filled with new data
      Then Error message disappears from field password
      When first name field is filled with new data
      Then Error message disappears from field first name
      When last name field is filled with new data
      Then Error message disappears from field last name

    @wip
    Scenario Outline: Create account in international context
      When first name field is filled with new data
      And last name field is filled with new data
      And email field is filled with new data
      And password field is filled with new data
      And User selects country from <country_group> group
      And User clicks Create An Account button
      Then Verify user is in homepage
      When User goes to My Details using header
      And User goes to My Details using My Account menu
      Examples:
        | country_group |
        |US             |
        #|PRICEBOOK      |
        #|NON-PRICEBOOK  |

      
      #Go to my Account
      #Go to my Details
      #Check the data matches


      #Country US - No copy
      #        Any Other - Copy
      #        HK - Copy


    #Scenario: Create an existing account
      # Check the copies
