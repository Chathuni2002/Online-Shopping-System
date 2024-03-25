import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    protected List<CartItem> cartItems;
    public List<PurchaseHistory> purchaseHistoryList;

    public ShoppingCart(){
        this.cartItems = new ArrayList<>();
        this.purchaseHistoryList = new ArrayList<>();
    }
    //Method to add products to the cart
    public void addProductsToCart(Product product, int quantity) {
        CartItem cartItem = new CartItem(product, quantity);
        cartItems.add(cartItem);
        System.out.println(quantity + " " + product.getProductName() + " (" + product.getProductId() +
                ") is added to your cart.");
    }
    //Method to remove products from the cart
    public void removeProductsFromCart(Product product){
        // Convert Product to CartItem for removal
        CartItem cartItemToRemove = new CartItem(product, 0);
        boolean removed = cartItems.remove(cartItemToRemove);
        if (removed) {
            System.out.println(product.getProductName() + " (" + product.getProductId() +
                    ") is removed from the cart.");
        } else {
            System.out.println("Product Not Found!");
        }
    }
    //method to calculate the total of the cart
    public double calculateCartTotal() {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotalPrice();
        }
        return totalPrice;
    }

    // Method to Calculate total price with discounts.
    public double calculateTotalPriceWithDiscounts() {
        double totalPrice = calculateCartTotal();

        //10% discount - for the very first purchase
        if (purchaseHistoryList.isEmpty()) {
            totalPrice *= 0.9;
        }

        // 20% discount - if the user buys at least three products of the same category
        for (CartItem cartItem : cartItems) {
            int quantity = cartItem.getQuantity();
            if (quantity >= 3) {
                totalPrice -= cartItem.getTotalPrice() * 0.2;
            }
        }

        return totalPrice;
    }


    public void addPurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseHistoryList.add(purchaseHistory);
    }

    // Inner class - represent items in the shopping cart
    public static class CartItem {
        private Product product;
        private int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalPrice() {
            return product.getProductPrice() * quantity;
        }

        @Override
        public String toString() {
            return quantity + " " + product.getProductName() + " (" + product.getProductId() + ") - Total Price: " + getTotalPrice();
        }
    }

}
