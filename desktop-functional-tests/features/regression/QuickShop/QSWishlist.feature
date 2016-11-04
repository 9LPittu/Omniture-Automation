@Quickshop


Scenario: Add to Wishlist from QS is functional for guest user
  When User hovers on women category from header
  And User selects sweaters subcategory array
  Then Verify user is in sweaters category array page
  When User selects random quick shop from product array
  Then Verify quick shop modal is displayed
  When Selects random color
  And Selects random size
  When User clicks on Wishlist button
  Then User goes to sign in page
  When User fills form and signs in
  Then Verify user is in sweaters category array page

Scenario: Add to Wishlist from QS is functional for registered user
  When User clicks on sign in using header
  Then User goes to sign in page
  And User fills user data and signs in
  Then Verify user is in My Account main page

  When User hovers on women category from header
  And User selects sweaters subcategory array
  Then Verify user is in sweaters category array page

  When User selects random quick shop from product array
  Then Verify quick shop modal is displayed

  When Selects random color
  And Selects random size

  When User clicks on Wishlist button
  Then User clicks on X icon

  When User goes to Wishlist using header
  Then Verify user is in wishlist page