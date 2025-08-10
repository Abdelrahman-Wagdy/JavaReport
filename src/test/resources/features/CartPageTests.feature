Feature: Testing full purchase cycle
  Background: User should be logged in successfully
    Given that the user is logged in successfully
    And that the user add the required items
    Then the user clicks on the cart

  Scenario: Validate that selected products are added to the cart successfully
    When the cart page opens
    Then the selected products should be displayed with its data

  Scenario: Validate that user can remove items by clicking on the remove button
    When the user clicks on the remove button
    Then the item should be removed from the cart page

  Scenario: Validate that user can remove items by swiping the item and click on the bin icon
    When the user swipes the item "left"
    And the user clicks on the bin icon
    Then the item should be removed from the cart page

  Scenario: Validate that the user will navigate to the products page if the continue shopping button is clicked
    When the user scrolls "down" to the "continue shopping" button
    And clicks on the "continue shopping" button
    Then the user navigates to the "products" page

  Scenario: Validate that the user will navigate to the products page if the continue shopping button is clicked
    When the user scrolls "down" to the "checkout" button
    And clicks on the "checkout" button
    Then the user navigates to the "checkout: information" page
