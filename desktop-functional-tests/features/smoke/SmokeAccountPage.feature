@Account
Feature: Account Page

  Background:
  	Given User is on homepage with clean session
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User clicks on My Details link in My Account Page
    Then Verify user is in account details page
    And User should be in r/account/details menu link page

  Scenario: Header and Footer display validations in non responsive Account page
    Then Verify jcrew logo is visible
    
    Then Verify that top nav options contains Women
    Then Verify that top nav options contains Men
    Then Verify that top nav options contains Boys
    Then Verify that top nav options contains Girls
    Then Verify that top nav options contains Blog
    Then Verify that top nav options contains Sale
    Then Verify Returns & Exchanges link is displayed under Let Us Help You in footer
	Then Verify Shipping & Handling link is displayed under Let Us Help You in footer
	Then Verify Need Some Help? link is displayed under Let Us Help You in footer
	Then Verify Request A Style Guide link is displayed under Let Us Help You in footer
	Then Verify The J.Crew Gift Card link is displayed under Let Us Help You in footer
	Then Verify Our Story link is displayed under About J.Crew in footer
	Then Verify Careers link is displayed under About J.Crew in footer
	Then Verify Social Responsibility link is displayed under About J.Crew in footer
	Then Verify Investor Relations link is displayed under About J.Crew in footer
	Then Verify Madewell link is displayed under Our Brands in footer
	Then Verify Store Locator link is displayed under Our Stores in footer

   