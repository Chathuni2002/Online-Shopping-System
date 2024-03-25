public class Clothing extends Product {
    protected double size;
    protected String colour;

    public Clothing(){
        size = 0.0;
        colour = "Unknown";
    }

    public Clothing(String productId, String productName, int productCount, double productPrice, double size, String colour){
        super(productId, productName, productCount, productPrice);
        this.size = size;
        this.colour = colour;
    }

    public double getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String getProductType(){
        return "Clothing";
    }
    @Override
    public String toString(){
        return ", Product ID: " + productId + ", Product Name: " + productName +
                ", Product Count: " + productCount + ", Product Price: " + productPrice
                + ", Product Size: " + size + ", Product Colour: " + colour;
    }
    @Override
    public String getProductInfo(){
        return "Size: " + size + ", Colour: " + colour;
    }
}
