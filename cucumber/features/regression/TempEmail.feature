@Email
Feature: Handling email capture pop up


  Scenario:  Email capture option should be displayed on fresh session.
    Given User is on homepage
    And Close the email pop up
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page
    And Email pop up is not displayed
    And User clicks on hamburger menu
    And User clicks on back link
    And Selects sale Category from hamburger menu
    And Email pop up is not displayed
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Email pop up is not displayed

  Scenario: Email capture is closed after subcribing with valid email id
    Given User is on homepage
    And Enter the valid email address and submit
    And Verify footer section in the page is displayed
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page
    And Email pop up is not displayed
    And User clicks on hamburger menu
    And User clicks on back link
    And Selects sale Category from hamburger menu
    And Email pop up is not displayed
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Email pop up is not displayed

  Scenario:  Email capture option should be displayed on fresh session on landing pages (jsp pages)
    And User goes to /womens-clothing.jsp?sidecar=true page
    And Close the email pop up
    And Verify footer section in the page is displayed
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page
    And Email pop up is not displayed
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And Email pop up is not displayed
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Email pop up is not displayed