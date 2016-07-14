@WelcomeMat
Feature: Welcome Mat for ROW with checkout

#US15294_TC01,US15294_TC03,US15294_TC04,US15294_TC05
  Scenario Outline: Welcome mat display validation for international home page
    Given User is on clean session in <country_group> homepage page
    Then Welcome mat page is displayed
    And Welcome mat header message is displayed as Hello, Canada for canada, Around the World for the ROW
    And JCrew Logo is present on the welcome mat
    And Corresponding country name and flag is displayed
    And Welcome mat content is displayed

    When User clicks on START SHOPPING on welcome mat
    Then user should see country code in the url for international countries
    And user should see selected country in the footer
    And Handle the Email Capture pop up

    #Category
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory
    Then Verify proper currency symbol is displayed on product grid list
    And user should see selected country in the footer
    And user should see country code in the url for international countries

    When user selects any item from array page, select any color and size
    Then User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And user should see selected country in the footer
    And user should see country code in the url for international countries

    When Add to cart button is pressed

    #Sale
    When User clicks on hamburger menu
    And User clicks on back link
    And Selects sale Category from hamburger menu
    And User clicks on women subcategory from Sales
    Then Verify proper currency symbol is displayed on product grid list
    When user selects any item from array page, select any color and size
    Then User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And user should see selected country in the footer
    And user should see country code in the url for international countries

    When Add to cart button is pressed

    #Search
    When User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Verify proper currency symbol is displayed on product grid list
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And user should see selected country in the footer
    And user should see country code in the url for international countries

    When Add to cart button is pressed

    #Shoppable Tray
#    When User clicks on hamburger menu
#    And User selects random tray from available categories
#      | Women | THIS MONTH'S FEATURES | looks we love  |
#      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
#      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
#      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
#    When Add random product to cart

    And User clicks on item bag
    Then User should be in shopping bag page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And user should see selected country in the footer
    Then make sure that subtotal is less than creditcard threshold
    And Clicks on checkout
    And user should see selected country in the footer
    And Selects to checkout as guest   
    When user fills selected country shipping address
    And user should see selected country in the footer
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And user should see selected country in the footer
    And Uses default value for shipping method
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And user should see selected country in the footer
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And user should see selected country in the footer
    And Clicks on place your order
    And User should be in order confirmation page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And user should see selected country in the footer

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

  Scenario Outline: PDP message validation for sold out item, item with variations, vps item and shipping restriction item
    Given User is on clean session in <country_group> homepage page
    And Welcome mat content is displayed
    And User clicks on START SHOPPING on welcome mat
    And Handle the Email Capture pop up
    And user should see country code in the url for international countries
    And user should see selected country in the footer
    And User presses search button
    When user enters soldout.item in search field
    And Clicks on search button for input field
    And user selects first product from search results
    Then User is in product detail page
    Then user should see PDP page with soldout message which includes phone number
    And User presses search button
    When user enters variations.item in search field
    And Clicks on search button for input field
    And user selects first product from search results
    Then User is in product detail page
    And user should see the PDP messages for the selected country
    And user selects random variant on the PDP page
    Then User is in product detail page
    And user should see the PDP messages for the selected country
    And User presses search button
    When user enters shipping.restriction.item in search field
    And Clicks on search button for input field
    And user selects first product from search results
    Then User is in product detail page
    Then user should see PDP page with shipping restriction message

#  currently vps is set up only in bronze in canada context. Will run the below when data is available
#    And User presses search button
#    When Enters vps.item to the search field
#    And Clicks on search button for input field
#    And user selects first product from search results
#    Then User is in product detail page
#    Then user should see PDP page with message for vps item

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |