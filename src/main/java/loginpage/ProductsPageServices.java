package loginpage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPageServices extends PageBase {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PRODUCTS\")")
    WebElement productTitleElement;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"test-ADD TO CART\").instance(0)")
    WebElement backPackAddToCartButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"test-ADD TO CART\").instance(1)")
    WebElement bikeLightAddToCartButton;

    @AndroidFindBy(accessibility = "test-Cart")
    WebElement cartIcon;

    public void addProducts(){
        click(this.backPackAddToCartButton);
        a.perform("Add items to the cart", ()-> click(this.bikeLightAddToCartButton));
    }

    public void goToCart(){
        a.perform("Go to cart page", ()-> click(this.cartIcon));
    }

    public String getPageTitle(){
        return productTitleElement.getText();
    }
}
