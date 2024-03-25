public abstract class Product {
    protected String productId;
    protected String productName;
    protected int productCount;
    protected double productPrice;

    public Product(){
        productId = "w0000";
        productName = "Unavailable";
        productCount = 0;
        productPrice = 0.0;
    }

    public Product(String productId, String productName, int productCount, double productPrice){
        this.productId = productId;
        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public double getProductPrice() {
        return productPrice;
    }

    public int getProductCount() {
        return productCount;
    }
    //Get the product type as a String
    public abstract String getProductType();
    //Get full details of a product
    public abstract String toString();
    //Get details only unique to the product type
    public abstract String getProductInfo();

}
