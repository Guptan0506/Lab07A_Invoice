import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LineItemTableDialog extends JDialog {
    //create a dialog box that will let the user enter the line item information (product name, quantity, and price) and add it to the invoice
    private JTable lineItemTable;
    private DefaultTableModel tableModel;
    private ArrayList<LineItem> lineItems;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton doneBtn;
    private boolean completed;
    private JButton quitBtn;

    public LineItemTableDialog(JFrame parent) {
        super(parent, "Add Line Items", true);
        lineItems = new ArrayList<>();
        completed = false;

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create table with columns
        String[] columnNames = {"Product Name", "Quantity", "Unit Price", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing for first 3 columns, not the total
                return column < 3;
            }
        };

        // Add listener to automatically calculate totals
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    // If quantity (column 1) or unit price (column 2) changed, update total
                    if (column == 1 || column == 2) {
                        updateRowTotal(row);
                    }
                }
            }
        });

        lineItemTable = new JTable(tableModel);
        lineItemTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(lineItemTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("Add Row");
        removeBtn = new JButton("Remove Row");
        doneBtn = new JButton("Done");

        addBtn.addActionListener(e -> addRow());
        removeBtn.addActionListener(e -> removeRow());
        doneBtn.addActionListener(e -> finish());

        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(doneBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateRowTotal(int row) {
        try {
            Object quantityObj = tableModel.getValueAt(row, 1);
            Object priceObj = tableModel.getValueAt(row, 2);

            if (quantityObj == null || priceObj == null) {
                return;
            }

            int quantity = Integer.parseInt(quantityObj.toString());
            double unitPrice = Double.parseDouble(priceObj.toString());
            double total = quantity * unitPrice;

            // Update the total column without triggering the listener again
            tableModel.setValueAt(String.format("%.2f", total), row, 3);
        } catch (NumberFormatException e) {
            // If input is not valid, just skip the calculation
        }
    }

    private void addRow() {
        tableModel.addRow(new Object[]{"", 0, 0.0, 0.0});
    }

    private void removeRow() {
        int selectedRow = lineItemTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void finish() {
        // Convert table data to LineItem objects
        lineItems.clear();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                String productName = (String) tableModel.getValueAt(i, 0);
                int quantity = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                double unitPrice = Double.parseDouble(tableModel.getValueAt(i, 2).toString());

                if (productName == null || productName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product product = new Product(productName, unitPrice);
                LineItem lineItem = new LineItem(product, quantity, unitPrice);
                lineItems.add(lineItem);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number and Unit Price must be a valid decimal", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        completed = true;
        dispose();
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public boolean isCompleted() {
        return completed;
    }
}
