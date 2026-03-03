import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InvoiceGeneratorFrame extends JFrame {
    Invoice thisInvoice;
    JPanel titlePnl;
    JPanel inputPnl;
    JPanel invoiceDisplayPnl;
    JPanel controlPnl;
    JLabel titleLabel;
    JTextArea customerAddressDisplay;
    JTextArea itemsList;
    JButton enterAddressBtn;
    JButton enterLineItemBtn;
    JButton showInvoiceBtn;
    ArrayList<LineItem> lineItems;
    Address customerAddress;


    public InvoiceGeneratorFrame()  {

        setTitle("Invoice Generator");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title Panel
        setLayout(new BorderLayout());
        titlePnl = new JPanel();
        titleLabel = new JLabel("INVOICE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePnl.add(titleLabel);
        add(titlePnl, BorderLayout.NORTH);

        lineItems = new ArrayList<>();
        customerAddress = null;
        createInputPanel();

        setLocationRelativeTo(null); // Center the frame on the screen
        pack();
        setVisible(true);
    }

    private void createInputPanel() {
        inputPnl = new JPanel();
        inputPnl.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Enter Address Button
        enterAddressBtn = new JButton("Enter Address");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPnl.add(enterAddressBtn, gbc);

        // Address display area
        JLabel addressLabel = new JLabel("Customer Address:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPnl.add(addressLabel, gbc);

        customerAddressDisplay = new JTextArea(4, 30);
        customerAddressDisplay.setEditable(false);
        customerAddressDisplay.setLineWrap(true);
        customerAddressDisplay.setWrapStyleWord(true);
        customerAddressDisplay.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane addressScrollPane = new JScrollPane(customerAddressDisplay);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.3;
        inputPnl.add(addressScrollPane, gbc);

        // Enter Line Items Button
        enterLineItemBtn = new JButton("Enter Line Items");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPnl.add(enterLineItemBtn, gbc);

        // Items display area
        JLabel itemsLabel = new JLabel("Line Items:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPnl.add(itemsLabel, gbc);

        itemsList = new JTextArea(8, 30);
        itemsList.setEditable(false);
        itemsList.setLineWrap(true);
        itemsList.setWrapStyleWord(true);
        itemsList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(itemsList);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        inputPnl.add(scrollPane, gbc);

        // Show Invoice Button
        showInvoiceBtn = new JButton("Show Invoice");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPnl.add(showInvoiceBtn, gbc);

        // Button actions
        enterAddressBtn.addActionListener(e -> openAddressDialog());
        enterLineItemBtn.addActionListener(e -> openLineItemDialog());
        showInvoiceBtn.addActionListener(e -> displayInvoice());

        add(inputPnl, BorderLayout.CENTER);
    }

    private void openAddressDialog() {
        AddressDialog dialog = new AddressDialog(this);
        dialog.setVisible(true);

        if (dialog.isCompleted()) {
            customerAddress = dialog.getAddress();
            updateAddressDisplay();
        }
    }

    private void updateAddressDisplay() {
        if (customerAddress != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(customerAddress.getCustomerName()).append("\n");
            sb.append(customerAddress.getStreet()).append("\n");
            sb.append(customerAddress.getCity()).append(", ");
            sb.append(customerAddress.getState()).append(" ");
            sb.append(customerAddress.getZipCode());
            customerAddressDisplay.setText(sb.toString());
        }
    }

    private void openLineItemDialog() {
        LineItemTableDialog dialog = new LineItemTableDialog(this);
        dialog.setVisible(true);

        if (dialog.isCompleted()) {
            lineItems.addAll(dialog.getLineItems());
            updateItemsDisplay();
        }
    }

    private void updateItemsDisplay() {
        StringBuilder sb = new StringBuilder();
        double subtotal = 0;

        for (LineItem item : lineItems) {
            double itemTotal = item.getProduct().getProductPrice() * item.getQuantity();
            subtotal += itemTotal;
            sb.append(String.format("%s x %d @ $%.2f = $%.2f%n",
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getProduct().getProductPrice(),
                itemTotal));
        }

        double tax = subtotal * 0.07;  // 7% tax
        double grandTotal = subtotal + tax;

        sb.append(String.format("%n----%nSubtotal: $%.2f", subtotal));
        sb.append(String.format("%nTax (7%%): $%.2f", tax));
        sb.append(String.format("%nGrand Total: $%.2f", grandTotal));
        itemsList.setText(sb.toString());
    }

    private void displayInvoice() {
        if (customerAddress == null) {
            JOptionPane.showMessageDialog(this, "Please enter customer address first", "No Address", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (lineItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add line items first", "No Items", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calculate subtotal
        double subtotal = 0;
        for (LineItem item : lineItems) {
            subtotal += item.getProduct().getProductPrice() * item.getQuantity();
        }

        // Calculate tax and grand total
        double tax = subtotal * 0.07;  // 7% tax
        double grandTotal = subtotal + tax;

        // Create invoice
        thisInvoice = new Invoice("My Store", customerAddress, lineItems, grandTotal);

        // Display invoice
        StringBuilder invoiceText = new StringBuilder();
        invoiceText.append("=== INVOICE ===\n\n");
        invoiceText.append("Customer: ").append(customerAddress.getCustomerName()).append("\n");
        invoiceText.append("Address: ").append(customerAddress.getStreet()).append(", ")
                   .append(customerAddress.getCity()).append(", ")
                   .append(customerAddress.getState()).append(" ")
                   .append(customerAddress.getZipCode()).append("\n\n");
        invoiceText.append("LINE ITEMS:\n");

        for (LineItem item : lineItems) {
            double itemTotal = item.getProduct().getProductPrice() * item.getQuantity();
            invoiceText.append(String.format("%s x %d @ $%.2f = $%.2f%n",
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getProduct().getProductPrice(),
                itemTotal));
        }

        invoiceText.append(String.format("%n----%nSubtotal: $%.2f", subtotal));
        invoiceText.append(String.format("%nTax (7%%): $%.2f", tax));
        invoiceText.append(String.format("%nGRAND TOTAL: $%.2f", grandTotal));

        JOptionPane.showMessageDialog(this, invoiceText.toString(), "Invoice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InvoiceGeneratorFrame();
        });
    }
}