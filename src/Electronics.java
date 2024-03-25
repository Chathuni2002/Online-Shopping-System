public class Electronics extends Product {
    protected String brand;
    protected int warranty;

    public Electronics(){
        brand = "Unknown";
        warranty = 0;
    }

    public Electronics(String productId, String productName, int productCount, double productPrice, String brand, int warranty){
        super(productId, productName, productCount, productPrice);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }


    public int getWarranty() {
        return warranty;
    }


    @Override
    public String getProductType(){
        return "Electronics";
    }

    @Override
    public String toString(){
        return ", Product ID: " + productId + ", Product Name: " + productName +
                ", Product Count: " + productCount + ", Product Price: " + productPrice
                + ", Product Brand: " + brand + ", Product Warranty: " + warranty;
    }
    @Override
    public String getProductInfo(){
        return "Brand: " + brand + ", Warranty: " + warranty + " months.";
    }

}
