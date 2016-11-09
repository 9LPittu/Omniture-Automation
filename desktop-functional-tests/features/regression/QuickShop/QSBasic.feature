@Quickshop

Feature: Quick Shop Basic

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: View full detail link in QS is functional
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|sweaters|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Selects random size
    And Verify view full details link is displayed in QS modal

    When User clicks on view full details link
    Then Verify product detail page is displayed
    And Verify product name on PDP matches with QS

  Scenario: View Close(X) link in QS is functional
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Verify close is displayed in QS modal

    When User clicks on X icon
    Then Verify quick shop modal is not displayed

  Scenario: Verify user able to change variations in quickshop
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop with variation product from product array
    Then Verify quick shop modal is displayed
    And Verify variations is displayed in QS modal
    And Verify user able to change variations

  Scenario: Verify Error message on Quick shop if size and color not selected and adds to bag
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    When User clicks on Add to Bag button
    Then Verify PLEASE SELECT A SIZE message displayed
    And Verify Add to Bag button is in disabled state
    And Verify Wishlist button is in disabled state

  Scenario: Verify Error message on Quick shop if size and color not selected and adds to wishlist
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    When User clicks on Wishlist button
    Then Verify PLEASE SELECT A SIZE message displayed
    And Verify Add to Bag button is in disabled state
    And Verify Wishlist button is in disabled state

  Scenario: Verify guest user able to add item to bag from quick shop
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    And Selects random color
    And Selects random size
    And Selects random quantity from Quickshop
    And User clicks on Add to Bag button

    When User clicks on X icon
    And User clicks in bag

    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag

  Scenario: Verify registered user able to add item to bag from quick shop

    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page
    And This script cleans bag for current user

    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in selected category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    And Selects random color
    And Selects random size
    And Selects random quantity from Quickshop
    And User clicks on Add to Bag button

    When User clicks on X icon
    And User clicks in bag

    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag
    # Registered user able to add item to wishlist from quick shop
  Scenario: Development in progress scenario

    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page
    When User clicks on My Wishlist link in My Account Page
    Then User should be in /wishlist menu link page
    And Deletes all previous wishlist items from the list
    And User goes to homepage

    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    When Selects random color
    And Selects random size

    When User clicks on Wishlist button

    When User clicks on X icon
    When User goes to homepage

    When User goes to Wishlist using header
    Then Verify user is in wishlist page
    And Verify product name on wishlist matches with QS



