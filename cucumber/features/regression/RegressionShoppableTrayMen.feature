@ShoppableTray
  Feature: Shoppable Tray in Men Category

    Background: Navigate to a multiple pdp page in Men Category
      Given User is on homepage
      And User clicks on hamburger menu
      And Selects Men Category from hamburger menu
      And User clicks on THIS MONTH'S FEATURES subcategory from Men Category
      And User clicks on 1 Suit, 5 Ways from featured this month
      And User selects random shop the look page for Men

    #US15510_TC01, US15510_TC02, US15510_TC03, US15510_TC06
    Scenario: Verify basic initial state for shoppable tray
      Then Verifies multiple pdp page contains pagination
      Then Verifies initial multiple pdp page state
      And User clicks last product in multiple pdp page
      Then Verifies next item arrow is disabled
      Then Verifies previous item arrow is enabled

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
    Scenario: Verify every product contains details
      Then Verify every product contains name, image, price, color and size
      Then Verify every product contains product, size and fit and review drawers

    #US9697_TC06_Part_1
    Scenario: Verify ability to add to cart
      And Add all products to cart
      Then Verify all products are in cart