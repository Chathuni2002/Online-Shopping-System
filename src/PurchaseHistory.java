import java.util.Date;

public class PurchaseHistory {
    private Date purchaseDate;
    private double purchaseAmount;

    public PurchaseHistory(Date purchaseDate, double purchaseAmount) {
        this.purchaseDate = purchaseDate;
        this.purchaseAmount = purchaseAmount;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }
}