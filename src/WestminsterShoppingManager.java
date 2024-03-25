import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    public static final int max_Products = 50;
    public List<Product> productList;


    public WestminsterShoppingManager(){
        productList = new ArrayList<>();
    }

    Scanner input = new Scanner(System.in);

    public boolean runMenu(){

        boolean exit = false;

        System.out.println("---Westminster Shopping Manager Menu---");
        System.out.println("Enter '1' to add a new product.");
        System.out.println("Enter '2' to delete a product.");
        System.out.println("Enter '3' to print the product list");
        System.out.println("Enter '4' to save products to a file.");
        System.out.println("Enter '5' to check file information.");
        System.out.println("Enter '6' to open the User Interface.");
        System.out.println("Enter '7' to exit.");
        System.out.println("Enter your choice (1-7): ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice){
            case 1:
                addProductToSystem();
                break;
            case 2:
                deleteProductFromSystem();
                break;
            case 3:
                printProductList();
                break;
            case 4:
                saveToFile();
                break;
            case 5:
                checkFile();
                break;
            case 6:
                initializeGUI();
                break;
            case 7:
                System.out.println("Exiting...");
                exit = true;
                break;
            default:
                System.out.println("Invalid input. Please enter a number between 1 to 5.");
        }
        return exit;

    }
    //Method to add products to the system.
    public void addProductToSystem(){
        if(productList.size()>=max_Products){
            System.out.println("Can not add the product to the system. " +
                    "The maximum amount has been reached.");
        }
        System.out.println("Select the type of the product.");
        System.out.println("Enter '1' to add an electronics product.");
        System.out.println("Enter '2' to add a clothing product.");
        System.out.println("Enter your choice (1-2): ");

        int productChoice = input.nextInt();
        input.nextLine();

        System.out.println("Enter the product ID: ");
        String p_ID = input.next();
        System.out.println("Enter the product Name: ");
        String p_Name = input.next();
        System.out.println("Enter the product Count: ");
        int p_Count = input.nextInt();
        System.out.println("Enter the product Price: ");
        double p_Price = input.nextDouble();
        input.nextLine();

        if(productChoice == 1){
            System.out.println("Enter the product brand: ");
            String p_Brand = input.next();
            System.out.println("Enter the product warranty (months): ");
            int p_Warranty = input.nextInt();

            productList.add(new Electronics(p_ID, p_Name, p_Count, p_Price, p_Brand, p_Warranty));
            System.out.println("Product added.");

        } else if (productChoice == 2) {
            System.out.println("Enter the product size: ");
            double p_Size = input.nextDouble();
            System.out.println("Enter the product colour: ");
            String p_Color = input.next();

            productList.add(new Clothing(p_ID, p_Name, p_Count, p_Price, p_Size, p_Color));
            System.out.println("Product added.");

        }else {
            System.out.println("Invalid input. Please enter a number 1 or 2.");
        }

    }
    //Method to delete a products from the system.
    public void deleteProductFromSystem(){
        System.out.println("To delete a product, ");
        System.out.println("Enter product ID: ");
        String p_ID = input.nextLine();

        ListIterator<Product> iterator = productList.listIterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            if(product.getProductId().equals(p_ID)){
                iterator.remove();
                System.out.println("Product deleted successfully.");
                System.out.println("Deleted product details: " + product);
                System.out.println("Remaining number of products in the system: "
                        + productList.size());
                return;
            }
        }
        System.out.println("Product ID " + p_ID + " Not Found!");
    }
    //Method to print the list of the products.
    public void printProductList(){
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                // Compare product IDs as strings
                return p1.getProductId().compareTo(p2.getProductId());
            }
        });
        System.out.println("---Product List---");
        for (Product product : productList) {
            if (product.getProductType().equals("Electronics")) {
                System.out.println("Product Type: Electronics" +
                        product.toString());
            }
            if (product.getProductType().equals("Clothing")) {
                System.out.println("Product Type: Clothing" +
                        product.toString());
            }
        }
    }
    //Method to save the list of the products to a file.
    @Override
    public void saveToFile() {
        try {
            File file = new File("DataList.txt");

            // Check if the file already exists
            if (file.exists()) {
                List<String> previousData = readExistingData(file);

                // Add only the new products that are not already in the file
                for (Product product : productList) {
                    String productInfo = getProductInfoString(product);
                    if (!previousData.contains(productInfo)) {
                        previousData.add(productInfo);
                    }
                }

                // Write all data back to the file
                try (FileWriter dataWriter = new FileWriter(file)) {
                    for (String line : previousData) {
                        dataWriter.write(line);
                        dataWriter.write("\n");
                    }
                }

                System.out.println("Information appended to the existing file.");
            } else {
                // If the file does not exist, create it and save all products
                try (FileWriter dataWriter = new FileWriter(file)) {
                    for (Product product : productList) {
                        String productInfo = getProductInfoString(product);
                        dataWriter.write(productInfo);
                        dataWriter.write("\n");
                    }
                }

                System.out.println("File created, and information saved.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Method to read existing data from the file
    private List<String> readExistingData(File file) throws IOException {
        List<String> previousData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                previousData.add(line);
            }
        }
        return previousData;
    }

    // Method to get a formatted string representing a product
    private String getProductInfoString(Product product) {
        String productType = product.getProductType();
        String productDetails = product.toString();

        return "Product Type: " + productType + productDetails;
    }

    // Method to check data from the file.

    @Override
    public void checkFile(){
        try{
            File savedFile = new File("DataList.txt");
            Scanner fileReader = new Scanner(savedFile);
            while(fileReader.hasNextLine()){
                String dataText = fileReader.nextLine();
                System.out.println(dataText);
            }
            fileReader.close();
        }
        catch(IOException e){
            System.out.println("Error while reading the file.");
            e.printStackTrace();
        }
    }
    @Override
    public void initializeGUI() {
        new ShoppingManagerGUI(this);
    }


    public static void main(String[] args) {
        ShoppingManager sys = new WestminsterShoppingManager();
        boolean exit = false;

        while (!exit){
            exit = sys.runMenu();
        }

    }

}