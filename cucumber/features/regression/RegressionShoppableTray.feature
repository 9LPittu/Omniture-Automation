@ShoppableTray
  Feature: Shoppable Tray

    Background: Navigate to a multiple pdp page in Boys Category
      Given User is on homepage
      And User clicks on hamburger menu
      And User selects random tray from available categories
        |Women|THIS MONTH'S FEATURES|looks we love |
        |Men  |THIS MONTH'S FEATURES|1 Suit, 5 Ways|
        |Girls|THIS MONTH'S FEATURES|Looks We Love |
        |Boys |THIS MONTH'S FEATURES|Looks We Love |

    #US15510_TC01, US15510_TC02, US15510_TC03, US15510_TC06
    Scenario: Verify basic initial state for shoppable tray
      Then Verifies multiple pdp page contains pagination
      Then Verifies initial multiple pdp page state
      And User clicks last product in multiple pdp page
      Then Verifies next item arrow is disabled
      Then Verifies previous item arrow is enabled
      Then Breadcrumb should display J.Crew
      Then Verify all header items are correctly displayed
      Then Verify all footer items are visible

    #US15510_TC04, US15510_TC05
    Scenario: Verify next and previous buttons functionality
      Then Verifies initial multiple pdp page state
      And User clicks next product
      Then Verifies selected product is product 1
      Then Verifies product details have changed
      And User clicks previous product
      Then Verifies selected product is product 0
      Then Verifies product details have changed

    #US9697_TC01, US9697_TC02, US9697_TC04, US9697_TC05
    #Currently failing because SC-788
    Scenario: Verify every product contains details
      Then Verify every product contains name, image, price, color and size
      Then Verify every product contains product, size and fit and review drawers

    #US9697_TC06_Part_1
    Scenario: Verify ability to add to cart
      And Add all products to cart
      Then Verify all products are in cart

    #US9881_TC13
    Scenario: Verify that externalProductCodes parameter supports lowercase
      And Copy URL and use externalProductCodes in lower case to access tray
      Then Verify that all products match with original URL
      Then Verifies multiple pdp page contains pagination
      Then Verifies initial multiple pdp page state
      And User clicks last product in multiple pdp page
      Then Verifies next item arrow is disabled
      Then Verifies previous item arrow is enabled
      Then Breadcrumb should display J.Crew
      Then Verify every product contains name, image, price, color and size
      Then Verify every product contains product, size and fit and review drawers

    #US9881_TC14
    Scenario: Verify URL support preselected colors for items
      And Selects color for every item and visits URL
      Then Verify tray has selected colors as default