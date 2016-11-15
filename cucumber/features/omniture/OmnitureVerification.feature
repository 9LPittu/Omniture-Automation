@Omniture1

Feature: Verify Omniture variables

  Scenario: Verify Omniture variable values on Home, Category and PDP pages
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    Then Verify omniture variables have values

    When User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory
    Then User should be in subcategory page
    And Verify omniture variables have values

    When Selects any product from product grid list
    Then User is in product detail page
    And Verify omniture variables have values


  Scenario: Verify Omniture variable values on Sale and Search pages
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

    When User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    Then User is in sale landing page
    And Verify omniture variables have values

    When User clicks on sale department random
    Then User is in Sale results page
    And Verify omniture variables have values

    When User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Verify omniture variables have values


  Scenario: Verify Omniture variable values on Shoppable Tray
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

    When User clicks on hamburger menu
    And User selects random tray from available categories
      | Women | THIS MONTH'S FEATURES | Looks We Love  |
      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
    And Verify omniture variables have values

  Scenario: Verify Omniture variable values on Landing pages
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

    When User clicks on random link from top nav
    Then User is in corresponding gender landing page
    And Verify omniture variables have values
