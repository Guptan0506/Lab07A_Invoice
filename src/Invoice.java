import java.util.ArrayList;

public class Invoice {
    // Fields: Store title, customer Address, Line Items, and the total amount
    private String storeTitle;
    private Address customerAddress;
    private ArrayList<LineItem> lineItems;
    private double totalAmount;

    // Constructor: Initialize the fields
    public Invoice(String storeTitle, Address customerAddress, ArrayList<LineItem> lineItems, double totalAmount) {
        this.storeTitle = storeTitle;
        this.customerAddress = customerAddress;
        this.lineItems = lineItems;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters for each field
    public String getStoreTitle() {
        return storeTitle;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void setStoreTitle(String storeTitle) {
        this.storeTitle = storeTitle;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
